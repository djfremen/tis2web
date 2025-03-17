/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import org.apache.regexp.RE;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringUtilities
/*    */ {
/*    */   public static void replace(StringBuffer origin, RE sectionContentPattern, ReplaceCallback callback) {
/* 18 */     SectionIndex index = new SectionIndex(0, 0);
/*    */     
/* 20 */     CharSequence replacement = null;
/* 21 */     int offset = 0;
/* 22 */     while ((index = getSectionIndex(origin.toString(), sectionContentPattern, offset)) != null) {
/* 23 */       if ((replacement = callback.getReplacement(getSectionContent(origin, index))) != null) {
/* 24 */         replaceSectionContent(origin, index, replacement);
/* 25 */         offset = index.start + replacement.length(); continue;
/*    */       } 
/* 27 */       offset = index.end;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void replaceSectionContent(StringBuffer origin, SectionIndex index, CharSequence replaceWith) {
/* 34 */     origin.replace(index.start, index.end, replaceWith.toString());
/*    */   }
/*    */   
/*    */   public static CharSequence getSectionContent(CharSequence origin, SectionIndex index) {
/* 38 */     if (index == null) {
/* 39 */       return null;
/*    */     }
/* 41 */     return origin.subSequence(index.start, index.end);
/*    */   }
/*    */   
/*    */   public static SectionIndex getSectionIndex(CharSequence origin, RE sectionContent, int group, int offset) {
/*    */     try {
/* 46 */       if (!sectionContent.match(origin.toString(), offset)) {
/* 47 */         return null;
/*    */       }
/* 49 */       return new SectionIndex(sectionContent.getParenStart(group), sectionContent.getParenEnd(group));
/*    */     }
/* 51 */     catch (Exception e) {
/* 52 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static SectionIndex getSectionIndex(CharSequence origin, RE sectionContent, int offset) {
/* 57 */     return getSectionIndex(origin, sectionContent, 0, offset);
/*    */   }
/*    */   
/*    */   public static void replace(StringBuffer origin, CharSequence searchFor, CharSequence replaceWith) {
/* 61 */     int nLength = searchFor.length();
/* 62 */     replaceWith = (replaceWith != null) ? replaceWith : "";
/* 63 */     int nPosition = 0;
/* 64 */     while ((nPosition = origin.indexOf(searchFor.toString(), nPosition)) != -1) {
/* 65 */       origin.replace(nPosition, nPosition + nLength, replaceWith.toString());
/* 66 */       nPosition += replaceWith.length();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static String replace(String origin, CharSequence searchFor, CharSequence replaceWith) {
/* 72 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get(origin);
/*    */     try {
/* 74 */       replace(tmp, searchFor, replaceWith);
/* 75 */       return tmp.toString();
/*    */     } finally {
/*    */       
/* 78 */       StringBufferPool.getThreadInstance().free(tmp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface ReplaceCallback {
/*    */     CharSequence getReplacement(CharSequence param1CharSequence);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\StringUtilities.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */