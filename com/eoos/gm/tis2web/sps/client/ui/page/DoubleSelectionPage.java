/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoubleSelectionPage
/*    */   extends DefaultPage
/*    */ {
/*    */   public DoubleSelectionPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 20 */     super(agent, gui, data, request);
/* 21 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ToolSelectionRequest) {
/* 22 */       activate(data.getSavePoint());
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/* 27 */     if (request instanceof com.eoos.gm.tis2web.sps.common.ProcessSelectionRequest) {
/* 28 */       transferRequestGroup(request);
/* 29 */       this.gui.handleRequest(request);
/* 30 */       return true;
/* 31 */     }  if (request instanceof com.eoos.gm.tis2web.sps.common.TypeSelectionRequest) {
/* 32 */       transferRequestGroup(request);
/* 33 */       this.gui.handleRequest(request);
/* 34 */       return true;
/* 35 */     }  if (request instanceof com.eoos.gm.tis2web.sps.common.SequenceSelectionRequest) {
/* 36 */       transferRequestGroup(request);
/* 37 */       this.gui.handleRequest(request);
/* 38 */       return true;
/* 39 */     }  if (request instanceof com.eoos.gm.tis2web.sps.common.FunctionSelectionRequest) {
/* 40 */       transferRequestGroup(request);
/* 41 */       this.gui.handleRequest(request);
/* 42 */       return true;
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/* 49 */     log.debug("handle selection: " + getSelectionAttributeDisplay(result));
/* 50 */     Attribute selection = result.getAttribute();
/* 51 */     if (selection.equals(CommonAttribute.DEVICE)) {
/* 52 */       this.agent.blockGUI();
/* 53 */     } else if (!selection.equals(CommonAttribute.TOOL)) {
/*    */       
/* 55 */       if (selection.equals(CommonAttribute.CONTROLLER)) {
/*    */         
/* 57 */         this.agent.blockGUI();
/* 58 */       } else if (selection.equals(CommonAttribute.SEQUENCE)) {
/*    */         
/* 60 */         this.agent.blockGUI();
/* 61 */       } else if (selection.equals(CommonAttribute.FUNCTION)) {
/*    */         
/* 63 */         this.agent.blockGUI();
/* 64 */       } else if (selection.equals(CommonAttribute.CONTROLLER_METHOD)) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 73 */         this.gui.setNextButtonState(true);
/*    */       } else {
/* 75 */         this.gui.setNextButtonState(true);
/*    */       } 
/* 77 */     }  updateValue(data, selection);
/* 78 */     return selection.equals(CommonAttribute.CONTROLLER_METHOD);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\DoubleSelectionPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */