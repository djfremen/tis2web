/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchNotResultException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String id;
/*    */   
/*    */   public SearchNotResultException(String id) {
/* 16 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 20 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\SearchNotResultException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */