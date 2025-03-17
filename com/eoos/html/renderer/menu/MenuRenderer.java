/*    */ package com.eoos.html.renderer.menu;
/*    */ 
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import java.util.List;
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
/*    */ public abstract class MenuRenderer
/*    */   implements HtmlCodeRenderer
/*    */ {
/*    */   public static final String PARAMS_KEY_CALLBACK = "callback";
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 40 */     Callback callback = (Callback)params.get("callback");
/* 41 */     return getHtmlCode(callback, params);
/*    */   }
/*    */   
/*    */   public abstract String getHtmlCode(Callback paramCallback, Map paramMap);
/*    */   
/*    */   public static interface Remainder {
/*    */     HtmlElement getRemainder();
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void init(Map param1Map);
/*    */     
/*    */     List getItems();
/*    */     
/*    */     boolean isActive(Object param1Object);
/*    */     
/*    */     String getCode(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\menu\MenuRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */