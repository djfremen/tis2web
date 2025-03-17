/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCPrune;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCPruneFilterImpl;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public abstract class CTOCPruneImpl
/*     */   implements CTOCPrune {
/*  23 */   private static final Logger log = Logger.getLogger(CTOCPruneImpl.class);
/*     */   
/*     */   protected boolean cachedProperties = false;
/*     */   
/*     */   protected int level_pruned;
/*     */   
/*  29 */   protected Filter filter = null;
/*     */   
/*  31 */   Set vcrs = new HashSet();
/*     */   
/*     */   protected CTOCPruneImpl(Configuration config) throws Exception {
/*  34 */     initializeConfigurations(config);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initializeConfigurations(Configuration config) {
/*     */     try {
/*  40 */       if (ConfigurationUtil.isTrue("properties.cached", config))
/*  41 */         this.cachedProperties = true; 
/*  42 */       TypeDecorator _configuration = new TypeDecorator(config);
/*  43 */       Number levelNumber = _configuration.getNumber("level");
/*  44 */       this.level_pruned = (levelNumber == null) ? Integer.valueOf(2).intValue() : levelNumber.intValue();
/*     */       
/*  46 */       SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper(config, "vcr.");
/*  47 */       for (Iterator<String> iter = subConfigurationWrapper.getKeys().iterator(); iter.hasNext(); ) {
/*  48 */         String vcr = subConfigurationWrapper.getProperty(iter.next());
/*  49 */         this.vcrs.add(vcr);
/*     */       }
/*     */     
/*  52 */     } catch (Exception ex) {
/*  53 */       log.error("unable to initialize Configurations - execption:" + ex, ex);
/*  54 */       this.level_pruned = Integer.valueOf(2).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract Collection getCTOCS();
/*     */   
/*     */   public synchronized void pruneCTOCs() {
/*     */     try {
/*  63 */       log.info("....start reducing ctocs.");
/*     */       
/*  65 */       for (Iterator<CTOC> iterCTOCs = getCTOCS().iterator(); iterCTOCs.hasNext(); ) {
/*  66 */         CTOC _ctoc = iterCTOCs.next();
/*  67 */         CTOCPruneFilterImpl ctocPruneFilter = new CTOCPruneFilterImpl(this.vcrs, _ctoc.getLvcRetrieval().getLVCAdapter());
/*  68 */         this.filter = ctocPruneFilter.getCTOCFilter();
/*  69 */         for (Iterator it = _ctoc.getCTOCs().iterator(); it.hasNext(); ) {
/*  70 */           Object root = it.next();
/*  71 */           VCR vcr = ((CTOCElement)root).getVCR();
/*  72 */           if (vcr != null && vcr != VCR.NULL && 
/*  73 */             this.filter.include(vcr)) {
/*  74 */             int level_ctoc = 0;
/*  75 */             pruneCTOCChildren(_ctoc, (CTOCElement)root, this.cachedProperties, level_ctoc);
/*  76 */             if (level_ctoc == this.level_pruned);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       log.info("....done reducing ctocs.");
/*     */     }
/*  93 */     catch (Exception ex) {
/*  94 */       log.error("unable to pruneCTOCs -exception: " + ex, ex);
/*     */     } finally {
/*     */       
/*  97 */       this.filter = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pruneCTOCChildren(CTOC ctoc, CTOCElement node, boolean cachedProperties, int level_ctoc) {
/* 105 */     if (node.getChildren() == null)
/*     */       return; 
/*     */     try {
/* 108 */       level_ctoc++;
/* 109 */       Iterator it = node.getChildren().iterator();
/* 110 */       while (it.hasNext()) {
/* 111 */         Object child = it.next();
/* 112 */         if (child instanceof CTOCElement) {
/* 113 */           pruneCTOCChildren(ctoc, (CTOCElement)child, cachedProperties, level_ctoc);
/* 114 */           if (level_ctoc >= this.level_pruned)
/*     */           {
/* 116 */             if (level_ctoc > this.level_pruned) {
/* 117 */               CTOCCache.remove((CTOCNode)child);
/* 118 */               if (cachedProperties);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 125 */       level_ctoc--;
/* 126 */     } catch (Exception ex) {
/* 127 */       log.error("unable to pruneCTOCChildren -exception: " + ex, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\CTOCPruneImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */