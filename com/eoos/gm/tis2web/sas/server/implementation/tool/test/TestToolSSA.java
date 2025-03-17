/*     */ package com.eoos.gm.tis2web.sas.server.implementation.tool.test;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sas.common.model.AccessType;
/*     */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*     */ import com.eoos.gm.tis2web.sas.common.model.SalesOrganisation;
/*     */ import com.eoos.gm.tis2web.sas.common.model.VIN;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSARequest2;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SSAResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessRequest;
/*     */ import com.eoos.gm.tis2web.sas.common.model.reqres.SecurityAccessResponse;
/*     */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*     */ import com.eoos.util.DevelopmentUtil;
/*     */ import java.io.Serializable;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestToolSSA
/*     */   implements Tool, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   private static class SSARequestImpl
/*     */     implements SSARequest2, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  29 */     private List vins = new LinkedList();
/*     */     
/*  31 */     private HardwareKey hardwareKey = null;
/*     */     
/*     */     public SSARequestImpl() {
/*  34 */       VIN vin1 = VIN.getInstance("xxxfxxxxx41007000");
/*  35 */       VIN vin2 = VIN.getInstance("w0l0tgf35y1234567");
/*  36 */       VIN vin3 = VIN.getInstance("xxxfxxxxx31007100");
/*  37 */       this.vins.add(vin2);
/*  38 */       this.vins.add(vin1);
/*  39 */       this.vins.add(vin3);
/*     */     }
/*     */ 
/*     */     
/*     */     public List getVINs() {
/*  44 */       return this.vins;
/*     */     }
/*     */     
/*     */     public SSAResponse createResponse(Integer dbVersion, Integer freeshot, List secCodes) {
/*  48 */       return new TestToolSSA.SSAResponseImpl() {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public SecurityAccessRequest getRequest() {
/*  52 */             return (SecurityAccessRequest)TestToolSSA.SSARequestImpl.this;
/*     */           }
/*     */           
/*     */           public boolean isIncomplete() {
/*  56 */             return false;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public SSARequest2 setHardwareKey(HardwareKey key) {
/*  62 */       this.hardwareKey = key;
/*  63 */       return this;
/*     */     }
/*     */     
/*     */     public HardwareKey getHardwareKey() {
/*  67 */       return this.hardwareKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class SSAResponseImpl
/*     */     implements SSAResponse, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */ 
/*     */     
/*     */     public Integer getDBVersion() {
/*  80 */       return null;
/*     */     }
/*     */     
/*     */     public Integer getFreeShots() {
/*  84 */       return null;
/*     */     }
/*     */     
/*     */     public SSAResponse.SecurityCodes getSecCodes(VIN vin) {
/*  88 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/*  94 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/*  95 */     return (SecurityAccessRequest)new SSARequestImpl();
/*     */   }
/*     */   
/*     */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/*  99 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/*     */     
/* 101 */     return new AccessType[] { AccessType.SSA };
/*     */   }
/*     */   
/*     */   public SalesOrganisation getSalesOrganisation() throws Tool.Exception {
/* 105 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/* 106 */     return SalesOrganisation.GME;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 110 */     return "TestTool ( SSA )";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\test\TestToolSSA.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */