/*    */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr;
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
/* 38 */   private static final Object[] MANDATORY_INSPEC = new Object[] { VCService.KEY_MODEL, VCService.KEY_MODELYEAR, VCService.KEY_ENGINE };
/*    */   
/*    */   public static synchronized boolean checkMandatory_Inspections(IConfiguration cfg) {
/* 41 */     boolean ret = true;
/* 42 */     for (int i = 0; i < MANDATORY_INSPEC.length && ret; i++) {
/* 43 */       ret = (cfg.getValue(MANDATORY_INSPEC[i]) != null);
/*    */     }
/* 45 */     return ret;
/*    */   }
/*    */   
/*    */   public static synchronized boolean checkMandatoryMake(IConfiguration cfg) {
/* 49 */     return (cfg.getValue(VCService.KEY_MAKE) != null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\vcr\VCRUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */