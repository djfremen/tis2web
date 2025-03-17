/*     */ package com.eoos.gm.tis2web.frame.scout.nao;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NaoVspGroupHandler
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(NaoVspGroupHandler.class);
/*     */   
/*     */   private List candidates;
/*     */   
/*     */   public NaoVspCandidate getGroupMapWinner(Map params, List groupMapData) {
/*  25 */     NaoVspCandidate result = null;
/*  26 */     String pGroups = (String)params.get("group");
/*  27 */     String countryCode = (String)params.get("country");
/*  28 */     String divisions = (String)params.get("divisions");
/*  29 */     if (pGroups != null) {
/*  30 */       StringTokenizer st = new StringTokenizer(pGroups, ",;");
/*     */       
/*  32 */       while (st.hasMoreTokens()) {
/*  33 */         String portalGroup = trim(st.nextToken());
/*  34 */         this.candidates = getCandidates(portalGroup, groupMapData);
/*  35 */         filterCandidatesByBAC(portalGroup);
/*  36 */         filterCandidatesByCountryCode(countryCode);
/*  37 */         filterCandidatesByDivision(divisions);
/*  38 */         finalizeCandidates(countryCode, divisions);
/*  39 */         if (log.isDebugEnabled() && this.candidates != null) {
/*  40 */           Iterator<NaoVspCandidate> it = this.candidates.iterator();
/*  41 */           while (it.hasNext()) {
/*  42 */             NaoVspCandidate cd = it.next();
/*  43 */             log.debug("Verified candidate (country=" + countryCode + " divisions=" + divisions + "): " + cd.toString());
/*     */           } 
/*     */         } 
/*  46 */         result = getFinalCandidate();
/*  47 */         if (result != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*  52 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private List getCandidates(final String portalGroup, List<?> groupMapData) {
/*     */     List list;
/*     */     try {
/*  59 */       list = new ArrayList(groupMapData);
/*     */       
/*  61 */       CollectionUtil.filter(list, new Filter()
/*     */           {
/*     */             public boolean include(Object obj) {
/*  64 */               return ((NaoVspCandidate)obj).isCandidateFor(portalGroup);
/*     */             }
/*     */           });
/*     */     }
/*  68 */     catch (Exception e) {
/*  69 */       list = Collections.EMPTY_LIST;
/*  70 */       log.error("Cannot get candidates: " + e);
/*     */     } 
/*     */     
/*  73 */     return list;
/*     */   }
/*     */   
/*     */   private void filterCandidatesByBAC(String portalGroup) {
/*  77 */     if (this.candidates != null) {
/*  78 */       Iterator<NaoVspCandidate> it = this.candidates.iterator();
/*  79 */       while (it.hasNext()) {
/*  80 */         NaoVspCandidate candidate = it.next();
/*  81 */         if (!candidate.passedBacCheck(portalGroup)) {
/*  82 */           log.debug("Removing due to BAC mismatch: " + candidate.toString());
/*  83 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterCandidatesByCountryCode(String countryCode) {
/*  90 */     if (this.candidates != null) {
/*  91 */       Iterator<NaoVspCandidate> it = this.candidates.iterator();
/*  92 */       while (it.hasNext()) {
/*  93 */         NaoVspCandidate candidate = it.next();
/*  94 */         if (!candidate.passedCountryCodeCheck(countryCode)) {
/*  95 */           log.debug("Removing due to countryCode mismatch: " + candidate.toString());
/*  96 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterCandidatesByDivision(String divisions) {
/* 103 */     if (this.candidates != null) {
/* 104 */       Iterator<NaoVspCandidate> it = this.candidates.iterator();
/* 105 */       while (it.hasNext()) {
/* 106 */         NaoVspCandidate candidate = it.next();
/* 107 */         if (!candidate.passedDivisionCheck(divisions)) {
/* 108 */           log.debug("Removing due to division mismatch: " + candidate.toString());
/* 109 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void finalizeCandidates(String countryCode, String divisions) {
/* 116 */     if (this.candidates != null) {
/* 117 */       boolean exactMatches = false;
/* 118 */       boolean ccMatches = false;
/* 119 */       boolean dMatches = false;
/*     */       
/* 121 */       Iterator<NaoVspCandidate> it = this.candidates.iterator();
/*     */       
/* 123 */       while (it.hasNext()) {
/* 124 */         NaoVspCandidate candidate = it.next();
/* 125 */         if (candidate.isExactMatch(countryCode, divisions)) {
/* 126 */           exactMatches = true; continue;
/* 127 */         }  if (candidate.matchesCountryCode(countryCode) && candidate.isDivisionWildcard()) {
/* 128 */           ccMatches = true; continue;
/* 129 */         }  if (candidate.isCountryCodeWildcard() && candidate.matchesDivision(divisions)) {
/* 130 */           dMatches = true;
/*     */         }
/*     */       } 
/* 133 */       it = this.candidates.iterator();
/* 134 */       while (it.hasNext()) {
/* 135 */         NaoVspCandidate candidate = it.next();
/* 136 */         if (exactMatches) {
/* 137 */           if (!candidate.isExactMatch(countryCode, divisions)) {
/* 138 */             log.debug("Removing due to countryCode/division mismatch: " + candidate.toString());
/* 139 */             it.remove();
/*     */           }  continue;
/* 141 */         }  if (ccMatches) {
/* 142 */           if (!candidate.matchesCountryCode(countryCode) || !candidate.isDivisionWildcard()) {
/* 143 */             log.debug("Removing due to countryCode mismatch: " + candidate.toString());
/* 144 */             it.remove();
/*     */           }  continue;
/* 146 */         }  if (dMatches && (
/* 147 */           !candidate.isCountryCodeWildcard() || !candidate.matchesDivision(divisions))) {
/* 148 */           log.debug("Removing due to division mismatch: " + candidate.toString());
/* 149 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NaoVspCandidate getFinalCandidate() {
/* 168 */     NaoVspCandidate result = null;
/* 169 */     if (!Util.isNullOrEmpty(this.candidates)) {
/* 170 */       Collections.sort(this.candidates, new Comparator() { public int compare(Object o1, Object o2) {
/*     */               int result;
/* 172 */               NaoVspGroupHandler.log.debug("Performing priority 1 evaluation.");
/* 173 */               int p1 = ((NaoVspCandidate)o1).getPriority1();
/* 174 */               int p2 = ((NaoVspCandidate)o2).getPriority1();
/*     */               
/* 176 */               if (p1 < p2) {
/* 177 */                 result = -1;
/* 178 */               } else if (p1 > p2) {
/* 179 */                 result = 1;
/*     */               } else {
/* 181 */                 NaoVspGroupHandler.log.debug("Performing priority 2 evaluation.");
/* 182 */                 p1 = ((NaoVspCandidate)o1).getPriority2(((NaoVspCandidate)o2).getPrefix(), ((NaoVspCandidate)o2).getRequiredGroup(), ((NaoVspCandidate)o2).getPostfix());
/* 183 */                 result = (p1 < 0) ? -1 : ((p1 == 0) ? 0 : 1);
/*     */               } 
/* 185 */               return result;
/*     */             } }
/*     */         );
/*     */       
/* 189 */       result = this.candidates.get(0);
/*     */     } else {
/* 191 */       log.debug("No candidates left");
/*     */     } 
/*     */     
/* 194 */     return result;
/*     */   }
/*     */   
/*     */   private String trim(String str) {
/* 198 */     String result = str;
/* 199 */     if (str.startsWith("\"") && str.endsWith("\"")) {
/* 200 */       result = str.substring(1, str.length() - 1);
/*     */     }
/* 202 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\NaoVspGroupHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */