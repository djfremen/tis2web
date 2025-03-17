/*    */ package com.eoos.gm.tis2web.sps.client.ui.swing;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.ui.datamodel.AttributeInput;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import javax.swing.JEditorPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomizeHtmlEditorPane
/*    */   extends JEditorPane
/*    */   implements AttributeInput, ValueRetrieval
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private DisplayRequest dataReq;
/*    */   
/*    */   public CustomizeHtmlEditorPane(DisplayRequest dataRequest) {
/* 23 */     this.dataReq = dataRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomizeHtmlEditorPane() {}
/*    */ 
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\swing\CustomizeHtmlEditorPane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */