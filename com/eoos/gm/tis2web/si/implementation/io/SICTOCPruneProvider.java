/*    */ package com.eoos.gm.tis2web.si.implementation.io;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.implementation.io.CTOCPruneImpl;
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.SubConfigurationWrapper;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class SICTOCPruneProvider
/*    */   extends CTOCPruneImpl
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(SICTOCPruneProvider.class);
/*    */   
/* 19 */   private static SICTOCPruneProvider instance = null;
/*    */   
/*    */   public SICTOCPruneProvider(Configuration config) throws Exception {
/* 22 */     super(config);
/*    */   }
/*    */   
/*    */   public static synchronized SICTOCPruneProvider getInstance() {
/* 26 */     if (instance == null) {
/*    */       try {
/* 28 */         ConfigurationService cfgService = ConfigurationServiceProvider.getService();
/* 29 */         SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)cfgService, "component.si.cache.ctoc.reduced.");
/* 30 */         String cache = ApplicationContext.getInstance().getProperty("frame.ctoc.cache");
/* 31 */         boolean cached = true;
/* 32 */         if (cache.equalsIgnoreCase("false")) {
/* 33 */           cached = false;
/*    */         }
/* 35 */         if (cached && ConfigurationUtil.isTrue("activated", (Configuration)subConfigurationWrapper)) {
/* 36 */           instance = new SICTOCPruneProvider((Configuration)subConfigurationWrapper);
/*    */         } else {
/* 38 */           instance = null;
/* 39 */           log.debug("reduced si ctocs are not enabled, returning disabled instance");
/*    */         } 
/* 41 */       } catch (Exception e) {}
/*    */     }
/*    */     
/* 44 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Collection getCTOCS() {
/* 49 */     return SIDataAdapterFacade.getInstance().getCTOCs();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\SICTOCPruneProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */