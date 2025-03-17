/*     */ package com.eoos.gm.tis2web.vc.implementation.io.vin;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRvin;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VINStructure;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VINStructureImpl
/*     */   implements VINStructure
/*     */ {
/*     */   protected Integer structure_id;
/*     */   protected VCValue make;
/*     */   protected VCValue modelYear;
/*     */   protected VCValue wmi;
/*     */   protected String filterVIN;
/*     */   protected ArrayList elements;
/*     */   
/*     */   public Integer getStructureID() {
/*  25 */     return this.structure_id;
/*     */   }
/*     */   
/*     */   public VCValue getMake() {
/*  29 */     return this.make;
/*     */   }
/*     */   
/*     */   public VCValue getModelYear() {
/*  33 */     return this.modelYear;
/*     */   }
/*     */   
/*     */   public VCValue getWMI() {
/*  37 */     return this.wmi;
/*     */   }
/*     */   
/*     */   public String getFilterVIN() {
/*  41 */     return this.filterVIN;
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  45 */     return makeKey(this.make, this.modelYear, this.wmi);
/*     */   }
/*     */   
/*     */   public String getFilterKey() {
/*  49 */     return makeFilterKey(this.make, this.modelYear, this.wmi, this.filterVIN);
/*     */   }
/*     */   
/*     */   public List getElements() {
/*  53 */     return this.elements;
/*     */   }
/*     */   
/*     */   public VINStructure.VINStructureElement getElement(VCDomain attribute) {
/*  57 */     for (int i = 0; i < this.elements.size(); i++) {
/*  58 */       VINStructureElementImpl element = this.elements.get(i);
/*  59 */       if (element.domain == attribute) {
/*  60 */         return element;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */   public void setStructureID(Integer structure_id) {
/*  67 */     this.structure_id = structure_id;
/*     */   }
/*     */   
/*     */   public VINStructureImpl(Integer structure_id, VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/*  71 */     this(make, modelYear, wmi, filterVIN);
/*  72 */     this.structure_id = structure_id;
/*     */   }
/*     */   
/*     */   public VINStructureImpl(VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/*  76 */     this.make = make;
/*  77 */     this.modelYear = modelYear;
/*  78 */     this.wmi = wmi;
/*  79 */     this.filterVIN = (filterVIN == null) ? "#" : filterVIN;
/*  80 */     this.elements = new ArrayList();
/*     */   }
/*     */   
/*     */   public static String makeKey(VCValue make, VCValue modelYear, VCValue wmi) {
/*  84 */     return make.toString() + ((modelYear == null) ? "*" : modelYear.toString()) + wmi.toString();
/*     */   }
/*     */   
/*     */   public static String makeFilterKey(VCValue make, VCValue modelYear, VCValue wmi, String filterVIN) {
/*  88 */     return makeKey(make, modelYear, wmi) + ((filterVIN == null) ? "#" : filterVIN);
/*     */   }
/*     */   
/*     */   public boolean match(VCValue make, VCValue modelYear, VCValue wmi) {
/*  92 */     return (this.make == make && this.modelYear == modelYear && this.wmi == wmi);
/*     */   }
/*     */   
/*     */   public boolean match(VCValue modelYear, VCValue wmi, String vin) {
/*  96 */     if (this.modelYear == modelYear && this.wmi == wmi) {
/*  97 */       if (this.filterVIN.equals("#")) {
/*  98 */         return true;
/*     */       }
/* 100 */       vin = vin.substring(3);
/* 101 */       for (int i = 0, j = 0; i < vin.length() && j < this.filterVIN.length(); i++) {
/* 102 */         char c = vin.charAt(i);
/* 103 */         char t = this.filterVIN.charAt(j);
/* 104 */         if (t == '?') {
/* 105 */           j++;
/* 106 */         } else if (t == '*') {
/* 107 */           if (j == this.filterVIN.length() - 1) {
/* 108 */             return true;
/*     */           }
/* 110 */           t = this.filterVIN.charAt(++j);
/* 111 */           boolean found = false;
/* 112 */           for (; i < vin.length(); i++) {
/* 113 */             c = vin.charAt(i);
/* 114 */             if (c == t) {
/* 115 */               found = true;
/*     */             }
/*     */           } 
/* 118 */           if (!found) {
/* 119 */             return false;
/*     */           }
/*     */         } else {
/* 122 */           if (c != t) {
/* 123 */             return false;
/*     */           }
/* 125 */           j++;
/*     */         } 
/*     */       } 
/* 128 */       return true;
/*     */     } 
/*     */     
/* 131 */     return false;
/*     */   }
/*     */   
/*     */   public void add(VCDomain domain, int from, int to) {
/* 135 */     this.elements.add(new VINStructureElementImpl(domain, from, to));
/*     */   }
/*     */   
/*     */   public void update(VCDomain domain, int from, int to) {
/* 139 */     for (int i = 0; i < this.elements.size(); i++) {
/* 140 */       VINStructureElementImpl element = this.elements.get(i);
/* 141 */       if (element.domain == domain) {
/* 142 */         element.from = from;
/* 143 */         element.to = to;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(VCRvin pattern) {
/* 150 */     for (int i = 0; i < this.elements.size(); i++) {
/* 151 */       VINStructure.VINStructureElement element = this.elements.get(i);
/* 152 */       if (element.getDomain().getDomainID().equals(pattern.getValue().getDomainID())) {
/* 153 */         List<VCRvin> patterns = element.getPatterns();
/* 154 */         for (int p = 0; p < patterns.size(); p++) {
/* 155 */           VCRvin instance = patterns.get(p);
/* 156 */           if (pattern.equals(instance)) {
/* 157 */             return false;
/*     */           }
/*     */         } 
/* 160 */         element.add(pattern);
/* 161 */         return true;
/*     */       } 
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public ArrayList getPatterns() {
/* 168 */     ArrayList patterns = new ArrayList();
/* 169 */     for (int i = 0; i < this.elements.size(); i++) {
/* 170 */       VINStructure.VINStructureElement element = this.elements.get(i);
/* 171 */       patterns.addAll(element.getPatterns());
/*     */     } 
/* 173 */     return patterns;
/*     */   }
/*     */   
/*     */   public VCValue match(String vin, VCDomain domain) {
/* 177 */     for (int i = 0; i < this.elements.size(); i++) {
/* 178 */       VINStructure.VINStructureElement element = this.elements.get(i);
/* 179 */       if (element.getDomain().getDomainID().equals(domain.getDomainID())) {
/* 180 */         List<VCRvin> patterns = element.getPatterns();
/* 181 */         for (int p = 0; p < patterns.size(); p++) {
/* 182 */           VCRvin instance = patterns.get(p);
/* 183 */           if (instance.match(vin, element.getFromPosition())) {
/* 184 */             return instance.getValue();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public class VINStructureElementImpl
/*     */     implements VINStructure.VINStructureElement
/*     */   {
/*     */     protected VCDomain domain;
/*     */     
/*     */     protected int from;
/*     */     
/*     */     protected int to;
/*     */     
/*     */     protected ArrayList patterns;
/*     */     
/*     */     public VCDomain getDomain() {
/* 205 */       return this.domain;
/*     */     }
/*     */     
/*     */     public int getFromPosition() {
/* 209 */       return this.from;
/*     */     }
/*     */     
/*     */     public int getToPosition() {
/* 213 */       return this.to;
/*     */     }
/*     */     
/*     */     public List getPatterns() {
/* 217 */       return this.patterns;
/*     */     }
/*     */     
/*     */     public void add(VCRvin pattern) {
/* 221 */       this.patterns.add(pattern);
/*     */     }
/*     */     
/*     */     VINStructureElementImpl(VCDomain domain, int from, int to) {
/* 225 */       this.domain = domain;
/* 226 */       this.from = from;
/* 227 */       this.to = to;
/* 228 */       this.patterns = new ArrayList();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\vin\VINStructureImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */