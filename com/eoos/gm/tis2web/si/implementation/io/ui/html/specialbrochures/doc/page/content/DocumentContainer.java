/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.content;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
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
/*    */ public abstract class DocumentContainer
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   protected ClientContext context;
/* 20 */   protected DocumentContainer predecessor = null;
/*    */ 
/*    */   
/*    */   public DocumentContainer(ClientContext context) {
/* 24 */     this.context = context;
/*    */   }
/*    */   
/*    */   public ClientContext getContext() {
/* 28 */     return this.context;
/*    */   }
/*    */   
/*    */   public void setPredecessor(DocumentContainer predecessor) {
/* 32 */     this.predecessor = predecessor;
/*    */   }
/*    */   
/*    */   public DocumentContainer getPredecessor() {
/* 36 */     return this.predecessor;
/*    */   }
/*    */   
/*    */   public abstract String getStyleSheet();
/*    */   
/*    */   public abstract String getTitle();
/*    */   
/*    */   public abstract String getEncoding();
/*    */   
/*    */   public abstract SIO getSIO();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\doc\page\content\DocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */