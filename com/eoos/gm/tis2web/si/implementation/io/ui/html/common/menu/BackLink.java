/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.common.menu;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BackLink
/*    */   extends IDLinkElement
/*    */ {
/*    */   private HtmlImgRenderer.Callback imgRendererCallback;
/*    */   
/*    */   public BackLink(final ClientContext context) {
/* 30 */     super("smenu:" + context.createID(), null, "Back");
/* 31 */     this.imgRendererCallback = (HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*    */         public String getImageSource() {
/* 33 */           String image = "si/back.gif";
/* 34 */           if (BackLink.this.isDisabled()) {
/* 35 */             image = "si/back-disabled.gif";
/*    */           }
/* 37 */           return "pic/" + image;
/*    */         }
/*    */ 
/*    */         
/*    */         public String getAlternativeText() {
/* 42 */           return context.getLabel("si.tooltip.back");
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
/*    */   public static interface Callback {
/*    */     boolean isDisabled();
/*    */     Object onClick(); }
/*    */   public abstract Object onClick(Map paramMap);
/*    */   public abstract boolean isDisabled();
/*    */   public boolean clicked() {
/* 62 */     return (this.clicked && !isDisabled());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\common\menu\BackLink.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */