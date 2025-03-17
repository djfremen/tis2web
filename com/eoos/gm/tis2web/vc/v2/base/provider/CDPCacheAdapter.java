/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import com.eoos.scsm.v2.cache.ReadThroughCacheAdapter;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class CDPCacheAdapter
/*    */   extends AbstractCfgDataProviderWrapper
/*    */ {
/*    */   private Cache cacheBackend;
/*    */   private ReadThroughCacheAdapter valueCache;
/*    */   
/*    */   public CDPCacheAdapter(CfgDataProvider backend, Cache cache) {
/* 18 */     super(backend);
/* 19 */     this.cacheBackend = cache;
/* 20 */     this.valueCache = new ReadThroughCacheAdapter(cache, new ReadThroughCacheAdapter.ReadOperation()
/*    */         {
/*    */           public Object get(Object identifier) {
/* 23 */             Object key = ((CDPCacheAdapter.Identifier)identifier).getKey();
/* 24 */             IConfiguration cfg = ((CDPCacheAdapter.Identifier)identifier).getConfiguration();
/*    */             
/* 26 */             return CDPCacheAdapter.this.getBackend().getValues(key, cfg);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   private static final class Identifier
/*    */   {
/*    */     private Object key;
/*    */     
/*    */     private IConfiguration cfg;
/*    */     
/*    */     private Identifier(Object key, IConfiguration cfg) {
/* 39 */       this.key = key;
/* 40 */       this.cfg = cfg;
/*    */     }
/*    */     
/*    */     public Object getKey() {
/* 44 */       return this.key;
/*    */     }
/*    */     
/*    */     public IConfiguration getConfiguration() {
/* 48 */       return this.cfg;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object obj) {
/* 53 */       if (this == obj)
/* 54 */         return true; 
/* 55 */       if (obj instanceof Identifier) {
/* 56 */         Identifier other = (Identifier)obj;
/* 57 */         boolean ret = Util.equals(this.key, other.key);
/* 58 */         ret = (ret && Util.equals(this.cfg, other.cfg));
/* 59 */         return ret;
/*    */       } 
/* 61 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 66 */       int ret = Identifier.class.hashCode();
/* 67 */       ret = HashCalc.addHashCode(ret, this.key);
/* 68 */       ret = HashCalc.addHashCode(ret, this.cfg);
/* 69 */       return ret;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 73 */       return "[" + this.key + "," + this.cfg + "]";
/*    */     }
/*    */   }
/*    */   
/*    */   public Set getValues(Object key, IConfiguration currentCfg) {
/* 78 */     return (Set)this.valueCache.get(new Identifier(key, currentCfg));
/*    */   }
/*    */   
/*    */   public void clear() {
/* 82 */     this.cacheBackend.clear();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\CDPCacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */