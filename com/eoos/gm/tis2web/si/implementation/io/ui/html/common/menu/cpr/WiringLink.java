/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu.cpr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie.LinkListElement;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks.WiringDiagrammList;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.appllinks.WiringDiagrammPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*    */ public abstract class WiringLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public WiringLink(final ClientContext context) {
/* 30 */     super(context.createID(), null);
/* 31 */     this.context = context;
/* 32 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 34 */           String image = "si/wd.gif";
/* 35 */           if (WiringLink.this.isDisabled()) {
/* 36 */             image = "si/wd-disabled.gif";
/*    */           }
/* 38 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 42 */           return context.getLabel("si.wiring.diagrams");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 46 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 54 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 58 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 62 */     SIO sio = getSIO();
/* 63 */     if (sio != null && sio instanceof SIOCPR) {
/* 64 */       SIOCPR siocpr = (SIOCPR)sio;
/* 65 */       WiringDiagrammList wdlist = new WiringDiagrammList(this.context, siocpr);
/* 66 */       WiringDiagrammPage page = WiringDiagrammPage.getInstance(this.context, siocpr, (LinkListElement)wdlist);
/*    */       
/* 68 */       return new ResultObject(0, page.getHtmlCode(submitParams));
/*    */     } 
/*    */     
/* 71 */     return "error";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getTargetFrame() {
/* 76 */     return "CprLink";
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 80 */     SIO sio = getSIO();
/* 81 */     if (sio != null && sio instanceof SIOCPR) {
/* 82 */       SIOCPR siocpr = (SIOCPR)sio;
/* 83 */       WiringDiagrammList wdlist = new WiringDiagrammList(this.context, siocpr);
/* 84 */       return (wdlist.getData() == null || wdlist.getData().size() == 0);
/*    */     } 
/* 86 */     return true;
/*    */   }
/*    */   
/*    */   protected abstract SIO getSIO();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\cpr\WiringLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */