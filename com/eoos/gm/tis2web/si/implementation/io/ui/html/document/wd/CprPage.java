/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkDialog;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkListElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CprPage
/*    */   extends LinkDialog
/*    */ {
/*    */   public CprPage(ClientContext context, SIOWD sioCPR, LinkListElement li) {
/* 21 */     super(context, (SIO)sioCPR, li);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean checkInstance(HtmlElement elem) {
/* 26 */     return elem instanceof CprContainer;
/*    */   }
/*    */   
/*    */   public static synchronized CprPage getInstance(ClientContext context, SIOWD sioCPR, CprList cprList) {
/* 30 */     CprPage instance = (CprPage)context.getObject(CprPage.class);
/* 31 */     if (instance == null) {
/* 32 */       instance = new CprPage(context, sioCPR, cprList);
/* 33 */       context.storeObject(CprPage.class, instance);
/*    */     } else {
/* 35 */       instance.setNode((SIO)sioCPR, cprList);
/*    */     } 
/* 37 */     return instance;
/*    */   }
/*    */   
/*    */   protected void unregister(HtmlElement elem) {
/* 41 */     if (elem instanceof CprContainer) {
/* 42 */       ((CprContainer)elem).unregister();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTitle() {
/* 51 */     this.title = this.sio.getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 55 */     this.title = title;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\wd\CprPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */