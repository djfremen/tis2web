/*    */ package com.eoos.ref.v3;
/*    */ 
/*    */ public class IReferenceAdapter_Hard
/*    */   implements IReference {
/*    */   private Object referent;
/*    */   
/*    */   public IReferenceAdapter_Hard(Object referent) {
/*  8 */     this.referent = referent;
/*    */   }
/*    */   
/*    */   public Object get() {
/* 12 */     return this.referent;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 16 */     this.referent = null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\IReferenceAdapter_Hard.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */