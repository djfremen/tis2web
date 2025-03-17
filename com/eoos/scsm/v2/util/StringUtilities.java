/*     */ package com.eoos.scsm.v2.util;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import org.apache.regexp.RE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringUtilities
/*     */ {
/*     */   public static void replace(StringBuffer origin, RE sectionContentPattern, int group, ReplaceCallback callback) {
/*  39 */     SectionIndex index = new SectionIndex(0, 0);
/*     */     
/*  41 */     CharSequence replacement = null;
/*  42 */     int offset = 0;
/*  43 */     while ((index = getSectionIndex(origin.toString(), sectionContentPattern, group, offset)) != null) {
/*  44 */       if ((replacement = callback.getReplacement(getSectionContent(origin, index))) != null) {
/*  45 */         replaceSectionContent(origin, index, replacement);
/*  46 */         offset = index.start + replacement.length(); continue;
/*     */       } 
/*  48 */       offset = index.end;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void replaceSectionContent(StringBuffer origin, SectionIndex index, CharSequence replaceWith) {
/*  55 */     origin.replace(index.start, index.end, replaceWith.toString());
/*     */   }
/*     */   
/*     */   public static CharSequence getSectionContent(CharSequence origin, SectionIndex index) {
/*  59 */     if (index == null) {
/*  60 */       return null;
/*     */     }
/*  62 */     return origin.subSequence(index.start, index.end);
/*     */   }
/*     */   
/*     */   public static SectionIndex getSectionIndex(CharSequence origin, RE sectionContent, int group, int offset) {
/*     */     try {
/*  67 */       if (!sectionContent.match(origin.toString(), offset)) {
/*  68 */         return null;
/*     */       }
/*  70 */       return new SectionIndex(sectionContent.getParenStart(group), sectionContent.getParenEnd(group));
/*     */     }
/*  72 */     catch (Exception e) {
/*  73 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void replace(StringBuffer origin, CharSequence searchFor, CharSequence replaceWith) {
/*  78 */     int nLength = searchFor.length();
/*  79 */     replaceWith = (replaceWith != null) ? replaceWith : "";
/*  80 */     int nPosition = 0;
/*  81 */     while ((nPosition = origin.indexOf(searchFor.toString(), nPosition)) != -1) {
/*  82 */       origin.replace(nPosition, nPosition + nLength, replaceWith.toString());
/*  83 */       nPosition += replaceWith.length();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String replace(String origin, CharSequence searchFor, CharSequence replaceWith) {
/*  89 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get(origin);
/*     */     try {
/*  91 */       replace(tmp, searchFor, replaceWith);
/*  92 */       return tmp.toString();
/*     */     } finally {
/*     */       
/*  95 */       StringBufferPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String removeWhitespace(String sequence) {
/* 100 */     if (sequence == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     StringBuffer retValue = StringBufferPool.getThreadInstance().get();
/*     */     try {
/* 105 */       for (int i = 0; i < sequence.length(); i++) {
/* 106 */         char c = sequence.charAt(i);
/* 107 */         if (!Character.isWhitespace(c)) {
/* 108 */           retValue.append(c);
/*     */         }
/*     */       } 
/* 111 */       return retValue.toString();
/*     */     } finally {
/*     */       
/* 114 */       StringBufferPool.getThreadInstance().free(retValue);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface ReplaceCallback {
/*     */     CharSequence getReplacement(CharSequence param1CharSequence);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\StringUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */