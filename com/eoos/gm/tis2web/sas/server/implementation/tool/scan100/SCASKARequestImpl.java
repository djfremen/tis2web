/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.scan100;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequestWithoutHWK;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SCASKARequestImpl
/*    */   implements SCASKARequestWithoutHWK, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SCASKAResponse createResponse() {
/* 16 */     return new SCASKAResponseImpl((SCASKARequest)this);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 20 */     return super.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\scan100\SCASKARequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */