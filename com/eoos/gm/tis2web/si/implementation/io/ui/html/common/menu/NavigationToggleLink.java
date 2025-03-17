/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ public abstract class NavigationToggleLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public NavigationToggleLink(final ClientContext context) {
/* 22 */     super("smenu:" + context.createID(), null);
/* 23 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 25 */           String image = "si/navswitch.gif";
/* 26 */           if (NavigationToggleLink.this.isDisabled()) {
/* 27 */             image = "si/navswitch-disabled.gif";
/*    */           }
/* 29 */           return "pic/" + image;
/*    */         }
/*    */         
/*    */         public String getAlternativeText() {
/* 33 */           return context.getLabel("si.tooltip.nav.toggle");
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
/*    */   public abstract Object onClick(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\NavigationToggleLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */