/*     */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.IVCRMapping;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRLabel;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VCRDomainImpl
/*     */   implements VCDomain, VCRDomain
/*     */ {
/*     */   protected Integer domain_id;
/*     */   protected VCRLabel domain_label;
/*     */   protected String domain_name;
/*     */   protected boolean language_dependent;
/*     */   protected HashMap values;
/*     */   protected HashMap associations;
/*     */   protected HashMap mappings;
/*     */   
/*     */   public Integer getDomainID() {
/*  35 */     return this.domain_id;
/*     */   }
/*     */   
/*     */   public VCRLabel getDomainLabel() {
/*  39 */     return this.domain_label;
/*     */   }
/*     */   
/*     */   public String getDomainName() {
/*  43 */     return this.domain_name;
/*     */   }
/*     */   
/*     */   public boolean isLanguageDependent() {
/*  47 */     return this.language_dependent;
/*     */   }
/*     */   
/*     */   public int getMemberCount() {
/*  51 */     return (this.values == null) ? 0 : this.values.size();
/*     */   }
/*     */   
/*     */   public Collection getValues() {
/*  55 */     return (this.values == null) ? null : this.values.values();
/*     */   }
/*     */   
/*     */   public VCRValue getValue(int value_id) {
/*  59 */     return getValue(Integer.valueOf(value_id));
/*     */   }
/*     */   
/*     */   public VCRValue getValue(Integer value_id) {
/*  63 */     return (this.values == null) ? null : (VCRValueImpl)this.values.get(value_id);
/*     */   }
/*     */   
/*     */   public VCRValue lookup(String key) {
/*  67 */     return lookup(null, key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VCRValue lookup(Integer locale, String key) {
/*  73 */     if (this.values == null || key == null) {
/*  74 */       return null;
/*     */     }
/*  76 */     Iterator<VCRValueImpl> it = getValues().iterator();
/*  77 */     while (it.hasNext()) {
/*  78 */       VCRValueImpl v = it.next();
/*  79 */       VCRLabel label = v.getLabel();
/*  80 */       if (label == null)
/*     */         continue; 
/*  82 */       if (label instanceof VCLocaleLabel) {
/*  83 */         Collection locales = LocaleInfoProvider.getInstance().getLocales();
/*  84 */         Iterator<LocaleInfo> iterator = locales.iterator();
/*  85 */         while (iterator.hasNext()) {
/*  86 */           LocaleInfo localeInfo = iterator.next();
/*  87 */           if (key.equalsIgnoreCase(label.getLabel(localeInfo.getLocaleID())))
/*  88 */             return v; 
/*     */         }  continue;
/*     */       } 
/*  91 */       if (key.equalsIgnoreCase(label.getLabel(locale))) {
/*  92 */         return v;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public VCRDomainImpl(int domain_id, String domain_name, VCRLabel domain_label, boolean language_dependent) {
/* 100 */     this.domain_id = Integer.valueOf(domain_id);
/* 101 */     this.domain_name = domain_name;
/* 102 */     this.domain_label = domain_label;
/* 103 */     this.language_dependent = language_dependent;
/*     */   }
/*     */   
/*     */   public void add(VCRValue value) {
/* 107 */     if (this.values == null) {
/* 108 */       this.values = new HashMap<Object, Object>();
/*     */     }
/* 110 */     this.values.put(value.getValueID(), value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(VCRValue value) {
/* 116 */     if (value == null || this.values.remove(value.getValueID()) == null) {
/* 117 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(IVCRMapping mapping) {
/* 122 */     if (mapping.getType() == 1) {
/* 123 */       if (this.associations == null) {
/* 124 */         this.associations = new HashMap<Object, Object>();
/*     */       }
/* 126 */       List<VCRValue> association = (List)this.associations.get(mapping.getValue());
/* 127 */       if (association == null) {
/* 128 */         association = new ArrayList();
/* 129 */         this.associations.put(mapping.getValue(), association);
/*     */       } 
/* 131 */       if (!association.contains(mapping.getMappedValue())) {
/* 132 */         association.add(mapping.getMappedValue());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 137 */       if (this.mappings == null) {
/* 138 */         this.mappings = new HashMap<Object, Object>();
/*     */       }
/* 140 */       this.mappings.put(mapping.getKey(), mapping);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean associated(VCValue value, VCValue mappedValue) {
/* 147 */     if (this.associations == null) {
/* 148 */       return false;
/*     */     }
/* 150 */     List association = (List)this.associations.get(value);
/* 151 */     return (association == null) ? false : association.contains(mappedValue);
/*     */   }
/*     */   
/*     */   public List getAssociations(VCValue value) {
/* 155 */     return (this.associations == null) ? null : (List)this.associations.get(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getAssociations(VCValue value, VCDomain domain) {
/* 161 */     List list = (this.associations == null) ? null : (List)this.associations.get(value);
/* 162 */     if (list == null) {
/* 163 */       return null;
/*     */     }
/* 165 */     List<VCRValue> result = new ArrayList();
/* 166 */     Iterator<VCRValue> it = list.iterator();
/* 167 */     while (it.hasNext()) {
/* 168 */       VCRValue item = it.next();
/* 169 */       if (item.getDomainID().equals(domain.getDomainID())) {
/* 170 */         result.add(item);
/*     */       }
/*     */     } 
/* 173 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCValue map(VCValue value, VCDomain domain) {
/* 180 */     if (this.mappings == null) {
/* 181 */       return null;
/*     */     }
/* 183 */     return (VCValue)this.mappings.get(VCRMapping.makeKey((VCRValue)value, (VCRDomain)domain));
/*     */   }
/*     */   
/*     */   public void remove(IVCRMapping mapping) {
/* 187 */     if (this.mappings == null || this.mappings.remove(mapping.getKey()) == null) {
/* 188 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */   
/*     */   public List getMappings(VCDomain domain) {
/* 193 */     if (this.mappings == null) {
/* 194 */       return null;
/*     */     }
/* 196 */     List<IVCRMapping> result = new ArrayList();
/* 197 */     Iterator<IVCRMapping> it = this.mappings.values().iterator();
/* 198 */     while (it.hasNext()) {
/* 199 */       IVCRMapping mapping = it.next();
/* 200 */       if (mapping.getMappedValue().getDomainID().equals(domain.getDomainID())) {
/* 201 */         result.add(mapping);
/*     */       }
/*     */     } 
/* 204 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRDomainImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */