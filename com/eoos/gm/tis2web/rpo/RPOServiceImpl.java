/*    */ package com.eoos.gm.tis2web.rpo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOService;
/*    */ import com.eoos.gm.tis2web.rpo.ui.html.MainPage;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import java.util.Collection;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RPOServiceImpl
/*    */   implements RPOService, Singleton
/*    */ {
/* 26 */   private static RPOServiceImpl instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized RPOServiceImpl getInstance() {
/* 34 */     if (instance == null) {
/* 35 */       instance = new RPOServiceImpl();
/*    */     }
/* 37 */     return instance;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 41 */     return "rpo";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameter) {
/* 45 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 46 */     SharedContextProxy.getInstance(context).update();
/* 47 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 55 */     return "rposervice";
/*    */   }
/*    */   
/*    */   public CharSequence getRPOXML(String vin, Locale locale) {
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public RPOContainer getRPOs(String vin) throws VIN.UnsupportedVINException, VIN.InvalidVINException {
/* 63 */     if (!VehicleConfigurationUtil.checkVIN(vin, 2)) {
/* 64 */       throw new VIN.InvalidVINException(vin);
/*    */     }
/* 66 */     return DatabaseFacade.getInstance().getRPOs(vin);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<String, RPOFamily> resolveFamilies(Collection<String> familyIDs) {
/* 73 */     return DatabaseFacade.getInstance().resolveFamilies(familyIDs);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\RPOServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */