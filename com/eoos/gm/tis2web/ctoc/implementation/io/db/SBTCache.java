/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SBTCache
/*     */   extends CTOCCache {
/*  18 */   protected Map labels = new HashMap<Object, Object>();
/*     */   
/*     */   public static CTOCNode getNode(Integer tocID) {
/*  21 */     return (CTOCNode)nodes.get(tocID);
/*     */   }
/*     */   
/*     */   private SI.Retrieval siRetrieval;
/*     */   private SICTOCService.Retrieval siCTOCServiceRetrieval;
/*     */   
/*     */   public SBTCache(CTOCRootElement root, SI.Retrieval siRetrieval, SICTOCService.Retrieval siCTOCServiceRetrieval) {
/*  28 */     super(null, root);
/*  29 */     this.siRetrieval = siRetrieval;
/*  30 */     this.siCTOCServiceRetrieval = siCTOCServiceRetrieval;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toKey(Integer labelID, LocaleInfo locale) {
/*  35 */     return String.valueOf(locale.getLocaleID()) + "#" + String.valueOf(labelID);
/*     */   }
/*     */   
/*     */   public void registerLabel(Integer labelID, LocaleInfo locale, String translation) {
/*  39 */     this.labels.put(toKey(labelID, locale), translation);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel(CTOCNode node, LocaleInfo locale, Integer labelID) {
/*  44 */     SICTOCService siCTOCService = this.siCTOCServiceRetrieval.getSICTOCService();
/*  45 */     CTOC ctoc = siCTOCService.getCTOC();
/*  46 */     CTOCLabelCache labelsCached = ctoc.getCTOCStore().getCTOCCacheLabel();
/*  47 */     String translation = (String)this.labels.get(toKey(labelID, locale));
/*  48 */     if (translation == null) {
/*  49 */       translation = labelsCached.getLabel(labelID, locale, false);
/*     */     }
/*  51 */     return translation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadChildren(CTOCNode node) {
/*  56 */     SICTOCService siCTOCService = this.siCTOCServiceRetrieval.getSICTOCService();
/*  57 */     CTOC ctoc = siCTOCService.getCTOC();
/*  58 */     ctoc.getCTOCStore().loadChildren((CTOCElement)node);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void loadContent(CTOCNode node) {
/*  64 */     if (node.getParent().getID().intValue() == 1000000) {
/*     */ 
/*     */ 
/*     */       
/*  68 */       CTOCNode sitq = (CTOCNode)node.getProperty((SITOCProperty)CTOCProperty.SITQ);
/*  69 */       SI si = this.siRetrieval.getSI();
/*  70 */       List sios = si.provideIBs(sitq.getID());
/*  71 */       if (sios == null) {
/*     */         return;
/*     */       }
/*     */       
/*  75 */       ((CTOCElement)node).shareChildren(sios);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  82 */     SICTOCService siCTOCService = this.siCTOCServiceRetrieval.getSICTOCService();
/*  83 */     CTOC ctoc = siCTOCService.getCTOC();
/*  84 */     ctoc.getCTOCStore().loadContent((CTOCElement)node);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadProperties(CTOCNode node) {
/*  90 */     ((CTOCElement)node).setProperties(getStore().getProperties((CTOCElement)node));
/*     */   }
/*     */   
/*     */   private CTOCStore getStore() {
/*  94 */     SICTOCService siCTOCService = this.siCTOCServiceRetrieval.getSICTOCService();
/*  95 */     CTOC ctoc = siCTOCService.getCTOC();
/*  96 */     return ctoc.getCTOCStore();
/*     */   }
/*     */   
/*     */   public Map getProperties(CTOCElement element) {
/* 100 */     return getStore().getProperties(element);
/*     */   }
/*     */   
/*     */   public CTOCNode loadNode(Integer ctocID) {
/* 104 */     throw new IllegalArgumentException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\SBTCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */