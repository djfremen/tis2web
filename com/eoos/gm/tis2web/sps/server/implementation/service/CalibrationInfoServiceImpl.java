/*    */ package com.eoos.gm.tis2web.sps.server.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformationFactory;
/*    */ import com.eoos.gm.tis2web.frame.export.common.service.Module;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.main.CalibInfoMainPage;
/*    */ import com.eoos.gm.tis2web.sps.service.CalibrationInfoService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CalibrationInfoServiceImpl
/*    */   implements CalibrationInfoService, Configurable
/*    */ {
/*    */   private Configuration configuration;
/* 21 */   private final Object SYNC_MODULEINFO = new Object();
/*    */   
/* 23 */   private ModuleInformation moduleInfo = null;
/*    */ 
/*    */   
/*    */   public CalibrationInfoServiceImpl(Configuration configuration) {
/* 27 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 32 */     return "sps_ci";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameter) {
/* 36 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 37 */     SharedContextProxy.getInstance(context).update();
/* 38 */     return CalibInfoMainPage.getInstance(context).getHtmlCode(parameter);
/*    */   }
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 42 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 46 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 50 */     synchronized (this.SYNC_MODULEINFO) {
/* 51 */       if (this.moduleInfo == null) {
/* 52 */         String version = this.configuration.getProperty("version");
/* 53 */         this.moduleInfo = ModuleInformationFactory.create((Module)this, version, null, null);
/*    */       } 
/* 55 */       return this.moduleInfo;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSupported(String salesmake) {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 65 */     return "spsciservice";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\service\CalibrationInfoServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */