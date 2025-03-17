/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.CTOCImpl2;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCSurrogateImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.CTOCStore;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.DBMS;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.SBT;
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.io.db.SBTCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class CTOCImpl
/*     */   extends CTOCImpl2 {
/*     */   protected CTOCStore store;
/*     */   protected SI.Retrieval siRetrieval;
/*     */   protected ILVCAdapter.Retrieval lvcRetrieval;
/*     */   
/*     */   public CTOCImpl(IDatabaseLink db, CTOCDomain domain, Map factories, ILVCAdapter.Retrieval lvcRetrieval, SI.Retrieval siRetrieval, SICTOCService.Retrieval siCTOCServiceRetrieval, FTSService.Retrieval ftsRetrieval) throws Exception {
/*  43 */     this.lvcRetrieval = lvcRetrieval;
/*  44 */     this.siRetrieval = siRetrieval;
/*  45 */     this.siCTOCServiceRetrieval = siCTOCServiceRetrieval;
/*  46 */     this.ftsRetrieval = ftsRetrieval;
/*  47 */     DBMS dbms = new DBMS(db);
/*     */     
/*  49 */     this.store = new CTOCStore(dbms, domain, factories, lvcRetrieval.getLVCAdapter());
/*  50 */     this.ctocs = this.store.getCTOCs();
/*  51 */     if (factories != null) {
/*  52 */       Iterator<CTOCFactory> it = factories.values().iterator();
/*  53 */       while (it.hasNext()) {
/*  54 */         CTOCFactory factory = it.next();
/*  55 */         factory.register((CTOC)this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   protected SICTOCService.Retrieval siCTOCServiceRetrieval; protected FTSService.Retrieval ftsRetrieval; protected List dtcs;
/*     */   public CTOCNode getCTOC(VCR vcr) {
/*  61 */     CTOCCache cache = (CTOCCache)this.ctocs.get(CTOCCache.makeKey(CTOCDomain.SI.ord(), vcr.getID()));
/*  62 */     return (cache == null) ? null : cache.getRoot();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getCTOCs() {
/*  68 */     List<CTOCNode> list = new LinkedList();
/*  69 */     Iterator<CTOCCache> it = this.ctocs.values().iterator();
/*  70 */     while (it.hasNext()) {
/*  71 */       CTOCCache cache = it.next();
/*  72 */       list.add(cache.getRoot());
/*     */     } 
/*  74 */     return list;
/*     */   }
/*     */   
/*     */   public CTOCNode getCTOC(CTOCDomain type) {
/*  78 */     CTOCCache cache = (CTOCCache)this.ctocs.get(CTOCCache.makeKey(type.ord()));
/*  79 */     return (cache == null) ? null : cache.getRoot();
/*     */   }
/*     */   
/*     */   public CTOCNode lookupCTOC(CTOCDomain type, Integer ctocID) {
/*  83 */     if (type != CTOCDomain.SIT) {
/*  84 */       throw new IllegalArgumentException();
/*     */     }
/*  86 */     CTOCCache cache = (CTOCCache)this.ctocs.get(CTOCCache.makeKey(type.ord()));
/*  87 */     CTOCNode node = (cache == null) ? null : CTOCCache.getNode(ctocID);
/*  88 */     if (node == null && cache != null) {
/*  89 */       node = cache.loadNode(ctocID);
/*  90 */       CTOCCache.add(node);
/*     */     } 
/*  92 */     return node;
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode lookupMO(Integer ctocID) {
/*  97 */     CTOCCache cache = this.ctocs.values().iterator().next();
/*  98 */     return cache.loadNode(ctocID);
/*     */   }
/*     */   
/*     */   public Map lookupMOs(List ids) {
/* 102 */     CTOCCache cache = this.ctocs.values().iterator().next();
/* 103 */     return cache.loadNodes(ids);
/*     */   }
/*     */   
/*     */   public CTOCNode searchMO(String es) {
/* 107 */     List<Integer> result = this.store.loadMajorOperations(es);
/* 108 */     if (result != null) {
/* 109 */       CTOCSurrogateImpl root = new CTOCSurrogateImpl(this.siRetrieval, this.lvcRetrieval, this.ftsRetrieval);
/* 110 */       for (int i = 0; i < result.size(); i++) {
/* 111 */         Integer id = result.get(i);
/* 112 */         CTOCNode mo = lookupMO(id);
/* 113 */         if (mo != null) {
/* 114 */           root.add((SITOCElement)mo);
/*     */         }
/*     */       } 
/* 117 */       return (CTOCNode)root;
/*     */     } 
/* 119 */     return null;
/*     */   }
/*     */   
/*     */   public CTOCNode searchByProperty(CTOCNode node, CTOCProperty property, String value, VCR vcr) {
/* 123 */     return this.store.searchByProperty(node, property, value, vcr);
/*     */   }
/*     */   
/*     */   public List getCellLinks(String publicationID, int cellID) {
/* 127 */     List links = this.store.getCellLinks(publicationID, cellID);
/* 128 */     if (links == null) {
/* 129 */       links = this.store.getAssociatedCellLinks(publicationID, cellID);
/*     */     }
/* 131 */     return links;
/*     */   }
/*     */   
/*     */   public SITOCElement loadContent(Integer contentID) {
/* 135 */     return this.store.loadContent(contentID);
/*     */   }
/*     */   
/*     */   public Map<Integer, SITOCElement> loadContent(Collection content) {
/* 139 */     return this.store.loadContent(content);
/*     */   }
/*     */   
/*     */   public static Integer extractSITKey(CTOCNode sit) {
/* 143 */     Integer id = null;
/*     */     try {
/* 145 */       Object property = sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 146 */       if (property != null && property instanceof String) {
/* 147 */         int pos = ((String)property).indexOf('-');
/* 148 */         if (pos > 0) {
/* 149 */           id = Integer.valueOf(((String)property).substring(pos + 1));
/*     */         }
/*     */       } 
/* 152 */     } catch (Exception x) {}
/*     */     
/* 154 */     return id;
/*     */   }
/*     */   
/*     */   public boolean checkElectronicSystemID(String escode) {
/* 158 */     return (this.store.getElectronicSystemID(escode) != null);
/*     */   }
/*     */   
/*     */   public String getElectronicSystemLabel(LocaleInfo locale, String escode) {
/* 162 */     Integer tocid = this.store.getElectronicSystemID(escode);
/* 163 */     if (tocid == null) {
/* 164 */       return null;
/*     */     }
/* 166 */     CTOCElement es = (CTOCElement)this.store.loadNode(tocid);
/* 167 */     return (es == null) ? null : this.store.getLabel(locale, es.getLabelID());
/*     */   }
/*     */   
/*     */   public void registryRootSBT() {
/* 171 */     CTOCRootElement sbtRoot = new CTOCRootElement(-1 * CTOCDomain.SPECIAL_BROCHURE.ord(), CTOCType.CTOC.ord(), true, false, null, this.lvcRetrieval.getLVCAdapter());
/* 172 */     SBTCache cache = new SBTCache(sbtRoot, this.siRetrieval, this.siCTOCServiceRetrieval);
/* 173 */     String key = CTOCCache.makeKey(CTOCDomain.SPECIAL_BROCHURE.ord());
/* 174 */     this.ctocs.put(key, cache);
/* 175 */     sbtRoot.setCache((CTOCCache)cache);
/* 176 */     SBT sbt = new SBT();
/* 177 */     sbt.buildRootSBT((CTOC)this, cache, this.lvcRetrieval.getLVCAdapter());
/*     */   }
/*     */   
/*     */   public void registerDTCs(Set<?> set) {
/* 181 */     this.dtcs = new ArrayList(set);
/* 182 */     Collections.sort(this.dtcs);
/*     */   }
/*     */   
/*     */   public List getDTCs() {
/* 186 */     return this.dtcs;
/*     */   }
/*     */   
/*     */   public CTOCStore getCTOCStore() {
/* 190 */     return this.store;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 196 */     this.store = null;
/* 197 */     this.ctocs = null;
/*     */   }
/*     */   
/*     */   public ILVCAdapter.Retrieval getLvcRetrieval() {
/* 201 */     return this.lvcRetrieval;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\CTOCImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */