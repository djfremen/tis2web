/*    */ package com.eoos.gm.tis2web.sas.client.ui.main;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.i18n.LabelResource;
/*    */ import com.eoos.gm.tis2web.sas.client.system.SASClientContextProvider;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.ToolSelectionPanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.panel.WizardStepPanel;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandle;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.ButtonHandleProxy;
/*    */ import com.eoos.gm.tis2web.sas.client.ui.util.PanelStack;
/*    */ import com.eoos.gm.tis2web.sas.common.model.tool.Tool;
/*    */ import com.eoos.gm.tis2web.sas.common.system.LabelResourceProvider;
/*    */ import java.awt.Container;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JPanel;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToolSelectionWizardCallback
/*    */   implements ToolSelectionPanel.Callback, WizardStepPanel.Callback
/*    */ {
/* 29 */   private static final Logger log = Logger.getLogger(ToolSelectionWizardCallback.class);
/*    */   
/*    */   private ToolSelectionPanel contentPanel;
/*    */   
/* 33 */   private ButtonHandle buttonHandle = (ButtonHandle)new ButtonHandleProxy();
/*    */   
/*    */   public ToolSelectionWizardCallback(List tools) {
/* 36 */     this.contentPanel = new ToolSelectionPanel(this, tools);
/*    */   }
/*    */ 
/*    */   
/*    */   public Locale getLocale() {
/* 41 */     return SASClientContextProvider.getInstance().getContext().getLocale();
/*    */   }
/*    */   
/*    */   private LabelResource getLabelResource() {
/* 45 */     return LabelResourceProvider.getInstance().getLabelResource();
/*    */   }
/*    */   
/*    */   public String getMessage(String key) {
/* 49 */     return getLabelResource().getMessage(getLocale(), key);
/*    */   }
/*    */   
/*    */   public String getLabel(String key) {
/* 53 */     return getLabelResource().getLabel(getLocale(), key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSelectionChanged(Tool tool) {}
/*    */   
/*    */   public void setButtonHandle(ButtonHandle handle) {
/* 60 */     if (this.buttonHandle instanceof ButtonHandleProxy) {
/* 61 */       this.buttonHandle = ((ButtonHandleProxy)this.buttonHandle).flush(handle);
/*    */     }
/*    */   }
/*    */   
/*    */   public ButtonHandle getButtonHandle() {
/* 66 */     return this.buttonHandle;
/*    */   }
/*    */   
/*    */   public List getPopupButtons(final JPanel panel) {
/* 70 */     JButton settingsButton = new JButton(getLabel("settings"));
/* 71 */     settingsButton.addActionListener(new ActionListener()
/*    */         {
/*    */           public void actionPerformed(ActionEvent e) {
/* 74 */             Container container = panel;
/* 75 */             while (container != null && !(container instanceof MainFrame)) {
/* 76 */               container = container.getParent();
/*    */             }
/* 78 */             if (container != null) {
/* 79 */               ((MainFrame)container).showSettingsDialog();
/*    */             } else {
/* 81 */               ToolSelectionWizardCallback.log.warn("unable to find main frame, ignoring");
/*    */             } 
/*    */           }
/*    */         });
/* 85 */     return Collections.singletonList(settingsButton);
/*    */   }
/*    */   
/*    */   public PanelStack getPanelStack() {
/* 89 */     return MainFrame.getInstance();
/*    */   }
/*    */   
/*    */   public JPanel getContentPanel() {
/* 93 */     return (JPanel)this.contentPanel;
/*    */   }
/*    */   
/*    */   public Tool getSelectedTool() {
/* 97 */     return this.contentPanel.getSelectedTool();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\ui\main\ToolSelectionWizardCallback.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */