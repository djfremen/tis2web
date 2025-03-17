/*    */ package com.eoos.gm.tis2web.sas.client.ui.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.VINValidationPanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.WizardStepPanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandle;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandleProxy;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStack;
/*    */ import com.eoos.gm.tis2web.sas.common.model.VIN;
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
/*    */ public class VINValidationWizardCallback
/*    */   implements VINValidationPanel.Callback, WizardStepPanel.Callback
/*    */ {
/*    */   private VIN vin;
/* 25 */   private ButtonHandle buttonHandle = (ButtonHandle)new ButtonHandleProxy();
/*    */   
/*    */   private VINValidationPanel panel;
/*    */   
/*    */   public VINValidationWizardCallback(VIN vin) {
/* 30 */     this.vin = vin;
/* 31 */     this.panel = new VINValidationPanel(this);
/*    */   }
/*    */   
/*    */   public Locale getLocale() {
/* 35 */     return SASClientContextProvider.getInstance().getContext().getLocale();
/*    */   }
/*    */   
/*    */   private LabelResource getLabelResource() {
/* 39 */     return LabelResourceProvider.getInstance().getLabelResource();
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 43 */     return getLabelResource().getMessage(getLocale(), key);
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 47 */     return getLabelResource().getLabel(getLocale(), key);
/*    */   }
/*    */   
/*    */   public VIN getVIN() {
/* 51 */     return this.vin;
/*    */   }
/*    */   
/*    */   public void setButtonHandle(ButtonHandle handle) {
/* 55 */     if (this.buttonHandle instanceof ButtonHandleProxy) {
/* 56 */       this.buttonHandle = ((ButtonHandleProxy)this.buttonHandle).flush(handle);
/*    */     }
/*    */   }
/*    */   
/*    */   public List getPopupButtons(JPanel panel) {
/* 61 */     return null;
/*    */   }
/*    */   
/*    */   public PanelStack getPanelStack() {
/* 65 */     return MainFrame.getInstance();
/*    */   }
/*    */   
/*    */   public JPanel getContentPanel() {
/* 69 */     return (JPanel)this.panel;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\main\VINValidationWizardCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */