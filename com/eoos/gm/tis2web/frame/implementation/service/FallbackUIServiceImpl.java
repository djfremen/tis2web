/*    */ package com.eoos.gm.tis2web.frame.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ui.html.main.toplink.VCLinkElement;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.FallbackUIService;
/*    */ import com.eoos.gm.tis2web.frame.implementation.fallback.ui.html.main.MainPage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FallbackUIServiceImpl
/*    */   implements FallbackUIService
/*    */ {
/*    */   public String getType() {
/* 24 */     return "fallback";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, final String moduleType, Map parameter) {
/* 28 */     return getUI(sessionID, parameter, new FallbackUIService.Callback()
/*    */         {
/*    */           public VCLinkElement.Callback getVCLinkCallback() {
/* 31 */             return null;
/*    */           }
/*    */           
/*    */           public String getModuleType() {
/* 35 */             return moduleType;
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getUI(String sessionID, Map params, FallbackUIService.Callback callback) {
/* 42 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 43 */     SharedContextProxy.getInstance(context).update();
/*    */     
/* 45 */     return (new MainPage(context, callback)).getHtmlCode(params);
/*    */   }
/*    */ 
/*    */   
/*    */   public Boolean isActive(String sessionID) {
/* 50 */     return new Boolean(ClientContextProvider.getInstance().isActive(sessionID));
/*    */   }
/*    */   
/*    */   public Boolean invalidateSession(String sessionID) {
/* 54 */     return new Boolean(ClientContextProvider.getInstance().invalidateSession(sessionID));
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 58 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isSupported(String salesmake) {
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 66 */     return "fallbackservice";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\FallbackUIServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */