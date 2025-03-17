/*     */ package com.eoos.gm.tis2web.si.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VC;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SIOTSBImpl
/*     */   implements SIOTSB
/*     */ {
/*     */   protected SIOLU tsb;
/*     */   protected Date pubdate;
/*     */   protected String ag;
/*     */   protected String make;
/*     */   protected List dtcs;
/*     */   protected List complaints;
/*     */   protected boolean modelRestrictionChecked;
/*     */   protected boolean modelRestriction;
/*     */   protected String model;
/*     */   protected List models;
/*     */   protected boolean engineRestrictionChecked;
/*     */   protected boolean engineRestriction;
/*     */   protected String engine;
/*     */   protected List engines;
/*     */   protected String remedy;
/*     */   protected VCR vcr;
/*     */   private ILVCAdapter adapter;
/*     */   
/*     */   public SIOTSBImpl(SIOLU tsb, String ag, ILVCAdapter adapter) {
/*  57 */     this.tsb = tsb;
/*  58 */     this.ag = ag;
/*  59 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   private VC getVC() {
/*  63 */     return this.adapter.getVC();
/*     */   }
/*     */   
/*     */   public String getRemedyNumber() {
/*  67 */     if (this.remedy == null) {
/*  68 */       this.remedy = (String)getProperty(SIOProperty.LU);
/*  69 */       StringBuffer tmp = new StringBuffer();
/*  70 */       for (int i = 0; i < this.remedy.length(); i++) {
/*  71 */         char c = this.remedy.charAt(i);
/*  72 */         if (c != 'E' && c != '0') {
/*  73 */           tmp.append(c);
/*  74 */         } else if (tmp.length() > 0) {
/*  75 */           tmp.append(c);
/*     */         } 
/*     */       } 
/*  78 */       this.remedy = tmp.toString();
/*     */     } 
/*  80 */     return this.remedy;
/*     */   }
/*     */   
/*     */   public Date getPublicationDate() {
/*  84 */     if (this.pubdate == null) {
/*  85 */       String pdate = (String)getProperty(SIOProperty.PublicationDate);
/*     */       
/*     */       try {
/*  88 */         SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
/*  89 */         this.pubdate = format.parse(pdate);
/*  90 */       } catch (Exception e) {}
/*     */     } 
/*     */ 
/*     */     
/*  94 */     return this.pubdate;
/*     */   }
/*     */   
/*     */   public String getSalesMake() {
/*  98 */     if (this.make == null) {
/*  99 */       this.make = makeList(getVC().getAttributes("Make", getVCR()));
/*     */     }
/* 101 */     return this.make;
/*     */   }
/*     */   
/*     */   public boolean hasModelRestriction() {
/* 105 */     if (!this.modelRestrictionChecked) {
/* 106 */       String scds = (String)getProperty(SIOProperty.SCDS);
/* 107 */       if (scds != null && scds.indexOf("models=all") >= 0) {
/* 108 */         this.modelRestriction = false;
/*     */       } else {
/* 110 */         this.modelRestriction = true;
/*     */       } 
/* 112 */       this.modelRestrictionChecked = true;
/*     */     } 
/* 114 */     return this.modelRestriction;
/*     */   }
/*     */   
/*     */   public String getModel() {
/* 118 */     if (this.model == null) {
/* 119 */       this.model = makeList(getModels());
/*     */     }
/* 121 */     return this.model;
/*     */   }
/*     */   
/*     */   public List getModels() {
/* 125 */     if (this.models == null) {
/* 126 */       VC vci = getVC();
/* 127 */       this.models = vci.getAttributes("Model", getVCR());
/* 128 */       Collections.sort(this.models);
/*     */     } 
/* 130 */     return this.models;
/*     */   }
/*     */   
/*     */   public boolean hasEngineRestriction() {
/* 134 */     if (!this.engineRestrictionChecked) {
/* 135 */       VC vci = getVC();
/* 136 */       VCRDomain domain = (VCRDomain)vci.getDomain("Engine");
/*     */ 
/*     */       
/* 139 */       getEngines();
/* 140 */       if (this.engines == null) {
/* 141 */         this.engineRestriction = false;
/*     */       } else {
/* 143 */         this.engineRestriction = (this.engines.size() < domain.getMemberCount());
/*     */       } 
/* 145 */       this.engineRestrictionChecked = true;
/*     */     } 
/* 147 */     return this.engineRestriction;
/*     */   }
/*     */   
/*     */   public String getEngine() {
/* 151 */     if (this.engine == null) {
/* 152 */       VC vci = getVC();
/* 153 */       this.engine = makeList(vci.getAttributes("Engine", getVCR()));
/*     */     } 
/* 155 */     return this.engine;
/*     */   }
/*     */   
/*     */   public List getEngines() {
/* 159 */     if (this.engines == null) {
/* 160 */       VC vci = getVC();
/* 161 */       this.engines = vci.getAttributes("Engine", getVCR());
/* 162 */       Collections.sort(this.engines);
/*     */     } 
/* 164 */     return this.engines;
/*     */   }
/*     */   
/*     */   public String getAssemblyGroup() {
/* 168 */     return this.ag;
/*     */   }
/*     */   
/*     */   public boolean matchSalesMake(VCValue sm) {
/* 172 */     VCR vcr = getVCR();
/* 173 */     return vcr.match(sm);
/*     */   }
/*     */   
/*     */   public boolean matchModel(VCValue model) {
/* 177 */     VCR vcr = getVCR();
/* 178 */     return vcr.match(model);
/*     */   }
/*     */   
/*     */   public boolean matchEngine(VCValue engine) {
/* 182 */     VCR vcr = getVCR();
/* 183 */     return vcr.match(engine);
/*     */   }
/*     */   
/*     */   public boolean matchAssemblyGroup(String group) {
/* 187 */     return group.equals(this.ag);
/*     */   }
/*     */   
/*     */   public boolean matchDTC(String dtc) {
/* 191 */     if (this.dtcs != null) {
/* 192 */       return this.dtcs.contains(dtc);
/*     */     }
/* 194 */     List dtcs = (List)getProperty(SIOProperty.DTC);
/* 195 */     return dtcs.contains(dtc);
/*     */   }
/*     */   
/*     */   public boolean matchSymptom(String complaint) {
/* 199 */     List<String> complaints = null;
/* 200 */     if (this.complaints != null) {
/* 201 */       complaints = this.complaints;
/*     */     } else {
/* 203 */       complaints = (List)getProperty(SIOProperty.COMPLAINT);
/* 204 */       if (complaints == null) {
/* 205 */         return false;
/*     */       }
/*     */     } 
/* 208 */     for (int i = 0; i < complaints.size(); i++) {
/* 209 */       String item = complaints.get(i);
/* 210 */       if (complaint.endsWith(item)) {
/* 211 */         return true;
/*     */       }
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */   
/*     */   public boolean matchSymptom(CTOCNode complaint) {
/* 218 */     String sitq = (String)complaint.getProperty((SITOCProperty)CTOCProperty.SITQ);
/* 219 */     return matchSymptom(sitq);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer getID() {
/* 224 */     return this.tsb.getID();
/*     */   }
/*     */   
/*     */   public SITOCType getType() {
/* 228 */     return this.tsb.getType();
/*     */   }
/*     */   
/*     */   public boolean isSIO() {
/* 232 */     return this.tsb.isSIO();
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/* 236 */     return this.tsb.getLabel(locale);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/* 240 */     return this.tsb.getOrder();
/*     */   }
/*     */   
/*     */   public List getChildren() {
/* 244 */     return this.tsb.getChildren();
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 248 */     return this.tsb.getChildren(filterSITs, locale, country, vcr);
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/* 252 */     if (this.vcr != null)
/* 253 */       return this.vcr; 
/* 254 */     return this.tsb.getVCR();
/*     */   }
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/* 258 */     return this.tsb.hasProperty(property);
/*     */   }
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/* 262 */     return this.tsb.getProperty(property);
/*     */   }
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 266 */     return this.tsb.isQualified(locale, country, vcr);
/*     */   }
/*     */   
/*     */   public String getSubject(LocaleInfo locale) {
/* 270 */     return this.tsb.getSubject(locale);
/*     */   }
/*     */   
/*     */   public SIOBlob getDocument(LocaleInfo locale) {
/* 274 */     return this.tsb.getDocument(locale);
/*     */   }
/*     */   
/*     */   public List getRelatedLUs() {
/* 278 */     return this.tsb.getRelatedLUs();
/*     */   }
/*     */   
/*     */   protected String makeList(List<E> attributes) {
/* 282 */     if (attributes == null || attributes.size() == 0) {
/* 283 */       return null;
/*     */     }
/* 285 */     StringBuffer result = new StringBuffer();
/* 286 */     for (int i = 0; i < attributes.size(); i++) {
/* 287 */       if (i > 0) {
/* 288 */         result.append(", ");
/*     */       }
/* 290 */       result.append(attributes.get(i).toString());
/*     */     } 
/* 292 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPublicationDate(String publicationDate) {
/* 299 */     SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
/*     */     try {
/* 301 */       this.pubdate = format.parse(publicationDate);
/* 302 */     } catch (ParseException e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRemedyNumber(String remedy) {
/* 310 */     this.remedy = remedy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDTCs(List dtcs) {
/* 317 */     this.dtcs = dtcs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSymptoms(List symptoms) {
/* 325 */     this.complaints = symptoms;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVCR(VCR vcr) {
/* 330 */     this.vcr = vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public SIOLU getSIOLU() {
/* 335 */     return this.tsb;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOTSBImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */