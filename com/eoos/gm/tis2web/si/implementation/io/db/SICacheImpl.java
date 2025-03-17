/*     */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCDomain;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCFactory;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.frame.ws.e5.mhtml.MHTMLDocument;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentContainerConstructionException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentNotFoundException;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.CPRDocumentNotSupportedException;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SICache;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProxy;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.service.VCRService;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.UncheckedInterruptedException;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SICacheImpl
/*     */   implements CTOCFactory, SICache, SIOElementImpl.Callback
/*     */ {
/*  47 */   protected static Logger log = Logger.getLogger(SICacheImpl.class);
/*     */   
/*  49 */   private SoftReference srSIOMap = null;
/*     */   
/*     */   private SIStore store;
/*     */   
/*     */   private CTOC ctoc;
/*     */   
/*     */   private ILVCAdapter lvcAdapter;
/*     */   
/*     */   private SICTOCService siCTOCService;
/*     */   
/*     */   public DBVersionInformation getVersionInfo() {
/*  60 */     return this.store.getVersionInfo();
/*     */   }
/*     */   
/*     */   public SICacheImpl(DBMS dbms, ILVCAdapter adapter, SICTOCService siCTOCService) {
/*  64 */     this.lvcAdapter = adapter;
/*  65 */     this.siCTOCService = siCTOCService;
/*     */     try {
/*  67 */       this.store = new SIStore(dbms, adapter);
/*     */     }
/*  69 */     catch (Exception e) {
/*  70 */       throw Util.toRuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private synchronized Map getSIOMap() {
/*  75 */     Map<Object, Object> ret = (this.srSIOMap != null) ? this.srSIOMap.get() : null;
/*  76 */     if (ret == null) {
/*  77 */       ret = new ConcurrentHashMap<Object, Object>();
/*  78 */       this.srSIOMap = new SoftReference<Map<Object, Object>>(ret);
/*     */     } 
/*  80 */     return ret;
/*     */   }
/*     */   
/*     */   public void register(CTOC ctoc) {
/*  84 */     this.ctoc = ctoc;
/*  85 */     if (ctoc.getCTOC(CTOCDomain.SPECIAL_BROCHURE) == null)
/*     */     {
/*  87 */       ctoc.registryRootSBT();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String makePublicationKey(String value) {
/*  96 */     value = value.toLowerCase(Locale.ENGLISH);
/*  97 */     StringBuffer key = new StringBuffer();
/*     */     
/*  99 */     for (int i = 0; i < value.length(); i++) {
/* 100 */       char c = value.charAt(i);
/* 101 */       if (Character.isLetterOrDigit(c)) {
/* 102 */         key.append(c);
/*     */       }
/*     */     } 
/* 105 */     return key.toString();
/*     */   }
/*     */   
/*     */   private SICTOCService getSICTOCService() {
/* 109 */     return this.siCTOCService;
/*     */   }
/*     */   
/*     */   public SITOCElement make(CTOCType contentType, int contentID, int order, long propertyIndicator, VCR vcr) {
/* 113 */     SIOType sioType = SIOType.get(contentType.ord());
/* 114 */     SIOElementImpl sio = null;
/* 115 */     if (sioType == SIOType.SI) {
/* 116 */       Integer sioID = Integer.valueOf(contentID);
/* 117 */       sio = (SIOElementImpl)getSIOMap().get(sioID);
/* 118 */       if (sio != null) {
/* 119 */         return (SITOCElement)sio;
/*     */       }
/* 121 */       if ((propertyIndicator & SIOProperty.WIS.ord()) > 0L) {
/* 122 */         sio = new SIOWISElementImpl(sioID, order, vcr, this, this.lvcAdapter.createRetrievalImpl());
/*     */       } else {
/* 124 */         sio = new SIOLUElementImpl(sioID, order, vcr, this, this.lvcAdapter.createRetrievalImpl());
/*     */       } 
/* 126 */     } else if (sioType == SIOType.WD) {
/* 127 */       Integer sioID = Integer.valueOf(contentID);
/* 128 */       sio = (SIOElementImpl)getSIOMap().get(sioID);
/* 129 */       if (sio != null) {
/* 130 */         return (SITOCElement)sio;
/*     */       }
/* 132 */       sio = new SIOWDElement(sioID, order, vcr, this, this.lvcAdapter.createRetrievalImpl());
/* 133 */     } else if (sioType == SIOType.CPR) {
/* 134 */       Integer sioID = Integer.valueOf(contentID);
/* 135 */       sio = (SIOElementImpl)getSIOMap().get(sioID);
/* 136 */       if (sio != null) {
/* 137 */         return (SITOCElement)sio;
/*     */       }
/* 139 */       sio = new SIOCPRElement(sioID, order, vcr, this, this.lvcAdapter.createRetrievalImpl());
/*     */     } else {
/* 141 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 144 */     getSIOMap().put(sio.getID(), sio);
/* 145 */     return (SITOCElement)sio;
/*     */   }
/*     */   
/*     */   public CTOCNode lookupSIT(String id) {
/*     */     try {
/* 150 */       return this.ctoc.lookupCTOC(CTOCDomain.SIT, Integer.valueOf(id));
/* 151 */     } catch (Exception x) {
/* 152 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List loadSIOs(final List ids) {
/* 157 */     final Map resolved = this.ctoc.loadContent(ids);
/*     */     
/* 159 */     return new AbstractList()
/*     */       {
/*     */         public Object get(int index)
/*     */         {
/* 163 */           return resolved.get(ids.get(index));
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 168 */           return ids.size();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean checkRT = false;
/*     */   
/* 176 */   protected static VCRService vcrService = null;
/*     */   
/*     */   public void loadProperties(List sios) {
/* 179 */     this.store.getProperties(sios);
/*     */   }
/*     */   
/*     */   public Map getProperties(SIO sio) {
/* 183 */     return this.store.getProperties(sio);
/*     */   }
/*     */   
/*     */   public SIOElement getElement(Integer sioID) {
/* 187 */     return (SIOElementImpl)getSIOMap().get(sioID);
/*     */   }
/*     */   
/*     */   protected SIOElement loadViaCTOC(Integer sioID) {
/* 191 */     SITOCElement element = this.ctoc.loadContent(sioID);
/* 192 */     if (element instanceof SIOProxy) {
/* 193 */       return (SIOElement)((SIOProxy)element).getSIO();
/*     */     }
/* 195 */     return (SIOElement)element;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SIOElement loadElement(Integer sioID) {
/* 201 */     SIOElement element = (SIOElementImpl)getSIOMap().get(sioID);
/* 202 */     if (element == null) {
/* 203 */       element = loadViaCTOC(sioID);
/*     */     }
/* 205 */     if (element == null) {
/* 206 */       Integer chapterID = this.store.lookupChapterCPRId(sioID);
/* 207 */       if (chapterID != null) {
/* 208 */         element = loadViaCTOC(chapterID);
/*     */       }
/*     */     } 
/* 211 */     if (element == null) {
/* 212 */       element = (SIOElementImpl)make(CTOCType.SI, sioID.intValue(), 0, 0L, VCR.NULL);
/*     */     }
/* 214 */     return element;
/*     */   }
/*     */   
/*     */   public String getSubject(Integer sioId, LocaleInfo locale) {
/* 218 */     return this.store.getSubject(sioId, locale);
/*     */   }
/*     */   
/*     */   public String getSubject(SIO sio, LocaleInfo locale) {
/* 222 */     return this.store.getSubject(sio, locale);
/*     */   }
/*     */   
/*     */   protected LGSIT getFLCSIT(SIO sio) {
/* 226 */     return getFLCSIT((SIOType)sio.getType());
/*     */   }
/*     */   
/*     */   protected LGSIT getFLCSIT(SIOType sioType) {
/* 230 */     if (sioType == SIOType.SI)
/* 231 */       return LGSIT.SI; 
/* 232 */     if (sioType == SIOType.CPR)
/* 233 */       return LGSIT.CPR; 
/* 234 */     if (sioType == SIOType.WD)
/* 235 */       return LGSIT.WD; 
/* 236 */     if (sioType == SIOType.MajorOperation) {
/* 237 */       return LGSIT.LT;
/*     */     }
/* 239 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public SIOBlob getDocument(SIO sio, LocaleInfo locale) {
/*     */     try {
/* 245 */       SIOBlob blob = this.store.loadDocument(sio.getID().intValue(), locale.getLocaleID().intValue());
/* 246 */       if (blob == null) {
/* 247 */         List<Integer> flc = locale.getLocaleFLC(getFLCSIT(sio));
/* 248 */         if (flc == null) {
/* 249 */           return null;
/*     */         }
/* 251 */         for (int i = 0; i < flc.size(); i++) {
/* 252 */           LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(flc.get(i));
/* 253 */           blob = this.store.loadDocument(sio.getID().intValue(), li.getLocaleID().intValue());
/* 254 */           if (blob != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 259 */       return blob;
/* 260 */     } catch (UncheckedInterruptedException e) {
/* 261 */       throw e;
/* 262 */     } catch (Exception e) {
/* 263 */       log.error("failed to fetch document '" + sio.getID() + "' (locale=" + locale.getLocale() + ").", e);
/* 264 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SIOBlob getDocument(SIOType sioType, Integer sioID, LocaleInfo locale) {
/*     */     try {
/* 270 */       SIOBlob blob = this.store.loadDocument(sioID.intValue(), locale.getLocaleID().intValue());
/* 271 */       if (blob == null) {
/* 272 */         List<Integer> flc = locale.getLocaleFLC(getFLCSIT(sioType));
/* 273 */         if (flc == null) {
/* 274 */           return null;
/*     */         }
/* 276 */         for (int i = 0; i < flc.size(); i++) {
/* 277 */           LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(flc.get(i));
/* 278 */           blob = this.store.loadDocument(sioID.intValue(), li.getLocaleID().intValue());
/* 279 */           if (blob != null) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 284 */       return blob;
/* 285 */     } catch (UncheckedInterruptedException e) {
/* 286 */       throw e;
/* 287 */     } catch (Exception e) {
/* 288 */       log.error("failed to fetch document '" + sioID + "' (locale=" + locale.getLocale() + ").", e);
/* 289 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SIOBlob getGraphic(int sioID) {
/*     */     try {
/* 295 */       return this.store.loadGraphic(sioID);
/* 296 */     } catch (Exception e) {
/* 297 */       log.error("failed to load graphic '" + sioID + "'.", e);
/* 298 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public SIOBlob getImage(int sioID) {
/*     */     try {
/* 304 */       return this.store.loadImage(sioID);
/* 305 */     } catch (Exception e) {
/* 306 */       log.error("failed to load image '" + sioID + "'.", e);
/* 307 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public List searchDocumentsByNumber(String number) {
/* 312 */     List<Integer> result = null;
/* 313 */     if (number != null && number.length() != 0) {
/* 314 */       number = number.trim();
/* 315 */       if (number.charAt(0) == '#') {
/*     */         
/*     */         try {
/* 318 */           Integer id = Integer.valueOf(number.substring(1));
/* 319 */           result = new LinkedList();
/* 320 */           result.add(id);
/* 321 */         } catch (Exception ex) {
/* 322 */           return result;
/*     */         } 
/*     */       } else {
/*     */         
/* 326 */         number = number.toUpperCase();
/* 327 */         result = searchDocumentsByLU(number);
/* 328 */         if (Util.isNullOrEmpty(result)) {
/* 329 */           result = searchDocumentsByES(number);
/*     */         }
/* 331 */         if (Util.isNullOrEmpty(result)) {
/* 332 */           result = searchDocumentsByWD(number);
/*     */         }
/*     */       } 
/*     */     } 
/* 336 */     return result;
/*     */   }
/*     */   
/*     */   private List searchDocumentsByES(String es) {
/* 340 */     List result = this.store.loadDocumentByES(es);
/* 341 */     return result;
/*     */   }
/*     */   
/*     */   private List searchDocumentsByWD(String wd) {
/* 345 */     List result = this.store.loadDocumentByWD(wd);
/* 346 */     return result;
/*     */   }
/*     */   
/*     */   public List searchDocumentsByLU(String lu) {
/* 350 */     List result = null;
/* 351 */     result = this.store.loadDocumentByLU(lu);
/*     */     
/* 353 */     return result;
/*     */   }
/*     */   
/*     */   public Object searchDocumentsBySIO(String sioid) {
/*     */     try {
/* 358 */       Integer id = Integer.valueOf(sioid);
/* 359 */       SIOElementImpl sio = (SIOElementImpl)getElement(id);
/* 360 */       if (sio == null) {
/* 361 */         sio = (SIOElementImpl)loadElement(id);
/*     */       }
/* 363 */     } catch (Exception x) {}
/*     */     
/* 365 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List searchDocumentsByPublicationID(String publicationID) {
/* 370 */     String publicationKey = makePublicationKey(publicationID);
/* 371 */     return this.store.loadDocumentByPublicationID(publicationKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode getWiringDiagrams(String es) {
/* 376 */     List result = null;
/* 377 */     result = this.store.loadWiringDiagramsIds(es);
/* 378 */     List sios = loadSIOs(result);
/*     */     
/* 380 */     if (result != null && result.size() > 0) {
/* 381 */       SICTOCService service = getSICTOCService();
/* 382 */       CTOCSurrogate root = service.createCTOCSurrogate();
/* 383 */       int hits = 0;
/* 384 */       LinkedList<SIO> toAdd = new LinkedList();
/* 385 */       for (Iterator<SIO> iter = sios.iterator(); iter.hasNext(); ) {
/* 386 */         SIO sio = iter.next();
/* 387 */         if (sio != null && sio.getType() == SIOType.WD) {
/* 388 */           toAdd.add(sio);
/* 389 */           hits++;
/*     */         } 
/*     */       } 
/* 392 */       root.add(toAdd);
/* 393 */       return (hits == 0) ? null : (CTOCNode)root;
/*     */     } 
/* 395 */     return null;
/*     */   }
/*     */   
/*     */   public String getElectronicSystemLabel(LocaleInfo locale, String escode) {
/* 399 */     return this.ctoc.getElectronicSystemLabel(locale, escode);
/*     */   }
/*     */   
/*     */   public boolean checkElectronicSystemID(String escode) {
/* 403 */     return this.ctoc.checkElectronicSystemID(escode);
/*     */   }
/*     */   
/*     */   public List loadCheckingProcedures(List escs) {
/* 407 */     List siosIds = this.store.loadCPRs(escs);
/* 408 */     List sios = new LinkedList(loadSIOs(siosIds));
/* 409 */     for (Iterator<SIO> it = sios.iterator(); it.hasNext(); ) {
/* 410 */       SIO sio = it.next();
/* 411 */       if (!(sio instanceof SIOCPRElement)) {
/* 412 */         log.error(("FAILURE (no sio-cpr as dsc-link), removing from list: " + sio != null) ? sio.getID() : "null sio");
/* 413 */         it.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 417 */     return sios;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Integer getTOCIdSIT(String sitDomain) {
/* 422 */     CTOCNode sits = this.ctoc.getCTOC(CTOCDomain.SIT);
/* 423 */     List<CTOCNode> children = sits.getChildren();
/* 424 */     Integer tocId = null;
/* 425 */     for (int i = 0; i < children.size(); i++) {
/* 426 */       CTOCNode sit = children.get(i);
/* 427 */       String id = (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 428 */       if (sitDomain.equals(id)) {
/* 429 */         return sit.getID();
/*     */       }
/*     */     } 
/* 432 */     return tocId;
/*     */   }
/*     */ 
/*     */   
/*     */   public List provideTSBs() {
/* 437 */     Integer tsbSITNodeId = getTOCIdSIT("SIT-12");
/* 438 */     List tsbs = this.store.getTSBs(tsbSITNodeId, this.ctoc);
/* 439 */     return tsbs;
/*     */   }
/*     */ 
/*     */   
/*     */   public List provideDTCs() {
/* 444 */     return this.store.loadDTCs();
/*     */   }
/*     */ 
/*     */   
/*     */   public List provideIBs(Integer sitQ) {
/* 449 */     List sios = this.store.loadInspections(sitQ, this.ctoc);
/* 450 */     return sios;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMimeType(int sioID) {
/* 455 */     return this.store.getMimeType(sioID);
/*     */   }
/*     */   
/*     */   public String getMimeType4Image(int sioID) {
/* 459 */     return this.store.getMimeType4Image(sioID);
/*     */   }
/*     */   
/*     */   public SIO getSIO(Integer sioID) {
/* 463 */     SIOElement sIOElement = getElement(sioID);
/* 464 */     if (sIOElement == null) {
/* 465 */       sIOElement = loadElement(sioID);
/*     */     }
/* 467 */     return (SIO)sIOElement;
/*     */   }
/*     */   
/*     */   public List getRelatedLUs(Integer id) {
/* 471 */     return this.store.getRelatedLUs(id);
/*     */   }
/*     */   
/*     */   public CTOCNode provideDocumentsBySIO(List sios) {
/* 475 */     if (!Util.isNullOrEmpty(sios)) {
/* 476 */       CTOCSurrogate root = this.siCTOCService.createCTOCSurrogate();
/* 477 */       for (Iterator<Integer> it = sios.iterator(); it.hasNext(); ) {
/* 478 */         Integer id = it.next();
/* 479 */         SIOElementImpl sio = (SIOElementImpl)getElement(id);
/* 480 */         if (sio == null) {
/* 481 */           sio = (SIOElementImpl)loadElement(id);
/*     */         }
/* 483 */         root.add((SITOCElement)sio);
/*     */       } 
/* 485 */       return (CTOCNode)root;
/*     */     } 
/* 487 */     return null;
/*     */   }
/*     */   
/*     */   public SI.MHTML provideMHTMLDocument(List sios, LocaleInfo locale) throws DocumentNotFoundException, DocumentContainerConstructionException, CPRDocumentNotSupportedException {
/* 491 */     StringBuffer subject = new StringBuffer();
/* 492 */     Integer sioId = null;
/* 493 */     for (Iterator<Integer> it = sios.iterator(); it.hasNext(); ) {
/* 494 */       sioId = it.next();
/* 495 */       subject.append(this.store.getSubject(sioId, locale));
/*     */     } 
/* 497 */     return (new MHTMLDocument((SIO)loadElement(sioId), locale)).getMHTMLDocument(subject.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getScreenData(String identifier) throws Exception {
/* 502 */     return this.store.getScreenData(identifier);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SICacheImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */