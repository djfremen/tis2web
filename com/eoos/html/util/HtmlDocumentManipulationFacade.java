/*    */ package com.eoos.html.util;
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
/*    */ public class HtmlDocumentManipulationFacade
/*    */   extends HtmlDocumentFacade
/*    */   implements HtmlDocumentFacade.Callback
/*    */ {
/*    */   protected StringBuffer document;
/*    */   
/*    */   public HtmlDocumentManipulationFacade(StringBuffer document) {
/* 19 */     this.document = document;
/* 20 */     this.callback = this;
/*    */   }
/*    */   
/*    */   public String getDocument() {
/* 24 */     return this.document.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\htm\\util\HtmlDocumentManipulationFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */