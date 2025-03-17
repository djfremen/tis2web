/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.StringUtilities;
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
/*    */ public class HtmlRadioButtonRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   private static final String TEMPLATE = "<input type=\"radio\" value=\"{VALUE}\" name=\"{NAME}\" {DISABLED} {READONLY} {ADDITIONAL} {CHECKED} />{LABEL}";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, Label, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void init(Map params) {}
/*    */     
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public boolean isDisabled() {
/* 34 */       return false;
/*    */     }
/*    */     
/*    */     public boolean isReadonly() {
/* 38 */       return false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 44 */   private static HtmlRadioButtonRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlRadioButtonRenderer getInstance() {
/* 53 */     if (instance == null) {
/* 54 */       instance = new HtmlRadioButtonRenderer();
/*    */     }
/*    */     
/* 57 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 61 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 65 */     callback.init(params);
/* 66 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<input type=\"radio\" value=\"{VALUE}\" name=\"{NAME}\" {DISABLED} {READONLY} {ADDITIONAL} {CHECKED} />{LABEL}");
/*    */     
/*    */     try {
/* 69 */       StringUtilities.replace(strbCode, "{NAME}", callback.getParameterName());
/* 70 */       StringUtilities.replace(strbCode, "{VALUE}", callback.getSubmitValue());
/* 71 */       StringUtilities.replace(strbCode, "{DISABLED}", callback.isDisabled() ? "disabled" : "");
/* 72 */       StringUtilities.replace(strbCode, "{READONLY}", callback.isReadonly() ? "readonly" : "");
/* 73 */       StringUtilities.replace(strbCode, "{CHECKED}", callback.isChecked() ? "checked" : "");
/*    */       
/* 75 */       String label = null;
/* 76 */       if (callback instanceof Label) {
/* 77 */         label = ((Label)callback).getLabel();
/*    */       }
/* 79 */       StringUtilities.replace(strbCode, "{LABEL}", (label != null) ? label : "");
/*    */       
/* 81 */       String additional = null;
/* 82 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 83 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 85 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 87 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 90 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 95 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Label {
/*    */     String getLabel();
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     boolean isChecked();
/*    */     
/*    */     boolean isDisabled();
/*    */     
/*    */     boolean isReadonly();
/*    */     
/*    */     String getParameterName();
/*    */     
/*    */     String getSubmitValue();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlRadioButtonRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */