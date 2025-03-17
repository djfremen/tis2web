/*    */ package com.eoos.util;
/*    */ 
/*    */ import com.eoos.datatype.SectionIndex;
/*    */ import java.util.Iterator;
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
/*    */ public class PatternIterator
/*    */   implements Iterator
/*    */ {
/* 18 */   private SectionIndex currentIndex = null;
/* 19 */   private SectionIndex nextIndex = null;
/*    */   
/* 21 */   private RE startPattern = null;
/* 22 */   private RE endPattern = null;
/*    */   
/*    */   private StringBuffer buffer;
/*    */   
/*    */   public PatternIterator(StringBuffer buffer, String pattern) {
/*    */     try {
/* 28 */       this.buffer = buffer;
/* 29 */       this.startPattern = new RE(pattern);
/* 30 */     } catch (Exception e) {
/* 31 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public PatternIterator(StringBuffer buffer, String startPattern, String endPattern) {
/*    */     try {
/* 37 */       this.buffer = buffer;
/* 38 */       this.startPattern = new RE(startPattern);
/* 39 */       this.endPattern = new RE(endPattern);
/* 40 */     } catch (Exception e) {
/* 41 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 46 */     if (this.endPattern != null) {
/* 47 */       this.nextIndex = StringUtilities.getSectionIndex(this.buffer.toString(), this.startPattern, this.endPattern, (this.currentIndex != null) ? this.currentIndex.end : 0, true, false);
/*    */     } else {
/* 49 */       this.nextIndex = StringUtilities.getSectionIndex(this.buffer.toString(), this.startPattern, (this.currentIndex != null) ? this.currentIndex.end : 0);
/*    */     } 
/* 51 */     return (this.nextIndex != null);
/*    */   }
/*    */   
/*    */   public Object next() {
/* 55 */     this.currentIndex = this.nextIndex;
/* 56 */     return StringUtilities.getSectionContent(this.buffer, this.currentIndex);
/*    */   }
/*    */   
/*    */   public void remove() {
/* 60 */     StringUtilities.replaceSectionContent(this.buffer, this.currentIndex, "");
/*    */   }
/*    */ 
/*    */   
/*    */   public void replace(String replacement) {
/* 65 */     StringUtilities.replaceSectionContent(this.buffer, this.currentIndex, replacement);
/* 66 */     this.currentIndex.end += replacement.length() - this.currentIndex.end - this.currentIndex.start;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\PatternIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */