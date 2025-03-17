/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.protocol;
/*    */ 
/*    */ import com.eoos.html.ResultObject;
/*    */ import com.eoos.html.element.input.TextInputElement;
/*    */ import java.util.HashMap;
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
/*    */ public class ActiveTextInputElement
/*    */   extends TextInputElement
/*    */ {
/* 19 */   private Map addAttrs = new HashMap<Object, Object>();
/*    */   
/*    */   protected boolean clicked = false;
/*    */ 
/*    */   
/*    */   public ActiveTextInputElement(String parameterName, int size, int maxlength) {
/* 25 */     super(parameterName, size, maxlength);
/* 26 */     this.addAttrs.put("onchange", "javascript:FormSubmit('" + parameterName + "','1','" + parameterName + "')");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean clicked() {
/* 31 */     return this.clicked;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 36 */     Object obj = submitParams.get(this.parameterName);
/* 37 */     if (obj != null) {
/* 38 */       this.clicked = true;
/* 39 */       if (obj instanceof String) {
/* 40 */         this.value = obj;
/* 41 */       } else if (obj instanceof String[]) {
/* 42 */         this.value = ((String[])obj)[1];
/*    */       } 
/*    */     } else {
/*    */       
/* 46 */       this.clicked = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 51 */     return new ResultObject(9, new Object());
/*    */   }
/*    */   
/*    */   protected void getAdditionalAttributes(Map map) {
/* 55 */     map.putAll(this.addAttrs);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\protocol\ActiveTextInputElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */