/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlImgRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class IconElement
/*    */   extends HtmlElementBase
/*    */ {
/*    */   private String url;
/*    */   private String tooltip;
/*    */   private RenderingCallback renderingCallback;
/*    */   
/*    */   private class RenderingCallback
/*    */     extends HtmlImgRenderer.CallbackAdapter {
/*    */     private RenderingCallback() {}
/*    */     
/*    */     public String getImageSource() {
/* 18 */       return IconElement.this.url;
/*    */     }
/*    */     
/*    */     public String getAlternativeText() {
/* 22 */       return (IconElement.this.tooltip != null) ? IconElement.this.tooltip : "";
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IconElement(String imageURL, String tooltip) {
/* 33 */     this.url = imageURL;
/* 34 */     this.tooltip = tooltip;
/*    */     
/* 36 */     this.renderingCallback = new RenderingCallback();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 41 */     return HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)this.renderingCallback, params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\IconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */