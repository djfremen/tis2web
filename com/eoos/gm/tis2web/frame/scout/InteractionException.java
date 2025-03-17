/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eoos.html.ResultObject;
/*    */ 
/*    */ public class InteractionException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private ResultObject response;
/*    */   
/*    */   public InteractionException(ResultObject response) {
/* 12 */     this.response = response;
/*    */   }
/*    */   
/*    */   public ResultObject getResponse() {
/* 16 */     return this.response;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\InteractionException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */