/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequestWithHWK;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SCASKARequestImpl
/*    */   implements SCASKARequestWithHWK, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SCASKAResponse createResponse(HardwareKey key) {
/* 17 */     return new SCASKAResponseImpl(key, (SCASKARequest)this);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 21 */     return super.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\SCASKARequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */