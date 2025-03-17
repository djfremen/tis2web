/*    */ package com.eoos.ref.v3;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IReferenceAdapter
/*    */   implements IReference
/*    */ {
/*    */   private Reference reference;
/*    */   
/*    */   public IReferenceAdapter(Reference reference) {
/* 13 */     this.reference = reference;
/*    */   }
/*    */   
/*    */   public Object get() {
/* 17 */     return this.reference.get();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 21 */     this.reference.clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\ref\v3\IReferenceAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */