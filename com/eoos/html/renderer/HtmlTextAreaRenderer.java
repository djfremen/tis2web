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
/*    */ 
/*    */ 
/*    */ public class HtmlTextAreaRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<textarea name=\"{NAME}\" {COLS} {ROWS} {DISABLED} {READONLY} {ADDITIONAL} >{VALUE}</textarea>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void init(Map params) {}
/*    */     
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public boolean isDisabled() {
/* 33 */       return false;
/*    */     }
/*    */     
/*    */     public boolean isReadonly() {
/* 37 */       return false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   private static HtmlTextAreaRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlTextAreaRenderer getInstance() {
/* 50 */     if (instance == null) {
/* 51 */       instance = new HtmlTextAreaRenderer();
/*    */     }
/* 53 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 57 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 61 */     callback.init(params);
/* 62 */     StringBuffer strbInput = StringBufferPool.getThreadInstance().get("<textarea name=\"{NAME}\" {COLS} {ROWS} {DISABLED} {READONLY} {ADDITIONAL} >{VALUE}</textarea>");
/*    */     try {
/* 64 */       StringUtilities.replace(strbInput, "{NAME}", callback.getParameterName());
/* 65 */       StringUtilities.replace(strbInput, "{VALUE}", (callback.getValue() != null) ? Util.escapeReservedHTMLChars(callback.getValue()) : "");
/* 66 */       StringUtilities.replace(strbInput, "{DISABLED}", callback.isDisabled() ? "disabled=\"disabled\"" : "");
/* 67 */       StringUtilities.replace(strbInput, "{READONLY}", callback.isReadonly() ? "readonly=\"readonly\"" : "");
/* 68 */       StringUtilities.replace(strbInput, "{COLS}", (callback.getCols() != null) ? ("cols=\"" + callback.getCols() + "\"") : "");
/* 69 */       StringUtilities.replace(strbInput, "{ROWS}", (callback.getCols() != null) ? ("rows=\"" + callback.getRows() + "\"") : "");
/*    */       
/* 71 */       String additional = null;
/* 72 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 73 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/* 75 */       StringUtilities.replace(strbInput, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 77 */       return strbInput.toString();
/*    */     } finally {
/*    */       
/* 80 */       StringBufferPool.getThreadInstance().free(strbInput);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 85 */     return getHtmlCode((Callback)callback);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getParameterName();
/*    */     
/*    */     String getValue();
/*    */     
/*    */     String getCols();
/*    */     
/*    */     String getRows();
/*    */     
/*    */     boolean isDisabled();
/*    */     
/*    */     boolean isReadonly();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTextAreaRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */