/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonException;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ 
/*    */ public class InputPage
/*    */   extends DefaultPage
/*    */ {
/*    */   public InputPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 14 */     super(agent, gui, data, request);
/*    */   }
/*    */   
/*    */   public void activate(Object savepoint) {
/* 18 */     super.activate(savepoint);
/* 19 */     this.gui.setNextButtonState(true);
/*    */   }
/*    */   
/*    */   public boolean handleInput() {
/* 23 */     Value input = this.gui.getValue(this.request.getAttribute());
/* 24 */     if (input == null || input.toString().length() == 0) {
/* 25 */       this.agent.handleException(CommonException.NoVCI.getID());
/* 26 */       return true;
/*    */     } 
/* 28 */     this.data.set(this.request.getAttribute(), input);
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\InputPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */