/*    */ package com.eoos.html.element;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HtmlElementBase
/*    */   implements HtmlElement
/*    */ {
/* 14 */   private HtmlElementContainer container = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getBookmark() {
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public HtmlElementContainer getContainer() {
/* 25 */     return this.container;
/*    */   }
/*    */   
/*    */   public HtmlElementContainer getTopLevelContainer() {
/* 29 */     HtmlElementContainer container = getContainer();
/* 30 */     while (container.getContainer() != null) {
/* 31 */       container = container.getContainer();
/*    */     }
/* 33 */     return container;
/*    */   }
/*    */   
/*    */   public abstract String getHtmlCode(Map paramMap);
/*    */   
/*    */   public HtmlLayout getLayout() {
/* 39 */     return this.container.getLayout();
/*    */   }
/*    */   
/*    */   public void setContainer(HtmlElementContainer container) {
/* 43 */     this.container = container;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\HtmlElementBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */