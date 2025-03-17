/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPRDocumentNotSupportedException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String id;
/*    */   
/*    */   public CPRDocumentNotSupportedException(String id) {
/* 13 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 17 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\CPRDocumentNotSupportedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */