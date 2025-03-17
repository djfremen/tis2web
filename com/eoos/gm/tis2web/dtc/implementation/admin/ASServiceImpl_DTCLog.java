/*    */ package com.eoos.gm.tis2web.dtc.implementation.admin;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.dtc.implementation.admin.ui.html.main.MainPanel;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ASServiceImpl_DTCLog
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 16 */   private static ASServiceImpl_DTCLog instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_DTCLog(Configuration configuration) {
/* 21 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_DTCLog createInstance(Configuration configuration) {
/* 26 */     instance = new ASServiceImpl_DTCLog(configuration);
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_DTCLog getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 36 */     return context.isSpecialAccess();
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 40 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 44 */     return ApplicationContext.getInstance().getLabel(locale, "dtc.log");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 48 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 52 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\implementation\admin\ASServiceImpl_DTCLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */