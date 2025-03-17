/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.page;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.input.ClickButtonElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BackButton
/*    */   extends ClickButtonElement
/*    */ {
/*    */   private DocumentPage page;
/*    */   private final ClientContext context;
/*    */   
/*    */   public BackButton(DocumentPage page, String parameterName, String targetBookmark, ClientContext context) {
/* 18 */     super(parameterName, targetBookmark);
/* 19 */     this.page = page;
/* 20 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 24 */     return this.context.getLabel("back");
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 28 */     if (this.page.documentContainer.getPredecessor() != null) {
/* 29 */       this.page.setDocumentContainer(this.page.documentContainer.getPredecessor());
/*    */     }
/* 31 */     return this.page.getPage(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\page\BackButton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */