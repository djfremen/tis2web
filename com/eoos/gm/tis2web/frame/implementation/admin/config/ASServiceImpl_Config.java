/*    */ package com.eoos.gm.tis2web.frame.implementation.admin.config;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.implementation.admin.config.ui.html.main.MainPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_Config
/*    */   implements AdminSubService
/*    */ {
/*    */   public boolean isAvailable(ClientContext context) {
/* 19 */     return context.isSpecialAccess();
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 23 */     return (HtmlElement)MainPanel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 27 */     return ApplicationContext.getInstance().getLabel(locale, "configuration");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 31 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\admin\config\ASServiceImpl_Config.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */