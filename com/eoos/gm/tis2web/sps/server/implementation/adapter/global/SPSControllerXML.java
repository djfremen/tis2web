/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SPSControllerXML
/*    */   extends SPSControllerVCI
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected XMLConfiguration xml;
/*    */   
/*    */   SPSControllerXML(SPSSession session, SPSControllerData data, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 17 */     super(session, data, adapter);
/*    */   }
/*    */   
/*    */   public String getBuildFile(SPSVehicle vehicle) {
/* 21 */     return this.xml.getBuildFile(vehicle.getVIN().toString());
/*    */   }
/*    */   
/*    */   protected SPSProgrammingData constructProgrammingData(SPSVehicle vehicle, SPSSchemaAdapterGlobal adapter) throws Exception {
/* 25 */     SPSProgrammingData data = new SPSProgrammingData();
/* 26 */     data.setDeviceID(Integer.valueOf(this.deviceID));
/* 27 */     data.setVIN(vehicle.getVIN().toString());
/* 28 */     if (this.xml == null) {
/* 29 */       this.actualVCI = resolveVCI(vehicle.getVIN(), adapter);
/* 30 */       this.xml = new XMLConfiguration(adapter, (SPSLanguage)this.session.getLanguage(), this, this.actualVCI);
/*    */     } 
/* 32 */     Set usageCOP = COPQualification.qualify(adapter, vehicle, this.preRPOCode, this.postRPOCode);
/*    */     
/* 34 */     data.add((SPSLanguage)this.session.getLanguage(), this.xml.getPartType4().getPartNumber(), "00", adapter);
/*    */     
/* 36 */     data.add((SPSLanguage)this.session.getLanguage(), this.xml.getPartXML().getPartNumber(), "01", adapter);
/* 37 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, usageCOP, adapter);
/* 38 */     if (this.hardware != null && this.hardware.size() == 0) {
/* 39 */       this.hardware = null;
/*    */     }
/* 41 */     List<SPSModule> modules = data.getModules();
/* 42 */     for (int i = 0; i < modules.size(); i++) {
/* 43 */       SPSModule module = modules.get(i);
/* 44 */       SPSPart origin = (SPSPart)module.getSelectedPart();
/* 45 */       SPSPart part = findValidPart(origin);
/* 46 */       if (part != origin) {
/* 47 */         module.setSelectedPart((Part)part);
/*    */       }
/*    */     } 
/* 50 */     return data;
/*    */   }
/*    */   
/*    */   protected SPSPart findValidPart(SPSPart part) {
/* 54 */     List<SPSCOP> cop = part.getCOP();
/* 55 */     if (!Util.isNullOrEmpty(cop)) {
/* 56 */       SPSCOP link = cop.get(0);
/* 57 */       part = (SPSPart)link.getPart();
/* 58 */       if (link.getReplacementType() == 'V' && SPSCOP.isLeaf(part)) {
/* 59 */         return part;
/*    */       }
/* 61 */       return findValidPart(part);
/*    */     } 
/*    */ 
/*    */     
/* 65 */     return part;
/*    */   }
/*    */   
/*    */   public SPSPart getPart(int partno) {
/* 69 */     return new SPSPart(partno, this.adapter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSControllerXML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */