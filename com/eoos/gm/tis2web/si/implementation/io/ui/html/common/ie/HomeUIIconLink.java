/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainPage;
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
/*    */ public class HomeUIIconLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public HomeUIIconLink(final ClientContext context) {
/* 26 */     super("smenu:" + context.createID(), null);
/* 27 */     this.context = context;
/* 28 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 30 */           String image = "si/home.gif";
/* 31 */           if (HomeUIIconLink.this.isDisabled()) {
/* 32 */             image = "si/home-disabled.gif";
/*    */           }
/* 34 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 38 */           return context.getLabel("si.tooltip.home");
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
/* 54 */     MainHook.getInstance(this.context).switchUI(0);
/* 55 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clicked() {
/* 60 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\ie\HomeUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */