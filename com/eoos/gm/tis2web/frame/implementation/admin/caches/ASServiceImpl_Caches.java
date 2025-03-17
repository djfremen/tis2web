/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.caches;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.caches.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ASServiceImpl_Caches
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 17 */   private static ASServiceImpl_Caches instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   private ASServiceImpl_Caches(Configuration configuration) {
/* 22 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Caches createInstance(Configuration configuration) {
/* 26 */     if (instance != null) {
/* 27 */       throw new IllegalStateException();
/*    */     }
/* 29 */     instance = new ASServiceImpl_Caches(configuration);
/* 30 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Caches getInstance() {
/* 34 */     if (instance != null) {
/* 35 */       return instance;
/*    */     }
/* 37 */     throw new IllegalStateException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 42 */     return ComponentAccessPermission.getInstance(context).check("admin.caches");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 46 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 50 */     return ApplicationContext.getInstance().getLabel(locale, "caches");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 54 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 58 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\caches\ASServiceImpl_Caches.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */