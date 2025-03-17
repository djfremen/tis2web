/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCLabel;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SILabel;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class IOElement
/*     */   implements IIOElement
/*     */ {
/*  23 */   protected static Logger log = Logger.getLogger(SIO.class);
/*     */   
/*     */   protected Integer sioID;
/*     */   
/*     */   protected SIOType sioType;
/*     */   
/*     */   protected CTOCLabel label;
/*     */   
/*     */   protected int order;
/*     */   
/*     */   protected VCR vcr;
/*     */   
/*     */   protected Map properties;
/*     */   
/*     */   protected IOCache cache;
/*     */   
/*     */   public Integer getID() {
/*  40 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public SITOCType getType() {
/*  44 */     return (SITOCType)this.sioType;
/*     */   }
/*     */   
/*     */   public boolean isSIO() {
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/*  52 */     String subject = (this.label == null) ? null : this.label.get(locale);
/*  53 */     return subject;
/*     */   }
/*     */   
/*     */   public SIOBlob getDocument(LocaleInfo locale) {
/*  57 */     return this.cache.getDocument((SIO)this, locale);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  61 */     return this.order;
/*     */   }
/*     */   
/*     */   public List getChildren() {
/*  65 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/*  69 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/*  73 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public Set getPropertySet() {
/*  77 */     if (this.properties != null) {
/*  78 */       return (this.properties.size() > 0) ? this.properties.keySet() : null;
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/*  85 */     return (getProperty(property) != null);
/*     */   }
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/*  89 */     if (property instanceof SIOProperty) {
/*  90 */       return this.properties.get(property);
/*     */     }
/*  92 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 101 */     return (this.vcr == null) ? true : this.vcr.match(locale, vcr);
/*     */   }
/*     */   
/*     */   public IOElement clone(int id) {
/* 105 */     IOElement copy = new IOElement(Integer.valueOf(id), this.sioType, this.order, this.vcr);
/* 106 */     copy.label = this.label;
/* 107 */     copy.properties = this.properties;
/* 108 */     copy.cache = this.cache;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     return copy;
/*     */   }
/*     */   
/*     */   public IOElement(Integer id, SIOType sioType, int order, VCR vcr) {
/* 117 */     this.sioID = id;
/* 118 */     this.sioType = sioType;
/* 119 */     this.order = order;
/* 120 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public void setVCR(VCR vcr) {
/* 124 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public void setOrder(int order) {
/* 128 */     this.order = order;
/*     */   }
/*     */   
/*     */   public void add(SIOProperty property, Object value) {
/* 132 */     if (this.properties == null) {
/* 133 */       this.properties = new HashMap<Object, Object>();
/*     */     }
/* 135 */     this.properties.put(property, value);
/*     */   }
/*     */   
/*     */   void setCache(IOCache cache) {
/* 139 */     this.cache = cache;
/*     */   }
/*     */   
/*     */   void setLabel(CTOCLabel subject) {
/* 143 */     this.label = subject;
/*     */   }
/*     */   
/*     */   public SIOBlob getGraphic(String sioID) {
/* 147 */     return this.cache.getGraphic(sioID);
/*     */   }
/*     */   
/*     */   public SIO getReferencedSIO(int sio) {
/* 151 */     return (SIO)this.cache.getElement(sio);
/*     */   }
/*     */   
/*     */   public boolean isLabelCached(LocaleInfo locale) {
/* 155 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SILabel getSubject() {
/* 159 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\IOElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */