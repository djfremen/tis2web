/*     */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.cache.SoftCache;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class IntersectingCfgsCalculation_STDImpl
/*     */   implements IntersectingCfgsCalculation
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(IntersectingCfgsCalculation_STDImpl.class);
/*     */   
/*     */   private CfgProvider configProvider;
/*     */   
/*     */   private ConfigurationUtil cfgUtil;
/*     */   
/*     */   private IntersectingCfgsCalculation supportedCfgsCacheFront;
/*     */   
/*     */   private Cache supportedCfgsCache;
/*     */   
/*  31 */   private long modificationDate = 0L;
/*     */   
/*     */   public IntersectingCfgsCalculation_STDImpl(CfgProvider backend, ConfigurationUtil cfgUtil) {
/*  34 */     this.configProvider = backend;
/*  35 */     this.cfgUtil = cfgUtil;
/*  36 */     this.supportedCfgsCache = (Cache)new SoftCache();
/*     */     
/*  38 */     this.supportedCfgsCacheFront = (IntersectingCfgsCalculation)ReflectionUtil.createCachingProxy(new IntersectingCfgsCalculation()
/*     */         {
/*     */           public Set getIntersectingConfigs(IConfiguration cfg) {
/*  41 */             return IntersectingCfgsCalculation_STDImpl.this._getIntersectingConfigs(cfg);
/*     */           }
/*     */         },  this.supportedCfgsCache, (ReflectionUtil.CachingProxyCallback)new ReflectionUtil.CachingProxyCallbackAdapter()
/*     */         {
/*     */           public Object createKey(Method m, Object[] args)
/*     */           {
/*  47 */             return args[0];
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Set _getIntersectingConfigs(final IConfiguration currentCfg) {
/*  54 */     if (log.isDebugEnabled()) {
/*  55 */       log.debug("retrieving supporting configurations for currentCfg: " + currentCfg);
/*     */     }
/*     */     
/*  58 */     if (currentCfg.equals(this.cfgUtil.getEmptyConfiguration())) {
/*  59 */       return new LinkedHashSet(this.configProvider.getConfigurations());
/*     */     }
/*     */     
/*  62 */     Collection configsToExaminate = this.configProvider.getConfigurations();
/*  63 */     for (Iterator<IConfiguration> iter = ((Cache.KeyQuery)this.supportedCfgsCache).getKeys().iterator(); iter.hasNext(); ) {
/*  64 */       IConfiguration key = iter.next();
/*  65 */       if (this.cfgUtil.isPartialConfiguration(key, currentCfg, ConfigurationUtil.EXPAND_WITH_ANY)) {
/*  66 */         Set _candidate = (Set)this.supportedCfgsCache.lookup(key);
/*  67 */         if (_candidate != null && _candidate.size() < configsToExaminate.size()) {
/*  68 */           configsToExaminate = _candidate;
/*  69 */           if (log.isDebugEnabled()) {
/*  70 */             log.debug("...found partial config:" + key + " of currentConfig that has a cached result, replacing cfg set to examine");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     if (log.isDebugEnabled()) {
/*  77 */       log.debug("...examine " + configsToExaminate.size() + " configurations");
/*     */     }
/*     */     
/*  80 */     final Set<?> intersection = new LinkedHashSet();
/*     */     try {
/*  82 */       CollectionUtil.foreach(configsToExaminate, new CollectionUtil.ForeachCallback()
/*     */           {
/*     */             public Object executeOperation(Object item) throws Exception {
/*  85 */               IConfiguration cfg = IntersectingCfgsCalculation_STDImpl.this.cfgUtil.intersect((IConfiguration)item, currentCfg, ConfigurationUtil.EXPAND_WITH_ANY);
/*  86 */               if (cfg != null) {
/*  87 */                 intersection.add(cfg);
/*     */               }
/*  89 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*  93 */     catch (Exception e) {
/*  94 */       throw new RuntimeException(e);
/*     */     } 
/*  96 */     return Collections.unmodifiableSet(intersection);
/*     */   }
/*     */   
/*     */   public Set getIntersectingConfigs(IConfiguration currentCfg) {
/* 100 */     if (this.modificationDate != this.configProvider.getLastModified()) {
/* 101 */       this.supportedCfgsCache.clear();
/* 102 */       this.modificationDate = this.configProvider.getLastModified();
/*     */     } 
/* 104 */     return this.supportedCfgsCacheFront.getIntersectingConfigs(currentCfg);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\IntersectingCfgsCalculation_STDImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */