/*    */ package com.eoos.gm.tis2web.sas.client;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ 
/*    */ public interface ExecutionAdapter {
/*    */   Object getValue(Object paramObject);
/*    */   
/*    */   void setValue(Object paramObject1, Object paramObject2);
/*    */   
/*    */   public static interface Key {
/* 10 */     public static final Object TOOL = Tool.class;
/*    */     
/* 12 */     public static final Object SALESORGANISATION = SalesOrganisation.class;
/*    */     
/* 14 */     public static final Object REQUEST = SecurityAccessRequest.class;
/*    */     
/* 16 */     public static final Object VINVALIDATION = "vin.validation";
/*    */     
/* 18 */     public static final Object SOVALIDATION = "so.validation";
/*    */     
/* 20 */     public static final Object INCOMPLETE_SSAR_WARNING = "inc.ssar.warning";
/*    */     
/* 22 */     public static final Object RESPONSE = SecurityAccessResponse.class;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\ExecutionAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */