/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKARequestWithHWK;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SCASKAResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*    */ import com.eoos.util.DevelopmentUtil;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class TestToolSCA
/*    */   implements Tool, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private static class SCASKARequestImpl
/*    */     implements SCASKARequestWithHWK, Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private SCASKARequestImpl() {}
/*    */     
/*    */     public SCASKAResponse createResponse(HardwareKey key) {
/* 27 */       return new TestToolSCA.SCASKAResponseImpl(key) {
/*    */           private static final long serialVersionUID = 1L;
/*    */           
/*    */           public SecurityAccessRequest getRequest() {
/* 31 */             return (SecurityAccessRequest)TestToolSCA.SCASKARequestImpl.this;
/*    */           }
/*    */         };
/*    */     }
/*    */   }
/*    */   
/*    */   private static abstract class SCASKAResponseImpl
/*    */     implements SCASKAResponse, Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     private HardwareKey key;
/*    */     
/*    */     public SCASKAResponseImpl(HardwareKey key) {
/* 43 */       this.key = key;
/*    */     }
/*    */     
/*    */     public HardwareKey getHardwareKey() {
/* 47 */       return this.key;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/* 53 */     DevelopmentUtil.sleepRandom(1000L, 5000L);
/* 54 */     return (SecurityAccessRequest)new SCASKARequestImpl();
/*    */   }
/*    */   
/*    */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/* 58 */     DevelopmentUtil.sleepRandom(1000L, 5000L);
/* 59 */     return new AccessType[] { AccessType.SCA };
/*    */   }
/*    */   
/*    */   public SalesOrganisation getSalesOrganisation() throws Tool.Exception {
/* 63 */     DevelopmentUtil.sleepRandom(1000L, 5000L);
/* 64 */     return SalesOrganisation.GME;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 68 */     return "TestTool ( SCA )";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\test\TestToolSCA.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */