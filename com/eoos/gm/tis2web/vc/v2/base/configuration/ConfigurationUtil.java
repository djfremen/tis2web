/*     */ package com.eoos.gm.tis2web.vc.v2.base.configuration;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.v2.base.NotNormalizedException;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgDataProviderImpl;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.Value;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.value.ValueUtil;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.objectpool.ListPool;
/*     */ import com.eoos.scsm.v2.objectpool.SetPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationUtil
/*     */   implements ConfigurationManagement, ConfigurationManagement.Mixin1
/*     */ {
/*  30 */   private static final Logger log = Logger.getLogger(ConfigurationUtil.class);
/*     */   
/*     */   public final ConfigurationManagement cfgManagement;
/*     */   
/*     */   private ConfigurationManagement.Mixin1 mixin1;
/*     */   
/*     */   public final ValueUtil valueUtil;
/*     */   
/*     */   public ConfigurationUtil(ConfigurationManagement cfgMgmt, ValueUtil valueUtil) {
/*  39 */     this.cfgManagement = cfgMgmt;
/*  40 */     if (this.cfgManagement instanceof ConfigurationManagement.Mixin1) {
/*  41 */       this.mixin1 = (ConfigurationManagement.Mixin1)this.cfgManagement;
/*     */     } else {
/*  43 */       this.mixin1 = new Mixin1_STD(valueUtil);
/*     */     } 
/*  45 */     this.valueUtil = valueUtil;
/*     */   }
/*     */ 
/*     */   
/*     */   public ValueUtil getValueUtil() {
/*  50 */     return this.valueUtil;
/*     */   }
/*     */   
/*     */   public boolean isPartialConfiguration(IConfiguration configuration, IConfiguration potentialPart, NormalizationMode mode) {
/*  54 */     boolean ret = true;
/*  55 */     SetPool pool = SetPool.getThreadInstance();
/*     */     
/*  57 */     Set commonKeys = pool.get();
/*     */     try {
/*  59 */       commonKeys.addAll(configuration.getKeys());
/*  60 */       commonKeys.retainAll(potentialPart.getKeys());
/*     */       
/*  62 */       for (Iterator iter = commonKeys.iterator(); iter.hasNext() && ret; ) {
/*  63 */         Object key = iter.next();
/*  64 */         ret = (ret && this.valueUtil.includes(configuration.getValue(key), potentialPart.getValue(key)));
/*     */       } 
/*     */     } finally {
/*  67 */       pool.free(commonKeys);
/*     */     } 
/*     */     
/*  70 */     if (mode == EXPAND_WITH_ANY) {
/*     */       
/*  72 */       Set extraKeys = pool.get();
/*     */       try {
/*  74 */         extraKeys.addAll(configuration.getKeys());
/*     */         
/*  76 */         extraKeys.removeAll(potentialPart.getKeys());
/*  77 */         for (Iterator iter = extraKeys.iterator(); iter.hasNext() && ret;) {
/*  78 */           ret = (ret && this.valueUtil.isANY(configuration.getValue(iter.next())));
/*     */         }
/*     */       } finally {
/*  81 */         pool.free(extraKeys);
/*     */       } 
/*     */     } 
/*     */     
/*  85 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean areNormalized(IConfiguration cfg1, IConfiguration cfg2) {
/*  89 */     return cfg1.getKeys().equals(cfg2.getKeys());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Decorator
/*     */   {
/*     */     private ConfigurationUtil backend;
/*     */ 
/*     */ 
/*     */     
/*     */     public Decorator(ConfigurationUtil backend) {
/* 102 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     public ConfigurationUtil getConfigurationUtil() {
/* 106 */       return this.backend;
/*     */     }
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
/*     */     
/*     */     public IConfiguration[] normalize(IConfiguration[] cfgs, Value extensionValue) {
/* 122 */       return (IConfiguration[])normalize(CollectionUtil.toList((Object[])cfgs), extensionValue).toArray((Object[])new IConfiguration[cfgs.length]);
/*     */     }
/*     */     
/*     */     public Collection normalize(Collection configurations, Value extensionValue) {
/* 126 */       List<IConfiguration> tmpRet = ListPool.getThreadInstance().get();
/*     */       try {
/* 128 */         SetPool poolSet = SetPool.getThreadInstance();
/* 129 */         Set attributeKeys = poolSet.get();
/*     */         try {
/* 131 */           for (Iterator<IConfiguration> iterator1 = configurations.iterator(); iterator1.hasNext();) {
/* 132 */             attributeKeys.addAll(((IConfiguration)iterator1.next()).getKeys());
/*     */           }
/*     */           
/* 135 */           for (Iterator<IConfiguration> iter = configurations.iterator(); iter.hasNext();) {
/* 136 */             tmpRet.add(this.backend.normalize(iter.next(), attributeKeys, extensionValue));
/*     */           }
/*     */         } finally {
/* 139 */           poolSet.free(attributeKeys);
/*     */         } 
/*     */         
/* 142 */         if (tmpRet.size() == 0)
/* 143 */           return Collections.EMPTY_LIST; 
/* 144 */         if (tmpRet.size() == 1) {
/* 145 */           return Collections.singleton(tmpRet.get(0));
/*     */         }
/* 147 */         return new LinkedList<IConfiguration>(tmpRet);
/*     */       }
/*     */       finally {
/*     */         
/* 151 */         ListPool.getThreadInstance().free(tmpRet);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Collection normalize(Collection configurations, IConfiguration masterCfg, Value extensionValue) {
/* 156 */       List<IConfiguration> retTmp = ListPool.getThreadInstance().get();
/*     */       
/*     */       try {
/* 159 */         SetPool poolSet = SetPool.getThreadInstance();
/* 160 */         Set attributeKeys = poolSet.get();
/*     */         try {
/* 162 */           attributeKeys.addAll(masterCfg.getKeys());
/*     */           
/* 164 */           for (Iterator<IConfiguration> iter = configurations.iterator(); iter.hasNext();) {
/* 165 */             retTmp.add(this.backend.normalize(iter.next(), attributeKeys, extensionValue));
/*     */           }
/*     */         } finally {
/* 168 */           poolSet.free(attributeKeys);
/*     */         } 
/*     */         
/* 171 */         if (retTmp.size() == 0)
/* 172 */           return Collections.EMPTY_LIST; 
/* 173 */         if (retTmp.size() == 1) {
/* 174 */           return Collections.singleton(retTmp.get(0));
/*     */         }
/* 176 */         return new LinkedList<IConfiguration>(retTmp);
/*     */       }
/*     */       finally {
/*     */         
/* 180 */         ListPool.getThreadInstance().free(retTmp);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IConfiguration normalize(IConfiguration config, Set keys, Value extensionValue) {
/* 188 */     IConfiguration ret = toMutableConfiguration(config);
/*     */     
/* 190 */     SetPool poolSet = SetPool.getThreadInstance();
/* 191 */     Set missingKeys = poolSet.get();
/*     */     try {
/* 193 */       missingKeys.addAll(keys);
/*     */       Iterator iter;
/* 195 */       for (iter = config.getKeys().iterator(); iter.hasNext(); ) {
/* 196 */         Object key = iter.next();
/* 197 */         if (!keys.contains(key)) {
/* 198 */           ((IConfiguration.Mutable)ret).removeAttribute(key); continue;
/*     */         } 
/* 200 */         missingKeys.remove(key);
/*     */       } 
/*     */       
/* 203 */       for (iter = missingKeys.iterator(); iter.hasNext();) {
/* 204 */         ((IConfiguration.Mutable)ret).setAttribute(iter.next(), extensionValue);
/*     */       }
/*     */     } finally {
/*     */       
/* 208 */       poolSet.free(missingKeys);
/*     */     } 
/* 210 */     return toImmutableConfiguration(ret);
/*     */   }
/*     */   
/*     */   public static final class NormalizationMode
/*     */   {
/*     */     private String description;
/*     */     
/*     */     private NormalizationMode(String description) {
/* 218 */       this.description = description;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 222 */       return this.description;
/*     */     }
/*     */   }
/*     */   
/* 226 */   public static final NormalizationMode EXPAND_WITH_ANY = new NormalizationMode("EXPAND_WITH_ANY");
/*     */   
/* 228 */   public static final NormalizationMode REDUCE_TO_COMMON = new NormalizationMode("REDUCE_TO_COMMON");
/*     */   
/*     */   public IConfiguration intersect(IConfiguration cfg1, IConfiguration cfg2, NormalizationMode mode) throws NotNormalizedException {
/* 231 */     if (log.isDebugEnabled()) {
/* 232 */       log.debug("intersecting " + cfg1 + " with " + cfg2 + " (normalization mode: " + mode + ")");
/*     */     }
/*     */     
/* 235 */     IConfiguration ret = this.cfgManagement.toMutableConfiguration(this.cfgManagement.getEmptyConfiguration());
/*     */     
/* 237 */     SetPool poolSet = SetPool.getThreadInstance();
/* 238 */     Set<?> commonKeys = poolSet.get();
/*     */     try {
/* 240 */       commonKeys.addAll(cfg1.getKeys());
/* 241 */       commonKeys.retainAll(cfg2.getKeys());
/*     */       
/* 243 */       for (Iterator iter = commonKeys.iterator(); iter.hasNext() && ret != null; ) {
/* 244 */         Object key = iter.next();
/* 245 */         Value value = this.valueUtil.intersect(cfg1.getValue(key), cfg2.getValue(key));
/* 246 */         if (value != null) {
/* 247 */           ((IConfiguration.Mutable)ret).setAttribute(key, value); continue;
/*     */         } 
/* 249 */         ret = null;
/*     */       } 
/*     */       
/* 252 */       if (ret != null && mode == EXPAND_WITH_ANY) {
/* 253 */         Set missingKeys = poolSet.get();
/*     */         try {
/* 255 */           missingKeys.addAll(cfg1.getKeys());
/* 256 */           missingKeys.removeAll(commonKeys); Iterator iterator;
/* 257 */           for (iterator = missingKeys.iterator(); iterator.hasNext(); ) {
/* 258 */             Object key = iterator.next();
/* 259 */             ((IConfiguration.Mutable)ret).setAttribute(key, cfg1.getValue(key));
/*     */           } 
/*     */           
/* 262 */           missingKeys.clear();
/* 263 */           missingKeys.addAll(cfg2.getKeys());
/* 264 */           missingKeys.removeAll(commonKeys);
/* 265 */           for (iterator = missingKeys.iterator(); iterator.hasNext(); ) {
/* 266 */             Object key = iterator.next();
/* 267 */             ((IConfiguration.Mutable)ret).setAttribute(key, cfg1.getValue(key));
/*     */           } 
/*     */         } finally {
/* 270 */           poolSet.free(missingKeys);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 274 */       poolSet.free(commonKeys);
/*     */     } 
/*     */     
/* 277 */     if (log.isDebugEnabled()) {
/* 278 */       log.debug("...result: " + ret);
/*     */     }
/* 280 */     return toImmutableConfiguration(ret);
/*     */   }
/*     */   
/*     */   public boolean haveIntersection(IConfiguration cfg1, IConfiguration cfg2) {
/* 284 */     boolean ret = true;
/*     */     
/* 286 */     SetPool poolSet = SetPool.getThreadInstance();
/* 287 */     Set commonKeys = poolSet.get();
/*     */     try {
/* 289 */       commonKeys.addAll(cfg1.getKeys());
/* 290 */       commonKeys.retainAll(cfg2.getKeys());
/*     */       
/* 292 */       for (Iterator iter = commonKeys.iterator(); iter.hasNext() && ret; ) {
/* 293 */         Object key = iter.next();
/* 294 */         ret = this.valueUtil.haveIntersection(cfg1.getValue(key), cfg2.getValue(key));
/*     */       } 
/*     */     } finally {
/* 297 */       poolSet.free(commonKeys);
/*     */     } 
/* 299 */     return ret;
/*     */   }
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
/*     */   public boolean supportsFully(CfgDataProvider dataProvider, IConfiguration configuration) {
/* 313 */     if (log.isDebugEnabled()) {
/* 314 */       log.debug("determining if data provider " + String.valueOf(dataProvider) + " supports configuration " + String.valueOf(configuration));
/*     */     }
/*     */     
/* 317 */     boolean ret = true;
/* 318 */     for (Iterator iter = configuration.getKeys().iterator(); iter.hasNext() && ret; ) {
/* 319 */       Set values = null;
/* 320 */       Object key = iter.next();
/* 321 */       values = dataProvider.getValues(key, configuration);
/* 322 */       if (values == null || values.size() == 0) {
/* 323 */         ret = false; continue;
/*     */       } 
/* 325 */       Value combinedValue = this.valueUtil.union(values);
/* 326 */       Value cfgValue = configuration.getValue(key);
/* 327 */       ret = this.valueUtil.includes(combinedValue, cfgValue);
/*     */     } 
/*     */ 
/*     */     
/* 331 */     if (log.isDebugEnabled()) {
/* 332 */       log.debug("...result: " + ret);
/*     */     }
/* 334 */     return ret;
/*     */   }
/*     */   
/*     */   public static String toString(IConfiguration configuration) {
/* 338 */     StringBuffer ret = new StringBuffer();
/* 339 */     ret.append("[");
/* 340 */     for (Iterator iter = configuration.getKeys().iterator(); iter.hasNext(); ) {
/* 341 */       Object key = iter.next();
/* 342 */       Object value = configuration.getValue(key);
/* 343 */       ret.append(key + ":" + value);
/* 344 */       if (iter.hasNext()) {
/* 345 */         ret.append(", ");
/*     */       }
/*     */     } 
/* 348 */     ret.append("]");
/* 349 */     return ret.toString();
/*     */   }
/*     */   
/*     */   public Set getConfigurations(CfgDataProvider dataProvider) {
/* 353 */     List tmp = ListPool.getThreadInstance().get();
/*     */     try {
/* 355 */       tmp.addAll(dataProvider.getKeys());
/* 356 */       return getConfigurations(dataProvider, tmp);
/*     */     } finally {
/* 358 */       ListPool.getThreadInstance().free(tmp);
/*     */     } 
/*     */   }
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
/*     */   private Set getConfigurations(CfgDataProvider dataProvider, List keys) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokeinterface size : ()I
/*     */     //   6: ifne -> 22
/*     */     //   9: aload_0
/*     */     //   10: getfield cfgManagement : Lcom/eoos/gm/tis2web/vc/v2/base/configuration/ConfigurationManagement;
/*     */     //   13: invokeinterface getEmptyConfiguration : ()Lcom/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration;
/*     */     //   18: invokestatic singleton : (Ljava/lang/Object;)Ljava/util/Set;
/*     */     //   21: areturn
/*     */     //   22: new java/util/LinkedHashSet
/*     */     //   25: dup
/*     */     //   26: invokespecial <init> : ()V
/*     */     //   29: astore_3
/*     */     //   30: aload_2
/*     */     //   31: iconst_0
/*     */     //   32: invokeinterface get : (I)Ljava/lang/Object;
/*     */     //   37: astore #4
/*     */     //   39: aload_0
/*     */     //   40: aload_1
/*     */     //   41: aload_2
/*     */     //   42: iconst_1
/*     */     //   43: aload_2
/*     */     //   44: invokeinterface size : ()I
/*     */     //   49: invokeinterface subList : (II)Ljava/util/List;
/*     */     //   54: invokespecial getConfigurations : (Lcom/eoos/gm/tis2web/vc/v2/base/provider/CfgDataProvider;Ljava/util/List;)Ljava/util/Set;
/*     */     //   57: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   62: astore #5
/*     */     //   64: aload #5
/*     */     //   66: invokeinterface hasNext : ()Z
/*     */     //   71: ifeq -> 196
/*     */     //   74: aload #5
/*     */     //   76: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   81: checkcast com/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration
/*     */     //   84: astore #6
/*     */     //   86: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/SetPool;
/*     */     //   89: invokevirtual get : ()Ljava/util/Set;
/*     */     //   92: astore #7
/*     */     //   94: aload_0
/*     */     //   95: getfield valueUtil : Lcom/eoos/gm/tis2web/vc/v2/base/value/ValueUtil;
/*     */     //   98: aload_1
/*     */     //   99: aload #4
/*     */     //   101: aload #6
/*     */     //   103: invokeinterface getValues : (Ljava/lang/Object;Lcom/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration;)Ljava/util/Set;
/*     */     //   108: aload #7
/*     */     //   110: invokevirtual createDisjunctiveValues : (Ljava/util/Set;Ljava/util/Set;)Ljava/util/Set;
/*     */     //   113: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   118: astore #8
/*     */     //   120: aload #8
/*     */     //   122: invokeinterface hasNext : ()Z
/*     */     //   127: ifeq -> 167
/*     */     //   130: aload #8
/*     */     //   132: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   137: checkcast com/eoos/gm/tis2web/vc/v2/base/value/Value
/*     */     //   140: astore #9
/*     */     //   142: aload_3
/*     */     //   143: aload_0
/*     */     //   144: getfield cfgManagement : Lcom/eoos/gm/tis2web/vc/v2/base/configuration/ConfigurationManagement;
/*     */     //   147: aload #6
/*     */     //   149: aload #4
/*     */     //   151: aload #9
/*     */     //   153: invokeinterface setAttribute : (Lcom/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration;Ljava/lang/Object;Lcom/eoos/gm/tis2web/vc/v2/base/value/Value;)Lcom/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration;
/*     */     //   158: invokeinterface add : (Ljava/lang/Object;)Z
/*     */     //   163: pop
/*     */     //   164: goto -> 120
/*     */     //   167: jsr -> 181
/*     */     //   170: goto -> 193
/*     */     //   173: astore #10
/*     */     //   175: jsr -> 181
/*     */     //   178: aload #10
/*     */     //   180: athrow
/*     */     //   181: astore #11
/*     */     //   183: invokestatic getThreadInstance : ()Lcom/eoos/scsm/v2/objectpool/SetPool;
/*     */     //   186: aload #7
/*     */     //   188: invokevirtual free : (Ljava/util/Set;)V
/*     */     //   191: ret #11
/*     */     //   193: goto -> 64
/*     */     //   196: aload_3
/*     */     //   197: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #364	-> 0
/*     */     //   #365	-> 9
/*     */     //   #367	-> 22
/*     */     //   #368	-> 30
/*     */     //   #369	-> 39
/*     */     //   #370	-> 74
/*     */     //   #371	-> 86
/*     */     //   #373	-> 94
/*     */     //   #374	-> 130
/*     */     //   #375	-> 142
/*     */     //   #376	-> 164
/*     */     //   #377	-> 167
/*     */     //   #379	-> 170
/*     */     //   #378	-> 173
/*     */     //   #380	-> 193
/*     */     //   #381	-> 196
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   142	22	9	value	Lcom/eoos/gm/tis2web/vc/v2/base/value/Value;
/*     */     //   120	47	8	iterValues	Ljava/util/Iterator;
/*     */     //   86	107	6	cfg	Lcom/eoos/gm/tis2web/vc/v2/base/configuration/IConfiguration;
/*     */     //   94	99	7	disjunctiveValues	Ljava/util/Set;
/*     */     //   64	132	5	iterCfgs	Ljava/util/Iterator;
/*     */     //   30	168	3	ret	Ljava/util/Set;
/*     */     //   39	159	4	key	Ljava/lang/Object;
/*     */     //   0	198	0	this	Lcom/eoos/gm/tis2web/vc/v2/base/configuration/ConfigurationUtil;
/*     */     //   0	198	1	dataProvider	Lcom/eoos/gm/tis2web/vc/v2/base/provider/CfgDataProvider;
/*     */     //   0	198	2	keys	Ljava/util/List;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   94	170	173	finally
/*     */     //   173	178	173	finally
/*     */   }
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
/*     */   public Decorator getDecorator() {
/* 386 */     return new Decorator(this);
/*     */   }
/*     */   
/*     */   public Map createMap(IConfiguration cfg) {
/* 390 */     Map<Object, Object> ret = new HashMap<Object, Object>();
/* 391 */     for (Iterator iter = cfg.getKeys().iterator(); iter.hasNext(); ) {
/* 392 */       Object key = iter.next();
/* 393 */       ret.put(key, cfg.getValue(key));
/*     */     } 
/* 395 */     return ret;
/*     */   }
/*     */   
/*     */   public IConfiguration getEmptyConfiguration() {
/* 399 */     return this.cfgManagement.getEmptyConfiguration();
/*     */   }
/*     */   
/*     */   public IConfiguration removeAttribute(IConfiguration configuration, Object key) {
/* 403 */     return this.cfgManagement.removeAttribute(configuration, key);
/*     */   }
/*     */   
/*     */   public IConfiguration setAttribute(IConfiguration configuration, Object key, Value value) {
/* 407 */     return this.cfgManagement.setAttribute(configuration, key, value);
/*     */   }
/*     */   
/*     */   public boolean equals(IConfiguration cfg1, IConfiguration cfg2) {
/* 411 */     return this.mixin1.equals(cfg1, cfg2);
/*     */   }
/*     */   
/*     */   public int hashCode(IConfiguration cfg) {
/* 415 */     return this.mixin1.hashCode(cfg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IConfiguration create(CreationCallback callback) {
/* 427 */     IConfiguration ret = this.cfgManagement.toMutableConfiguration(this.cfgManagement.getEmptyConfiguration());
/*     */     
/* 429 */     for (Iterator iter = callback.getKeys().iterator(); iter.hasNext(); ) {
/* 430 */       Object key = iter.next();
/* 431 */       Value value = callback.getValue(key);
/* 432 */       if (value != null || callback.includeNullValues()) {
/* 433 */         ((IConfiguration.Mutable)ret).setAttribute(key, value);
/*     */       }
/*     */     } 
/* 436 */     return toImmutableConfiguration(ret);
/*     */   }
/*     */ 
/*     */   
/*     */   public IConfiguration toMutableConfiguration(IConfiguration configuration) {
/* 441 */     return this.cfgManagement.toMutableConfiguration(configuration);
/*     */   }
/*     */   
/*     */   public Filter createInclusionFilter(final Collection configurations) {
/* 445 */     if (Util.isNullOrEmpty(configurations)) {
/* 446 */       return Filter.EXCLUDE_ALL;
/*     */     }
/*     */     
/* 449 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 452 */           boolean ret = false;
/* 453 */           IConfiguration conf = (IConfiguration)obj;
/* 454 */           for (Iterator<IConfiguration> iter = configurations.iterator(); iter.hasNext() && !ret; ) {
/* 455 */             IConfiguration cfg = iter.next();
/* 456 */             ret = ConfigurationUtil.this.isPartialConfiguration(cfg, conf, ConfigurationUtil.EXPAND_WITH_ANY);
/*     */           } 
/*     */           
/* 459 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter createInclusionFilter(CfgProvider cfgProvider) {
/* 466 */     return createInclusionFilter((CfgDataProvider)new CfgDataProviderImpl(cfgProvider, this));
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter createInclusionFilter(final CfgDataProvider cfgDataProvider) {
/* 471 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/* 474 */           IConfiguration cfg = (IConfiguration)obj;
/*     */           
/* 476 */           return ConfigurationUtil.this.supportsFully(cfgDataProvider, cfg);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public enum Mode
/*     */   {
/* 484 */     COMMON_KEYS, ALL_KEYS;
/*     */   }
/*     */   
/*     */   public Set getKeys(Collection configurations, Mode mode) {
/* 488 */     Set<?> ret = null;
/* 489 */     for (Iterator<IConfiguration> iter = configurations.iterator(); iter.hasNext(); ) {
/* 490 */       IConfiguration cfg = iter.next();
/* 491 */       Set<?> keys = cfg.getKeys();
/* 492 */       if (ret == null) {
/* 493 */         ret = new HashSet(keys); continue;
/* 494 */       }  if (mode == Mode.COMMON_KEYS) {
/* 495 */         ret.retainAll(keys); continue;
/*     */       } 
/* 497 */       ret.addAll(keys);
/*     */     } 
/*     */ 
/*     */     
/* 501 */     return (ret != null) ? ret : Collections.EMPTY_SET;
/*     */   }
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
/*     */   public static CfgProvider createFilteredProvider(final Filter filter, final CfgProvider backend) {
/* 516 */     return new CfgProvider()
/*     */       {
/* 518 */         private Set configurations = null;
/* 519 */         private long lastModified = 0L;
/*     */         
/*     */         public long getLastModified() {
/* 522 */           return backend.getLastModified();
/*     */         }
/*     */         
/*     */         public Set getKeys() {
/* 526 */           return backend.getKeys();
/*     */         }
/*     */         
/*     */         public synchronized Set getConfigurations() {
/* 530 */           if (this.configurations == null || this.lastModified != backend.getLastModified()) {
/* 531 */             this.configurations = (Set)CollectionUtil.filterAndReturn(new HashSet(backend.getConfigurations()), filter);
/* 532 */             this.lastModified = backend.getLastModified();
/*     */           } 
/* 534 */           return this.configurations;
/*     */         }
/*     */       };
/*     */   }
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
/*     */   
/*     */   public boolean supports(CfgProvider provider, IConfiguration cfg) {
/* 552 */     boolean ret = false;
/* 553 */     for (Iterator<IConfiguration> iter = provider.getConfigurations().iterator(); iter.hasNext() && !ret;) {
/* 554 */       ret = haveIntersection(iter.next(), cfg);
/*     */     }
/* 556 */     return ret;
/*     */   }
/*     */   
/*     */   public IConfiguration toImmutableConfiguration(IConfiguration configuration) {
/* 560 */     return this.cfgManagement.toImmutableConfiguration(configuration);
/*     */   }
/*     */   
/*     */   public void addToMap(IConfiguration cfg, Map<Object, Value> map) {
/* 564 */     for (Iterator iter = cfg.getKeys().iterator(); iter.hasNext(); ) {
/* 565 */       Object key = iter.next();
/* 566 */       Value value = cfg.getValue(key);
/* 567 */       map.put(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface CreationCallback {
/*     */     Set getKeys();
/*     */     
/*     */     Value getValue(Object param1Object);
/*     */     
/*     */     boolean includeNullValues();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\configuration\ConfigurationUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */