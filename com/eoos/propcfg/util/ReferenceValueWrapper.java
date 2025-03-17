/*     */ package com.eoos.propcfg.util;
/*     */ 
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.scsm.v2.objectpool.ListPool;
/*     */ import com.eoos.scsm.v2.objectpool.StringBufferPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReferenceValueWrapper
/*     */   extends ConfigurationWrapperBase
/*     */ {
/*  17 */   private static final Logger log = Logger.getLogger(ReferenceValueWrapper.class);
/*     */ 
/*     */   
/*     */   private static class CircularReferenceException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class MissingReferenceTargetException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     private String targetKey;
/*     */     
/*     */     private String referencedBy;
/*     */ 
/*     */     
/*     */     public MissingReferenceTargetException(String targetKey, String referencedBy) {
/*  38 */       this.targetKey = targetKey;
/*  39 */       this.referencedBy = referencedBy;
/*     */     }
/*     */     
/*     */     public String getKey() {
/*  43 */       return this.targetKey;
/*     */     }
/*     */     
/*     */     public String getReferencingKey() {
/*  47 */       return this.referencedBy;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  52 */   private static String contentPatternStart = "${";
/*  53 */   private static Pattern contentPattern = Pattern.compile("(?:[^\\\\]|^)\\$\\{(.*?)\\}");
/*     */   
/*     */   private final class ReplaceCallback
/*     */     implements Util.ReplacementCallback {
/*     */     private List processedKeys;
/*     */     
/*     */     public ReplaceCallback(List processedKeys) {
/*  60 */       this.processedKeys = processedKeys;
/*     */     }
/*     */     
/*     */     public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/*  64 */       String key = matcherCallback.getGroup(1);
/*  65 */       return ReferenceValueWrapper.this.getProperty(key, this.processedKeys);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceValueWrapper(Configuration backend) {
/*  71 */     super(backend);
/*     */   }
/*     */   
/*     */   private String getProperty(String key, List<String> processedKeys) throws CircularReferenceException, MissingReferenceTargetException {
/*  75 */     String value = null;
/*  76 */     if (processedKeys.contains(key))
/*     */     {
/*  78 */       throw new CircularReferenceException();
/*     */     }
/*  80 */     value = super.getProperty(key);
/*  81 */     processedKeys.add(key);
/*  82 */     if (value != null) {
/*  83 */       if (value.contains(contentPatternStart)) {
/*  84 */         StringBuffer tmp = StringBufferPool.getThreadInstance().get(value);
/*     */         try {
/*  86 */           Util.replace(tmp, contentPattern, new ReplaceCallback(processedKeys));
/*  87 */           value = tmp.toString();
/*     */         } finally {
/*     */           
/*  90 */           StringBufferPool.getThreadInstance().free(tmp);
/*     */         } 
/*     */       } 
/*  93 */     } else if (processedKeys.size() > 1) {
/*  94 */       throw new MissingReferenceTargetException(key, (String)processedKeys.get(processedKeys.size() - 2));
/*     */     } 
/*     */     
/*  97 */     return value;
/*     */   }
/*     */   
/*     */   public String getProperty(String key) {
/* 101 */     String ret = null;
/* 102 */     List processedKeys = ListPool.getThreadInstance().get();
/*     */     try {
/* 104 */       ret = getProperty(key, processedKeys);
/* 105 */     } catch (MissingReferenceTargetException e) {
/* 106 */       log.warn("missing referenced key: '" + e.getKey() + "' (referenced by '" + e.getReferencingKey() + "'), returing null");
/* 107 */     } catch (CircularReferenceException e) {
/* 108 */       log.warn("found circluar value-reference for key: '" + key + "' , returing null");
/*     */     } finally {
/* 110 */       ListPool.getThreadInstance().free(processedKeys);
/*     */     } 
/* 112 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\ReferenceValueWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */