/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.login;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.login.ui.html.main.MainPanel;
/*    */ import com.eoos.gm.tis2web.frame.login.log.LoginLogFacade;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_LoginLog
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 19 */   private static ASServiceImpl_LoginLog instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_LoginLog(Configuration configuration) {
/* 24 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_LoginLog createInstance(Configuration configuration) {
/* 28 */     instance = new ASServiceImpl_LoginLog(configuration);
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_LoginLog getInstance() {
/* 33 */     return instance;
/*    */   }
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 37 */     boolean ret = LoginLogFacade.getInstance().isEnabled();
/* 38 */     ret = (ret && LoginLogFacade.getInstance().supportsQuery());
/* 39 */     ret = (ret && ComponentAccessPermission.getInstance(context).check("admin.loginlog"));
/* 40 */     return ret;
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 44 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 48 */     return ApplicationContext.getInstance().getLabel(locale, "login.log");
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getIdentifier() {
/* 53 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 57 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\login\ASServiceImpl_LoginLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */