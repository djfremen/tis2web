/*    */ package com.eoos.gm.tis2web.admin.info;
/*    */ 
/*    */ import com.eoos.gm.tis2web.admin.info.ui.Panel;
/*    */ import com.eoos.gm.tis2web.admin.service.cai.AdminSubService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ASServiceImpl_ServerInfo
/*    */   implements AdminSubService
/*    */ {
/*    */   public boolean isAvailable(ClientContext context) {
/* 18 */     return true;
/*    */   }
/*    */   
/*    */   public HtmlElement getEmbeddableUI(ClientContext context) {
/* 22 */     return (HtmlElement)Panel.getInstance(context);
/*    */   }
/*    */   
/*    */   public CharSequence getName(Locale locale) {
/* 26 */     return ApplicationContext.getInstance().getLabel(locale, "server.info");
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 30 */     return toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\info\ASServiceImpl_ServerInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */