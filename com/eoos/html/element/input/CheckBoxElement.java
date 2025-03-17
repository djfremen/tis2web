/*    */ package com.eoos.html.element.input;
/*    */ 
/*    */ import com.eoos.html.renderer.HtmlCheckBoxRenderer;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CheckBoxElement extends HtmlInputElementBase {
/*    */   private RenderingCallback renderingCallback;
/*    */   private String clickID;
/*    */   
/*    */   private class RenderingCallback extends HtmlCheckBoxRenderer.CallbackAdapter {
/*    */     private RenderingCallback() {}
/*    */     
/*    */     public boolean isChecked() {
/* 15 */       return CheckBoxElement.this.value.equals(Boolean.TRUE);
/*    */     }
/*    */     
/*    */     public boolean isDisabled() {
/* 19 */       return CheckBoxElement.this.isDisabled();
/*    */     }
/*    */     
/*    */     public String getParameterName() {
/* 23 */       return CheckBoxElement.this.parameterName;
/*    */     }
/*    */     
/*    */     public String getSubmitValue() {
/* 27 */       return "1";
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */     
/*    */     public void getAdditionalAttributes(Map<String, String> map) {
/* 34 */       if (CheckBoxElement.this.autoSubmitOnClick()) {
/* 35 */         map.put("onClick", "javascript:FormSubmit('" + CheckBoxElement.this.clickID + "','1','" + CheckBoxElement.this.getBookmark() + "')");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean clicked = false;
/*    */ 
/*    */ 
/*    */   
/*    */   public CheckBoxElement(String parameterName) {
/* 47 */     super(parameterName);
/* 48 */     setValue(Boolean.FALSE);
/* 49 */     this.clickID = this.parameterName + "oc";
/*    */     
/* 51 */     this.renderingCallback = new RenderingCallback();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 56 */     StringBuffer code = StringBufferPool.getThreadInstance().get();
/*    */     try {
/* 58 */       code.append(HtmlCheckBoxRenderer.getInstance().getHtmlCode((HtmlCheckBoxRenderer.Callback)this.renderingCallback));
/* 59 */       return code.toString();
/*    */     } finally {
/*    */       
/* 62 */       StringBufferPool.getThreadInstance().free(code);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setValue(Map submitParams) {
/* 67 */     if (submitParams.containsKey(this.clickID)) {
/* 68 */       this.clicked = true;
/*    */     } else {
/* 70 */       this.clicked = false;
/*    */     } 
/*    */     
/* 73 */     if (submitParams.containsKey(this.parameterName)) {
/* 74 */       this.value = Boolean.TRUE;
/*    */     } else {
/* 76 */       this.value = Boolean.FALSE;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean clicked() {
/* 81 */     return this.clicked;
/*    */   }
/*    */   
/*    */   public Object onClick(Map submitParams) {
/* 85 */     return null;
/*    */   }
/*    */   
/*    */   protected boolean autoSubmitOnClick() {
/* 89 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\CheckBoxElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */