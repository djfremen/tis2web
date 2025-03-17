/*    */ package com.eoos.gm.tis2web.vc.v2.service;
/*    */ 
/*    */ import com.eoos.context.IContext;
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.scsm.v2.util.LockObjectProvider;
/*    */ 
/*    */ public class VehicleConfigurationStorage {
/*  8 */   private static LockObjectProvider LOCKPROV = new LockObjectProvider();
/*    */   
/* 10 */   private IConfiguration vehicleCfg = null;
/*    */ 
/*    */   
/*    */   public VehicleConfigurationStorage(IContext context) {}
/*    */   
/*    */   public static VehicleConfigurationStorage getInstance(IContext context) {
/* 16 */     synchronized (LOCKPROV.getLockObject(context)) {
/* 17 */       VehicleConfigurationStorage instance = (VehicleConfigurationStorage)context.getObject(VehicleConfigurationStorage.class);
/* 18 */       if (instance == null) {
/* 19 */         instance = new VehicleConfigurationStorage(context);
/* 20 */         context.storeObject(VehicleConfigurationStorage.class, instance);
/*    */       } 
/* 22 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setVehicleConfiguration(IConfiguration vehicleCfg) {
/* 27 */     this.vehicleCfg = vehicleCfg;
/*    */   }
/*    */ 
/*    */   
/*    */   public IConfiguration getVehicleConfiguration() {
/* 32 */     return this.vehicleCfg;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VehicleConfigurationStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */