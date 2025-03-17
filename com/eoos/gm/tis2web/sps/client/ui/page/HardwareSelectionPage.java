/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.HardwareSelectionRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.PairValueImpl;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ public class HardwareSelectionPage extends DefaultPage {
/*    */   protected String id;
/*    */   
/*    */   public HardwareSelectionPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 18 */     super(agent, gui, data, request);
/* 19 */     this.id = ((HardwareSelectionRequest)request).getControllerID();
/*    */   }
/*    */   
/*    */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/* 23 */     String id = ((HardwareSelectionRequest)request).getControllerID();
/* 24 */     return (id != null && id.equals(this.id));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/* 31 */     Attribute attribute = result.getAttribute();
/* 32 */     if (data != null) {
/* 33 */       if (this.id != null) {
/* 34 */         Value selection = data.getValue(attribute);
/* 35 */         Value current = this.data.getValue(attribute);
/* 36 */         if (current != null) {
/* 37 */           if (!current.equals(selection)) {
/* 38 */             this.data.set(attribute, (Value)new PairValueImpl(this.id, selection));
/*    */           } else {
/* 40 */             return false;
/*    */           } 
/* 42 */         } else if (selection != null) {
/* 43 */           this.data.set(attribute, (Value)new PairValueImpl(this.id, selection));
/*    */         } 
/*    */       } else {
/* 46 */         updateValue(data, attribute);
/*    */       } 
/*    */     }
/* 49 */     log.debug("handle hw-selection: " + getSelectionAttributeDisplay(result));
/* 50 */     this.gui.setNextButtonState(true);
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\HardwareSelectionPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */