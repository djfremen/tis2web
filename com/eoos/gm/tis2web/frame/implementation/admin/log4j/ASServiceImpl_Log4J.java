/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.log4j;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.permission.ComponentAccessPermission;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.log4j.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_Log4J
/*    */   implements AdminSubService
/*    */ {
/*    */   public boolean isAvailable(ClientContext context) {
/* 20 */     return (context.isSpecialAccess() || ComponentAccessPermission.getInstance(context).check("admin.log4j"));
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 24 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 28 */     return ApplicationContext.getInstance().getLabel(locale, "log4j.configuration");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 32 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\log4j\ASServiceImpl_Log4J.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */