/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CVNResultImpl
/*    */   implements SchemaAdapterAPI.CVNResult
/*    */ {
/*    */   private String cvn;
/*    */   private String schemaAdapterID;
/*    */   
/*    */   public CVNResultImpl(String cvn, String schemaAdapterID) {
/* 12 */     this.cvn = cvn;
/* 13 */     this.schemaAdapterID = schemaAdapterID;
/*    */   }
/*    */   
/*    */   public String getCalibrationVerificationNumber() {
/* 17 */     return this.cvn;
/*    */   }
/*    */   
/*    */   public String getSchemaAdapterDisplayableID() {
/* 21 */     return this.schemaAdapterID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\CVNResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */