/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import com.javio.webwindow.WebWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeHtmlRender
/*    */   extends WebWindow
/*    */   implements AttributeInput, ValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private DisplayRequest dataReq;
/*    */   
/*    */   public CustomizeHtmlRender(DisplayRequest dataRequest) {
/* 21 */     super(false);
/* 22 */     this.dataReq = dataRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomizeHtmlRender() {
/* 28 */     super(false);
/*    */   }
/*    */   
/*    */   public void setRequest(DisplayRequest dataRequest) {
/* 32 */     this.dataReq = dataRequest;
/*    */   }
/*    */   
/*    */   public Attribute getAttribute() {
/* 36 */     if (this.dataReq == null) {
/* 37 */       return null;
/*    */     }
/* 39 */     return this.dataReq.getAttribute();
/*    */   }
/*    */ 
/*    */   
/*    */   public Value getValue(Attribute attr) {
/* 44 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeHtmlRender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */