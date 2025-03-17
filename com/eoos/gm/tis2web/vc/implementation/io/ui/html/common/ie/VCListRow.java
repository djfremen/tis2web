/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie;
/*    */ 
/*    */ import com.eoos.html.element.HtmlElement;
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
/*    */ public class VCListRow
/*    */ {
/* 16 */   protected HtmlElement attributeLabel = null;
/*    */   
/* 18 */   protected HtmlElement inputElement = null;
/*    */   
/*    */   protected boolean mandatoryFlag = false;
/*    */ 
/*    */   
/*    */   protected VCListRow() {}
/*    */ 
/*    */   
/*    */   public VCListRow(HtmlElement label, HtmlElement element, HtmlElement indicator) {
/* 27 */     this.attributeLabel = label;
/* 28 */     this.inputElement = element;
/*    */   }
/*    */   
/*    */   public void setAttributeLabel(HtmlElement label) {
/* 32 */     this.attributeLabel = label;
/*    */   }
/*    */   
/*    */   public HtmlElement getAttributeLabel() {
/* 36 */     return this.attributeLabel;
/*    */   }
/*    */   
/*    */   public void setInputElement(HtmlElement element) {
/* 40 */     this.inputElement = element;
/*    */   }
/*    */   
/*    */   public HtmlElement getInputElement() {
/* 44 */     return this.inputElement;
/*    */   }
/*    */   
/*    */   public void setMandatoryFlag(boolean flag) {
/* 48 */     this.mandatoryFlag = flag;
/*    */   }
/*    */   
/*    */   public boolean getMandatoryFlag() {
/* 52 */     return this.mandatoryFlag;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\VCListRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */