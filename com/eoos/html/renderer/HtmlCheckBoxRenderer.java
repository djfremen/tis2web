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
/*    */ public class HtmlCheckBoxRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   private static final String TEMPLATE = "<input type=\"CHECKBOX\" value=\"{VALUE}\" name=\"{NAME}\" {ADDITIONAL} {CHECKED} {DISABLED} />{LABEL}";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, Label, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public boolean isDisabled() {
/* 31 */       return false;
/*    */     }
/*    */     
/*    */     public String getLabel() {
/* 35 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */   
/* 43 */   private static HtmlCheckBoxRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlCheckBoxRenderer getInstance() {
/* 52 */     if (instance == null) {
/* 53 */       instance = new HtmlCheckBoxRenderer();
/*    */     }
/*    */     
/* 56 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 60 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 64 */     callback.init(params);
/* 65 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<input type=\"CHECKBOX\" value=\"{VALUE}\" name=\"{NAME}\" {ADDITIONAL} {CHECKED} {DISABLED} />{LABEL}");
/*    */     
/*    */     try {
/* 68 */       StringUtilities.replace(strbCode, "{NAME}", callback.getParameterName());
/* 69 */       StringUtilities.replace(strbCode, "{VALUE}", callback.getSubmitValue());
/* 70 */       StringUtilities.replace(strbCode, "{DISABLED}", callback.isDisabled() ? "disabled" : "");
/* 71 */       StringUtilities.replace(strbCode, "{CHECKED}", callback.isChecked() ? "checked" : "");
/*    */       
/* 73 */       String label = null;
/*    */       
/* 75 */       if (callback instanceof Label) {
/* 76 */         label = ((Label)callback).getLabel();
/*    */       }
/*    */       
/* 79 */       StringUtilities.replace(strbCode, "{LABEL}", (label != null) ? label : "");
/*    */       
/* 81 */       String additional = null;
/*    */       
/* 83 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 84 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/*    */       
/* 87 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 89 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 92 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 97 */     return getHtmlCode((Callback)callback, params);
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
/*    */     String getParameterName();
/*    */     
/*    */     String getSubmitValue();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlCheckBoxRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */