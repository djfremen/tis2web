/*    */ package com.eoos.gm.tis2web.si.implementation.statcont.ui.html.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.main.MainHook;
/*    */ import com.eoos.gm.tis2web.si.implementation.statcont.ui.html.main.MainPage;
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
/*    */ public class HomeUIIconLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   private ClientContext context;
/*    */   
/*    */   public HomeUIIconLink(final ClientContext context) {
/* 25 */     super(context.createID(), null);
/* 26 */     this.context = context;
/* 27 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 29 */           String image = "si/home.gif";
/* 30 */           if (HomeUIIconLink.this.isDisabled()) {
/* 31 */             image = "si/home-disabled.gif";
/*    */           }
/* 33 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 37 */           return context.getLabel("si.tooltip.home");
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
/* 53 */     MainHook.getInstance(this.context).switchUI(0, false);
/* 54 */     return MainPage.getInstance(this.context);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clicked() {
/* 59 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\statcon\\ui\html\menu\HomeUIIconLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */