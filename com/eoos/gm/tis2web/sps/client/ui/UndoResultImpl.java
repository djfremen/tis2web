/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*    */ 
/*    */ public class UndoResultImpl implements UndoResult {
/*    */   protected RequestGroup group;
/*    */   
/*    */   public UndoResultImpl(RequestGroup group) {
/* 10 */     this.group = group;
/*    */   }
/*    */   
/*    */   public RequestGroup getRequestGroup() {
/* 14 */     return this.group;
/*    */   }
/*    */   
/*    */   public Status getStatus() {
/* 18 */     return null;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 22 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\UndoResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */