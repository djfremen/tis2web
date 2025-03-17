/*    */ package com.eoos.gm.tis2web.tech2view.server.implementation.ui.html.home;
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
/*    */ public class Tech2ViewHomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(Tech2ViewHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 24 */       template = ApplicationContext.getInstance().loadFile(Tech2ViewHomePanel.class, "tech2viewpanel.html", null).toString();
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
/*    */   private Tech2ViewHomePanel(ClientContext context) {
/* 38 */     this.context = context;
/*    */     
/* 40 */     this.buttonStartDownload = new DownloadButton(context);
/* 41 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized Tech2ViewHomePanel getInstance(ClientContext context) {
/* 46 */     Tech2ViewHomePanel instance = (Tech2ViewHomePanel)context.getObject(Tech2ViewHomePanel.class);
/* 47 */     if (instance == null) {
/* 48 */       instance = new Tech2ViewHomePanel(context);
/* 49 */       context.storeObject(Tech2ViewHomePanel.class, instance);
/*    */     } 
/* 51 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 55 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 57 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("tech2view.home.panel.message"));
/* 58 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 59 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\server\implementatio\\ui\html\home\Tech2ViewHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */