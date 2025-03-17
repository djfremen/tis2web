/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.admin.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_SWDLLog
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 18 */   private static ASServiceImpl_SWDLLog instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_SWDLLog(Configuration configuration) {
/* 23 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SWDLLog createInstance(Configuration configuration) {
/* 27 */     instance = new ASServiceImpl_SWDLLog(configuration);
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SWDLLog getInstance() {
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 36 */     return ComponentAccessPermission.getInstance(context).check("admin.swdllog");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 40 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 44 */     return ApplicationContext.getInstance().getLabel(locale, "swdl.log");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 49 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 53 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\admin\ASServiceImpl_SWDLLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */