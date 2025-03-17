/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.vcr;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*    */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*    */ import com.eoos.gm.tis2web.vc.v2.service.VCService;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VCRUtil
/*    */ {
/*    */   public static synchronized boolean vehicleSelected(IConfiguration cfg) {
/* 24 */     IConfiguration cfg2 = VehicleConfigurationUtil.cfgUtil.removeAttribute(cfg, VehicleConfigurationUtil.KEY_MAKE);
/* 25 */     return !VehicleConfigurationUtil.isEmptyCfg(cfg2);
/*    */   }
/*    */   
/* 28 */   private static final Object[] MANDATORY = new Object[] { VCService.KEY_MAKE, VCService.KEY_MODEL, VCService.KEY_MODELYEAR };
/*    */   
/*    */   public static synchronized boolean checkMandatory(IConfiguration cfg) {
/* 31 */     boolean ret = true;
/* 32 */     for (int i = 0; i < MANDATORY.length && ret; i++) {
/* 33 */       ret = (cfg.getValue(MANDATORY[i]) != null);
/*    */     }
/* 35 */     return ret;
/*    */   }
/*    */   
/*    */   public static synchronized boolean checkMandatory_Inspections(IConfiguration cfg) {
/* 39 */     boolean ret = true;
/* 40 */     for (int i = 0; i < MANDATORY.length && ret; i++) {
/* 41 */       ret = (cfg.getValue(MANDATORY[i]) != null);
/*    */     }
/* 43 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\vcr\VCRUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */