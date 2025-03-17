/*    */ package com.eoos.gm.tis2web.vc.v2.provider;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.scsm.v2.filter.Filter;
/*    */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ACLFilterFactory
/*    */ {
/* 20 */   private static final Logger log = Logger.getLogger(ACLFilterFactory.class);
/*    */   
/* 22 */   private static LockObjectProvider LOCKPROVIDER = new LockObjectProvider(LockObjectProvider.Mode.STATE);
/*    */   
/* 24 */   private static Map aclToFilter = new ConcurrentHashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   private static Filter createFilter(Set aclResources) {
/* 28 */     if (Util.isNullOrEmpty(aclResources)) {
/* 29 */       return Filter.INCLUDE_ALL;
/*    */     }
/* 31 */     if (log.isDebugEnabled()) {
/* 32 */       log.debug("...creating vc filter for ACL resource: " + String.valueOf(aclResources));
/*    */     }
/* 34 */     final Set<IConfiguration> cfgs = new HashSet();
/* 35 */     for (Iterator<String> iter = aclResources.iterator(); iter.hasNext(); ) {
/* 36 */       String tmp = iter.next();
/*    */       
/* 38 */       Object[] values = new Object[5];
/* 39 */       Arrays.fill(values, VehicleConfigurationUtil.valueManagement.getANY());
/*    */       
/* 41 */       String[] parts = tmp.split("#");
/* 42 */       for (int j = 0; j < parts.length; j++) {
/* 43 */         Object key = VehicleConfigurationUtil.KEYS[j];
/* 44 */         values[j] = VehicleConfigurationUtil.toModelObject(key, parts[j]);
/*    */       } 
/*    */       
/* 47 */       cfgs.add(VehicleConfigurationUtil.createCfg(values[0], values[1], values[2], values[3], values[4]));
/*    */     } 
/*    */     
/* 50 */     return new Filter()
/*    */       {
/*    */         public boolean include(Object obj) {
/* 53 */           boolean ret = false;
/* 54 */           IConfiguration conf = (IConfiguration)obj;
/* 55 */           for (Iterator<IConfiguration> iter = cfgs.iterator(); iter.hasNext() && !ret; ) {
/* 56 */             IConfiguration cfg = iter.next();
/* 57 */             ret = VehicleConfigurationUtil.cfgUtil.isPartialConfiguration(cfg, conf, ConfigurationUtil.EXPAND_WITH_ANY);
/*    */           } 
/*    */           
/* 60 */           return ret;
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public static Filter getACLFilter(Set aclResources) {
/* 67 */     synchronized (LOCKPROVIDER.getLockObject(aclResources)) {
/* 68 */       Filter ret = (Filter)aclToFilter.get(aclResources);
/* 69 */       if (ret == null) {
/* 70 */         ret = createFilter(aclResources);
/* 71 */         aclToFilter.put(aclResources, ret);
/*    */       } 
/* 73 */       return ret;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\provider\ACLFilterFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */