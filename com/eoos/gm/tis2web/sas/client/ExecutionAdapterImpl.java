/*    */ package com.eoos.gm.tis2web.sas.client;
/*    */ 
/*    */ import com.eoos.context.Context;
/*    */ 
/*    */ 
/*    */ public class ExecutionAdapterImpl
/*    */   implements ExecutionAdapter
/*    */ {
/*  9 */   private Context context = new Context();
/*    */ 
/*    */   
/*    */   public Object getValue(Object key) {
/* 13 */     return this.context.getObject(key);
/*    */   }
/*    */   
/*    */   public void setValue(Object key, Object value) {
/* 17 */     this.context.storeObject(key, value);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\ExecutionAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */