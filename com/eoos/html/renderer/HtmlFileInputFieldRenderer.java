/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlFileInputFieldRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<input name=\"{NAME}\" type=\"file\" {ADDITIONAL} {DISABLED}  />\n";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public boolean isDisabled() {
/* 21 */       return false;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 31 */   private static HtmlFileInputFieldRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlFileInputFieldRenderer getInstance() {
/* 37 */     if (instance == null) {
/* 38 */       instance = new HtmlFileInputFieldRenderer();
/*    */     }
/* 40 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 44 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 48 */     callback.init(params);
/*    */     
/* 50 */     StringBuffer strbInput = StringBufferPool.getThreadInstance().get("<input name=\"{NAME}\" type=\"file\" {ADDITIONAL} {DISABLED}  />\n");
/*    */     try {
/* 52 */       StringUtilities.replace(strbInput, "{NAME}", callback.getParameterName());
/* 53 */       StringUtilities.replace(strbInput, "{DISABLED}", callback.isDisabled() ? "DISABLED" : "");
/*    */       
/* 55 */       String additional = null;
/* 56 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 57 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 59 */       StringUtilities.replace(strbInput, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 61 */       return strbInput.toString();
/*    */     } finally {
/*    */       
/* 64 */       StringBufferPool.getThreadInstance().free(strbInput);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 69 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getParameterName();
/*    */     
/*    */     boolean isDisabled();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlFileInputFieldRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */