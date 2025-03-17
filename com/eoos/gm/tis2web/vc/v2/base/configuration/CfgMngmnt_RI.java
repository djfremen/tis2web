/*     */ package com.eoos.gm.tis2web.vc.v2.base.configuration;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueUtil;
/*     */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*     */ import com.eoos.scsm.v2.multiton.v4.SoftMultitonSupport;
/*     */ import com.eoos.scsm.v2.objectpool.HashMapPool;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class CfgMngmnt_RI
/*     */   implements ConfigurationManagement {
/*  17 */   private static final Logger log = Logger.getLogger(CfgMngmnt_RI.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ValueUtil valueUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ConfigurationUtil cfgUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final IConfiguration EMPTYCFG;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadLocal<SoftReference<SoftMultitonSupport>> tl_msCfg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CfgMngmnt_RI(ValueUtil valueUtil) {
/*  47 */     this.tl_msCfg = new ThreadLocal<SoftReference<SoftMultitonSupport>>(); this.valueUtil = valueUtil; this.cfgUtil = new ConfigurationUtil(this, valueUtil); this.EMPTYCFG = new AbstractConfiguration() {
/*     */         public Value getValue(Object key) { return null; } public Set getKeys() { return Collections.EMPTY_SET; } protected ConfigurationUtil getConfigurationUtil() { return CfgMngmnt_RI.this.cfgUtil; }
/*     */       };
/*  50 */   } private SoftMultitonSupport getCfgMultitonSupport() { SoftMultitonSupport ret = null;
/*  51 */     SoftReference<SoftMultitonSupport> sr = this.tl_msCfg.get();
/*  52 */     if (sr == null || (ret = sr.get()) == null) {
/*  53 */       ret = new SoftMultitonSupport((IMultitonSupport.CreationCallback)new IMultitonSupport.CreationCallbackExt()
/*     */           {
/*     */             public Object createInstance(Object key) {
/*  56 */               return new ConfigurationRI((HashMap)key)
/*     */                 {
/*     */                   protected ConfigurationUtil getConfigurationUtil()
/*     */                   {
/*  60 */                     return CfgMngmnt_RI.this.cfgUtil;
/*     */                   }
/*     */                 };
/*     */             }
/*     */ 
/*     */             
/*     */             public Object createStorageReplacement(Object key) {
/*  67 */               return new HashMap<Object, Object>((Map<?, ?>)key);
/*     */             }
/*     */           });
/*  70 */       this.tl_msCfg.set(new SoftReference<SoftMultitonSupport>(ret));
/*     */     } 
/*  72 */     return ret; }
/*     */ 
/*     */   
/*     */   private static HashMapPool getPool() {
/*  76 */     return HashMapPool.getThreadInstance();
/*     */   }
/*     */   
/*     */   public ConfigurationUtil getConfigurationUtil() {
/*  80 */     return this.cfgUtil;
/*     */   }
/*     */   
/*     */   public ValueUtil getValueUtil() {
/*  84 */     return this.valueUtil;
/*     */   }
/*     */   
/*     */   public IConfiguration getEmptyConfiguration() {
/*  88 */     return this.EMPTYCFG;
/*     */   }
/*     */   
/*     */   public IConfiguration removeAttribute(IConfiguration configuration, Object key) {
/*  92 */     if (configuration == this.EMPTYCFG) {
/*  93 */       return this.EMPTYCFG;
/*     */     }
/*  95 */     Map keyMap = getPool().get();
/*     */     try {
/*  97 */       if (configuration instanceof ConfigurationRI) {
/*  98 */         keyMap.putAll(((ConfigurationRI)configuration).map);
/*     */       } else {
/* 100 */         log.warn("detected foreign implementation of IConfiguration interface");
/* 101 */         this.cfgUtil.addToMap(configuration, keyMap);
/*     */       } 
/* 103 */       keyMap.remove(key);
/* 104 */       return (IConfiguration)getCfgMultitonSupport().getInstance(keyMap, true);
/*     */     } finally {
/* 106 */       getPool().free(keyMap);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IConfiguration setAttribute(IConfiguration configuration, Object key, Value value) {
/* 113 */     Map<Object, Value> keyMap = getPool().get();
/*     */     try {
/* 115 */       if (configuration != this.EMPTYCFG) {
/* 116 */         if (configuration instanceof ConfigurationRI) {
/* 117 */           keyMap.putAll(((ConfigurationRI)configuration).map);
/*     */         } else {
/* 119 */           log.warn("detected foreign implementation of IConfiguration interface");
/* 120 */           this.cfgUtil.addToMap(configuration, keyMap);
/*     */         } 
/*     */       }
/* 123 */       keyMap.put(key, value);
/* 124 */       return (IConfiguration)getCfgMultitonSupport().getInstance(keyMap, true);
/*     */     } finally {
/* 126 */       getPool().free(keyMap);
/*     */     } 
/*     */   }
/*     */   
/*     */   public IConfiguration toMutableConfiguration(IConfiguration configuration) {
/* 131 */     HashMap<Object, Object> newMap = null;
/* 132 */     if (configuration == this.EMPTYCFG) {
/* 133 */       newMap = new HashMap<Object, Object>(6);
/* 134 */     } else if (configuration instanceof ConfigurationRI) {
/* 135 */       newMap = new HashMap<Object, Object>(((ConfigurationRI)configuration).map);
/*     */     } else {
/* 137 */       log.warn("detected foreign implementation of IConfiguration interface");
/* 138 */       newMap = new HashMap<Object, Object>();
/* 139 */       this.cfgUtil.addToMap(configuration, newMap);
/*     */     } 
/*     */     
/* 142 */     return new ConfigurationRI(newMap)
/*     */       {
/*     */         protected ConfigurationUtil getConfigurationUtil()
/*     */         {
/* 146 */           return CfgMngmnt_RI.this.cfgUtil;
/*     */         }
/*     */         
/*     */         public int hashCode() {
/* 150 */           return getConfigurationUtil().hashCode(this);
/*     */         }
/*     */         
/*     */         public String toString() {
/* 154 */           return ConfigurationUtil.toString(this);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public IConfiguration toImmutableConfiguration(IConfiguration configuration) {
/* 161 */     if (configuration != null) {
/* 162 */       Map keyMap = getPool().get();
/*     */       try {
/* 164 */         if (configuration instanceof ConfigurationRI) {
/* 165 */           keyMap.putAll(((ConfigurationRI)configuration).map);
/*     */         } else {
/* 167 */           log.warn("detected foreign implementation of IConfiguration interface");
/* 168 */           this.cfgUtil.addToMap(configuration, keyMap);
/*     */         } 
/* 170 */         if (keyMap.size() == 0) {
/* 171 */           return this.EMPTYCFG;
/*     */         }
/* 173 */         return (IConfiguration)getCfgMultitonSupport().getInstance(keyMap, true);
/*     */       } finally {
/*     */         
/* 176 */         getPool().free(keyMap);
/*     */       } 
/*     */     } 
/* 179 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\CfgMngmnt_RI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */