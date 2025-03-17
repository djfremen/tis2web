/*    */ package com.eoos.gm.tis2web.snapshot.server.implementation.ui.html.home;
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
/*    */ public class SnapshotHomePanel
/*    */   extends HtmlElementContainerBase
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(SnapshotHomePanel.class);
/*    */   private static String template;
/*    */   
/*    */   static {
/*    */     try {
/* 24 */       template = ApplicationContext.getInstance().loadFile(SnapshotHomePanel.class, "snapshotpanel.html", null).toString();
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
/*    */   private SnapshotHomePanel(ClientContext context) {
/* 38 */     this.context = context;
/*    */     
/* 40 */     this.buttonStartDownload = new DownloadButton(context);
/* 41 */     addElement((HtmlElement)this.buttonStartDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized SnapshotHomePanel getInstance(ClientContext context) {
/* 46 */     SnapshotHomePanel instance = (SnapshotHomePanel)context.getObject(SnapshotHomePanel.class);
/* 47 */     if (instance == null) {
/* 48 */       instance = new SnapshotHomePanel(context);
/* 49 */       context.storeObject(SnapshotHomePanel.class, instance);
/*    */     } 
/* 51 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 55 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 57 */     StringUtilities.replace(code, "{MESSAGE}", this.context.getMessage("snapshot.home.panel.message"));
/* 58 */     StringUtilities.replace(code, "{BUTTON_START}", this.buttonStartDownload.getHtmlCode(params));
/* 59 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\server\implementatio\\ui\html\home\SnapshotHomePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */