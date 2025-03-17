/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.xml.XMLSupport;
/*    */ 
/*    */ public class SPSControllerXML
/*    */   extends SPSControllerGME {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected XMLSupport xml;
/*    */   
/*    */   public String getBuildFile() {
/* 13 */     return this.xml.getBuildFile(this.adapter, this.session.getVehicle().getVIN().toString());
/*    */   }
/*    */   
/*    */   SPSControllerXML(SPSSession session, Integer vehicle, Integer id, Integer system, Integer ecu, SPSSchemaAdapterGME adapter) {
/* 17 */     super(session, vehicle, id, system, ecu, adapter);
/*    */   }
/*    */   
/*    */   protected SPSProgrammingData determineProgrammingData() throws Exception {
/* 21 */     SPSProgrammingData pdata = super.determineProgrammingData();
/* 22 */     if (this.vci <= 0) {
/* 23 */       this.vci = (int)((SPSSession)this.session).getDealerVCI();
/*    */     }
/* 25 */     this.xml = new XMLSupport(this.adapter, (SPSLanguage)this.session.getLanguage(), this, this.vci);
/* 26 */     return pdata;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerXML.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */