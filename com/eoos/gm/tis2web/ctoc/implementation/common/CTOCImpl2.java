/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.XMLCTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.CTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCLabel;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.CachingStrategy;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.sql.Connection;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class CTOCImpl2
/*     */   implements CTOC
/*     */ {
/*     */   protected CTOCStore store;
/*     */   protected HashMap ctocs;
/*     */   
/*     */   protected CTOCImpl2() throws Exception {}
/*     */   
/*     */   public CTOCImpl2(IDatabaseLink dblink, Connection db, CTOCDomain domain, CachingStrategy strategy, Map<Object, Object> factories, Collection sits, ILVCAdapter.Retrieval lvcRetrieval) throws Exception {
/*  38 */     if (factories == null) {
/*  39 */       factories = new HashMap<Object, Object>();
/*     */     }
/*  41 */     this.store = (CTOCStore)new XMLCTOCStore(dblink, db, domain, strategy, factories, sits, lvcRetrieval);
/*  42 */     this.ctocs = this.store.getCTOCs();
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode getCTOC(VCR vcr) {
/*  47 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCTOCs() {
/*  53 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public CTOCNode getCTOC(CTOCDomain type) {
/*  57 */     CTOCCache cache = (CTOCCache)this.ctocs.get(CTOCCache.makeKey(type.ord()));
/*  58 */     return (cache == null) ? null : cache.getRoot();
/*     */   }
/*     */   
/*     */   public void replaceCTOC(CTOCRootElement merge, CTOCDomain type) {
/*  62 */     this.ctocs.put(CTOCCache.makeKey(type.ord()), new CTOCCache((CTOCElementImpl)merge));
/*     */   }
/*     */   
/*     */   public CTOCNode lookupCTOC(CTOCDomain type, Integer ctocID) {
/*  66 */     if (type != CTOCDomain.SIT) {
/*  67 */       throw new IllegalArgumentException();
/*     */     }
/*  69 */     CTOCCache cache = (CTOCCache)this.ctocs.get(CTOCCache.makeKey(type.ord()));
/*  70 */     return (cache == null) ? null : CTOCCache.getNode(ctocID);
/*     */   }
/*     */   
/*     */   public CTOCNode lookupMO(Integer ctocID) {
/*  74 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public CTOCNode searchMO(String es) {
/*  78 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/*  82 */     return this.store.searchByProperty(node, property, value, vcr);
/*     */   }
/*     */   
/*     */   public SITOCElement loadContent(Integer contentID) {
/*  86 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getElectronicSystemLabel(LocaleInfo locale, String escode) {
/*  90 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public void registryRootSBT() {
/*  94 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerDTCs(Set set) {
/* 100 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getDTCs() {
/* 104 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getCellLinks(String publicationID, int cellID) {
/* 108 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Map<Integer, SITOCElement> loadContent(Collection content) {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean checkElectronicSystemID(String escode) {
/* 116 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public CTOCLabel getLabel(Integer labelID) {
/* 120 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public CTOCStore getCTOCStore() {
/* 124 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public ILVCAdapter.Retrieval getLvcRetrieval() {
/* 128 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getSITS() {
/* 132 */     return new LinkedList(getCTOC(CTOCDomain.SIT).getChildren());
/*     */   }
/*     */   
/*     */   public Map lookupMOs(List ids) {
/* 136 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\CTOCImpl2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */