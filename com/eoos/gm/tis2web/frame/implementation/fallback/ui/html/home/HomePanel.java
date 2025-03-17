/*    */ package com.eoos.gm.tis2web.frame.implementation.fallback.ui.html.home;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 25 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "homepanel.html", null).toString();
/* 26 */     } catch (Exception e) {
/* 27 */       log.error("unable to load template - error:" + e, e);
/* 28 */       throw new RuntimeException();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private ClientContext context;
/*    */   
/*    */   public HomePanel(ClientContext context) {
/* 36 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 41 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 43 */     StringUtilities.replace(code, "{MESSAGE}", "<!--ApplicationNotAvailableForSalesmake--><span class=\"warning\">" + this.context.getMessage("fallback.no.instance") + "</span>");
/*    */     
/* 45 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\fallbac\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */