/*    */ package com.eoos.gm.tis2web.home.implementation.service;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.home.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.home.service.HomeService;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HomeServiceImpl
/*    */   implements HomeService, Configurable
/*    */ {
/*    */   public HomeServiceImpl(Configuration configuration) {}
/*    */   
/*    */   public String getType() {
/* 23 */     return "home";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameter) {
/* 27 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 28 */     return MainPage.getInstance(context).getHtmlCode(parameter);
/*    */   }
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 32 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 36 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 40 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isSupported(String salesmake) {
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 48 */     return "homeservice";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\home\implementation\service\HomeServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */