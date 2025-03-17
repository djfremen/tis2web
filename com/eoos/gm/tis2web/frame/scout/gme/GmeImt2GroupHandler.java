/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GmeImt2GroupHandler
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger(GmeImt2GroupHandler.class);
/*     */   
/*     */   private List candidates;
/*     */   
/*     */   public GmeImt2Candidate getGroupMapWinner(Map params, List<?> groupMapData) {
/*  21 */     GmeImt2Candidate result = null;
/*  22 */     String countryCode = (String)params.get("country");
/*  23 */     String manufacturers = (String)params.get("manufacturers");
/*  24 */     String portalGroup = (String)params.get("group");
/*  25 */     this.candidates = new ArrayList(groupMapData);
/*  26 */     filterCandidatesByPortalGroup(portalGroup);
/*  27 */     filterCandidatesByCountryCode(countryCode);
/*  28 */     filterCandidatesByManufacturers(manufacturers);
/*  29 */     if (log.isDebugEnabled() && this.candidates != null) {
/*  30 */       Iterator<GmeImt2Candidate> it = this.candidates.iterator();
/*  31 */       while (it.hasNext()) {
/*  32 */         GmeImt2Candidate cd = it.next();
/*  33 */         log.debug("Verified candidate (country=" + countryCode + " manufacturers=" + manufacturers + "): " + cd.toString());
/*     */       } 
/*     */     } 
/*  36 */     result = getFinalCandidate();
/*  37 */     return result;
/*     */   }
/*     */   
/*     */   private void filterCandidatesByPortalGroup(String grp) {
/*  41 */     if (this.candidates != null) {
/*  42 */       Iterator<GmeImt2Candidate> it = this.candidates.iterator();
/*  43 */       while (it.hasNext()) {
/*  44 */         GmeImt2Candidate candidate = it.next();
/*  45 */         if (!candidate.passedPortalGroupCheck(grp)) {
/*  46 */           log.debug("Removing due to group mismatch: " + candidate.toString());
/*  47 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterCandidatesByCountryCode(String countryCode) {
/*  54 */     if (this.candidates != null) {
/*  55 */       Iterator<GmeImt2Candidate> it = this.candidates.iterator();
/*  56 */       while (it.hasNext()) {
/*  57 */         GmeImt2Candidate candidate = it.next();
/*  58 */         if (!candidate.passedCountryCodeCheck(countryCode)) {
/*  59 */           log.debug("Removing due to countryCode mismatch: " + candidate.toString());
/*  60 */           it.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterCandidatesByManufacturers(String manufacturers) {
/*  67 */     if (this.candidates != null) {
/*  68 */       Iterator<GmeImt2Candidate> it = this.candidates.iterator();
/*  69 */       while (it.hasNext()) {
/*  70 */         GmeImt2Candidate candidate = it.next();
/*  71 */         if (!candidate.passedManufacturerCheck(manufacturers)) {
/*  72 */           log.debug("Removing due to manufacturer mismatch: " + candidate.toString());
/*  73 */           it.remove();
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
/*     */   private GmeImt2Candidate getFinalCandidate() {
/*  88 */     GmeImt2Candidate result = null;
/*  89 */     if (!Util.isNullOrEmpty(this.candidates)) {
/*  90 */       Collections.sort(this.candidates, new Comparator() {
/*     */             public int compare(Object o1, Object o2) {
/*  92 */               GmeImt2GroupHandler.log.debug("Performing priority 1 evaluation.");
/*  93 */               int p1 = ((GmeImt2Candidate)o1).getPriority1();
/*  94 */               int p2 = ((GmeImt2Candidate)o2).getPriority1();
/*  95 */               return p2 - p1;
/*     */             }
/*     */           });
/*     */       
/*  99 */       result = this.candidates.get(0);
/*     */     } else {
/* 101 */       log.debug("No candidates left");
/*     */     } 
/*     */     
/* 104 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeImt2GroupHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */