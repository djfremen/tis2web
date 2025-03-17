/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/* 19 */   protected DocumentContainer predecessor = null;
/*    */ 
/*    */   
/*    */   public DocumentContainer(ClientContext context) {
/* 23 */     this.context = context;
/*    */   }
/*    */   
/*    */   public ClientContext getContext() {
/* 27 */     return this.context;
/*    */   }
/*    */   
/*    */   public void setPredecessor(DocumentContainer predecessor) {
/* 31 */     this.predecessor = predecessor;
/*    */   }
/*    */   
/*    */   public DocumentContainer getPredecessor() {
/* 35 */     return this.predecessor;
/*    */   }
/*    */   
/*    */   public abstract String getStyleSheet();
/*    */   
/*    */   public abstract String getTitle();
/*    */   
/*    */   public abstract String getEncoding();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\DocumentContainer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */