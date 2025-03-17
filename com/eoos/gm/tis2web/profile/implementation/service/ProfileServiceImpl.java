/*    */ package com.eoos.gm.tis2web.profile.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import com.eoos.gm.tis2web.profile.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.profile.service.ProfileService;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProfileServiceImpl
/*    */   implements ProfileService
/*    */ {
/*    */   public String getType() {
/* 22 */     return "profile";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, Map parameter, ProfileService.ReturnHandler returnHandler) {
/* 26 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 27 */     SharedContextProxy.getInstance(context).update();
/* 28 */     return (new MainPage(context, returnHandler)).getHtmlCode(parameter);
/*    */   }
/*    */   
/*    */   public ModuleInformation getModuleInformation() {
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 36 */     return "profileservice";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\profile\implementation\service\ProfileServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */