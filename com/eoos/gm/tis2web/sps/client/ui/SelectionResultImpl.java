/*    */ package com.eoos.gm.tis2web.sps.client.ui;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ 
/*    */ public class SelectionResultImpl implements SelectionResult {
/*    */   private Attribute attribute;
/*    */   
/*    */   public Attribute getAttribute() {
/* 10 */     return this.attribute;
/*    */   }
/*    */   
/*    */   public SelectionResultImpl(Attribute attribute) {
/* 14 */     this.attribute = attribute;
/*    */   }
/*    */   
/*    */   public Status getStatus() {
/* 18 */     return Status.OK;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 22 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\SelectionResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */