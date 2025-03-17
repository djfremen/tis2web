/*     */ package com.eoos.gm.tis2web.si.implementation.io;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.provider.CfgProvider;
/*     */ import com.eoos.gm.tis2web.vc.v2.configuration.VehicleConfigurationUtil;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.Task;
/*     */ import com.eoos.util.Transforming;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SIPreload {
/*     */   private static final class CT_ClearCache
/*     */     implements ClusterTask {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public Object execute() {
/*     */       try {
/*  38 */         SIPreload.getInstance()._clear();
/*  39 */       } catch (Exception e) {
/*  40 */         return e;
/*     */       } 
/*  42 */       return null;
/*     */     }
/*     */     
/*     */     private CT_ClearCache() {} }
/*  46 */   private static final Logger log = Logger.getLogger(SIPreload.class);
/*     */   
/*  48 */   private static SIPreload instance = new SIPreload();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void _clear() {
/*  54 */     this.objectsToKeep.clear();
/*     */   }
/*     */   
/*     */   public boolean clear() {
/*  58 */     boolean ret = true;
/*  59 */     CT_ClearCache cT_ClearCache = new CT_ClearCache();
/*  60 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ClearCache, null);
/*     */     
/*  62 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/*  63 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/*  64 */       URL url = iter.next();
/*  65 */       if (result.getResult(url) instanceof Exception) {
/*  66 */         log.warn("failed to clear for cluster server :" + url);
/*  67 */         ret = false;
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     return ret;
/*     */   }
/*     */   
/*     */   public void execute() {
/*     */     try {
/*  76 */       log.debug("preloading data for si...");
/*  77 */       ConfigurationService configurationService = ConfigurationServiceProvider.getService();
/*  78 */       List _locales = ConfigurationUtil.getValueList((Configuration)configurationService, "component.si.preload.languages");
/*  79 */       if (!Util.isNullOrEmpty(_locales)) {
/*  80 */         log.debug("...locales: " + _locales);
/*  81 */         List<LocaleInfo> locales = Util.transformList(_locales, new Transforming()
/*     */             {
/*     */               public Object transform(Object object) {
/*  84 */                 return LocaleInfoProvider.getInstance().getLocale(Util.parseLocale((String)object));
/*     */               }
/*     */             });
/*     */         
/*  88 */         List<?> _cfgFilters = ConfigurationUtil.getValueList((Configuration)configurationService, "component.si.preload.filter");
/*  89 */         if (!Util.isNullOrEmpty(_cfgFilters)) {
/*  90 */           _cfgFilters = Util.transformList(_cfgFilters, new Transforming()
/*     */               {
/*     */                 public Object transform(Object object) {
/*  93 */                   return ((String)object).replace('#', ',');
/*     */                 }
/*     */               });
/*  96 */           Filter filter = VehicleConfigurationUtil.createConfigurationFilter(new HashSet(_cfgFilters));
/*     */           
/*  98 */           SIDataAdapterFacade sIDataAdapterFacade = SIDataAdapterFacade.getInstance();
/*     */           
/* 100 */           Set<IConfiguration> processedCfgs = new HashSet();
/* 101 */           Set<CfgProvider> cfgProviders = sIDataAdapterFacade.getCfgProviders();
/* 102 */           for (Iterator<CfgProvider> iter = cfgProviders.iterator(); iter.hasNext(); ) {
/* 103 */             CfgProvider provider = iter.next();
/* 104 */             for (IConfiguration cfg : provider.getConfigurations()) {
/* 105 */               cfg = VehicleConfigurationUtil.cfgUtil.removeAttribute(cfg, VehicleConfigurationUtil.KEY_ENGINE);
/* 106 */               cfg = VehicleConfigurationUtil.cfgUtil.removeAttribute(cfg, VehicleConfigurationUtil.KEY_TRANSMISSION);
/*     */               
/* 108 */               if (filter.include(cfg) && !processedCfgs.contains(cfg)) {
/* 109 */                 processedCfgs.add(cfg);
/*     */                 
/*     */                 try {
/* 112 */                   log.debug("... preloading data for " + cfg);
/* 113 */                   VCR vcr = sIDataAdapterFacade.getLVCAdapter().toVCR(cfg);
/* 114 */                   if (vcr == null) {
/* 115 */                     log.warn("unable to retieve vcr for " + cfg);
/*     */                     continue;
/*     */                   } 
/* 118 */                   CTOCNode cTOCNode = sIDataAdapterFacade.getSICTOCService().getCTOC().getCTOC(vcr);
/*     */                   
/* 120 */                   visitAll((SITOCElement)cTOCNode, locales);
/* 121 */                 } catch (Exception e) {
/* 122 */                   log.warn("unable to preload, skipping cfg - exception: ", e);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 129 */       log.debug("...done");
/* 130 */     } catch (Exception e) {
/* 131 */       log.warn("si preloading failed - exception: ", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static SIPreload getInstance() {
/* 137 */     return instance;
/*     */   }
/*     */   
/* 140 */   private Collection objectsToKeep = new LinkedList();
/*     */   
/*     */   private void visitAll(SITOCElement element, Collection<LocaleInfo> locales) {
/* 143 */     this.objectsToKeep.add(element);
/*     */     try {
/* 145 */       if (element != null) {
/* 146 */         for (LocaleInfo li : locales) {
/*     */           try {
/* 148 */             this.objectsToKeep.add(element.getLabel(li));
/* 149 */           } catch (UnsupportedOperationException e) {}
/*     */         } 
/*     */ 
/*     */         
/* 153 */         List<SITOCElement> childs = element.getChildren();
/* 154 */         if (!Util.isNullOrEmpty(childs)) {
/* 155 */           for (SITOCElement child : childs) {
/* 156 */             visitAll(child, locales);
/*     */           }
/*     */         }
/*     */       } 
/* 160 */     } catch (UnsupportedOperationException e) {
/*     */     
/* 162 */     } catch (Exception e) {
/* 163 */       log.warn("unable to visit - exception: ", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\SIPreload.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */