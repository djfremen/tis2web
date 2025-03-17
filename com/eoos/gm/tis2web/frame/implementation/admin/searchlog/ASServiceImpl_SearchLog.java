/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.searchlog;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.searchlog.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ASServiceImpl_SearchLog
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 16 */   private static ASServiceImpl_SearchLog instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_SearchLog(Configuration configuration) {
/* 21 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_SearchLog createInstance(Configuration configuration) {
/* 26 */     instance = new ASServiceImpl_SearchLog(configuration);
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SearchLog getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 35 */     return context.isSpecialAccess();
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 39 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 43 */     return ApplicationContext.getInstance().getLabel(locale, "search.in.log");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 47 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 51 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\searchlog\ASServiceImpl_SearchLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */