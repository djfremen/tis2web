/*    */ package com.eoos.gm.tis2web.frame.export.common.ui.html.logout;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReloginMessagePage
/*    */   implements HtmlCodeRenderer
/*    */ {
/* 20 */   private static ReloginMessagePage instance = null;
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 25 */       template = ApplicationContext.getInstance().loadFile(ReloginMessagePage.class, "reloginmessagepage.html", null).toString();
/* 26 */     } catch (Exception e) {
/* 27 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static final String KEY_LOCALE = "locale";
/*    */   public static final String KEY_PORTAL_URL = "portal.url";
/*    */   
/*    */   public static synchronized ReloginMessagePage getInstance() {
/* 36 */     if (instance == null) {
/* 37 */       instance = new ReloginMessagePage();
/*    */     }
/* 39 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 47 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 49 */     Locale locale = (Locale)params.get("locale");
/* 50 */     if (locale == null) {
/* 51 */       locale = Locale.getDefault();
/*    */     }
/*    */     
/* 54 */     StringUtilities.replace(code, "{TEXT}", ApplicationContext.getInstance().getMessage(locale, "reloginpage.text"));
/* 55 */     StringUtilities.replace(code, "{URL}", (String)params.get("portal.url"));
/* 56 */     StringUtilities.replace(code, "{LABEL_RELOGIN}", ApplicationContext.getInstance().getLabel(locale, "relogin"));
/*    */     
/* 58 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\ui\html\logout\ReloginMessagePage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */