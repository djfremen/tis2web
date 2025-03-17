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
/*    */ public class StandardUIIconLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public StandardUIIconLink(final ClientContext context) {
/* 25 */     super(context.createID(), null);
/* 26 */     this.context = context;
/* 27 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 29 */           String image = "lt/stdinfo2.gif";
/* 30 */           if (StandardUIIconLink.this.isDisabled()) {
/* 31 */             image = "lt/stdinfo2-disabled.gif";
/*    */           }
/* 33 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 37 */           return context.getLabel("lt.tooltip.standard.info");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 41 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 49 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 53 */     MainHook.getInstance(this.context).switchUI(1);
/* 54 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\StandardUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */