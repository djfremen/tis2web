/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SPSProgrammingData
/*    */   implements ProgrammingData
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient List modules;
/*    */   protected transient List hardware;
/*    */   protected String vin;
/*    */   protected List files;
/*    */   protected Integer deviceID;
/*    */   protected String vmecuhn;
/*    */   protected String ssecusvn;
/*    */   protected String rsc;
/*    */   protected boolean needsSecurityCode;
/*    */   
/*    */   public String getVIN() {
/* 29 */     return this.vin;
/*    */   }
/*    */   
/*    */   public String getRepairShopCode() {
/* 33 */     return this.rsc;
/*    */   }
/*    */   
/*    */   public boolean isSecurityCodeRequired() {
/* 37 */     return this.needsSecurityCode;
/*    */   }
/*    */   
/*    */   public List getCalibrationFiles() {
/* 41 */     return this.files;
/*    */   }
/*    */   
/*    */   public Integer getDeviceID() {
/* 45 */     return this.deviceID;
/*    */   }
/*    */   
/*    */   public List getVIT1TransferAttributes() {
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List getOptions() {
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   public List getHardwareList() throws Exception {
/* 60 */     return this.hardware;
/*    */   }
/*    */   
/*    */   public List getModules() throws Exception {
/* 64 */     return this.modules;
/*    */   }
/*    */   
/*    */   public String getVMECUHN() {
/* 68 */     return this.vmecuhn;
/*    */   }
/*    */   
/*    */   public String getSSECUSVN() {
/* 72 */     return this.ssecusvn;
/*    */   }
/*    */   
/*    */   public List getOptionBytes() {
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public String getOdometer() {
/* 80 */     return null;
/*    */   }
/*    */   
/*    */   public String getKeycode() {
/* 84 */     return null;
/*    */   }
/*    */   
/*    */   public void setRepairShopCode(String rsc) {
/* 88 */     this.rsc = rsc;
/*    */   }
/*    */   
/*    */   public void flagSecurityCodeRequired() {
/* 92 */     this.needsSecurityCode = true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */