/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HtmlLayerElement
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private class RendererCallback
/*    */     extends HtmlSpanRenderer.CallbackAdapter
/*    */   {
/*    */     private String content;
/*    */     
/*    */     public RendererCallback(String content) {
/* 21 */       this.content = content;
/*    */     }
/*    */     
/*    */     public String getID() {
/* 25 */       return "newlevel";
/*    */     }
/*    */     
/*    */     public String getContent() {
/* 29 */       return this.content;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 38 */     return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new RendererCallback(getContentCode(params)));
/*    */   }
/*    */   
/*    */   protected abstract String getContentCode(Map paramMap);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlLayerElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */