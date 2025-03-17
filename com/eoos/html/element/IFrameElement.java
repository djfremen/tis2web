/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlIFrameRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IFrameElement
/*    */   extends HtmlElementBase
/*    */ {
/*    */   private RenderingCallback renderingCallback;
/*    */   private String name;
/*    */   
/*    */   private class RenderingCallback
/*    */     extends HtmlIFrameRenderer.CallbackAdapter
/*    */   {
/*    */     private Map params;
/*    */     
/*    */     private RenderingCallback() {}
/*    */     
/*    */     public void init(Map params) {
/* 22 */       this.params = params;
/*    */     }
/*    */     
/*    */     public String getSource() {
/* 26 */       return IFrameElement.this.getSourceURL(this.params);
/*    */     }
/*    */     
/*    */     public String getName() {
/* 30 */       return IFrameElement.this.name;
/*    */     }
/*    */     
/*    */     public void getAdditionalAttributes(Map<String, String> map) {
/* 34 */       String height = IFrameElement.this.getHeight();
/* 35 */       String width = IFrameElement.this.getWidth();
/* 36 */       if (height != null) {
/* 37 */         map.put("height", height);
/*    */       }
/*    */       
/* 40 */       if (width != null) {
/* 41 */         map.put("width", width);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IFrameElement(String name) {
/* 53 */     this.name = name;
/* 54 */     this.renderingCallback = new RenderingCallback();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 58 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 62 */     return HtmlIFrameRenderer.getInstance().getHtmlCode((HtmlIFrameRenderer.Callback)this.renderingCallback, params);
/*    */   }
/*    */   
/*    */   protected abstract String getWidth();
/*    */   
/*    */   protected abstract String getHeight();
/*    */   
/*    */   protected abstract String getSourceURL(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\IFrameElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */