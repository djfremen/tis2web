/*    */ package com.eoos.gm.tis2web.swdl.client.ui;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AddBtnActionListener
/*    */   implements ActionListener
/*    */ {
/* 25 */   private long buttonId = -1L;
/* 26 */   private SDButtonPanel panel = null;
/*    */   
/*    */   public AddBtnActionListener(long buttonId, SDButtonPanel panel) {
/* 29 */     this.buttonId = buttonId;
/* 30 */     this.panel = panel;
/*    */   }
/*    */   
/*    */   public void actionPerformed(ActionEvent evt) {
/* 34 */     this.panel.jButtonAdditionalActionPerformed(this.buttonId);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\clien\\ui\AddBtnActionListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */