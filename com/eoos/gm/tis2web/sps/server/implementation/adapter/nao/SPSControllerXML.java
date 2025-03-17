/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.SPSException;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.xml.XMLConfiguration;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class SPSControllerXML
/*    */   extends SPSControllerVCI
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected XMLConfiguration xml;
/*    */   protected int pinLink;
/*    */   
/*    */   SPSControllerXML(SPSSession session, SPSControllerData data, SPSSchemaAdapterNAO adapter) throws Exception {
/* 21 */     super(session, data, adapter);
/*    */   }
/*    */   
/*    */   public int getPINLink() {
/* 25 */     return this.pinLink;
/*    */   }
/*    */   
/*    */   public void setPINLink(int pinLink) {
/* 29 */     this.pinLink = pinLink;
/*    */   }
/*    */   
/*    */   public String getBuildFile(SPSVehicle vehicle) {
/* 33 */     return this.xml.getBuildFile(vehicle.getVIN().toString());
/*    */   }
/*    */   
/*    */   protected SPSProgrammingData constructProgrammingData(SPSVehicle vehicle, SPSSchemaAdapterNAO adapter) throws Exception {
/* 37 */     SPSProgrammingData data = new SPSProgrammingData();
/* 38 */     data.setDeviceID(Integer.valueOf(this.deviceID));
/* 39 */     data.setVIN(vehicle.getVIN().toString());
/* 40 */     if (this.xml == null) {
/* 41 */       this.actualVCI = this.defaultVCI;
/* 42 */       if (this.VCI >= 0) {
/* 43 */         this.actualVCI = this.VCI;
/*    */       }
/* 45 */       if (this.actualVCI == 0) {
/* 46 */         this.actualVCI = resolveVCI(vehicle.getVIN(), adapter);
/* 47 */         if (this.actualVCI == 0) {
/* 48 */           throw new SPSException(CommonException.NoVCI);
/*    */         }
/*    */       } 
/* 51 */       this.xml = new XMLConfiguration(adapter, (SPSLanguage)this.session.getLanguage(), this, this.actualVCI);
/*    */     } 
/* 53 */     Set usageCOP = COPQualification.qualify(adapter, vehicle, this.preRPOCode, this.postRPOCode);
/*    */     
/* 55 */     data.add((SPSLanguage)this.session.getLanguage(), this.xml.getPartType4().getPartNumber(), "00", adapter);
/*    */     
/* 57 */     data.add((SPSLanguage)this.session.getLanguage(), this.xml.getPartXML().getPartNumber(), "01", adapter);
/* 58 */     data.build((SPSLanguage)this.session.getLanguage(), this.hardware, usageCOP, adapter);
/* 59 */     if (this.hardware != null && this.hardware.size() == 0) {
/* 60 */       this.hardware = null;
/*    */     }
/* 62 */     List<SPSModule> modules = data.getModules();
/* 63 */     for (int i = 0; i < modules.size(); i++) {
/* 64 */       SPSModule module = modules.get(i);
/* 65 */       SPSPart origin = (SPSPart)module.getSelectedPart();
/* 66 */       SPSPart part = findValidPart(origin);
/* 67 */       if (part != origin) {
/* 68 */         module.setSelectedPart((Part)part);
/*    */       }
/*    */     } 
/* 71 */     return data;
/*    */   }
/*    */   
/*    */   protected SPSPart findValidPart(SPSPart part) {
/* 75 */     List<SPSCOP> cop = part.getCOP();
/* 76 */     if (!Util.isNullOrEmpty(cop)) {
/* 77 */       SPSCOP link = cop.get(0);
/* 78 */       part = (SPSPart)link.getPart();
/* 79 */       if (link.getReplacementType() == 'V' && SPSCOP.isLeaf(part)) {
/* 80 */         return part;
/*    */       }
/* 82 */       return findValidPart(part);
/*    */     } 
/*    */     
/* 85 */     return part;
/*    */   }
/*    */   
/*    */   public SPSPart getPart(int partno) {
/* 89 */     return new SPSPart(partno, this.adapter);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerXML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */