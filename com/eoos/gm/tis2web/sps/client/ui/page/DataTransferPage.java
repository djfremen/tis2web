/*    */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*    */ 
/*    */ 
/*    */ public class DataTransferPage
/*    */   extends DefaultPage
/*    */ {
/*    */   public DataTransferPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/* 17 */     super(agent, gui, data, request);
/*    */   }
/*    */   
/*    */   public boolean handle(AssignmentRequest request, AttributeValueMapExt data) {
/* 21 */     if (request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest) {
/* 22 */       if (data.getValue(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY) == null) {
/* 23 */         data.set(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY, CommonValue.OK);
/*    */       }
/* 25 */       this.agent.triggerRequest();
/* 26 */       return true;
/* 27 */     }  if (request instanceof com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest) {
/* 28 */       transferRequestGroup(request);
/* 29 */       this.gui.handleRequest(request);
/* 30 */       return true;
/*    */     } 
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activate(Object savepoint) {
/* 37 */     this.savepoint = savepoint;
/* 38 */     this.data.set(CommonAttribute.CONFIRM_REPROGRAM_DISPLAY, CommonValue.OK);
/* 39 */     if (this.request instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramDisplayRequest) {
/* 40 */       this.gui.handleRequest(this.request);
/* 41 */       this.agent.triggerRequest();
/* 42 */     } else if (this.request instanceof com.eoos.gm.tis2web.sps.common.DownloadProgressDisplayRequest) {
/* 43 */       this.gui.handleRequest(this.request);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/* 48 */     if (this.data.getValue(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED) != null) {
/* 49 */       this.data.set(CommonAttribute.CONFIRM_PROGRAMMING_DATA_DOWNLOAD_FINISHED, CommonValue.OK);
/*    */     }
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public UIPage undo() {
/* 56 */     return super.undo();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\DataTransferPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */