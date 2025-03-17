/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.waitpage;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WaitPage
/*    */   implements HtmlCodeRenderer
/*    */ {
/*    */   private static String template;
/*    */   protected Callback callback;
/*    */   
/*    */   static {
/*    */     try {
/* 21 */       template = ApplicationContext.getInstance().loadFile(WaitPage.class, "waitpage.html", null).toString();
/* 22 */     } catch (Exception e) {
/* 23 */       throw new RuntimeException();
/*    */     } 
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WaitPage(Callback callback) {
/* 43 */     this.callback = callback;
/*    */   }
/*    */ 
/*    */   
/*    */   protected WaitPage() {}
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 50 */     StringBuffer code = new StringBuffer(template);
/* 51 */     StringUtilities.replace(code, "{TITLE}", this.callback.getTitle());
/* 52 */     StringUtilities.replace(code, "{STYLESHEET}", this.callback.getStylesheet());
/* 53 */     StringUtilities.replace(code, "{RELOAD}", String.valueOf(this.callback.getReload() / 1000L));
/* 54 */     StringUtilities.replace(code, "{URL}", this.callback.getURL());
/* 55 */     StringUtilities.replace(code, "{WAIT_MESSAGE}", this.callback.getMessage());
/* 56 */     return code.toString();
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     String getTitle();
/*    */     
/*    */     String getStylesheet();
/*    */     
/*    */     long getReload();
/*    */     
/*    */     String getURL();
/*    */     
/*    */     String getMessage();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\waitpage\WaitPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */