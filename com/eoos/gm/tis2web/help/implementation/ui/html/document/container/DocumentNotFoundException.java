/*    */ package com.eoos.gm.tis2web.help.implementation.ui.html.document.container;
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
/*    */ public class DocumentNotFoundException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String id;
/*    */   
/*    */   public DocumentNotFoundException(String id) {
/* 19 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 23 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\document\container\DocumentNotFoundException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */