/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.lttal.page.LTTALListPage;
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
/*    */ public class LTSelectionListLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public LTSelectionListLink(final ClientContext context) {
/* 25 */     super(context.createID(), null);
/* 26 */     this.context = context;
/* 27 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 29 */           return "pic/lt/labourtime.gif";
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 33 */           return context.getLabel("lt.tooltip.nav.ltlist");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 37 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 45 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 49 */     return LTTALListPage.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\LTSelectionListLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */