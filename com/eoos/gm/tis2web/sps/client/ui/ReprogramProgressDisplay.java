/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ReprogramProgress;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ 
/*    */ public class ReprogramProgressDisplay
/*    */   implements ReprogramProgress {
/*    */   public ReprogramProgressDisplay(long total) {
/* 10 */     init(total);
/*    */   }
/*    */   protected ReprogramProgressDisplayRequestImpl request;
/*    */   public void init(long total) {
/* 14 */     this.request = new ReprogramProgressDisplayRequestImpl();
/* 15 */     SPSFrame.getInstance().handleRequest((AssignmentRequest)this.request);
/* 16 */     this.request.start(total);
/*    */   }
/*    */   
/*    */   public void progress(long actual) {
/* 20 */     this.request.progress(actual);
/*    */   }
/*    */   
/*    */   public void onStatusChange(String labelKey) {
/* 24 */     this.request.onStatusChange(labelKey);
/*    */   }
/*    */   
/*    */   public void done() {
/* 28 */     this.request.done();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\ReprogramProgressDisplay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */