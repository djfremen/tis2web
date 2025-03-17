/*    */ package com.eoos.gm.tis2web.rpo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOFamily;
/*    */ import com.eoos.gm.tis2web.rpo.api.RPORetrieval;
/*    */ import com.eoos.gm.tis2web.vc.v2.vin.VIN;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class DatabaseFacade
/*    */   implements RPORetrieval
/*    */ {
/* 26 */   private static final Logger log = Logger.getLogger(DatabaseFacade.class);
/*    */   
/* 28 */   private static DatabaseFacade instance = null;
/*    */   
/*    */   private DatabaseAdapter adapter;
/*    */   
/*    */   private RPORetrieval rpoRetrieval;
/*    */   
/*    */   private Cache cache;
/*    */   
/*    */   private DatabaseFacade() {
/* 37 */     log.debug("creating instance ...");
/* 38 */     final SubConfigurationWrapper cfg = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "component.rpo.adapter.");
/*    */     
/* 40 */     log.debug("...creating adapter");
/* 41 */     this.adapter = new DatabaseAdapter((Configuration)new SubConfigurationWrapper((Configuration)subConfigurationWrapper, "std."));
/*    */     
/* 43 */     log.debug("...creating caching proxy");
/* 44 */     this.cache = Tis2webUtil.createStdCache();
/*    */     
/* 46 */     this.rpoRetrieval = (RPORetrieval)ReflectionUtil.createCachingProxy(this.adapter, this.cache, (ReflectionUtil.CachingProxyCallback)new ReflectionUtil.CachingProxyCallbackAdapter()
/*    */         {
/*    */           public Object createKey(Method m, Object[] args) {
/* 49 */             return Arrays.asList(args);
/*    */           }
/*    */         });
/*    */     
/* 53 */     ConfigurationService cfgService = ConfigurationServiceProvider.getService();
/* 54 */     cfgService.addObserver(new ConfigurationService.Observer()
/*    */         {
/* 56 */           private final int hash = ConfigurationUtil.configurationHash(cfg);
/*    */           
/*    */           public void onModification() {
/* 59 */             if (ConfigurationUtil.configurationHash(cfg) != this.hash) {
/* 60 */               DatabaseFacade.log.debug("configuration changed - resetting");
/* 61 */               ConfigurationServiceProvider.getService().removeObserver(this);
/* 62 */               DatabaseFacade.reset();
/*    */             } 
/*    */           }
/*    */         });
/* 66 */     log.debug("...done creating instance");
/*    */   }
/*    */   
/*    */   public static synchronized DatabaseFacade getInstance() {
/* 70 */     if (instance == null) {
/* 71 */       instance = new DatabaseFacade();
/*    */     }
/* 73 */     return instance;
/*    */   }
/*    */   
/*    */   public static synchronized void reset() {
/* 77 */     log.debug("reset");
/* 78 */     instance = null;
/*    */   }
/*    */   
/*    */   public RPOContainer getRPOs(String vin) throws VIN.InvalidVINException, VIN.UnsupportedVINException {
/* 82 */     return this.rpoRetrieval.getRPOs(vin);
/*    */   }
/*    */   
/*    */   public Map<String, RPOFamily> resolveFamilies(Collection<String> familyIDs) {
/* 86 */     return this.adapter.resolveRPOFamilies(familyIDs);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\DatabaseFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */