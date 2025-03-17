/*    */ package com.eoos.gm.tis2web.feedback.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.feedback.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.feedback.service.FeedbackService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.datatype.ModuleInformation;
/*    */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContextProxy;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FeedbackServiceImpl
/*    */   implements FeedbackService
/*    */ {
/*    */   public String getType() {
/* 22 */     return "feedback";
/*    */   }
/*    */   
/*    */   public Object getUI(String sessionID, String moduleType, Map moduleParams, Map parameter) {
/* 26 */     ClientContext context = ClientContextProvider.getInstance().getContext(sessionID);
/* 27 */     SharedContextProxy.getInstance(context).update();
/* 28 */     return (new MainPage(context, moduleType, moduleParams)).getHtmlCode(parameter);
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
/* 48 */     return "feedbackservice";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\feedback\implementation\service\FeedbackServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */