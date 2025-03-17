/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainHook;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.main.MainPage;
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
/*    */ public class TextSearchUIIconLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public TextSearchUIIconLink(final ClientContext context) {
/* 26 */     super(context.createID(), null);
/* 27 */     this.context = context;
/* 28 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 30 */           String image = "lt/textsearch.gif";
/* 31 */           if (TextSearchUIIconLink.this.isDisabled()) {
/* 32 */             image = "lt/textsearch-disabled.gif";
/*    */           }
/* 34 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 38 */           return context.getLabel("lt.tooltip.text.search");
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
/* 54 */     MainHook.getInstance(this.context).switchUI(2);
/* 55 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\TextSearchUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */