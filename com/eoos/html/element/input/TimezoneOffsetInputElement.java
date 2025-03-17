/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlTimezoneOffsetRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimezoneOffsetInputElement
/*    */   extends TextInputElementBase
/*    */ {
/*    */   private class RendererCallback
/*    */     extends HtmlTimezoneOffsetRenderer.CallbackAdapter
/*    */   {
/*    */     private RendererCallback() {}
/*    */     
/*    */     public String getParameterName() {
/* 20 */       return TimezoneOffsetInputElement.this.parameterName;
/*    */     }
/*    */   }
/*    */   
/* 24 */   private RendererCallback renderingCallback = new RendererCallback();
/*    */   
/*    */   public TimezoneOffsetInputElement(String parameterName) {
/* 27 */     super(parameterName);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 31 */     return HtmlTimezoneOffsetRenderer.getInstance().getHtmlCode((HtmlTimezoneOffsetRenderer.Callback)this.renderingCallback);
/*    */   }
/*    */   
/*    */   public long getOffset() {
/*    */     try {
/* 36 */       return Long.parseLong((String)getValue()) * 60000L;
/* 37 */     } catch (Exception e) {
/* 38 */       return 0L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\TimezoneOffsetInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */