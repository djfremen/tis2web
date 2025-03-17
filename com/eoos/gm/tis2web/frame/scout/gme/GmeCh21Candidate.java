/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GmeCh21Candidate
/*     */ {
/*     */   protected static final String WILDCARD = "*";
/*     */   private static final int COUNTRY_MATCH_WEIGHT = 1;
/*     */   private static final int DIVISIONS_WILDCARD_WEIGHT = 10;
/*     */   private String countryCode;
/*     */   private Set divisions;
/*     */   private String tis2webGroup;
/*     */   private String defaultBacCode;
/*     */   
/*     */   public GmeCh21Candidate(String countryCode, String divisions, String tis2webGroup, String defaultBacCode) {
/*  21 */     this.countryCode = countryCode;
/*  22 */     this.divisions = new HashSet();
/*  23 */     StringTokenizer st = new StringTokenizer(divisions, ",;");
/*  24 */     while (st.hasMoreTokens()) {
/*  25 */       this.divisions.add(st.nextToken());
/*     */     }
/*  27 */     this.tis2webGroup = tis2webGroup;
/*  28 */     this.defaultBacCode = defaultBacCode;
/*     */   }
/*     */   
/*     */   public boolean passedCountryCodeCheck(String countryCode) {
/*  32 */     boolean result = false;
/*  33 */     if (this.countryCode.compareTo("*") == 0 || (countryCode != null && this.countryCode.compareTo(countryCode) == 0)) {
/*  34 */       result = true;
/*     */     }
/*  36 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedDivisionCheck(String divisions) {
/*  40 */     boolean result = false;
/*  41 */     Set<String> providedDivisions = new HashSet();
/*  42 */     if (divisions != null) {
/*  43 */       StringTokenizer st = new StringTokenizer(divisions, ",;");
/*  44 */       while (st.hasMoreTokens()) {
/*  45 */         providedDivisions.add(st.nextToken());
/*     */       }
/*     */     } 
/*  48 */     if ((this.divisions.contains("*") && this.divisions.size() == 1) || providedDivisions.containsAll(this.divisions)) {
/*  49 */       result = true;
/*     */     }
/*  51 */     return result;
/*     */   }
/*     */   
/*     */   public String getT2WGroup() {
/*  55 */     return this.tis2webGroup;
/*     */   }
/*     */   
/*     */   public int getPriority1() {
/*  59 */     int result = 0;
/*  60 */     if (!isCountryCodeWildcard()) {
/*  61 */       result++;
/*     */     }
/*  63 */     if (isDivisionWildcard()) {
/*  64 */       result += 10;
/*     */     } else {
/*  66 */       result += this.divisions.size() * 10;
/*     */     } 
/*  68 */     if (!isCountryCodeWildcard()) {
/*  69 */       result++;
/*     */     }
/*  71 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isCountryCodeWildcard() {
/*  75 */     boolean result = false;
/*  76 */     if (this.countryCode != null && this.countryCode.compareTo("*") == 0) {
/*  77 */       result = true;
/*     */     }
/*  79 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isDivisionWildcard() {
/*  83 */     boolean result = false;
/*  84 */     if (this.divisions.contains("*") && this.divisions.size() == 1) {
/*  85 */       result = true;
/*     */     }
/*  87 */     return result;
/*     */   }
/*     */   
/*     */   public boolean matchesCountryCode(String countryCode) {
/*  91 */     boolean result = false;
/*  92 */     if (this.countryCode != null && countryCode != null && this.countryCode.compareTo(countryCode) == 0) {
/*  93 */       result = true;
/*     */     }
/*  95 */     return result;
/*     */   }
/*     */   
/*     */   public String getBacCode() {
/*  99 */     return this.defaultBacCode;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 103 */     return "(" + this.countryCode + ", " + this.divisions + ", " + this.tis2webGroup + ", " + this.defaultBacCode + ")";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeCh21Candidate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */