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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HtmlTimezoneOffsetRenderer
/*    */ {
/*    */   protected static final String TEMPLATE = "<input name=\"{NAME}\" id=\"{NAME}\" type=\"hidden\"/><script type=\"text/javascript\">Timerfield=document.getElementById(\"{NAME}\");Timerfield.value=new Date().getTimezoneOffset();</script>";
/*    */   
/*    */   public static abstract class CallbackAdapter
/*    */     implements Callback
/*    */   {
/*    */     public void init(Map params) {}
/*    */   }
/*    */   
/* 33 */   private static HtmlTimezoneOffsetRenderer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized HtmlTimezoneOffsetRenderer getInstance() {
/* 39 */     if (instance == null) {
/* 40 */       instance = new HtmlTimezoneOffsetRenderer();
/*    */     }
/* 42 */     return instance;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback) {
/* 46 */     return getHtmlCode(callback, (Map)null);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Callback callback, Map params) {
/* 50 */     callback.init(params);
/* 51 */     StringBuffer strbInput = StringBufferPool.getThreadInstance().get("<input name=\"{NAME}\" id=\"{NAME}\" type=\"hidden\"/><script type=\"text/javascript\">Timerfield=document.getElementById(\"{NAME}\");Timerfield.value=new Date().getTimezoneOffset();</script>");
/*    */     try {
/* 53 */       StringUtilities.replace(strbInput, "{NAME}", callback.getParameterName());
/*    */       
/* 55 */       return strbInput.toString();
/*    */     } finally {
/*    */       
/* 58 */       StringBufferPool.getThreadInstance().free(strbInput);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 63 */     return getHtmlCode((Callback)callback);
/*    */   }
/*    */   
/*    */   public static interface Callback extends HtmlTagRenderer.Callback {
/*    */     String getParameterName();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTimezoneOffsetRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */