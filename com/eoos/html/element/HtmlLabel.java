/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlLabel
/*    */   extends HtmlElementBase
/*    */ {
/*    */   private String label;
/*    */   
/*    */   private class RenderingCallback
/*    */     extends HtmlSpanRenderer.CallbackAdapter
/*    */   {
/*    */     private RenderingCallback() {}
/*    */     
/*    */     public String getContent() {
/* 19 */       return HtmlLabel.this.getLabel();
/*    */     }
/*    */     
/*    */     public String getID() {
/* 23 */       return HtmlLabel.this.getID();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HtmlLabel(String label) {
/* 31 */     this.label = label;
/*    */   }
/*    */ 
/*    */   
/*    */   public HtmlLabel() {}
/*    */   
/*    */   protected String getLabel() {
/* 38 */     return (this.label != null) ? this.label : "";
/*    */   }
/*    */   
/*    */   protected String getID() {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 46 */     String id = getID();
/* 47 */     if (id == null) {
/* 48 */       return getLabel();
/*    */     }
/* 50 */     return HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new RenderingCallback());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */