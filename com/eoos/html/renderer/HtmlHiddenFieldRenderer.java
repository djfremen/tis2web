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
/*    */ public class HtmlHiddenFieldRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<input name=\"{NAME}\" value=\"{VALUE}\" type=\"hidden\"  {ADDITIONAL} />";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */   
/* 27 */   private static HtmlHiddenFieldRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlHiddenFieldRenderer getInstance() {
/* 33 */     if (instance == null) {
/* 34 */       instance = new HtmlHiddenFieldRenderer();
/*    */     }
/* 36 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 40 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 44 */     callback.init(params);
/* 45 */     StringBuffer strbInput = StringBufferPool.getThreadInstance().get("<input name=\"{NAME}\" value=\"{VALUE}\" type=\"hidden\"  {ADDITIONAL} />");
/*    */     try {
/* 47 */       StringUtilities.replace(strbInput, "{NAME}", callback.getParameterName());
/* 48 */       StringUtilities.replace(strbInput, "{VALUE}", Util.escapeReservedHTMLChars(callback.getValue()));
/*    */       
/* 50 */       String additional = null;
/* 51 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 52 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 54 */       StringUtilities.replace(strbInput, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 56 */       return strbInput.toString();
/*    */     } finally {
/*    */       
/* 59 */       StringBufferPool.getThreadInstance().free(strbInput);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 64 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getParameterName();
/*    */     
/*    */     String getValue();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlHiddenFieldRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */