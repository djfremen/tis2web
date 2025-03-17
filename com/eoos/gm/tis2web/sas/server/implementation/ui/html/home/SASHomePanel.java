/*    */ package com.eoos.gm.tis2web.sas.server.implementation.ui.html.home;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SASHomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(SASHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 24 */       template = ApplicationContext.getInstance().loadFile(SASHomePanel.class, "saspanel.html", null).toString();
/* 25 */     } catch (Exception e) {
/* 26 */       log.error("unable to load template - error:" + e, e);
/* 27 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected DownloadButton buttonStartDownload;
/*    */ 
/*    */   
/*    */   private SASHomePanel(ClientContext context) {
/* 38 */     this.context = context;
/*    */     
/* 40 */     this.buttonStartDownload = new DownloadButton(context);
/* 41 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static SASHomePanel getInstance(ClientContext context) {
/* 46 */     synchronized (context.getLockObject()) {
/* 47 */       SASHomePanel instance = (SASHomePanel)context.getObject(SASHomePanel.class);
/* 48 */       if (instance == null) {
/* 49 */         instance = new SASHomePanel(context);
/* 50 */         context.storeObject(SASHomePanel.class, instance);
/*    */       } 
/* 52 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 57 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 59 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("sas.home.panel.message"));
/* 60 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 61 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementatio\\ui\html\home\SASHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */