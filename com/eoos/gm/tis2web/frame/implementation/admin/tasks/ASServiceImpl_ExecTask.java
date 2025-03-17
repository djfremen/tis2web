/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.tasks;
/*    */ 
/*    */ import com.eoos.datatype.marker.Configurable;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.tasks.ui.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.instantiation.Singleton;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_ExecTask
/*    */   implements AdminSubService, Singleton, Configurable
/*    */ {
/* 18 */   private static ASServiceImpl_ExecTask instance = null;
/*    */   
/*    */   private Configuration configuration;
/*    */   
/*    */   public ASServiceImpl_ExecTask(Configuration configuration) {
/* 23 */     this.configuration = configuration;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ASServiceImpl_ExecTask createInstance(Configuration configuration) {
/* 28 */     if (instance != null) {
/* 29 */       throw new IllegalStateException();
/*    */     }
/* 31 */     instance = new ASServiceImpl_ExecTask(configuration);
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public static ASServiceImpl_ExecTask getInstance() {
/* 36 */     if (instance != null) {
/* 37 */       return instance;
/*    */     }
/* 39 */     throw new IllegalStateException();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 44 */     return ComponentAccessPermission.getInstance(context).check("exec.task");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 48 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 52 */     return ApplicationContext.getInstance().getLabel(locale, "administrative.tasks");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 56 */     return toString();
/*    */   }
/*    */   
/*    */   public Configuration getConfiguration() {
/* 60 */     return this.configuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\tasks\ASServiceImpl_ExecTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */