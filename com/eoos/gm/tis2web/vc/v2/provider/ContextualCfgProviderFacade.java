/*     */ package com.eoos.gm.tis2web.vc.v2.provider;
/*     */ 
/*     */ import com.eoos.gm.tis2web.acl.service.ACLService;
/*     */ import com.eoos.gm.tis2web.acl.service.ACLServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.sharedcontext.SharedContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.v2.LVCAdapterProvider;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import com.eoos.scsm.v2.util.HashSurrogate;
/*     */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ContextualCfgProviderFacade
/*     */   implements CfgProvider
/*     */ {
/*     */   private static final class Key
/*     */   {
/*     */     private final Set aclResources;
/*     */     private final CfgProvider cfgProvider;
/*     */     
/*     */     private Key(Set aclResources, CfgProvider cfgProvider) {
/*  34 */       this.aclResources = aclResources;
/*  35 */       this.cfgProvider = cfgProvider;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  39 */       int ret = getClass().hashCode();
/*  40 */       ret = HashCalc.addHashCode(ret, this.aclResources);
/*  41 */       ret = HashCalc.addHashCode(ret, this.cfgProvider);
/*  42 */       return ret;
/*     */     }
/*     */     
/*     */     public boolean equals(Object obj) {
/*  46 */       if (this == obj)
/*  47 */         return true; 
/*  48 */       if (obj instanceof Key) {
/*  49 */         Key key = (Key)obj;
/*  50 */         boolean ret = this.aclResources.equals(key.aclResources);
/*  51 */         ret = (ret && this.cfgProvider.equals(key.cfgProvider));
/*  52 */         return ret;
/*     */       } 
/*  54 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  59 */   private static final Logger log = Logger.getLogger(ContextualCfgProviderFacade.class);
/*     */   
/*  61 */   private static Map instances = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  63 */   private static LockObjectProvider LOCKPROVIDER = new LockObjectProvider();
/*     */   
/*  65 */   private final Object SYNC_FILTER = new Object();
/*     */   
/*  67 */   private Filter cfgFilter = null;
/*     */   
/*  69 */   private final Object SYNC_CFGS = new Object();
/*     */   
/*  71 */   private SoftReference srCfgs = null;
/*     */   
/*     */   private Set aclResources;
/*     */   
/*     */   private CfgProvider backend;
/*     */   
/*     */   private ContextualCfgProviderFacade(Set aclResources, CfgProvider backend) {
/*  78 */     if (log.isDebugEnabled()) {
/*  79 */       log.debug("...creating instance for aclResources: " + aclResources + ", and backend: " + backend);
/*     */     }
/*  81 */     this.aclResources = aclResources;
/*  82 */     this.backend = backend;
/*     */   }
/*     */   
/*     */   public static ContextualCfgProviderFacade getInstance(ClientContext context, CfgProvider backend) {
/*  86 */     Object key = HashSurrogate.createFor(new Object[] { context, backend }, HashSurrogate.STATE_HASH);
/*  87 */     synchronized (context.getLockObject()) {
/*  88 */       ContextualCfgProviderFacade ret = (ContextualCfgProviderFacade)context.getObject(key);
/*  89 */       if (ret == null) {
/*  90 */         Set<String> aclResources = null;
/*  91 */         if (ApplicationContext.getInstance().developMode() && context.getSessionID().endsWith("testvc")) {
/*  92 */           aclResources = Collections.singleton("opel#*#*");
/*     */         } else {
/*  94 */           SharedContext sc = context.getSharedContext();
/*  95 */           ACLService aclMI = ACLServiceProvider.getInstance().getService();
/*  96 */           aclResources = aclMI.getAuthorizedResources("VC", sc.getUsrGroup2ManufMap(), sc.getCountry());
/*     */         } 
/*  98 */         ret = getInstance(aclResources, backend);
/*  99 */         context.storeObject(key, ret);
/*     */       } 
/*     */       
/* 102 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ContextualCfgProviderFacade getInstance(Set aclResources, CfgProvider backend) {
/* 107 */     Object key = new Key(aclResources, backend);
/* 108 */     synchronized (LOCKPROVIDER.getLockObject(key)) {
/* 109 */       SoftReference<ContextualCfgProviderFacade> sr = (SoftReference)instances.get(key);
/* 110 */       ContextualCfgProviderFacade ret = (sr != null) ? sr.get() : null;
/* 111 */       if (ret == null) {
/* 112 */         ret = new ContextualCfgProviderFacade(aclResources, backend);
/* 113 */         instances.put(key, new SoftReference<ContextualCfgProviderFacade>(ret));
/*     */       } 
/* 115 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Filter createACLFilter() {
/* 120 */     return ACLFilterFactory.getACLFilter(this.aclResources);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ContextualCfgProviderFacade getInstance(ClientContext context) {
/* 125 */     return getInstance(context, (CfgProvider)LVCAdapterProvider.getGlobalAdapter());
/*     */   }
/*     */   
/*     */   public Filter getACLFilter() {
/* 129 */     synchronized (this.SYNC_FILTER) {
/* 130 */       if (this.cfgFilter == null) {
/* 131 */         this.cfgFilter = createACLFilter();
/*     */       }
/* 133 */       return this.cfgFilter;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set getConfigurations() {
/* 138 */     synchronized (this.SYNC_CFGS) {
/* 139 */       Set<?> ret = null;
/* 140 */       if (this.srCfgs == null || (ret = this.srCfgs.get()) == null) {
/* 141 */         log.debug("determining valid configuration (filtering)");
/* 142 */         ret = this.backend.getConfigurations();
/* 143 */         Filter filter = getACLFilter();
/* 144 */         if (filter != Filter.INCLUDE_ALL) {
/* 145 */           ret = new HashSet(ret);
/* 146 */           StopWatch sw = StopWatch.getInstance().start();
/*     */           try {
/* 148 */             CollectionUtil.filter(ret, filter);
/* 149 */             if (log.isDebugEnabled()) {
/* 150 */               log.debug("...filtered configurations (remaining: " + ret.size() + ") in: " + sw.getElapsedTime() + " ms");
/*     */             }
/*     */           } finally {
/* 153 */             StopWatch.freeInstance(sw);
/*     */           } 
/*     */         } else {
/* 156 */           log.debug("...no restriction, returing all configurations");
/*     */         } 
/* 158 */         this.srCfgs = new SoftReference<Set<?>>(ret);
/*     */       } 
/* 160 */       return ret;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getLastModified() {
/* 165 */     return 0L;
/*     */   }
/*     */   
/*     */   public Set getKeys() {
/* 169 */     return VehicleConfigurationUtil.KEY_SET;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 173 */     int ret = ContextualCfgProviderFacade.class.hashCode();
/* 174 */     ret = HashCalc.addHashCode(ret, this.backend);
/* 175 */     ret = HashCalc.addHashCode(ret, this.aclResources);
/* 176 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 180 */     boolean ret = false;
/* 181 */     if (this == obj) {
/* 182 */       ret = true;
/* 183 */     } else if (obj instanceof ContextualCfgProviderFacade) {
/* 184 */       ContextualCfgProviderFacade facade = (ContextualCfgProviderFacade)obj;
/* 185 */       ret = facade.aclResources.equals(this.aclResources);
/* 186 */       ret = (ret && facade.backend.equals(this.backend));
/*     */     } 
/* 188 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\ContextualCfgProviderFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */