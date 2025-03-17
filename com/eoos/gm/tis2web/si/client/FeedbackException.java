/*    */ package com.eoos.gm.tis2web.si.client;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.I18NSupportV2;
/*    */ 
/*    */ public class FeedbackException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String key;
/*    */   
/*    */   public FeedbackException(String key) {
/* 12 */     this.key = key;
/*    */   }
/*    */   
/*    */   public FeedbackException(String key, Throwable t) {
/* 16 */     super(t);
/* 17 */     this.key = key;
/*    */   }
/*    */   
/*    */   public String getMessage(I18NSupportV2.FixedLocale i18n) {
/* 21 */     return i18n.getText(this.key, I18NSupportV2.Type.MESSAGE);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\FeedbackException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */