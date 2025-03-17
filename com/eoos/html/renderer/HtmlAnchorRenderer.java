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
/*    */ public class HtmlAnchorRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<a id=\"{NAME}\"{ADDITIONAL}>{LABEL}</a>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, Label, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public String getLabel() {
/* 26 */       return "";
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */   
/* 34 */   private static HtmlAnchorRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlAnchorRenderer getInstance() {
/* 43 */     if (instance == null) {
/* 44 */       instance = new HtmlAnchorRenderer();
/*    */     }
/*    */     
/* 47 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 51 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 55 */     callback.init(params);
/* 56 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<a id=\"{NAME}\"{ADDITIONAL}>{LABEL}</a>");
/*    */     
/*    */     try {
/* 59 */       String anchorName = callback.getName();
/* 60 */       StringUtilities.replace(strbCode, "{NAME}", anchorName);
/*    */       
/* 62 */       String label = null;
/*    */       
/* 64 */       if (callback instanceof Label) {
/* 65 */         label = ((Label)callback).getLabel();
/*    */       }
/*    */       
/* 68 */       StringUtilities.replace(strbCode, "{LABEL}", (label != null) ? label : "");
/*    */       
/* 70 */       String additional = null;
/*    */       
/* 72 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 73 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/*    */       
/* 76 */       StringUtilities.replace(strbCode, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 78 */       return strbCode.toString();
/*    */     } finally {
/*    */       
/* 81 */       StringBufferPool.getThreadInstance().free(strbCode);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getHtmlCode(final String name) {
/* 86 */     return getHtmlCode(new CallbackAdapter() {
/*    */           public String getName() {
/* 88 */             return name;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 94 */     return getHtmlCode((Callback)callback, params);
/*    */   }
/*    */   
/*    */   public static interface Label {
/*    */     String getLabel();
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getName();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlAnchorRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */