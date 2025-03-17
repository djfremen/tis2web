/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MessageBoxBase
/*    */   extends DialogBase
/*    */ {
/*    */   protected String message;
/*    */   protected String caption;
/*    */   
/*    */   public MessageBoxBase(ClientContext context, String caption, String message) {
/* 20 */     super(context);
/* 21 */     this.caption = caption;
/* 22 */     this.message = message;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getTitle(Map params) {
/* 27 */     return getIconCode(params) + "&nbsp;" + getCaption(params);
/*    */   }
/*    */   
/*    */   protected String getContent(Map params) {
/* 31 */     return "<table width=\"100%\"><tr><td align=\"center\">" + getMessage(params) + "</td></tr><tr><td align=\"center\">" + getButtonsCode(params) + "</td></tr></table>";
/*    */   }
/*    */   
/*    */   protected abstract String getIconCode(Map paramMap);
/*    */   
/*    */   protected String getCaption(Map params) {
/* 37 */     return this.caption;
/*    */   }
/*    */   
/*    */   protected String getMessage(Map params) {
/* 41 */     return this.message;
/*    */   }
/*    */   
/*    */   protected abstract String getButtonsCode(Map paramMap);
/*    */   
/*    */   protected String getTitleAlign() {
/* 47 */     return "left";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\MessageBoxBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */