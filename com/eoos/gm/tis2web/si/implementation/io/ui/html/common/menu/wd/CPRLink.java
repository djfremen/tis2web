/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.wd;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd.CprList;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.wd.CprPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOWD;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class CPRLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public CPRLink(final ClientContext context) {
/* 31 */     super(context.createID(), null);
/* 32 */     this.context = context;
/* 33 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 35 */           String image = "si/cpr.gif";
/* 36 */           if (CPRLink.this.isDisabled()) {
/* 37 */             image = "si/cpr-disabled.gif";
/*    */           }
/* 39 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 43 */           return context.getLabel("wd.Diagnostic.Tests");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 47 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getTargetFrame() {
/* 55 */     return "WdLink";
/*    */   }
/*    */   
/*    */   protected String getLabel() {
/* 59 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 63 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 67 */     SIO sio = getSIO();
/* 68 */     if (sio != null && sio instanceof SIOWD) {
/* 69 */       SIOWD siowd = (SIOWD)sio;
/* 70 */       CprList cprList = new CprList(this.context, siowd);
/* 71 */       CprPage page = CprPage.getInstance(this.context, siowd, cprList);
/* 72 */       return new ResultObject(0, page.getHtmlCode(submitParams));
/*    */     } 
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDisabled() {
/* 79 */     SIO sio = getSIO();
/* 80 */     if (sio != null && sio instanceof SIOWD) {
/* 81 */       SIOWD siowd = (SIOWD)sio;
/* 82 */       List eSLs = siowd.getElectronicSystemLinks();
/* 83 */       List rcpr = siowd.getRelatedCheckingProcedures();
/* 84 */       if (eSLs != null && eSLs.size() > 0 && rcpr != null && rcpr.size() > 0 && (new CprList(this.context, (SIOWD)sio)).getData().size() > 0) {
/* 85 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 89 */     return true;
/*    */   }
/*    */   
/*    */   public abstract SIO getSIO();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\wd\CPRLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */