/*    */ package com.eoos.gm.tis2web.kdr.ui.html.home;
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
/*    */ public class HomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 21 */   private static final Logger log = Logger.getLogger(HomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 26 */       template = ApplicationContext.getInstance().loadFile(HomePanel.class, "kdrpanel.html", null).toString();
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to load template - error:" + e, e);
/* 29 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */ 
/*    */   
/*    */   protected DownloadButton buttonStartDownload;
/*    */ 
/*    */   
/*    */   private HomePanel(ClientContext context) {
/* 42 */     this.context = context;
/*    */     
/* 44 */     this.buttonStartDownload = new DownloadButton(context);
/* 45 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static HomePanel getInstance(ClientContext context) {
/* 53 */     synchronized (context.getLockObject()) {
/* 54 */       HomePanel instance = (HomePanel)context.getObject(HomePanel.class);
/* 55 */       if (instance == null) {
/* 56 */         instance = new HomePanel(context);
/* 57 */         context.storeObject(HomePanel.class, instance);
/*    */       } 
/* 59 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 64 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 66 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("kdr.home.panel.message"));
/* 67 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 68 */     return code.toString();
/*    */   }
/*    */   
/*    */   public String getJNLPDescriptor(HttpServletRequest request) {
/* 72 */     return this.buttonStartDownload.getJNLPDescriptor(request);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\kd\\ui\html\home\HomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */