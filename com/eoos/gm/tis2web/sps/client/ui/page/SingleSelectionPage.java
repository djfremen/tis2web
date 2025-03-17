/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ 
/*    */ public class SingleSelectionPage
/*    */   extends DefaultPage {
/*    */   public SingleSelectionPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 14 */     super(agent, gui, data, request);
/*    */   }
/*    */   
/*    */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/* 18 */     log.debug("handle selection: " + getSelectionAttributeDisplay(result));
/* 19 */     Attribute selection = result.getAttribute();
/* 20 */     updateValue(data, selection);
/* 21 */     this.gui.setNextButtonState(true);
/* 22 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\SingleSelectionPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */