/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*    */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*    */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*    */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*    */ import com.eoos.util.DevelopmentUtil;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class TestToolExceptionOnRead
/*    */   implements Tool, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/* 19 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/* 20 */     throw new RuntimeException("unable to read from tool");
/*    */   }
/*    */   
/*    */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/* 24 */     return null;
/*    */   }
/*    */   
/*    */   public SalesOrganisation getSalesOrganisation() throws Tool.Exception {
/* 28 */     DevelopmentUtil.sleepRandom(1000L, 2000L);
/* 29 */     return SalesOrganisation.GME;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 33 */     return "TestTool (SCA/SKA Exception on read )";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\test\TestToolExceptionOnRead.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */