/*    */ package com.eoos.gm.tis2web.sas.client;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExecutionAdapterDeltaImpl
/*    */   implements ExecutionAdapter
/*    */ {
/*    */   private ExecutionAdapter backend;
/*    */   private ExecutionAdapter delta;
/*    */   
/*    */   public ExecutionAdapterDeltaImpl(ExecutionAdapter backend) {
/* 13 */     this.backend = backend;
/* 14 */     this.delta = new ExecutionAdapterImpl();
/*    */   }
/*    */   
/*    */   public Object getValue(Object key) {
/* 18 */     Object retValue = this.delta.getValue(key);
/* 19 */     if (retValue == null) {
/* 20 */       retValue = this.backend.getValue(key);
/*    */     }
/* 22 */     return retValue;
/*    */   }
/*    */   
/*    */   public void setValue(Object key, Object value) {
/* 26 */     this.delta.setValue(key, value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\ExecutionAdapterDeltaImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */