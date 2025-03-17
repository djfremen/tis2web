/*    */ package com.eoos.gm.tis2web.swdl.server.ui.html.home;
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
/*    */ public class HomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 24 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "swdlpanel.html", null).toString();
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
/*    */   private HomePanel(ClientContext context) {
/* 38 */     this.context = context;
/*    */     
/* 40 */     this.buttonStartDownload = new DownloadButton(context);
/* 41 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static HomePanel getInstance(ClientContext context) {
/* 46 */     synchronized (context.getLockObject()) {
/* 47 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/* 48 */       if (instance == null) {
/* 49 */         instance = new HomePanel(context);
/* 50 */         context.storeObject(HomePanel.class, instance);
/*    */       } 
/* 52 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 57 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 59 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("swdl.home.panel.message"));
/* 60 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 61 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\serve\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */