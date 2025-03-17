/*    */ package com.eoos.gm.tis2web.vc.v2.base.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class DialogDataProvider
/*    */   extends AbstractCfgDataProviderWrapper
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(DialogDataProvider.class);
/*    */   
/*    */   private ConfigurationUtil cfgUtil;
/*    */   
/*    */   private Object valueDefault;
/*    */   
/*    */   public DialogDataProvider(CfgDataProvider backend, ConfigurationUtil cfgUtil, Object valueDefault) {
/* 18 */     super(backend);
/* 19 */     this.cfgUtil = cfgUtil;
/* 20 */     this.valueDefault = valueDefault;
/*    */   }
/*    */   
/*    */   public Set getValues(Object key, IConfiguration currentCfg) {
/* 24 */     if (log.isDebugEnabled()) {
/* 25 */       log.debug("retrieving values for key: " + key + " and current cfg:" + currentCfg);
/*    */     }
/* 27 */     IConfiguration cfg = currentCfg;
/* 28 */     if (currentCfg.getValue(key) != null) {
/*    */ 
/*    */       
/* 31 */       cfg = this.cfgUtil.removeAttribute(currentCfg, key);
/* 32 */       if (log.isDebugEnabled()) {
/* 33 */         log.debug("....removed queried key from configuration, querying configuration: " + cfg);
/*    */       }
/*    */     } 
/* 36 */     Set<Object> ret = getBackend().getValues(key, cfg);
/* 37 */     if (log.isDebugEnabled()) {
/* 38 */       log.debug("...resulting values are " + ret);
/*    */     }
/* 40 */     ret.add(this.valueDefault);
/* 41 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\base\provider\DialogDataProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */