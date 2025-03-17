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
/*    */ public class HtmlImgRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map<String, String> map) {
/* 21 */       map.put("border", "0");
/*    */     }
/*    */     
/*    */     public String getAlternativeText() {
/* 25 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */   
/* 33 */   private static HtmlImgRenderer instance = null;
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String template = "<img src=\"{SOURCE}\" alt=\"{ALT}\" {ADDITIONAL}/>";
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlImgRenderer getInstance() {
/* 42 */     if (instance == null) {
/* 43 */       instance = new HtmlImgRenderer();
/*    */     }
/* 45 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 49 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 53 */     callback.init(params);
/* 54 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<img src=\"{SOURCE}\" alt=\"{ALT}\" {ADDITIONAL}/>");
/*    */     
/*    */     try {
/* 57 */       StringUtilities.replace(strbCode, "{SOURCE}", Util.escapeReservedHTMLChars(callback.getImageSource()));
/*    */       
/* 59 */       String altText = callback.getAlternativeText();
/* 60 */       StringUtilities.replace(strbCode, "{ALT}", (altText != null) ? altText : "");
/*    */       
/* 62 */       String additional = null;
/* 63 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 64 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 66 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/* 67 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 70 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 75 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getImageSource();
/*    */     
/*    */     String getAlternativeText();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlImgRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */