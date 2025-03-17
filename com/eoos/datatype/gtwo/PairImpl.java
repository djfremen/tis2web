/*    */ package com.eoos.datatype.gtwo;
/*    */ 
/*    */ import com.eoos.util.HashCalc;
/*    */ import com.eoos.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PairImpl
/*    */   implements Pair, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Object first;
/*    */   private Object second;
/*    */   
/*    */   public PairImpl(Object first, Object second) {
/* 15 */     this.first = first;
/* 16 */     this.second = second;
/*    */   }
/*    */   
/*    */   public Object getFirst() {
/* 20 */     return this.first;
/*    */   }
/*    */   
/*    */   public Object getSecond() {
/* 24 */     return this.second;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 28 */     boolean retValue = false;
/* 29 */     if (this == obj) {
/* 30 */       retValue = true;
/* 31 */     } else if (obj instanceof Pair) {
/* 32 */       Pair pair = (Pair)obj;
/* 33 */       retValue = true;
/* 34 */       retValue = (retValue && Util.equals(getFirst(), pair.getFirst()));
/* 35 */       retValue = (retValue && Util.equals(getSecond(), pair.getSecond()));
/*    */     } 
/* 37 */     return retValue;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 41 */     int retValue = Pair.class.hashCode();
/* 42 */     retValue = HashCalc.addHashCode(retValue, getFirst());
/* 43 */     retValue = HashCalc.addHashCode(retValue, getSecond());
/* 44 */     return retValue;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 48 */     return "(" + String.valueOf(getFirst()) + ", " + String.valueOf(getSecond()) + ")";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\gtwo\PairImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */