/*     */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.ctrl.RequestHandler;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.util.List;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseCustomizeJPanel
/*     */   extends JPanel
/*     */   implements RequestHandler, ValueRetrieval
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private BaseCustomizeJPanel previousPanel;
/*  31 */   private RequestGroup groupID = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseCustomizeJPanel(BaseCustomizeJPanel previous) {
/*  36 */     setLayout(new GridBagLayout());
/*  37 */     this.previousPanel = previous;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClosePanel(boolean isBackAction) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOpenPanel() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onNextAction() {
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResetAction() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCancelAction() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBackAction() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResizeForm() {}
/*     */ 
/*     */   
/*     */   public List getNewButtons() {
/*  72 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Value getValue(Attribute attr) {
/*     */     try {
/*  80 */       BaseCustomizeJPanel crtPanel = this;
/*  81 */       BaseCustomizeJPanel prevPanel = null;
/*     */       
/*  83 */       while (crtPanel != null) {
/*     */         
/*  85 */         Value selected = getSelectedValue(crtPanel, attr);
/*  86 */         if (selected != null) {
/*  87 */           return selected;
/*     */         }
/*  89 */         prevPanel = getPreviousPanel(crtPanel);
/*  90 */         crtPanel = prevPanel;
/*     */       } 
/*  92 */     } catch (Exception e) {
/*  93 */       System.out.println("Exception in methode getValue() " + e.getMessage());
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Value getSelectedValue(JPanel panel, Attribute attr) {
/*     */     try {
/* 110 */       Component[] comp = panel.getComponents();
/* 111 */       for (int i = 0; i < comp.length; i++) {
/*     */         
/* 113 */         if (comp[i] instanceof AttributeInput && (
/* 114 */           (AttributeInput)comp[i]).getAttribute() != null && 
/* 115 */           attr.equals(((AttributeInput)comp[i]).getAttribute())) {
/* 116 */           return ((ValueRetrieval)comp[i]).getValue(attr);
/*     */         }
/*     */ 
/*     */         
/* 120 */         if (comp[i] instanceof JPanel) {
/* 121 */           Value foundValue = getSelectedValue((JPanel)comp[i], attr);
/* 122 */           if (foundValue != null)
/* 123 */             return foundValue; 
/*     */         } 
/*     */       } 
/* 126 */     } catch (Exception e) {
/* 127 */       System.out.println("Exception in methode getSelectedValue() " + e.getMessage());
/*     */     } 
/*     */     
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   public BaseCustomizeJPanel getPreviousPanel() {
/* 134 */     return this.previousPanel;
/*     */   }
/*     */   
/*     */   public BaseCustomizeJPanel getPreviousPanel(BaseCustomizeJPanel crtPanel) {
/* 138 */     return crtPanel.getPreviousPanel();
/*     */   }
/*     */   
/*     */   public RequestGroup getRequestGroup() {
/* 142 */     return this.groupID;
/*     */   }
/*     */   
/*     */   public void setRequestGroup(RequestGroup groupID) {
/* 146 */     this.groupID = groupID;
/*     */   }
/*     */   
/*     */   public abstract void handleRequest(AssignmentRequest paramAssignmentRequest);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\BaseCustomizeJPanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */