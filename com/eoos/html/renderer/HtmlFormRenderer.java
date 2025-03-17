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
/*    */ 
/*    */ 
/*    */ public class HtmlFormRenderer
/*    */   extends HtmlTagRenderer
/*    */ {
/*    */   private static final String TEMPLATE_FORM = "<form {NAME} action=\"{ACTION}\" method=\"{METHOD}\" {ADDITIONAL}>{FORMBODY}</form>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback, Name, HtmlTagRenderer.AdditionalAttributes
/*    */   {
/*    */     public void getAdditionalAttributes(Map map) {}
/*    */     
/*    */     public String getMethod() {
/* 33 */       return "post";
/*    */     }
/*    */     
/*    */     public String getName() {
/* 37 */       return null;
/*    */     }
/*    */ 
/*    */     
/*    */     public void init(Map params) {}
/*    */   }
/*    */ 
/*    */   
/* 45 */   private static HtmlFormRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlFormRenderer getInstance() {
/* 54 */     if (instance == null) {
/* 55 */       instance = new HtmlFormRenderer();
/*    */     }
/*    */     
/* 58 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 62 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 66 */     callback.init(params);
/* 67 */     StringBuffer strbForm = StringBufferPool.getThreadInstance().get("<form {NAME} action=\"{ACTION}\" method=\"{METHOD}\" {ADDITIONAL}>{FORMBODY}</form>");
/*    */     try {
/* 69 */       StringUtilities.replace(strbForm, "{ACTION}", Util.escapeReservedHTMLChars(callback.getActionUrl()));
/* 70 */       StringUtilities.replace(strbForm, "{METHOD}", callback.getMethod());
/* 71 */       StringUtilities.replace(strbForm, "{FORMBODY}", callback.getFormBody());
/*    */       
/* 73 */       String name = null;
/*    */       
/* 75 */       if (callback instanceof Name) {
/* 76 */         name = ((Name)callback).getName();
/*    */       }
/*    */       
/* 79 */       StringUtilities.replace(strbForm, "{NAME}", (name != null) ? ("name=\"" + name + "\"") : "");
/*    */       
/* 81 */       String additional = null;
/*    */       
/* 83 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/* 84 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*    */       }
/*    */       
/* 87 */       StringUtilities.replace(strbForm, "{ADDITIONAL}", (additional != null) ? additional : "");
/*    */       
/* 89 */       return strbForm.toString();
/*    */     } finally {
/*    */       
/* 92 */       StringBufferPool.getThreadInstance().free(strbForm);
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
/*    */     public static final String METHOD_GET = "get";
/*    */     public static final String METHOD_POST = "post";
/*    */     
/*    */     String getActionUrl();
/*    */     
/*    */     String getFormBody();
/*    */     
/*    */     String getMethod();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlFormRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */