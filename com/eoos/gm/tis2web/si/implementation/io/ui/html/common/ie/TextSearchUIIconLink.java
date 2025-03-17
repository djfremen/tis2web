/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
/*    */ import com.eoos.html.element.input.IDLinkElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextSearchUIIconLink
/*    */   extends IDLinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public TextSearchUIIconLink(final ClientContext context) {
/* 29 */     super("smenu:" + context.createID(), null, "TextSearch");
/* 30 */     this.context = context;
/* 31 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 33 */           String image = "si/textsearch.gif";
/* 34 */           if (TextSearchUIIconLink.this.isDisabled()) {
/* 35 */             image = "si/textsearch-disabled.gif";
/*    */           }
/* 37 */           return "pic/" + image;
/*    */         }
/*    */ 
/*    */         
/*    */         public String getAlternativeText() {
/* 42 */           return context.getLabel("si.tooltip.text.search");
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
/*    */   public Object onClick(Map submitParams) {
/* 58 */     MainHook.getInstance(this.context).switchUI(2);
/* 59 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 63 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\TextSearchUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */