/*    */ package com.eoos.gm.tis2web.techlineprint.server.implementation.ui.html.home;
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
/*    */ public class TechlinePrintHomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(TechlinePrintHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 24 */       template = ApplicationContext.getInstance().loadFile(TechlinePrintHomePanel.class, "techlineprintpanel.html", null).toString();
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
/*    */   private TechlinePrintHomePanel(ClientContext context) {
/* 38 */     this.context = context;
/*    */     
/* 40 */     this.buttonStartDownload = new DownloadButton(context);
/* 41 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized TechlinePrintHomePanel getInstance(ClientContext context) {
/* 46 */     TechlinePrintHomePanel instance = (TechlinePrintHomePanel)context.getObject(TechlinePrintHomePanel.class);
/* 47 */     if (instance == null) {
/* 48 */       instance = new TechlinePrintHomePanel(context);
/* 49 */       context.storeObject(TechlinePrintHomePanel.class, instance);
/*    */     } 
/* 51 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 55 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 57 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("techlineprint.home.panel.message"));
/* 58 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 59 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\server\implementatio\\ui\html\home\TechlinePrintHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */