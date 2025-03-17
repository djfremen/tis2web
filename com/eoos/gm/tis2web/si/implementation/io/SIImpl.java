/*     */ package com.eoos.gm.tis2web.si.implementation.io;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.DBMS;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.SICacheImpl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.CPRDocumentNotSupportedException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.SearchNotResultException;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SICache;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SIImpl implements SI {
/*     */   private SICache cache;
/*     */   
/*     */   public Collection getVersionInfo() {
/*  33 */     return Collections.singleton(this.cache.getVersionInfo());
/*     */   }
/*     */   
/*     */   public SIImpl(IDatabaseLink db, ILVCAdapter lvcAdapter, SICTOCService siCTOCService) {
/*  37 */     this.cache = (SICache)new SICacheImpl(new DBMS(db), lvcAdapter, siCTOCService);
/*     */   }
/*     */   
/*     */   public CTOCFactory getFactory() {
/*  41 */     return (CTOCFactory)this.cache;
/*     */   }
/*     */   
/*     */   public SIO getSIO(int sioID) {
/*  45 */     Integer id = Integer.valueOf(sioID);
/*  46 */     SIOElement sio = this.cache.getElement(id);
/*  47 */     return (sio != null) ? (SIO)sio : (SIO)this.cache.loadElement(id);
/*     */   }
/*     */   
/*     */   public SIO getSIO(CTOCType ctype, int sioID) {
/*  51 */     Integer id = Integer.valueOf(sioID);
/*  52 */     SIOElement sio = this.cache.getElement(id);
/*  53 */     if (sio == null)
/*     */     {
/*  55 */       if (ctype.equals(CTOCType.WIS)) {
/*  56 */         sio = (SIOElement)this.cache.make(CTOCType.SI, sioID, 0, SIOProperty.WIS.ord(), VCR.NULL);
/*     */       } else {
/*  58 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*  61 */     return (SIO)sio;
/*     */   }
/*     */   
/*     */   public SIO lookupSIO(int sioID) {
/*  65 */     Integer id = Integer.valueOf(sioID);
/*  66 */     SIOElement sio = this.cache.getElement(id);
/*  67 */     if (sio == null) {
/*  68 */       sio = this.cache.loadElement(id);
/*     */     }
/*  70 */     return (SIO)sio;
/*     */   }
/*     */   
/*     */   public List loadSIOs(List sios) {
/*  74 */     if (sios == null)
/*  75 */       return null; 
/*  76 */     List result = new LinkedList();
/*  77 */     result = this.cache.loadSIOs(sios);
/*     */     
/*  79 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public SI.MHTML getDocument(String docNumber, LocaleInfo locale) throws DocumentNotFoundException, DocumentContainerConstructionException, SearchNotResultException, CPRDocumentNotSupportedException {
/*  84 */     List siosId = this.cache.searchDocumentsByNumber(docNumber);
/*  85 */     if (Util.isNullOrEmpty(siosId)) {
/*  86 */       throw new SearchNotResultException(docNumber);
/*     */     }
/*     */     
/*  89 */     SI.MHTML mhtmlDoc = this.cache.provideMHTMLDocument(siosId, locale);
/*  90 */     if (mhtmlDoc != null);
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
/*     */     
/* 103 */     return mhtmlDoc;
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode searchDocumentsByNumber(String docNumber) {
/* 108 */     List siosId = this.cache.searchDocumentsByNumber(docNumber);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     return this.cache.provideDocumentsBySIO(siosId);
/*     */   }
/*     */   
/*     */   public CTOCNode searchDocumentsByPublicationID(String publicationID) {
/* 118 */     List siosId = this.cache.searchDocumentsByPublicationID(publicationID);
/* 119 */     return this.cache.provideDocumentsBySIO(siosId);
/*     */   }
/*     */   
/*     */   public SIOBlob getGraphic(int sioID) {
/* 123 */     return this.cache.getGraphic(sioID);
/*     */   }
/*     */   
/*     */   public SIOBlob getImage(int sioID) {
/* 127 */     return this.cache.getImage(sioID);
/*     */   }
/*     */   
/*     */   public SIOBlob getDocumentBlob(SIO sio, LocaleInfo locale) {
/* 131 */     return this.cache.getDocument(sio, locale);
/*     */   }
/*     */   
/*     */   public List provideTSBs() {
/* 135 */     return this.cache.provideTSBs();
/*     */   }
/*     */   
/*     */   public List provideDTCs() {
/* 139 */     return this.cache.provideDTCs();
/*     */   }
/*     */   
/*     */   public List provideIBs(Integer sitqId) {
/* 143 */     return this.cache.provideIBs(sitqId);
/*     */   }
/*     */   
/*     */   public String getSubject(SIO sio, LocaleInfo locale) {
/* 147 */     return this.cache.getSubject(sio, locale);
/*     */   }
/*     */   
/*     */   public String getSubject(Integer sioId, LocaleInfo locale) {
/* 151 */     return this.cache.getSubject(sioId, locale);
/*     */   }
/*     */   
/*     */   public void loadProperties(List sios) {
/* 155 */     this.cache.loadProperties(sios);
/*     */   }
/*     */   
/*     */   public SI.Retrieval createRetrievalImpl() {
/* 159 */     return (SI.Retrieval)new SI.Retrieval.RI(this);
/*     */   }
/*     */   
/*     */   public String getMimeType(int sioID) {
/* 163 */     return this.cache.getMimeType(sioID);
/*     */   }
/*     */   
/*     */   public String getMimeType4Image(int sioID) {
/* 167 */     return this.cache.getMimeType4Image(sioID);
/*     */   }
/*     */   
/*     */   public byte[] getScreenData(String identifier) throws Exception {
/* 171 */     return this.cache.getScreenData(identifier);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\SIImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */