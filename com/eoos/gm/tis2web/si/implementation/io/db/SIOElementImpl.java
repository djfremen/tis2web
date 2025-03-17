/*     */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCComparator;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.AbstractSIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOElement;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class SIOElementImpl
/*     */   extends AbstractSIO implements SIOElement {
/*  29 */   private static Logger log = Logger.getLogger(SIO.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SIOType sioType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Callback callback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ILVCAdapter.Retrieval lvcr;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private List relatedLUs = null;
/*     */   
/*  64 */   private List sits = null;
/*  65 */   private List sitIDs = null;
/*     */   
/*  67 */   private List esLinks = null;
/*     */   
/*  69 */   private List complaints = null;
/*     */   
/*  71 */   private List dtcs = null;
/*     */   
/*  73 */   private List links = null;
/*     */   
/*     */   public SIOElementImpl(Integer id, int order, int sioType, VCR vcr, Callback callback, ILVCAdapter.Retrieval lvcr) {
/*  76 */     super(id, order, vcr);
/*  77 */     this.sioType = SIOType.get(sioType);
/*  78 */     this.callback = callback;
/*     */     
/*  80 */     this.lvcr = lvcr;
/*  81 */     if (this.sioType == null) {
/*  82 */       log.error("unable to resolve SIOType " + sioType + ", continuing with <null>");
/*     */     }
/*     */   }
/*     */   
/*     */   protected SIOElementImpl() {
/*  87 */     super(null, 0, null);
/*     */   }
/*     */   
/*     */   protected synchronized Map getProperties() {
/*  91 */     Map properties = this.callback.getProperties((SIO)this);
/*  92 */     return (properties != null) ? properties : Collections.EMPTY_MAP;
/*     */   }
/*     */   
/*     */   public synchronized List getRelatedLUs() {
/*  96 */     if (this.relatedLUs == null) {
/*  97 */       this.relatedLUs = new LinkedList();
/*     */       
/*  99 */       List relatedLUs_unresolved = this.callback.getRelatedLUs(getID());
/* 100 */       if (!Util.isNullOrEmpty(relatedLUs_unresolved)) {
/* 101 */         for (Iterator<Integer> iter = relatedLUs_unresolved.iterator(); iter.hasNext(); ) {
/* 102 */           Integer rsioID = iter.next();
/* 103 */           SIO rsio = this.callback.getSIO(rsioID);
/* 104 */           if (rsio != null) {
/* 105 */             this.relatedLUs.add(rsio);
/*     */             continue;
/*     */           } 
/* 108 */           log.error("related sio '" + rsioID + "' not resolved.");
/*     */         } 
/*     */         
/* 111 */         Collections.sort(this.relatedLUs, (Comparator<?>)CTOCComparator.INSTANCE);
/*     */       } 
/*     */     } 
/*     */     
/* 115 */     return this.relatedLUs;
/*     */   }
/*     */   
/*     */   public synchronized List getSITs() {
/* 119 */     if (this.sits == null) {
/*     */       
/* 121 */       String tmp = (String)getProperties().get(SIOProperty.SIT);
/* 122 */       if (!Util.isNullOrEmpty(tmp)) {
/* 123 */         this.sits = Util.parseList(tmp, "\\s*,\\s*", new Util.ObjectCreation()
/*     */             {
/*     */               public Object createObject(String id) {
/* 126 */                 CTOCNode sit = SIOElementImpl.this.callback.lookupSIT(id);
/* 127 */                 if (sit == null) {
/* 128 */                   SIOElementImpl.log.error("sit/sitq reference '" + id + "' not resolved.");
/*     */                 }
/* 130 */                 return sit;
/*     */               }
/*     */             });
/* 133 */         while (this.sits.remove((Object)null));
/*     */       }
/*     */       else {
/*     */         
/* 137 */         this.sits = Collections.EMPTY_LIST;
/*     */       } 
/*     */     } 
/*     */     
/* 141 */     return this.sits;
/*     */   }
/*     */   
/*     */   public synchronized List getElectronicSystemLinks() {
/* 145 */     if (this.esLinks == null) {
/*     */       
/* 147 */       String tmp = (String)getProperties().get(SIOProperty.ElectronicSystem);
/* 148 */       if (!Util.isNullOrEmpty(tmp)) {
/* 149 */         this.esLinks = Util.parseList(tmp, "\\s*,\\s*", new Util.ObjectCreation()
/*     */             {
/*     */               public Object createObject(String id) {
/* 152 */                 if (SIOElementImpl.this.callback.checkElectronicSystemID(id)) {
/* 153 */                   return Integer.valueOf(id);
/*     */                 }
/* 155 */                 return null;
/*     */               }
/*     */             });
/*     */         
/* 159 */         while (this.esLinks.remove((Object)null));
/*     */       }
/*     */       else {
/*     */         
/* 163 */         this.esLinks = Collections.EMPTY_LIST;
/*     */       } 
/*     */     } 
/* 166 */     return this.esLinks;
/*     */   } public static interface Callback {
/*     */     String getSubject(SIO param1SIO, LocaleInfo param1LocaleInfo); Map getProperties(SIO param1SIO); SIO getSIO(Integer param1Integer); CTOCNode lookupSIT(String param1String); boolean checkElectronicSystemID(String param1String); SIOBlob getDocument(SIO param1SIO, LocaleInfo param1LocaleInfo); SIOBlob getDocument(SIOType param1SIOType, Integer param1Integer, LocaleInfo param1LocaleInfo); List loadCheckingProcedures(List param1List); CTOCNode getWiringDiagrams(String param1String); String getElectronicSystemLabel(LocaleInfo param1LocaleInfo, String param1String); List getRelatedLUs(Integer param1Integer); }
/*     */   public synchronized List getComplaints() {
/* 170 */     if (this.complaints == null) {
/*     */       
/* 172 */       String tmp = (String)getProperties().get(SIOProperty.COMPLAINT);
/* 173 */       if (!Util.isNullOrEmpty(tmp)) {
/* 174 */         this.complaints = Util.parseList(tmp, "\\s*,\\s*", new Util.ObjectCreation()
/*     */             {
/*     */               public Object createObject(String id) {
/* 177 */                 return id;
/*     */               }
/*     */             });
/*     */       } else {
/* 181 */         this.complaints = Collections.EMPTY_LIST;
/*     */       } 
/*     */     } 
/*     */     
/* 185 */     return this.complaints;
/*     */   }
/*     */   
/*     */   public synchronized List getDTCs() {
/* 189 */     if (this.dtcs == null) {
/*     */       
/* 191 */       String tmp = (String)getProperties().get(SIOProperty.DTC);
/* 192 */       if (!Util.isNullOrEmpty(tmp)) {
/* 193 */         this.dtcs = Util.parseList(tmp, "\\s*,\\s*", new Util.ObjectCreation()
/*     */             {
/*     */               public Object createObject(String id) {
/* 196 */                 return id;
/*     */               }
/*     */             });
/*     */       } else {
/* 200 */         this.dtcs = Collections.EMPTY_LIST;
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     return this.dtcs;
/*     */   }
/*     */   
/*     */   public synchronized List getLinks() {
/* 208 */     if (this.links == null) {
/*     */       
/* 210 */       String tmp = (String)getProperties().get(SIOProperty.LINKS);
/* 211 */       if (!Util.isNullOrEmpty(tmp)) {
/* 212 */         this.links = Util.parseList(tmp, "\\s*,\\s*", new Util.ObjectCreation()
/*     */             {
/*     */               public Object createObject(String id) {
/* 215 */                 SIO link = SIOElementImpl.this.callback.getSIO(Integer.valueOf(id));
/* 216 */                 if (link == null) {
/* 217 */                   SIOElementImpl.log.error("link sio '" + link + "' not resolved.");
/*     */                 }
/* 219 */                 return link;
/*     */               }
/*     */             });
/* 222 */         while (this.links.remove((Object)null));
/*     */       }
/*     */       else {
/*     */         
/* 226 */         this.links = Collections.EMPTY_LIST;
/*     */       } 
/*     */     } 
/* 229 */     return this.links;
/*     */   }
/*     */   
/*     */   protected synchronized Integer getIntegerProperty(SITOCProperty property) {
/* 233 */     Integer tmp = null;
/*     */     try {
/* 235 */       tmp = Integer.valueOf((String)getProperties().get(property));
/* 236 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 240 */     return tmp;
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized Object getWDNumber(SITOCProperty property) {
/* 245 */     Object wd = getProperties().get(property);
/*     */     try {
/* 247 */       wd = Integer.valueOf(wd.toString());
/* 248 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/* 252 */     return wd;
/*     */   }
/*     */ 
/*     */   
/*     */   public SITOCType getType() {
/* 257 */     return (SITOCType)this.sioType;
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/* 261 */     return this.callback.getSubject((SIO)this, locale);
/*     */   }
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/* 265 */     if (SIOProperty.RelatedLU == property)
/* 266 */       return getRelatedLUs(); 
/* 267 */     if (SIOProperty.SIT == property)
/* 268 */       return getSITs(); 
/* 269 */     if (SIOProperty.WD == property)
/* 270 */       return getWDNumber(property); 
/* 271 */     if (SIOProperty.ElectronicSystem == property)
/* 272 */       return getElectronicSystemLinks(); 
/* 273 */     if (SIOProperty.COMPLAINT == property)
/* 274 */       return getComplaints(); 
/* 275 */     if (SIOProperty.DTC == property)
/* 276 */       return getDTCs(); 
/* 277 */     if (SIOProperty.LINKS == property)
/* 278 */       return getLinks(); 
/* 279 */     if (property instanceof SIOProperty) {
/* 280 */       Map properties = getProperties();
/* 281 */       if (properties == null)
/* 282 */         return null; 
/* 283 */       return getProperties().get(property);
/*     */     } 
/* 285 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 290 */     return (getVCR() == null) ? true : getVCR().match(locale, vcr);
/*     */   }
/*     */   
/*     */   public ILVCAdapter.Retrieval getLvcr() {
/* 294 */     return this.lvcr;
/*     */   }
/*     */   
/*     */   public String getNonMarketsConstraints() {
/* 298 */     return (String)getProperties().get(SIOProperty.NonMarketConstraint);
/*     */   }
/*     */   
/*     */   public String getLiteratureNumber() {
/* 302 */     return (String)getProperties().get(SIOProperty.LU);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List getSITIDs() {
/* 307 */     if (this.sitIDs == null) {
/* 308 */       this.sitIDs = new LinkedList(getSITs());
/* 309 */       for (ListIterator<Integer> iter = this.sitIDs.listIterator(); iter.hasNext();) {
/* 310 */         iter.set(((CTOCNode)iter.next()).getID());
/*     */       }
/*     */     } 
/* 313 */     return this.sitIDs;
/*     */   }
/*     */   
/*     */   public List getSits() {
/* 317 */     final List delegate = getSITIDs();
/* 318 */     return new AbstractList<String>()
/*     */       {
/*     */         public String get(int index)
/*     */         {
/* 322 */           return String.valueOf(delegate.get(index));
/*     */         }
/*     */ 
/*     */         
/*     */         public int size() {
/* 327 */           return delegate.size();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public String getSubject(LocaleInfo locale) {
/* 333 */     return getLabel(locale);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIOElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */