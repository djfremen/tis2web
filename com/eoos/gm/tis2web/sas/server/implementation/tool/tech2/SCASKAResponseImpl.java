/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SCASKAResponseImpl
/*    */   implements SCASKAResponse, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private HardwareKey hardwareKey;
/*    */   private SCASKARequest request;
/*    */   
/*    */   public SCASKAResponseImpl(HardwareKey hardwareKey, SCASKARequest request) {
/* 20 */     this.hardwareKey = (hardwareKey != null) ? hardwareKey : new HardwareKey("T2W:NO-HWK", "T2W:NO-HWK");
/* 21 */     this.request = request;
/*    */   }
/*    */   
/*    */   public HardwareKey getHardwareKey() {
/* 25 */     return this.hardwareKey;
/*    */   }
/*    */   
/*    */   public SecurityAccessRequest getRequest() {
/* 29 */     return (SecurityAccessRequest)this.request;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 34 */     return super.toString() + "[hwk: " + String.valueOf(this.hardwareKey) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\SCASKAResponseImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */