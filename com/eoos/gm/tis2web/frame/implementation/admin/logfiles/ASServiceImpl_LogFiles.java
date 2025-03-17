/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.logfiles;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.logfiles.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_LogFiles
/*    */   implements AdminSubService
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(ASServiceImpl_LogFiles.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAvailable(ClientContext context) {
/* 23 */     boolean retValue = true;
/* 24 */     retValue = (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("admin.log.files"));
/*    */     try {
/* 26 */       retValue = (retValue && MainPanel.getInstance(context) != null);
/* 27 */     } catch (Exception e) {
/* 28 */       log.error("unable to init main panel - exception: " + e + ", returning false (subservice is not available)", e);
/* 29 */       retValue = false;
/*    */     } 
/* 31 */     return retValue;
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 35 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 39 */     return ApplicationContext.getInstance().getLabel(locale, "admin.log.files");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 43 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\logfiles\ASServiceImpl_LogFiles.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */