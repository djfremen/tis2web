/*     */ package com.eoos.scsm.v2.jnlp;
/*     */ 
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.filter.LinkedFilter;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.scsm.v2.util.VersionNumber;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JNLPUtil
/*     */ {
/*  20 */   private static final Pattern PATTERN_SEPARATORS = Pattern.compile("[\\-\\_\\.]");
/*     */   
/*     */   private static String removeSeparators(String string) {
/*  23 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get(string);
/*     */     try {
/*  25 */       Util.delete(tmp, PATTERN_SEPARATORS);
/*  26 */       return tmp.toString();
/*     */     } finally {
/*     */       
/*  29 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Filter createVersionFilter_versionID(String versionID) {
/*  35 */     if (!Util.isNullOrEmpty(versionID)) {
/*  36 */       if (versionID.endsWith("*")) {
/*  37 */         final String prefix = removeSeparators(versionID.substring(0, versionID.length() - 1));
/*  38 */         return new Filter()
/*     */           {
/*     */             public boolean include(Object obj) {
/*  41 */               String versionNumber = ((VersionNumber)obj).toString();
/*  42 */               return JNLPUtil.removeSeparators(versionNumber).startsWith(prefix);
/*     */             }
/*     */           };
/*     */       } 
/*  46 */       if (versionID.endsWith("+")) {
/*  47 */         String version = versionID.substring(0, versionID.length() - 1);
/*  48 */         final VersionNumber minimalVersion = Util.parseVersionNumber(version);
/*  49 */         return new Filter()
/*     */           {
/*     */             public boolean include(Object obj) {
/*  52 */               VersionNumber versionNumber = (VersionNumber)obj;
/*  53 */               return !Util.isLower((Comparable)versionNumber, (Comparable)minimalVersion);
/*     */             }
/*     */           };
/*     */       } 
/*     */       
/*  58 */       final VersionNumber versionNumber = Util.normalizeVersionNumber(Util.parseVersionNumber(versionID), 10);
/*     */       
/*  60 */       return new Filter()
/*     */         {
/*     */           public boolean include(Object obj)
/*     */           {
/*  64 */             return Util.equals(versionNumber, Util.normalizeVersionNumber((VersionNumber)obj, 10));
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Filter createVersionFilter_versionRange(String versionRange) {
/*  76 */     if (!Util.isNullOrEmpty(versionRange)) {
/*  77 */       Collection<Filter> ANDedFilters = new HashSet();
/*  78 */       String[] versionIDs = versionRange.split("\\&");
/*  79 */       for (int j = 0; j < versionIDs.length; j++) {
/*  80 */         Filter filterVersionID = createVersionFilter_versionID(versionIDs[j]);
/*  81 */         if (filterVersionID != null) {
/*  82 */           ANDedFilters.add(filterVersionID);
/*     */         }
/*     */       } 
/*  85 */       if (!Util.isNullOrEmpty(ANDedFilters)) {
/*  86 */         return (Filter)new LinkedFilter(ANDedFilters, LinkedFilter.AND);
/*     */       }
/*     */     } 
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Filter createVersionFilter_versionString(String versionString) {
/*  94 */     if (!Util.isNullOrEmpty(versionString)) {
/*  95 */       String[] versionRanges = versionString.split("\\s+");
/*  96 */       Collection<Filter> ORedFilters = new HashSet();
/*  97 */       for (int i = 0; i < versionRanges.length; i++) {
/*  98 */         Filter rangeFilter = createVersionFilter_versionRange(versionRanges[i]);
/*  99 */         if (rangeFilter != null) {
/* 100 */           ORedFilters.add(rangeFilter);
/*     */         }
/*     */       } 
/* 103 */       if (!Util.isNullOrEmpty(ORedFilters)) {
/* 104 */         return (Filter)new LinkedFilter(ORedFilters, LinkedFilter.OR);
/*     */       }
/*     */     } 
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MIME getMIME(String name) {
/* 112 */     name = name.toLowerCase(Locale.ENGLISH);
/* 113 */     if (name.endsWith(".jar"))
/* 114 */       return MIME.JAR; 
/* 115 */     if (name.endsWith(".jpg") || name.endsWith(".jpeg"))
/* 116 */       return MIME.JPG; 
/* 117 */     if (name.endsWith(".gif"))
/* 118 */       return MIME.GIF; 
/* 119 */     if (name.endsWith(".jnlp")) {
/* 120 */       return MIME.JNLP;
/*     */     }
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 127 */     String requestedVersion = "1.3 1.4*&1.4.3+ 2.1*&2.1.0.4+ 3+";
/*     */     
/* 129 */     Filter filter = createVersionFilter_versionString(requestedVersion);
/*     */     
/* 131 */     VersionNumber vn = Util.parseVersionNumber("1.4.2");
/* 132 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 134 */     vn = Util.parseVersionNumber("1.4.3");
/* 135 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 137 */     vn = Util.parseVersionNumber("1.4.4.4");
/* 138 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 140 */     vn = Util.parseVersionNumber("1.4.5");
/* 141 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 143 */     vn = Util.parseVersionNumber("1.3");
/* 144 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 146 */     vn = Util.parseVersionNumber("2.0.4");
/* 147 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */     
/* 149 */     vn = Util.parseVersionNumber("2.1.0");
/* 150 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/* 151 */     vn = Util.parseVersionNumber("2.1.0.5.a");
/* 152 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/* 153 */     vn = Util.parseVersionNumber("5.0");
/* 154 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/* 155 */     vn = Util.parseVersionNumber("1.3.0.1");
/* 156 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/* 157 */     vn = Util.parseVersionNumber("1.3.0.0");
/* 158 */     System.out.println("includes  " + vn + ": " + filter.include(vn));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\jnlp\JNLPUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */