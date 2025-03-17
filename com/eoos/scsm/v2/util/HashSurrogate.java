/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ 
/*    */ public final class HashSurrogate
/*    */ {
/*    */   public static final class HashMode
/*    */   {
/*    */     private HashMode() {}
/*    */   }
/*    */   
/* 11 */   public static final HashMode IDENTITY_HASH = new HashMode();
/*    */   
/* 13 */   public static final HashMode STATE_HASH = new HashMode();
/*    */   
/*    */   private int hashCode;
/*    */   
/*    */   public HashSurrogate(int hashCode) {
/* 18 */     this.hashCode = hashCode;
/*    */   }
/*    */   public static HashSurrogate createFor(Object object, HashMode mode) {
/*    */     int hash;
/* 22 */     if (mode == null) {
/* 23 */       throw new IllegalArgumentException();
/*    */     }
/*    */     
/* 26 */     if (mode == IDENTITY_HASH) {
/* 27 */       hash = System.identityHashCode(object);
/*    */     } else {
/* 29 */       hash = HashCalc.addHashCode((object != null) ? object.getClass().hashCode() : 0, object);
/*    */     } 
/* 31 */     return new HashSurrogate(hash);
/*    */   }
/*    */   
/*    */   public boolean equals(Object object) {
/* 35 */     if (this == object)
/* 36 */       return true; 
/* 37 */     if (object instanceof HashSurrogate) {
/* 38 */       return (((HashSurrogate)object).hashCode == this.hashCode);
/*    */     }
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 45 */     return this.hashCode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 49 */     return Util.getClassName(this) + "[" + this.hashCode + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\HashSurrogate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */