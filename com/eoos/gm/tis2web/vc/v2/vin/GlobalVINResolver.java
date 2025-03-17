/*    */ package com.eoos.gm.tis2web.vc.v2.vin;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfiguredServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import com.eoos.scsm.v2.cache.CacheHitRatioAdapter;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class GlobalVINResolver
/*    */   implements VINResolver
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(GlobalVINResolver.class);
/*    */   
/* 20 */   private static GlobalVINResolver instance = null;
/*    */   
/* 22 */   private Set resolvers = new LinkedHashSet();
/*    */   
/*    */   private VINResolver cacheDelegate;
/*    */   
/*    */   private GlobalVINResolver(ClientContext context) {
/* 27 */     final CacheHitRatioAdapter cache = new CacheHitRatioAdapter(Tis2webUtil.createStdCache());
/* 28 */     if (context != null) {
/* 29 */       context.addLogoutListener(new ClientContextBase.LogoutListener()
/*    */           {
/*    */             public void onLogout() {
/* 32 */               if (GlobalVINResolver.log.isDebugEnabled()) {
/* 33 */                 GlobalVINResolver.log.info("cache hit ratio for VIN resolve cache: " + cache.getHitRatio());
/*    */               }
/* 35 */               GlobalVINResolver.this.resolvers.clear();
/* 36 */               GlobalVINResolver.this.resolvers = null;
/* 37 */               GlobalVINResolver.this.cacheDelegate = null;
/*    */             }
/*    */           });
/*    */     }
/*    */ 
/*    */     
/* 43 */     this.cacheDelegate = (VINResolver)ReflectionUtil.createCachingProxy(new VINResolver()
/*    */         {
/*    */           public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 46 */             return GlobalVINResolver.this._resolveVIN(vin);
/*    */           }
/*    */         },  (Cache)cache, ReflectionUtil.CachingProxyCallback.STD);
/*    */     
/*    */     Iterator<VINResolverRetrieval> iter;
/* 51 */     for (iter = ConfiguredServiceProvider.getInstance().getServices(VINResolver.class).iterator(); iter.hasNext();) {
/* 52 */       this.resolvers.add(iter.next());
/*    */     }
/*    */     
/* 55 */     for (iter = ConfiguredServiceProvider.getInstance().getServices(VINResolverRetrieval.class).iterator(); iter.hasNext(); ) {
/* 56 */       VINResolverRetrieval retrieval = iter.next();
/* 57 */       this.resolvers.addAll(retrieval.getVINResolvers(context));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static GlobalVINResolver getInstance(ClientContext context) {
/* 63 */     synchronized (context.getLockObject()) {
/* 64 */       GlobalVINResolver instance = (GlobalVINResolver)context.getObject(GlobalVINResolver.class);
/* 65 */       if (instance == null) {
/* 66 */         instance = new GlobalVINResolver(context);
/* 67 */         context.storeObject(GlobalVINResolver.class, instance);
/*    */       } 
/* 69 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static synchronized GlobalVINResolver getInstance() {
/* 74 */     if (instance == null) {
/* 75 */       instance = new GlobalVINResolver(null);
/*    */     }
/* 77 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 82 */     return this.cacheDelegate.resolveVIN(vin);
/*    */   }
/*    */   
/*    */   private Set _resolveVIN(VIN vin) throws VIN.InvalidVINException {
/* 86 */     Set ret = new LinkedHashSet();
/* 87 */     for (Iterator<VINResolver> iter = this.resolvers.iterator(); iter.hasNext();) {
/* 88 */       ret.addAll(((VINResolver)iter.next()).resolveVIN(vin));
/*    */     }
/* 90 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\GlobalVINResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */