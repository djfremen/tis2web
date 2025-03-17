/*    */ package com.eoos.gm.tis2web.si.implementation.io;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.AdministrativeTask;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class ATask_ReleasePrefetch
/*    */   implements AdministrativeTask
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 15 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("update.configuration"));
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 19 */     return ApplicationContext.getInstance().getLabel(locale, "release.si.preload");
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(ClientContext principal) throws Exception {
/* 24 */     SIPreload.getInstance().clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getErrorMessage(Exception e, Locale locale) {
/* 29 */     return ApplicationContext.getInstance().getMessage(locale, "release.si.preload.data.failed");
/*    */   }
/*    */   
/*    */   public String getSuccessMessage(Locale locale) {
/* 33 */     return ApplicationContext.getInstance().getMessage(locale, "release.si.preload.data.successful");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\ATask_ReleasePrefetch.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */