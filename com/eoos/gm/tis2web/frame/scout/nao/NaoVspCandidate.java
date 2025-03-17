/*     */ package com.eoos.gm.tis2web.frame.scout.nao;
/*     */ 
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.regexp.RE;
/*     */ 
/*     */ public class NaoVspCandidate
/*     */ {
/*   8 */   private static final Logger log = Logger.getLogger(NaoVspCandidate.class);
/*     */   
/*     */   protected static final String WILDCARD = "*";
/*     */   private static final String NORMALIZED_WILDCARD = ".*";
/*     */   private static final int PREFIX_WILDCARD = 1;
/*     */   private static final int REQ_GROUP_WILDCARD = 2;
/*     */   private static final int POSTFIX_WILDCARD = 4;
/*     */   private static final int ID_FROM_WILDCARD = 8;
/*     */   private static final int ID_TO_WILDCARD = 16;
/*     */   private static final int PREFIX_WEIGHT = 1;
/*     */   private static final int REQUIRED_GROUP_WEIGHT = 2;
/*     */   private static final int POSTFIX_WEIGHT = 4;
/*     */   private static final int BAC_LENGTH = 11;
/*     */   private String prefix;
/*     */   private String requiredGroup;
/*     */   private String idFrom;
/*     */   private String idTo;
/*     */   private String postfix;
/*     */   private String countryCode;
/*     */   private String division;
/*     */   private String tis2webGroup;
/*     */   private String bacCode;
/*     */   private String defaultBacCode;
/*     */   
/*     */   public NaoVspCandidate(String prefix, String requiredGroup, String idFrom, String idTo, String postfix, String countryCode, String division, String tis2webGroup, String defaultBacCode) {
/*  33 */     this.prefix = normalize(prefix);
/*  34 */     this.requiredGroup = normalize(requiredGroup);
/*  35 */     this.idFrom = normalize(idFrom);
/*  36 */     this.idTo = normalize(idTo);
/*  37 */     this.postfix = normalize(postfix);
/*  38 */     this.countryCode = countryCode;
/*  39 */     this.division = division;
/*  40 */     this.tis2webGroup = tis2webGroup;
/*  41 */     this.defaultBacCode = defaultBacCode;
/*     */   }
/*     */   
/*     */   public boolean isCandidateFor(String group) {
/*  45 */     boolean result = false;
/*  46 */     String pattern = this.prefix + ".*" + this.requiredGroup + ".*" + this.postfix;
/*     */     try {
/*  48 */       RE exp = new RE(pattern);
/*  49 */       result = exp.match(group);
/*  50 */     } catch (Exception e) {
/*  51 */       log.error("Wrong pattern. Check table 'sc.group_map'.");
/*     */     } 
/*  53 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedBacCheck(String portalGroup) {
/*  57 */     boolean result = false;
/*  58 */     this.bacCode = getBacCode(portalGroup);
/*  59 */     if (!needBacCode()) {
/*  60 */       result = true;
/*     */     } else {
/*  62 */       result = verifyBacCode(this.bacCode);
/*     */     } 
/*  64 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedCountryCodeCheck(String countryCode) {
/*  68 */     boolean result = false;
/*  69 */     if (this.countryCode.compareTo("*") == 0 || (countryCode != null && this.countryCode.compareTo(countryCode) == 0)) {
/*  70 */       result = true;
/*     */     }
/*  72 */     return result;
/*     */   }
/*     */   
/*     */   public boolean passedDivisionCheck(String divisions) {
/*  76 */     boolean result = false;
/*  77 */     if (this.division.compareTo("*") == 0 || (divisions != null && divisions.indexOf(this.division) >= 0)) {
/*  78 */       result = true;
/*     */     }
/*  80 */     return result;
/*     */   }
/*     */   
/*     */   public String getT2WGroup() {
/*  84 */     return this.tis2webGroup;
/*     */   }
/*     */   
/*     */   public String getBacCode() {
/*  88 */     String result = null;
/*  89 */     if (this.bacCode != null) {
/*  90 */       result = this.bacCode;
/*     */     } else {
/*  92 */       result = this.defaultBacCode;
/*     */     } 
/*  94 */     return result;
/*     */   }
/*     */   
/*     */   public int getPriority1() {
/*  98 */     int result = 0;
/*  99 */     if (hasPrefixWildcard()) {
/* 100 */       result |= 0x1;
/*     */     }
/*     */     
/* 103 */     if (hasRequiredGroupWildcard()) {
/* 104 */       result |= 0x2;
/*     */     }
/*     */     
/* 107 */     if (hasPostfixWildcard()) {
/* 108 */       result |= 0x4;
/*     */     }
/*     */     
/* 111 */     if (hasIdFromWildcard()) {
/* 112 */       result |= 0x8;
/*     */     }
/*     */     
/* 115 */     if (hasIdToWildcard()) {
/* 116 */       result |= 0x10;
/*     */     }
/*     */     
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPriority2(String prefix, String requiredGroup, String postfix) {
/* 124 */     int result = 0;
/* 125 */     if (this.prefix.compareTo(prefix) < 0) {
/* 126 */       result |= 0x1;
/*     */     }
/* 128 */     if (this.requiredGroup.compareTo(requiredGroup) < 0) {
/* 129 */       result |= 0x2;
/*     */     }
/* 131 */     if (this.postfix.compareTo(postfix) < 0) {
/* 132 */       result |= 0x4;
/*     */     }
/* 134 */     return result;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 138 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public String getRequiredGroup() {
/* 142 */     return this.requiredGroup;
/*     */   }
/*     */   
/*     */   public String getPostfix() {
/* 146 */     return this.postfix;
/*     */   }
/*     */   
/*     */   public boolean isCountryCodeWildcard() {
/* 150 */     boolean result = false;
/* 151 */     if (this.countryCode != null && this.countryCode.compareTo("*") == 0) {
/* 152 */       result = true;
/*     */     }
/* 154 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isDivisionWildcard() {
/* 158 */     boolean result = false;
/* 159 */     if (this.division != null && this.division.compareTo("*") == 0) {
/* 160 */       result = true;
/*     */     }
/* 162 */     return result;
/*     */   }
/*     */   
/*     */   public boolean matchesCountryCode(String countryCode) {
/* 166 */     boolean result = false;
/* 167 */     if (this.countryCode != null && countryCode != null && this.countryCode.compareTo(countryCode) == 0) {
/* 168 */       result = true;
/*     */     }
/* 170 */     return result;
/*     */   }
/*     */   
/*     */   public boolean matchesDivision(String divisions) {
/* 174 */     boolean result = false;
/* 175 */     if (divisions != null && this.division != null && divisions.indexOf(this.division) >= 0) {
/* 176 */       result = true;
/*     */     }
/* 178 */     return result;
/*     */   }
/*     */   
/*     */   public boolean isExactMatch(String countryCode, String divisions) {
/* 182 */     boolean result = false;
/* 183 */     if (countryCode != null && divisions != null && matchesCountryCode(countryCode) && matchesDivision(divisions)) {
/* 184 */       result = true;
/*     */     }
/* 186 */     return result;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 190 */     return "(" + this.prefix + ", " + this.requiredGroup + ", " + this.idFrom + ", " + this.idTo + ", " + this.postfix + ", " + this.countryCode + ", " + this.division + ", " + this.tis2webGroup + ", " + this.defaultBacCode + ")";
/*     */   }
/*     */   
/*     */   private String normalize(String param) {
/* 194 */     String result = param;
/* 195 */     if (param != null) {
/* 196 */       StringBuffer strbuf = new StringBuffer(param);
/* 197 */       for (int i = 0; i < strbuf.length(); i++) {
/* 198 */         char c = strbuf.charAt(i);
/* 199 */         if (c == '*') {
/* 200 */           strbuf.insert(i, '.');
/* 201 */           i++;
/*     */         } 
/*     */       } 
/* 204 */       result = strbuf.toString();
/*     */     } 
/* 206 */     return result;
/*     */   }
/*     */   
/*     */   private String getBacCode(String group) {
/* 210 */     String result = null;
/* 211 */     String pattern = this.prefix + ".*" + this.requiredGroup + "/";
/*     */     try {
/* 213 */       RE exp = new RE(pattern);
/* 214 */       exp.match(group);
/* 215 */       String first = exp.getParen(0);
/* 216 */       int i = first.length();
/* 217 */       int j = group.indexOf('/', first.length());
/* 218 */       String bacString = null;
/* 219 */       if (j >= 0) {
/* 220 */         bacString = group.substring(i, j);
/*     */       } else {
/* 222 */         bacString = group.substring(i, group.length());
/*     */       } 
/* 224 */       if (bacString.length() == 11) {
/* 225 */         result = bacString;
/*     */       }
/* 227 */     } catch (Exception e) {
/* 228 */       log.debug("No BAC Code found");
/*     */     } 
/* 230 */     return result;
/*     */   }
/*     */   
/*     */   private boolean needBacCode() {
/* 234 */     return (this.idFrom.compareTo(".*") != 0 || this.idTo.compareTo(".*") != 0);
/*     */   }
/*     */   
/*     */   private boolean verifyBacCode(String bacCode) {
/* 238 */     boolean result = false;
/*     */     
/*     */     try {
/* 241 */       long from, to, bac = Long.valueOf(bacCode).longValue();
/* 242 */       if (this.idFrom.compareTo(".*") != 0) {
/* 243 */         from = Long.valueOf(this.idFrom).longValue();
/*     */       } else {
/* 245 */         from = Long.MIN_VALUE;
/*     */       } 
/* 247 */       if (this.idTo.compareTo(".*") != 0) {
/* 248 */         to = Long.valueOf(this.idTo).longValue();
/*     */       } else {
/* 250 */         to = Long.MAX_VALUE;
/*     */       } 
/* 252 */       if (from <= bac && bac <= to) {
/* 253 */         result = true;
/*     */       }
/* 255 */     } catch (Exception e) {
/* 256 */       if (bacCode != null) {
/* 257 */         log.error("Cannot verify BAC Code: " + bacCode);
/*     */       }
/*     */     } 
/* 260 */     return result;
/*     */   }
/*     */   
/*     */   private boolean hasPrefixWildcard() {
/* 264 */     return (this.prefix.compareTo(".*") == 0);
/*     */   }
/*     */   
/*     */   private boolean hasRequiredGroupWildcard() {
/* 268 */     return (this.requiredGroup.compareTo("*") == 0);
/*     */   }
/*     */   
/*     */   private boolean hasPostfixWildcard() {
/* 272 */     return (this.postfix.compareTo(".*") == 0);
/*     */   }
/*     */   
/*     */   private boolean hasIdFromWildcard() {
/* 276 */     return (this.idFrom.compareTo(".*") == 0);
/*     */   }
/*     */   
/*     */   private boolean hasIdToWildcard() {
/* 280 */     return (this.idTo.compareTo(".*") == 0);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\NaoVspCandidate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */