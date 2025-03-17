/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.sendlogs2;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.sendlogs2.ui.LogMailPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_SendLogs
/*    */   implements AdminSubService
/*    */ {
/*    */   public boolean isAvailable(ClientContext context) {
/* 18 */     return context.isSpecialAccess();
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 22 */     return (HtmlElement)LogMailPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 26 */     return ApplicationContext.getInstance().getLabel(locale, "send.logs") + " (ext)";
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 30 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\sendlogs2\ASServiceImpl_SendLogs.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */