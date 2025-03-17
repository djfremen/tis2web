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
/*    */ public class HtmlLinkRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<a href=\"{LINK}\" {ADDITIONAL}>{LABEL}</a>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */   
/* 28 */   private static HtmlLinkRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlLinkRenderer getInstance() {
/* 37 */     if (instance == null) {
/* 38 */       instance = new HtmlLinkRenderer();
/*    */     }
/*    */     
/* 41 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 45 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 49 */     callback.init(params);
/* 50 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<a href=\"{LINK}\" {ADDITIONAL}>{LABEL}</a>");
/*    */     try {
/* 52 */       StringUtilities.replace(strbCode, "{LINK}", Util.escapeReservedHTMLChars(callback.getLink()));
/* 53 */       StringUtilities.replace(strbCode, "{LABEL}", callback.getLabel());
/*    */       
/* 55 */       String additional = null;
/*    */       
/* 57 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 58 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/*    */       
/* 61 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 63 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 66 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 71 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getLabel();
/*    */     
/*    */     String getLink();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlLinkRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */