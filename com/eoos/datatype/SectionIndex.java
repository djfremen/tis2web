/*    */ package com.eoos.datatype;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SectionIndex
/*    */ {
/*  7 */   public int start = -1;
/*  8 */   public int end = -1;
/*    */   
/*    */   public SectionIndex(int startIndex, int pastEndIndex) {
/* 11 */     this.start = startIndex;
/* 12 */     this.end = pastEndIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public SectionIndex() {}
/*    */   
/*    */   public boolean includes(SectionIndex index) {
/* 19 */     if (index == null) {
/* 20 */       return false;
/*    */     }
/* 22 */     return (this.start <= index.start && this.end > index.end);
/*    */   }
/*    */   
/*    */   public boolean includes(int index) {
/* 26 */     return (this.start <= index && index < this.end);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 30 */     return "SectionIndex (" + this.start + ", " + this.end + ")";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\SectionIndex.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */