/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.scsm.v2.util.Util;
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
/*    */ public class HtmlTextInputFieldRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<input name=\"{NAME}\" value=\"{VALUE}\" type=\"{TYPE}\" {DISABLED} {READONLY} {ADDITIONAL} />";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void init(Map params) {}
/*    */     
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public boolean isDisabled() {
/* 31 */       return false;
/*    */     }
/*    */     
/*    */     public boolean isReadonly() {
/* 35 */       return false;
/*    */     }
/*    */     
/*    */     public boolean isMasked() {
/* 39 */       return false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   private static HtmlTextInputFieldRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlTextInputFieldRenderer getInstance() {
/* 52 */     if (instance == null) {
/* 53 */       instance = new HtmlTextInputFieldRenderer();
/*    */     }
/* 55 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 59 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 63 */     callback.init(params);
/* 64 */     StringBuffer strbInput = StringBufferPool.getThreadInstance().get("<input name=\"{NAME}\" value=\"{VALUE}\" type=\"{TYPE}\" {DISABLED} {READONLY} {ADDITIONAL} />");
/*    */     try {
/* 66 */       StringUtilities.replace(strbInput, "{NAME}", callback.getParameterName());
/* 67 */       StringUtilities.replace(strbInput, "{VALUE}", Util.escapeReservedHTMLChars(callback.getValue()));
/* 68 */       StringUtilities.replace(strbInput, "{DISABLED}", callback.isDisabled() ? "disabled=\"disabled\"" : "");
/* 69 */       StringUtilities.replace(strbInput, "{READONLY}", callback.isReadonly() ? "readonly=\"readonly\"" : "");
/* 70 */       StringUtilities.replace(strbInput, "{TYPE}", callback.isMasked() ? "password" : "text");
/*    */       
/* 72 */       String additional = null;
/* 73 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 74 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 76 */       StringUtilities.replace(strbInput, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 78 */       return strbInput.toString();
/*    */     } finally {
/*    */       
/* 81 */       StringBufferPool.getThreadInstance().free(strbInput);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 86 */     return getHtmlCode((Callback)callback);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getParameterName();
/*    */     
/*    */     String getValue();
/*    */     
/*    */     boolean isDisabled();
/*    */     
/*    */     boolean isReadonly();
/*    */     
/*    */     boolean isMasked();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTextInputFieldRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */