/*    */ package com.eoos.gm.tis2web.frame.scout.gme;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GmeCh21GroupHandler
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(GmeCh21GroupHandler.class);
/*    */   
/*    */   private List candidates;
/*    */   
/*    */   public GmeCh21Candidate getGroupMapWinner(Map params, List<?> groupMapData) {
/* 21 */     GmeCh21Candidate result = null;
/* 22 */     String countryCode = (String)params.get("country");
/* 23 */     String divisions = (String)params.get("divisions");
/* 24 */     this.candidates = new ArrayList(groupMapData);
/* 25 */     filterCandidatesByCountryCode(countryCode);
/* 26 */     filterCandidatesByDivision(divisions);
/* 27 */     if (log.isDebugEnabled() && this.candidates != null) {
/* 28 */       Iterator<GmeCh21Candidate> it = this.candidates.iterator();
/* 29 */       while (it.hasNext()) {
/* 30 */         GmeCh21Candidate cd = it.next();
/* 31 */         log.debug("Verified candidate (country=" + countryCode + " divisions=" + divisions + "): " + cd.toString());
/*    */       } 
/*    */     } 
/* 34 */     result = getFinalCandidate();
/* 35 */     return result;
/*    */   }
/*    */   
/*    */   private void filterCandidatesByCountryCode(String countryCode) {
/* 39 */     if (this.candidates != null) {
/* 40 */       Iterator<GmeCh21Candidate> it = this.candidates.iterator();
/* 41 */       while (it.hasNext()) {
/* 42 */         GmeCh21Candidate candidate = it.next();
/* 43 */         if (!candidate.passedCountryCodeCheck(countryCode)) {
/* 44 */           log.debug("Removing due to countryCode mismatch: " + candidate.toString());
/* 45 */           it.remove();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void filterCandidatesByDivision(String divisions) {
/* 52 */     if (this.candidates != null) {
/* 53 */       Iterator<GmeCh21Candidate> it = this.candidates.iterator();
/* 54 */       while (it.hasNext()) {
/* 55 */         GmeCh21Candidate candidate = it.next();
/* 56 */         if (!candidate.passedDivisionCheck(divisions)) {
/* 57 */           log.debug("Removing due to division mismatch: " + candidate.toString());
/* 58 */           it.remove();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private GmeCh21Candidate getFinalCandidate() {
/* 73 */     GmeCh21Candidate result = null;
/* 74 */     if (!Util.isNullOrEmpty(this.candidates)) {
/* 75 */       Collections.sort(this.candidates, new Comparator() {
/*    */             public int compare(Object o1, Object o2) {
/* 77 */               GmeCh21GroupHandler.log.debug("Performing priority 1 evaluation.");
/* 78 */               int p1 = ((GmeCh21Candidate)o1).getPriority1();
/* 79 */               int p2 = ((GmeCh21Candidate)o2).getPriority1();
/* 80 */               return p2 - p1;
/*    */             }
/*    */           });
/*    */       
/* 84 */       result = this.candidates.get(0);
/*    */     } else {
/* 86 */       log.debug("no candidates left");
/*    */     } 
/* 88 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeCh21GroupHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */