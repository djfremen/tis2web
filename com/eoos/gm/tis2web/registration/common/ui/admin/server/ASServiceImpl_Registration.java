/*    */ package com.eoos.gm.tis2web.registration.common.ui.admin.server;
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
/*    */ public class ASServiceImpl_Registration
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 17 */   private static ASServiceImpl_Registration instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_Registration(Configuration configuration) {
/* 22 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_Registration createInstance(Configuration configuration) {
/* 27 */     instance = new ASServiceImpl_Registration(configuration);
/* 28 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Registration getInstance() {
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 36 */     return ComponentAccessPermission.getInstance(context).check("admin.authorization");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 40 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 44 */     return ApplicationContext.getInstance().getLabel(locale, "authorization.admin");
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\admin\server\ASServiceImpl_Registration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */