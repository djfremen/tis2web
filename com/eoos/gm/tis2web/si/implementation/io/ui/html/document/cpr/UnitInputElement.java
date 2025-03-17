/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ import com.eoos.html.element.input.TextInputElement;
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
/*    */ public class UnitInputElement
/*    */   extends TextInputElement
/*    */ {
/* 17 */   private String unit = null;
/*    */ 
/*    */   
/*    */   public UnitInputElement(String parameterName, int size, int maxlength) {
/* 21 */     super(parameterName, size, maxlength);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getUnit() {
/* 30 */     return this.unit;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setUnit(String unit) {
/* 40 */     this.unit = unit;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\UnitInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */