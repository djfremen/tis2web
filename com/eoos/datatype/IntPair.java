/*    */ package com.eoos.datatype;
/*    */ 
/*    */ import com.eoos.util.HashCalc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IntPair
/*    */ {
/*    */   private int first;
/*    */   private int second;
/*    */   
/*    */   public IntPair(int first, int second) {
/* 15 */     this.first = first;
/* 16 */     this.second = second;
/*    */   }
/*    */   
/*    */   public int first() {
/* 20 */     return this.first;
/*    */   }
/*    */   
/*    */   public int second() {
/* 24 */     return this.second;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 28 */     int retValue = getClass().hashCode();
/* 29 */     retValue = HashCalc.addHashCode(retValue, this.first);
/* 30 */     retValue = HashCalc.addHashCode(retValue, this.second);
/* 31 */     return retValue;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 35 */     boolean retValue = false;
/* 36 */     if (this == obj) {
/* 37 */       retValue = true;
/* 38 */     } else if (obj instanceof IntPair) {
/* 39 */       IntPair intPair = (IntPair)obj;
/* 40 */       retValue = (this.first == intPair.first);
/* 41 */       retValue = (retValue && this.second == intPair.second);
/*    */     } 
/*    */     
/* 44 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "(" + this.first + ", " + this.second + ")";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\IntPair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */