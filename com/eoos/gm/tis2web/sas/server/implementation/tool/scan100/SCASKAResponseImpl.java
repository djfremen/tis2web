/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.scan100;
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
/*    */   private SCASKARequest request;
/*    */   
/*    */   public SCASKAResponseImpl(SCASKARequest request) {
/* 19 */     this.request = request;
/*    */   }
/*    */   
/*    */   public HardwareKey getHardwareKey() {
/* 23 */     return new HardwareKey("empty_hwk", "empty_hwk");
/*    */   }
/*    */   
/*    */   public SecurityAccessRequest getRequest() {
/* 27 */     return (SecurityAccessRequest)this.request;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return super.toString() + "[hwk: " + String.valueOf(getHardwareKey()) + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\scan100\SCASKAResponseImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */