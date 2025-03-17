/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.gtwo.menu;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BackLink
/*    */   extends LinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public BackLink(final ClientContext context) {
/* 29 */     super("smenu:" + context.createID(), null);
/* 30 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 32 */           String image = "si/back.gif";
/* 33 */           if (BackLink.this.isDisabled()) {
/* 34 */             image = "si/back-disabled.gif";
/*    */           }
/* 36 */           return "pic/" + image;
/*    */         }
/*    */ 
/*    */         
/*    */         public String getAlternativeText() {
/* 41 */           return context.getLabel("si.tooltip.back");
/*    */         }
/*    */         
/*    */         public void getAdditionalAttributes(Map<String, String> map) {
/* 45 */           map.put("border", "0");
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getLabel() {
/* 53 */     return HtmlImgRenderer.getInstance().getHtmlCode(this.imgRendererCallback);
/*    */   }
/*    */   public static interface Callback {
/*    */     boolean isDisabled();
/*    */     Object onClick(); }
/*    */   public abstract Object onClick(Map paramMap);
/*    */   public abstract boolean isDisabled();
/*    */   public boolean clicked() {
/* 61 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\common\gtwo\menu\BackLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */