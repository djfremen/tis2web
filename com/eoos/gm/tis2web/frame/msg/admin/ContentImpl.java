/*    */ package com.eoos.gm.tis2web.frame.msg.admin;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContentImpl
/*    */   implements IMessage.IContent
/*    */ {
/*    */   private Locale locale;
/* 11 */   private String title = null;
/*    */   
/* 13 */   private String text = null;
/*    */   
/*    */   public ContentImpl(Locale locale) {
/* 16 */     this.locale = locale;
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 20 */     this.title = title;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 24 */     this.text = text;
/*    */   }
/*    */   
/*    */   public Locale getLocale() {
/* 28 */     return this.locale;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 32 */     return this.text;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 36 */     return this.title;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\ContentImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */