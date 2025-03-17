/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ public class SectionIndex {
/*  4 */   public static final SectionIndex START_INDEX = new SectionIndex(0, 0);
/*    */   
/*    */   public final int start;
/*    */   
/*    */   public final int end;
/*    */   
/*    */   public SectionIndex(int start, int end) {
/* 11 */     if (start < 0 || end < start) {
/* 12 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 15 */     this.start = start;
/* 16 */     this.end = end;
/*    */   }
/*    */   
/*    */   public int getSectionSize() {
/* 20 */     return this.end - this.start;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 24 */     return super.toString() + "[" + this.start + "..(" + this.end + ")]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\SectionIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */