/*    */ package com.eoos.html.renderer;
/*    */ 
/*    */ import com.eoos.html.HtmlCodeRenderer;
/*    */ import com.eoos.scsm.v2.objectpool.HashMapPool;
/*    */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ 
/*    */ 
/*    */ public abstract class HtmlTagRenderer
/*    */   implements HtmlCodeRenderer
/*    */ {
/* 31 */   private static final Logger log = Logger.getLogger(HtmlTagRenderer.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public static final String PARAMS_KEY_CALLBACK = "callback";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 41 */     Callback callback = (Callback)params.get("callback");
/*    */     
/* 43 */     return getHtmlCode(callback, params);
/*    */   }
/*    */   
/*    */   protected abstract String getHtmlCode(Callback paramCallback, Map paramMap);
/*    */   
/*    */   protected String getAdditionalAttributesCode(AdditionalAttributes cb) {
/* 49 */     StringBuffer tmp = StringBufferPool.getThreadInstance().get();
/*    */     
/*    */     try {
/* 52 */       if (cb != null) {
/* 53 */         Map additional = HashMapPool.getThreadInstance().get();
/*    */         try {
/* 55 */           cb.getAdditionalAttributes(additional);
/*    */           
/* 57 */           if (additional != null) {
/* 58 */             Iterator<String> iter = additional.keySet().iterator();
/*    */             
/* 60 */             while (iter.hasNext()) {
/*    */               try {
/* 62 */                 String key = iter.next();
/* 63 */                 String value = String.valueOf(additional.get(key));
/* 64 */                 tmp.append(key + "=\"" + value + "\" ");
/* 65 */               } catch (Exception e) {
/* 66 */                 log.error("::getAdditionalAttributesCode() - error:" + e, e);
/*    */               } 
/*    */             } 
/*    */           } 
/*    */         } finally {
/* 71 */           HashMapPool.getThreadInstance().free(additional);
/*    */         } 
/*    */       } 
/*    */       
/* 75 */       return tmp.toString();
/*    */     } finally {
/*    */       
/* 78 */       StringBufferPool.getThreadInstance().free(tmp);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void init(Map param1Map);
/*    */   }
/*    */   
/*    */   public static interface AdditionalAttributes {
/*    */     void getAdditionalAttributes(Map param1Map);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlTagRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */