/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Identifier;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ 
/*    */ public class IdentifierAdapter
/*    */   implements Identifier
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Object delegate;
/*    */   
/*    */   public IdentifierAdapter(Object delegate) {
/* 13 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public Object getDelegate() {
/* 17 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 21 */     return this.delegate.toString();
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 25 */     int ret = Identifier.class.hashCode();
/* 26 */     ret = HashCalc.addHashCode(ret, this.delegate);
/* 27 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 31 */     if (this == obj)
/* 32 */       return true; 
/* 33 */     if (obj instanceof IdentifierAdapter) {
/* 34 */       return this.delegate.equals(((IdentifierAdapter)obj).delegate);
/*    */     }
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\IdentifierAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */