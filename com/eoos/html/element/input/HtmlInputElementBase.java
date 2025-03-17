/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElementContainer;
/*    */ import com.eoos.html.element.HtmlLayout;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class HtmlInputElementBase
/*    */   implements HtmlInputElement
/*    */ {
/* 17 */   protected String parameterName = null;
/* 18 */   protected Object value = null;
/* 19 */   protected Boolean disabled = null;
/*    */   
/* 21 */   private HtmlElementContainer parent = null;
/*    */ 
/*    */   
/*    */   protected HtmlInputElementBase(String parameterName) {
/* 25 */     this.parameterName = parameterName;
/*    */   }
/*    */   
/*    */   protected HtmlElementContainer getTopLevelContainer() {
/* 29 */     HtmlElementContainer ret = this.parent;
/* 30 */     while (ret != null && ret.getContainer() != null) {
/* 31 */       ret = ret.getContainer();
/*    */     }
/* 33 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean check(boolean hints) {
/* 38 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public String getBookmark() {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public HtmlElementContainer getContainer() {
/* 46 */     return this.parent;
/*    */   }
/*    */   
/*    */   public HtmlLayout getLayout() {
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   public void setContainer(HtmlElementContainer container) {
/* 54 */     this.parent = container;
/*    */   }
/*    */   
/*    */   public void setDisabled(Boolean disabled) {
/* 58 */     this.disabled = disabled;
/*    */   }
/*    */   
/*    */   public boolean isDisabled() {
/* 62 */     if (this.disabled != null) {
/* 63 */       return this.disabled.booleanValue();
/*    */     }
/* 65 */     return (this.parent != null) ? this.parent.isDisabled() : false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object value) {
/* 70 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 74 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\HtmlInputElementBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */