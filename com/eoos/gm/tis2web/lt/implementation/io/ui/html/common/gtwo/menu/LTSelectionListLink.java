/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
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
/*    */ public class LTSelectionListLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public LTSelectionListLink(final ClientContext context) {
/* 24 */     super(context.createID(), null);
/* 25 */     this.context = context;
/* 26 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter()
/*    */       {
/*    */         public String getImageSource() {
/* 29 */           String image = "lt/labourtime.gif";
/* 30 */           if (LTSelectionListLink.this.isDisabled()) {
/* 31 */             image = "lt/labourtime-disabled.gif";
/*    */           }
/* 33 */           return "pic/" + image;
/*    */         }
/*    */ 
/*    */         
/*    */         public String getAlternativeText() {
/* 38 */           return context.getLabel("lt.tooltip.nav.ltlist");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 42 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 50 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 54 */     return LTTALListPage.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\LTSelectionListLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */