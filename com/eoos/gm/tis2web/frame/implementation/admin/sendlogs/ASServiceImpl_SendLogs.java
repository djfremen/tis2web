/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.sendlogs;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.sendlogs.ui.LogMailPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_SendLogs
/*    */   implements AdminSubService
/*    */ {
/*    */   public boolean isAvailable(ClientContext context) {
/* 20 */     return ComponentAccessPermission.getInstance(context).check("log.mailer");
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 24 */     return (HtmlElement)LogMailPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 28 */     return ApplicationContext.getInstance().getLabel(locale, "send.logs");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 32 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\sendlogs\ASServiceImpl_SendLogs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */