/*    */ package com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.label;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.implementation.io.ui.html.common.ie.MandatoryCallback;
/*    */ import com.eoos.html.element.HtmlLabel;
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
/*    */ public class VcHtmlLabel
/*    */   extends HtmlLabel
/*    */ {
/*    */   protected boolean mandatoryFlag = false;
/* 19 */   protected MandatoryCallback mandatoryCallback = null;
/*    */ 
/*    */   
/*    */   public VcHtmlLabel(String key) {
/* 23 */     super(key);
/* 24 */     this.mandatoryFlag = false;
/*    */   }
/*    */   
/*    */   public VcHtmlLabel(String key, boolean mandatoryFlag) {
/* 28 */     super(key);
/* 29 */     this.mandatoryFlag = mandatoryFlag;
/*    */   }
/*    */   
/*    */   public VcHtmlLabel(String key, MandatoryCallback callback) {
/* 33 */     super(key);
/* 34 */     this.mandatoryCallback = callback;
/*    */   }
/*    */   
/*    */   protected boolean enableMandatoryFlag() {
/* 38 */     if (this.mandatoryCallback != null) {
/* 39 */       return this.mandatoryCallback.isMandatory();
/*    */     }
/* 41 */     return this.mandatoryFlag;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 46 */     if (enableMandatoryFlag()) {
/* 47 */       StringBuffer buffer = new StringBuffer(super.getHtmlCode(params));
/* 48 */       buffer.append("<SPAN id=\"vcmandatory\">*</SPAN>");
/* 49 */       return buffer.toString();
/*    */     } 
/* 51 */     return super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\i\\ui\html\common\ie\label\VcHtmlLabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */