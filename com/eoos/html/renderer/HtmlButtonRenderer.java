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
/*    */ public class HtmlButtonRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   private static final String TEMPLATE = "<button type=\"{TYPE}\" {NAME} {DISABLED} {ADDITIONAL}>{LABEL}</button>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, Name, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public boolean isDisabled() {
/* 27 */       return false;
/*    */     }
/*    */     
/*    */     public void getAdditionalAttributes(Map<String, String> map) {
/* 31 */       map.put("onClick", getOnClickHandler());
/*    */     }
/*    */     
/*    */     protected abstract String getOnClickHandler();
/*    */     
/*    */     public String getName() {
/* 37 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */     
/*    */     public boolean isSubmitButton() {
/* 44 */       return false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 50 */   private static HtmlButtonRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlButtonRenderer getInstance() {
/* 57 */     if (instance == null) {
/* 58 */       instance = new HtmlButtonRenderer();
/*    */     }
/* 60 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 64 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 68 */     callback.init(params);
/*    */     
/* 70 */     StringBuffer strbCode = StringBufferPool.getThreadInstance().get("<button type=\"{TYPE}\" {NAME} {DISABLED} {ADDITIONAL}>{LABEL}</button>");
/*    */     
/*    */     try {
/* 73 */       StringUtilities.replace(strbCode, "{LABEL}", Util.escapeReservedHTMLChars(callback.getLabel()));
/* 74 */       StringUtilities.replace(strbCode, "{DISABLED}", callback.isDisabled() ? "disabled" : "");
/* 75 */       StringUtilities.replace(strbCode, "{TYPE}", callback.isSubmitButton() ? "submit" : "button");
/*    */       
/* 77 */       String name = null;
/* 78 */       if (callback instanceof Name) {
/* 79 */         name = ((Name)callback).getName();
/*    */       }
/* 81 */       StringUtilities.replace(strbCode, "{NAME}", (name != null) ? ("name=\"" + name + "\"") : "");
/*    */       
/* 83 */       String additional = null;
/* 84 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 85 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
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
/*    */   public static interface Name {
/*    */     String getName();
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getLabel();
/*    */     
/*    */     boolean isDisabled();
/*    */     
/*    */     boolean isSubmitButton();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlButtonRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */