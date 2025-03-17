/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import java.util.Set;
/*    */ 
/*    */ public abstract class AbstractCfgDataProviderWrapper
/*    */   implements CfgDataProvider
/*    */ {
/*    */   private CfgDataProvider backend;
/*    */   
/*    */   protected AbstractCfgDataProviderWrapper(CfgDataProvider backend) {
/* 12 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   protected final CfgDataProvider getBackend() {
/* 16 */     return this.backend;
/*    */   }
/*    */   
/*    */   public Set getKeys() {
/* 20 */     return this.backend.getKeys();
/*    */   }
/*    */   
/*    */   public Set getValues(Object key, IConfiguration currentCfg) {
/* 24 */     return this.backend.getValues(key, currentCfg);
/*    */   }
/*    */   
/*    */   public long getLastModified() {
/* 28 */     return this.backend.getLastModified();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\AbstractCfgDataProviderWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */