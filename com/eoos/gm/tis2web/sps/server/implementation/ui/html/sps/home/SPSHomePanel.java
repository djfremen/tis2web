/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.sps.home;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSHomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(SPSHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(SPSHomePanel.class, "spspanel.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */   
/*    */   protected DownloadButton buttonStartDownload;
/*    */ 
/*    */   
/*    */   private SPSHomePanel(ClientContext context) {
/* 40 */     this.context = context;
/*    */     
/* 42 */     this.buttonStartDownload = new DownloadButton(context);
/* 43 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static SPSHomePanel getInstance(ClientContext context) {
/* 48 */     synchronized (context.getLockObject()) {
/* 49 */       SPSHomePanel instance = (SPSHomePanel)context.getObject(SPSHomePanel.class);
/* 50 */       if (instance == null) {
/* 51 */         instance = new SPSHomePanel(context);
/* 52 */         context.storeObject(SPSHomePanel.class, instance);
/*    */       } 
/* 54 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getJNLPDescriptor(HttpServletRequest request) {
/* 59 */     return this.buttonStartDownload.getJNLPDescriptor(request);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 63 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 65 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("sps.home.panel.message"));
/* 66 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 67 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\sps\home\SPSHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */