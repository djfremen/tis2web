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
/*    */ public class ASServiceImpl_SWK
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 17 */   private static ASServiceImpl_SWK instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_SWK(Configuration configuration) {
/* 22 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SWK createInstance(Configuration configuration) {
/* 26 */     instance = new ASServiceImpl_SWK(configuration);
/* 27 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_SWK getInstance() {
/* 31 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 35 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("admin.ams"));
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 39 */     return (HtmlElement)MainPanelSWK.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 43 */     return ApplicationContext.getInstance().getLabel(locale, "ams");
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\admin\ASServiceImpl_SWK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */