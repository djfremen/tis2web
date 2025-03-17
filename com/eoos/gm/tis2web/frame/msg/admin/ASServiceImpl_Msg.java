/*    */ package com.eoos.gm.tis2web.frame.msg.admin;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.msg.admin.ui.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ASServiceImpl_Msg
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 17 */   private static ASServiceImpl_Msg instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   private ASServiceImpl_Msg(Configuration configuration) {
/* 22 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Msg createInstance(Configuration configuration) {
/* 26 */     if (instance != null) {
/* 27 */       throw new IllegalStateException();
/*    */     }
/* 29 */     instance = new ASServiceImpl_Msg(configuration);
/* 30 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_Msg getInstance() {
/* 34 */     if (instance != null) {
/* 35 */       return instance;
/*    */     }
/* 37 */     throw new IllegalStateException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 42 */     return (ComponentAccessPermission.getInstance(context).check("admin.messages") || context.isSpecialAccess());
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 46 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 50 */     return ApplicationContext.getInstance().getLabel(locale, "messages");
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


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\msg\admin\ASServiceImpl_Msg.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */