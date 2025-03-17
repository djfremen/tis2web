/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export;
/*    */ 
/*    */ public class SPSException
/*    */   extends Exception {
/*    */   private static final long serialVersionUID = 1L;
/*  6 */   private String messageKey = null;
/*    */   
/*    */   public SPSException(CustomException e) {
/*  9 */     super(e.getID());
/* 10 */     this.messageKey = e.getID();
/*    */   }
/*    */   
/*    */   protected SPSException(String messageKey) {
/* 14 */     super(messageKey);
/* 15 */     this.messageKey = messageKey;
/*    */   }
/*    */   
/*    */   public String getMessageKey() {
/* 19 */     return this.messageKey;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\SPSException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */