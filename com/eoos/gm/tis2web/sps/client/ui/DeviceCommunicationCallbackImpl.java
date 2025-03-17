/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DeviceCommunicationCallback;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DeviceCommunicationCallbackImpl
/*    */   implements DeviceCommunicationCallback {
/* 11 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/*    */   protected IUIAgent agent;
/*    */   protected String message;
/*    */   
/*    */   DeviceCommunicationCallbackImpl(IUIAgent agent, String message) {
/* 17 */     this.agent = agent;
/* 18 */     this.message = message;
/*    */   }
/*    */   
/*    */   public void start() {
/* 22 */     this.agent.displayMessageDialog(this.message);
/*    */   }
/*    */   
/*    */   public void stop() {
/* 26 */     this.agent.removeMessageDialog();
/*    */   }
/*    */   
/*    */   public boolean displayQuestionDialog(String messageKey) {
/* 30 */     Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 31 */     String message = resourceProvider.getMessage(locale, messageKey);
/* 32 */     return this.agent.displayQuestionDialog(message);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\DeviceCommunicationCallbackImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */