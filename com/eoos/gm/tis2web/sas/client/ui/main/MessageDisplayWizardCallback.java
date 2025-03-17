/*    */ package com.eoos.gm.tis2web.sas.client.ui.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.MessagePanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.WizardStepPanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandle;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandleProxy;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStack;
/*    */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageDisplayWizardCallback
/*    */   implements MessagePanel.Callback, WizardStepPanel.Callback
/*    */ {
/*    */   private String[] messages;
/* 24 */   private ButtonHandle buttonHandle = (ButtonHandle)new ButtonHandleProxy();
/*    */   
/*    */   private MessagePanel panel;
/*    */   
/*    */   public MessageDisplayWizardCallback(String message) {
/* 29 */     this(new String[] { message });
/*    */   }
/*    */   
/*    */   public MessageDisplayWizardCallback(String[] messages) {
/* 33 */     this.messages = messages;
/* 34 */     this.panel = new MessagePanel(this);
/*    */   }
/*    */   
/*    */   public Locale getLocale() {
/* 38 */     return SASClientContextProvider.getInstance().getContext().getLocale();
/*    */   }
/*    */   
/*    */   private LabelResource getLabelResource() {
/* 42 */     return LabelResourceProvider.getInstance().getLabelResource();
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 46 */     return getLabelResource().getMessage(getLocale(), key);
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 50 */     return getLabelResource().getLabel(getLocale(), key);
/*    */   }
/*    */   
/*    */   public void setButtonHandle(ButtonHandle handle) {
/* 54 */     if (this.buttonHandle instanceof ButtonHandleProxy) {
/* 55 */       this.buttonHandle = ((ButtonHandleProxy)this.buttonHandle).flush(handle);
/*    */     }
/*    */   }
/*    */   
/*    */   public ButtonHandle getButtonHandle() {
/* 60 */     return this.buttonHandle;
/*    */   }
/*    */   
/*    */   public List getPopupButtons(JPanel panel) {
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   public PanelStack getPanelStack() {
/* 68 */     return MainFrame.getInstance();
/*    */   }
/*    */   
/*    */   public JPanel getContentPanel() {
/* 72 */     return (JPanel)this.panel;
/*    */   }
/*    */   
/*    */   public String[] getMessages() {
/* 76 */     return this.messages;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\main\MessageDisplayWizardCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */