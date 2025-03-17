/*     */ package com.eoos.gm.tis2web.frame.scout.gme;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GmeImt2Candidate
/*     */ {
/*     */   protected static final String WILDCARD = "*";
/*     */   private static final int COUNTRY_MATCH_WEIGHT = 1;
/*     */   private static final int MANUFACTURER_WILDCARD_WEIGHT = 10;
/*     */   private String countryCode;
/*     */   private Set manufacturers;
/*     */   private String portalGroup;
/*     */   private String tis2webGroup;
/*     */   private String defaultBAC;
/*  23 */   private static final Logger log = Logger.getLogger(GmeImt2Candidate.class);
/*     */   
/*     */   public GmeImt2Candidate(String country, String manus, String portalGrp, String tis2webGrp, String defaultBac) {
/*  26 */     this.countryCode = country;
/*  27 */     this.manufacturers = new HashSet();
/*  28 */     StringTokenizer st = new StringTokenizer(manus, ",;");
/*  29 */     while (st.hasMoreTokens()) {
/*  30 */       this.manufacturers.add(st.nextToken());
/*     */     }
/*  32 */     this.portalGroup = portalGrp;
/*  33 */     this.tis2webGroup = tis2webGrp;
/*  34 */     this.defaultBAC = defaultBac;
/*     */   }
/*     */   
/*     */   public boolean passedPortalGroupCheck(String group) {
/*  38 */     boolean result = false;
/*  39 */     if (this.portalGroup != null && group != null && this.portalGroup.compareTo(group) == 0) {
/*  40 */       result = true;
/*     */     }
/*  42 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedCountryCodeCheck(String countryCode) {
/*  46 */     boolean result = false;
/*  47 */     if (this.countryCode.compareTo("*") == 0 || (countryCode != null && this.countryCode.compareTo(countryCode) == 0)) {
/*  48 */       result = true;
/*     */     }
/*  50 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedManufacturerCheck(String manufacturers) {
/*  54 */     boolean result = false;
/*  55 */     Set<String> providedManufacturers = new HashSet();
/*  56 */     if (manufacturers != null) {
/*  57 */       StringTokenizer st = new StringTokenizer(manufacturers, ",;");
/*  58 */       while (st.hasMoreTokens()) {
/*  59 */         providedManufacturers.add(st.nextToken());
/*     */       }
/*     */     } 
/*  62 */     if ((this.manufacturers.contains("*") && this.manufacturers.size() == 1) || providedManufacturers.containsAll(this.manufacturers)) {
/*  63 */       result = true;
/*     */     }
/*  65 */     return result;
/*     */   }
/*     */   
/*     */   public String getT2WGroup() {
/*  69 */     return this.tis2webGroup;
/*     */   }
/*     */   
/*     */   public int getPriority1() {
/*  73 */     int result = 0;
/*  74 */     if (!isCountryCodeWildcard()) {
/*  75 */       result++;
/*     */     }
/*  77 */     if (isManufacturerWildcard()) {
/*  78 */       result += 10;
/*     */     } else {
/*  80 */       result += this.manufacturers.size() * 10;
/*     */     } 
/*  82 */     if (!isCountryCodeWildcard()) {
/*  83 */       result++;
/*     */     }
/*  85 */     log.debug("priority1 = " + result + " manufacturers: " + this.manufacturers);
/*  86 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isCountryCodeWildcard() {
/*  90 */     boolean result = false;
/*  91 */     if (this.countryCode != null && this.countryCode.compareTo("*") == 0) {
/*  92 */       result = true;
/*     */     }
/*  94 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isManufacturerWildcard() {
/*  98 */     boolean result = false;
/*  99 */     if (this.manufacturers.contains("*") && this.manufacturers.size() == 1) {
/* 100 */       result = true;
/*     */     }
/* 102 */     return result;
/*     */   }
/*     */   
/*     */   public boolean matchesCountryCode(String countryCode) {
/* 106 */     boolean result = false;
/* 107 */     if (this.countryCode != null && countryCode != null && this.countryCode.compareTo(countryCode) == 0) {
/* 108 */       result = true;
/*     */     }
/* 110 */     return result;
/*     */   }
/*     */   
/*     */   public String getBacCode() {
/* 114 */     return this.defaultBAC;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 118 */     return "(" + this.countryCode + ", " + this.manufacturers + ", " + this.tis2webGroup + ", " + this.defaultBAC + ")";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeImt2Candidate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */