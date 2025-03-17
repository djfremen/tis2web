/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Archive;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*    */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*    */ public class SPSProgrammingData extends SPSProgrammingData {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   void setVIN(String vin) {
/* 15 */     this.vin = vin;
/*    */   }
/*    */   protected String EndModel;
/*    */   void setCalibrationFiles(List files) {
/* 19 */     this.files = files;
/*    */   }
/*    */   
/*    */   void setDeviceID(Integer deviceID) {
/* 23 */     this.deviceID = deviceID;
/*    */   }
/*    */   
/*    */   void setVMECUHN(String vmecuhn) {
/* 27 */     this.vmecuhn = vmecuhn;
/*    */   }
/*    */   
/*    */   void setSSECUSVN(String ssecusvn) {
/* 31 */     this.ssecusvn = ssecusvn;
/*    */   }
/*    */   
/*    */   public boolean skipSameCalibration() {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public SPSProgrammingData() {
/* 40 */     this.rsc = "";
/*    */   }
/*    */   
/*    */   void setEndModelIdent(String ident) {
/* 44 */     this.EndModel = ident;
/*    */   }
/*    */   
/*    */   public String getEndModelIdent() {
/* 48 */     return this.EndModel;
/*    */   }
/*    */   
/*    */   void add(int id, int order, String name, int type) {
/* 52 */     if (this.modules == null) {
/* 53 */       this.modules = new ArrayList();
/*    */     }
/* 55 */     this.modules.add(new SPSModule(id, order, name, type, new SPSPart(id, name)));
/*    */   }
/*    */   
/*    */   void setArchive(SPSLanguage language, Archive archive) {
/* 59 */     this.files = archive.getCalibrationUnits();
/* 60 */     this.modules = new ArrayList();
/* 61 */     List<String> descriptions = archive.getDescriptions();
/* 62 */     for (int i = 0; i < this.files.size(); i++) {
/* 63 */       SPSPart part = new SPSPart(i, descriptions.get(i));
/* 64 */       ProgrammingDataUnit calibration = this.files.get(i);
/* 65 */       SPSModule module = new SPSModule(i, i, calibration.getBlobName(), (i == 0) ? 0 : 1, part);
/* 66 */       this.modules.add(module);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */