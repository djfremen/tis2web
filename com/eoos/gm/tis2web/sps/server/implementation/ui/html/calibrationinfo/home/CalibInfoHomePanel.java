/*    */ package com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.ui.html.calibrationinfo.home.entry.EntryPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementStack;
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
/*    */ public class CalibInfoHomePanel
/*    */   extends HtmlElementStack
/*    */ {
/* 22 */   private static final Logger log = Logger.getLogger(CalibInfoHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 27 */       template = ApplicationContext.getInstance().loadFile(CalibInfoHomePanel.class, "calibhome.html", null).toString();
/* 28 */     } catch (Exception e) {
/* 29 */       log.error("unable to load template - error:" + e, e);
/* 30 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientContext context;
/*    */ 
/*    */   
/*    */   private CalibInfoHomePanel(ClientContext context) {
/* 39 */     this.context = context;
/*    */     
/* 41 */     EntryPanel panel = new EntryPanel(context, this);
/* 42 */     push((HtmlElement)panel);
/*    */   }
/*    */ 
/*    */   
/*    */   public static CalibInfoHomePanel getInstance(ClientContext context) {
/* 47 */     synchronized (context.getLockObject()) {
/* 48 */       CalibInfoHomePanel instance = (CalibInfoHomePanel)context.getObject(CalibInfoHomePanel.class);
/* 49 */       if (instance == null) {
/* 50 */         instance = new CalibInfoHomePanel(context);
/* 51 */         context.storeObject(CalibInfoHomePanel.class, instance);
/*    */       } 
/* 53 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public synchronized String getHtmlCode(Map params) {
/* 58 */     StringBuffer tmp = new StringBuffer(template);
/* 59 */     StringUtilities.replace(tmp, "{CONTENT}", super.getHtmlCode(params));
/* 60 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementatio\\ui\html\calibrationinfo\home\CalibInfoHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */