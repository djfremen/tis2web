/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log.admin;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.log.admin.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_SPSEventLog
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 18 */   private static ASServiceImpl_SPSEventLog instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_SPSEventLog(Configuration configuration) {
/* 23 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_SPSEventLog createInstance(Configuration configuration) {
/* 28 */     instance = new ASServiceImpl_SPSEventLog(configuration);
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SPSEventLog getInstance() {
/* 33 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 37 */     return ComponentAccessPermission.getInstance(context).check("admin.spslog");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 41 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 45 */     return ApplicationContext.getInstance().getLabel(locale, "sps.event.log");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 49 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 53 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\admin\ASServiceImpl_SPSEventLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */