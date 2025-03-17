/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlSpanRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<span {ADDITIONAL}>{CONTENT}</span>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map<String, String> map) {
/* 18 */       String id = getID();
/*    */       
/* 20 */       if (id != null) {
/* 21 */         map.put("id", id);
/*    */       }
/*    */       
/* 24 */       String clazz = getClaZZ();
/*    */       
/* 26 */       if (clazz != null) {
/* 27 */         map.put("class", clazz);
/*    */       }
/*    */       
/* 30 */       String style = getStyle();
/*    */       
/* 32 */       if (style != null) {
/* 33 */         map.put("style", style);
/*    */       }
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */     
/*    */     protected String getClaZZ() {
/* 41 */       return null;
/*    */     }
/*    */     
/*    */     protected String getID() {
/* 45 */       return null;
/*    */     }
/*    */     
/*    */     protected String getStyle() {
/* 49 */       return null;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 54 */   private static HtmlSpanRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlSpanRenderer getInstance() {
/* 63 */     if (instance == null) {
/* 64 */       instance = new HtmlSpanRenderer();
/*    */     }
/*    */     
/* 67 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 71 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 75 */     callback.init(params);
/* 76 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<span {ADDITIONAL}>{CONTENT}</span>");
/*    */     
/*    */     try {
/* 79 */       StringUtilities.replace(strbCode, "{CONTENT}", callback.getContent());
/* 80 */       String additional = null;
/*    */       
/* 82 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 83 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/*    */       
/* 86 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 88 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 91 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 96 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getContent();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlSpanRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */