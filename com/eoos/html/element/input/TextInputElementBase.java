/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TextInputElementBase
/*    */   extends HtmlInputElementBase
/*    */ {
/*    */   protected TextInputElementBase(String parameterName) {
/* 12 */     super(parameterName);
/*    */   }
/*    */   
/*    */   public void setValue(Object value) {
/* 16 */     if (value == null) {
/* 17 */       this.value = "";
/*    */     } else {
/* 19 */       this.value = value;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 24 */     return false;
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 28 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 32 */     if (!isDisabled()) {
/* 33 */       String value = (String)submitParams.get(this.parameterName);
/* 34 */       this.value = value;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\TextInputElementBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */