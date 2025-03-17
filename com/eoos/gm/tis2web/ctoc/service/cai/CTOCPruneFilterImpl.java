/*     */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.ConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CTOCPruneFilterImpl
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(CTOCPruneFilterImpl.class);
/*     */   
/*  23 */   private Set vcrResources = null;
/*     */   
/*  25 */   private ILVCAdapter iLVCAdapter = null;
/*     */   
/*     */   public CTOCPruneFilterImpl(Set vcrResources, ILVCAdapter iLVCAdapter) {
/*  28 */     this.vcrResources = vcrResources;
/*  29 */     this.iLVCAdapter = iLVCAdapter;
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/*  33 */     return this.iLVCAdapter;
/*     */   }
/*     */ 
/*     */   
/*     */   private Filter createFilter() {
/*  38 */     if (Util.isNullOrEmpty(this.vcrResources)) {
/*  39 */       return Filter.INCLUDE_ALL;
/*     */     }
/*  41 */     if (log.isDebugEnabled()) {
/*  42 */       log.debug("...creating vc filter for CTOC: " + String.valueOf(this.vcrResources));
/*     */     }
/*  44 */     final Set<IConfiguration> cfgs = new HashSet();
/*  45 */     for (Iterator<String> iter = this.vcrResources.iterator(); iter.hasNext(); ) {
/*  46 */       String tmp = iter.next();
/*     */       
/*  48 */       Object[] values = new Object[5];
/*  49 */       Arrays.fill(values, VehicleConfigurationUtil.valueManagement.getANY());
/*     */       
/*  51 */       String[] parts = tmp.split("#");
/*  52 */       for (int j = 0; j < parts.length; j++) {
/*  53 */         Object key = VehicleConfigurationUtil.KEYS[j];
/*  54 */         values[j] = VehicleConfigurationUtil.toModelObject(key, parts[j]);
/*     */       } 
/*     */       
/*  57 */       cfgs.add(VehicleConfigurationUtil.createCfg(values[0], values[1], values[2], values[3], values[4]));
/*     */     } 
/*     */     
/*  60 */     return new Filter()
/*     */       {
/*     */         public boolean include(Object obj) {
/*  63 */           IConfiguration conf = null;
/*  64 */           if (obj instanceof VCR) {
/*  65 */             conf = CTOCPruneFilterImpl.this.toBasicCfg((VCR)obj);
/*  66 */           } else if (obj instanceof IConfiguration) {
/*  67 */             conf = (IConfiguration)obj;
/*     */           } else {
/*     */             
/*  70 */             throw new IllegalArgumentException();
/*     */           } 
/*  72 */           boolean ret = false;
/*  73 */           for (Iterator<IConfiguration> iter = cfgs.iterator(); iter.hasNext() && !ret; ) {
/*  74 */             IConfiguration cfg = iter.next();
/*  75 */             ret = VehicleConfigurationUtil.cfgUtil.isPartialConfiguration(cfg, conf, ConfigurationUtil.EXPAND_WITH_ANY);
/*     */           } 
/*     */           
/*  78 */           return ret;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public Filter getCTOCFilter() {
/*  85 */     Filter ret = createFilter();
/*  86 */     return ret;
/*     */   }
/*     */   
/*     */   private IConfiguration toBasicCfg(VCR rootVcr) {
/*  90 */     String make = null;
/*  91 */     String model = null;
/*  92 */     String modelyear = null;
/*     */ 
/*     */     
/*     */     try {
/*  96 */       List<String> makes = getLVCAdapter().getVC().getAttributes("Make", rootVcr);
/*  97 */       List<String> models = getLVCAdapter().getVC().getAttributes("Model", rootVcr);
/*  98 */       List<String> modelyears = getLVCAdapter().getVC().getAttributes("ModelYear", rootVcr);
/*  99 */       make = makes.get(0);
/* 100 */       model = models.get(0);
/* 101 */       modelyear = modelyears.get(0);
/*     */     }
/* 103 */     catch (Exception e) {
/* 104 */       log.error("unable to create a IConfiguration object from a VCR object- exception:" + e, e);
/* 105 */       return null;
/*     */     } 
/* 107 */     return VehicleConfigurationUtil.createCfg(VehicleConfigurationUtil.toModelObject(VehicleConfigurationUtil.KEY_MAKE, make), VehicleConfigurationUtil.toModelObject(VehicleConfigurationUtil.KEY_MODEL, model), VehicleConfigurationUtil.toModelObject(VehicleConfigurationUtil.KEY_MODELYEAR, modelyear), null, null);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCPruneFilterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */