/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ImageElement
/*    */   extends HtmlElementBase
/*    */ {
/*    */   private RendererCallback rendererCallback;
/*    */   
/*    */   private class RendererCallback
/*    */     extends HtmlImgRenderer.CallbackAdapter
/*    */   {
/*    */     private RendererCallback() {}
/*    */     
/*    */     public String getAlternativeText() {
/* 20 */       return ImageElement.this.getAlternativeText();
/*    */     }
/*    */     
/*    */     public String getImageSource() {
/* 24 */       return ImageElement.this.getImageURL();
/*    */     }
/*    */     
/*    */     public void getAdditionalAttributes(Map map) {
/* 28 */       super.getAdditionalAttributes(map);
/* 29 */       ImageElement.this.getAdditionalAttributes(map);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ImageElement() {
/* 37 */     this.rendererCallback = new RendererCallback();
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 41 */     return HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)this.rendererCallback);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract String getImageURL();
/*    */ 
/*    */   
/*    */   protected void getAdditionalAttributes(Map map) {}
/*    */   
/*    */   protected String getAlternativeText() {
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\ImageElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */