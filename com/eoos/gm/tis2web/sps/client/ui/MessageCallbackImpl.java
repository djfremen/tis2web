/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.common.system.LabelResourceProvider;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class MessageCallbackImpl
/*    */   implements MessageCallback {
/* 11 */   protected static LabelResource resourceProvider = LabelResourceProvider.getInstance().getLabelResource();
/*    */   
/*    */   protected IUIAgent agent;
/*    */   
/*    */   MessageCallbackImpl(IUIAgent agent) {
/* 16 */     this.agent = agent;
/*    */   }
/*    */   
/*    */   public void displayMessageDialog(String messageKey) {
/* 20 */     Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 21 */     String message = resourceProvider.getMessage(locale, messageKey);
/* 22 */     this.agent.displayMessage(message);
/*    */   }
/*    */   
/*    */   public boolean displayQuestionDialog(String messageKey) {
/* 26 */     Locale locale = ClientAppContextProvider.getClientAppContext().getLocale();
/* 27 */     String message = resourceProvider.getMessage(locale, messageKey);
/* 28 */     return this.agent.displayQuestionDialog(message);
/*    */   }
/*    */   
/*    */   public void displayMessageDialogMsg(String message) {
/* 32 */     this.agent.displayMessage(message);
/*    */   }
/*    */   
/*    */   public boolean displayQuestionDialogMsg(String message) {
/* 36 */     return this.agent.displayQuestionDialog(message);
/*    */   }
/*    */   
/*    */   public void displayInformationText(String message) {
/* 40 */     this.agent.displayInformationText(message);
/*    */   }
/*    */   
/*    */   public void updateInformationText(String message) {
/* 44 */     this.agent.updateInformationText(message);
/*    */   }
/*    */   
/*    */   public void destroyInformationText() {
/* 48 */     this.agent.destroyInformationText();
/*    */   }
/*    */   
/*    */   public void displayHTMLMessage(String message, List traceInfo) {
/* 52 */     this.agent.displayHTMLMessage(message, traceInfo);
/*    */   }
/*    */   
/*    */   public boolean displayQuestionHTMLMessage(String message, List traceInfo) {
/* 56 */     return this.agent.displayQuestionHTMLMessage(message, traceInfo);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\MessageCallbackImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */