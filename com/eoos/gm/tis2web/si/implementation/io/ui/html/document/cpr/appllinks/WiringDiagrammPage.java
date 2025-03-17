/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkDialog;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkListElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WiringDiagrammPage
/*    */   extends LinkDialog
/*    */ {
/*    */   public WiringDiagrammPage(ClientContext context, SIOCPR sioCPR, LinkListElement li) {
/* 21 */     super(context, (SIO)sioCPR, li);
/*    */   }
/*    */   
/*    */   protected void unregister(HtmlElement elem) {
/* 25 */     if (elem instanceof WiringDiagrammContainer) {
/* 26 */       ((WiringDiagrammContainer)elem).unregister();
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean checkInstance(HtmlElement elem) {
/* 31 */     return elem instanceof WiringDiagrammContainer;
/*    */   }
/*    */   
/*    */   public static synchronized WiringDiagrammPage getInstance(ClientContext context, SIOCPR sioCPR, LinkListElement li) {
/* 35 */     WiringDiagrammPage instance = (WiringDiagrammPage)context.getObject(WiringDiagrammPage.class);
/* 36 */     if (instance == null) {
/* 37 */       instance = new WiringDiagrammPage(context, sioCPR, li);
/* 38 */       context.storeObject(WiringDiagrammPage.class, instance);
/*    */     } else {
/* 40 */       instance.setNode((SIO)sioCPR, li);
/*    */     } 
/* 42 */     return instance;
/*    */   }
/*    */   
/*    */   public void resetTitle() {
/* 46 */     this.title = ((SIOCPR)this.sio).getElectronicSystemLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\appllinks\WiringDiagrammPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */