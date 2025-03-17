/*    */ package com.eoos.html.element.input;
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
/*    */ public class MaskedTextInputElement
/*    */   extends TextInputElement
/*    */ {
/*    */   public MaskedTextInputElement(String parameterName, int size, int maxlength) {
/* 16 */     super(parameterName, size, maxlength);
/*    */   }
/*    */   
/*    */   public MaskedTextInputElement(String parameterName) {
/* 20 */     super(parameterName);
/*    */   }
/*    */   
/*    */   protected boolean isMasked() {
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\MaskedTextInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */