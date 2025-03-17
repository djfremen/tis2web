/*    */ package com.eoos.gm.tis2web.frame.dls.server.admin;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_DLS
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 17 */   private static ASServiceImpl_DLS instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_DLS(Configuration configuration) {
/* 22 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_DLS createInstance(Configuration configuration) {
/* 26 */     instance = new ASServiceImpl_DLS(configuration);
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_DLS getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 35 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("admin.dls"));
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 39 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 43 */     return ApplicationContext.getInstance().getLabel(locale, "dls");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 48 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 52 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\ASServiceImpl_DLS.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */