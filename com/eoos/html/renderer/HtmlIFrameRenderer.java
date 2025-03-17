/*     */ package com.eoos.html.renderer;
/*     */ 
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HtmlIFrameRenderer
/*     */   extends HtmlTagRenderer
/*     */ {
/*     */   protected static final String TEMPLATE = "<iframe src=\"{SOURCE}\" {NAME} {ADDITIONAL}>{NOFRAME_CODE}</iframe>";
/*     */   
/*     */   public static abstract class CallbackAdapter
/*     */     implements Callback, Name, NoFrame, HtmlTagRenderer.AdditionalAttributes
/*     */   {
/*     */     public void getAdditionalAttributes(Map map) {}
/*     */     
/*     */     public String getAlternativeCode() {
/*  30 */       return null;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  34 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void init(Map params) {}
/*     */   }
/*     */ 
/*     */   
/*  42 */   private static HtmlIFrameRenderer instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HtmlIFrameRenderer getInstance() {
/*  51 */     if (instance == null) {
/*  52 */       instance = new HtmlIFrameRenderer();
/*     */     }
/*     */     
/*  55 */     return instance;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback) {
/*  59 */     return getHtmlCode(callback, (Map)null);
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Callback callback, Map params) {
/*  63 */     callback.init(params);
/*  64 */     StringBuffer code = StringBufferPool.getThreadInstance().get("<iframe src=\"{SOURCE}\" {NAME} {ADDITIONAL}>{NOFRAME_CODE}</iframe>");
/*     */     
/*     */     try {
/*  67 */       StringUtilities.replace(code, "{SOURCE}", Util.escapeReservedHTMLChars(callback.getSource()));
/*     */       
/*  69 */       String name = null;
/*     */       
/*  71 */       if (callback instanceof Name) {
/*  72 */         name = ((Name)callback).getName();
/*     */       }
/*     */       
/*  75 */       StringUtilities.replace(code, "{NAME}", (name != null) ? ("name=\"" + name + "\"") : "");
/*     */       
/*  77 */       String alt = null;
/*     */       
/*  79 */       if (callback instanceof NoFrame) {
/*  80 */         alt = ((NoFrame)callback).getAlternativeCode();
/*     */       }
/*     */       
/*  83 */       StringUtilities.replace(code, "{NOFRAME_CODE}", (alt != null) ? alt : "");
/*     */       
/*  85 */       String additional = null;
/*     */       
/*  87 */       if (callback instanceof HtmlTagRenderer.AdditionalAttributes) {
/*  88 */         additional = getAdditionalAttributesCode((HtmlTagRenderer.AdditionalAttributes)callback);
/*     */       }
/*     */       
/*  91 */       StringUtilities.replace(code, "{ADDITIONAL}", (additional != null) ? additional : "");
/*     */       
/*  93 */       return code.toString();
/*     */     } finally {
/*     */       
/*  96 */       StringBufferPool.getThreadInstance().free(code);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getHtmlCode(HtmlTagRenderer.Callback callback, Map params) {
/* 101 */     return getHtmlCode((Callback)callback, params);
/*     */   }
/*     */   
/*     */   public static interface NoFrame {
/*     */     String getAlternativeCode();
/*     */   }
/*     */   
/*     */   public static interface Name {
/*     */     String getName();
/*     */   }
/*     */   
/*     */   public static interface Callback extends HtmlTagRenderer.Callback {
/*     */     String getSource();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\renderer\HtmlIFrameRenderer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */