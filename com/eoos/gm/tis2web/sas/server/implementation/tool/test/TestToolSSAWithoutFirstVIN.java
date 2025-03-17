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
/*     */ public class TestToolSSAWithoutFirstVIN
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
/*  37 */       this.vins.add(null);
/*  38 */       this.vins.add(vin1);
/*  39 */       this.vins.add(vin2);
/*  40 */       this.vins.add(vin3);
/*     */     }
/*     */ 
/*     */     
/*     */     public List getVINs() {
/*  45 */       return this.vins;
/*     */     }
/*     */     
/*     */     public SSAResponse createResponse(Integer dbVersion, Integer freeshot, List secCodes) {
/*  49 */       return new TestToolSSAWithoutFirstVIN.SSAResponseImpl() {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public SecurityAccessRequest getRequest() {
/*  53 */             return (SecurityAccessRequest)TestToolSSAWithoutFirstVIN.SSARequestImpl.this;
/*     */           }
/*     */           
/*     */           public boolean isIncomplete() {
/*  57 */             return false;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public SSARequest2 setHardwareKey(HardwareKey key) {
/*  63 */       this.hardwareKey = key;
/*  64 */       return this;
/*     */     }
/*     */     
/*     */     public HardwareKey getHardwareKey() {
/*  68 */       return this.hardwareKey;
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
/*  81 */       return null;
/*     */     }
/*     */     
/*     */     public Integer getFreeShots() {
/*  85 */       return null;
/*     */     }
/*     */     
/*     */     public SSAResponse.SecurityCodes getSecCodes(VIN vin) {
/*  89 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAccessRequest readRequest(Tool.CommunicationSettings communicationSettings) throws Tool.Exception {
/*  95 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/*  96 */     return (SecurityAccessRequest)new SSARequestImpl();
/*     */   }
/*     */   
/*     */   public AccessType[] writeResponse(SecurityAccessResponse response) throws Tool.Exception {
/* 100 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/*     */     
/* 102 */     return new AccessType[] { AccessType.SSA };
/*     */   }
/*     */   
/*     */   public SalesOrganisation getSalesOrganisation() throws Tool.Exception {
/* 106 */     DevelopmentUtil.sleepRandom(1000L, 3500L);
/* 107 */     return SalesOrganisation.GME;
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/* 111 */     return "TestTool ( SSA w/o first vin )";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\test\TestToolSSAWithoutFirstVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */