/*     */ package com.eoos.gm.tis2web.vc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCDomain;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRConstraint;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCRValue;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VCRConfigurationImpl
/*     */   implements VCRConfiguration
/*     */ {
/*     */   protected Integer config_id;
/*     */   protected ArrayList elements;
/*     */   protected ArrayList associations;
/*     */   protected ArrayList constraints;
/*     */   
/*     */   public Integer getConfigID() {
/*  23 */     return this.config_id;
/*     */   }
/*     */   
/*     */   public List getElements() {
/*  27 */     return this.elements;
/*     */   }
/*     */   
/*     */   public List getAssociations() {
/*  31 */     return this.associations;
/*     */   }
/*     */   
/*     */   public List getConstraints() {
/*  35 */     return this.constraints;
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  39 */     return toString();
/*     */   }
/*     */   
/*     */   public static String makeKey(VCRMake make, VCRModel model, VCRModelYear modelYear) {
/*  43 */     return make.getLabel() + " " + model.getLabel() + " " + modelYear.getLabel();
/*     */   }
/*     */   
/*     */   public VCRConfigurationImpl(int config_id) {
/*  47 */     this.elements = new ArrayList();
/*  48 */     this.config_id = Integer.valueOf(config_id);
/*     */   }
/*     */   
/*     */   public void setConfigID(int config_id) {
/*  52 */     this.config_id = Integer.valueOf(config_id);
/*     */   }
/*     */   
/*     */   public VCRConfigurationImpl() {
/*  56 */     this.elements = new ArrayList();
/*     */   }
/*     */   
/*     */   public void addElement(VCRValue value) {
/*  60 */     this.elements.add(value);
/*     */   }
/*     */   
/*     */   public boolean match(VCValue v) {
/*  64 */     for (int k = 0; k < this.elements.size(); k++) {
/*  65 */       VCRValue value = this.elements.get(k);
/*  66 */       if (value == v) {
/*  67 */         return true;
/*     */       }
/*     */     } 
/*  70 */     return false;
/*     */   }
/*     */   
/*     */   public void addAssociation(VCRValue value) {
/*  74 */     if (this.associations == null) {
/*  75 */       this.associations = new ArrayList();
/*     */     }
/*  77 */     this.associations.add(value);
/*     */   }
/*     */   
/*     */   public boolean matchAssociation(VCValue v) {
/*  81 */     if (this.associations == null) {
/*  82 */       return false;
/*     */     }
/*  84 */     for (int k = 0; k < this.associations.size(); k++) {
/*  85 */       VCRValue value = this.associations.get(k);
/*  86 */       if (value == v) {
/*  87 */         return true;
/*     */       }
/*     */     } 
/*  90 */     return false;
/*     */   }
/*     */   
/*     */   public void addConstraint(VCRConstraint constraint) {
/*  94 */     if (this.constraints == null) {
/*  95 */       this.constraints = new ArrayList();
/*     */     }
/*  97 */     this.constraints.add(constraint);
/*     */   }
/*     */   
/*     */   public boolean matchConstraint(VCRConstraint constraint) {
/* 101 */     if (this.constraints == null) {
/* 102 */       return false;
/*     */     }
/* 104 */     for (int k = 0; k < this.constraints.size(); k++) {
/* 105 */       VCRConstraint c = this.constraints.get(k);
/* 106 */       if (c.match(constraint)) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */   
/*     */   public boolean match(VCRConfiguration configuration) {
/* 114 */     for (int k = 0; k < this.elements.size(); k++) {
/* 115 */       VCRValueImpl value = this.elements.get(k);
/* 116 */       if (!configuration.match(value)) {
/* 117 */         return false;
/*     */       }
/*     */     } 
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public VCValue getElement(VCDomain domain) {
/* 124 */     for (int k = 0; k < this.elements.size(); k++) {
/* 125 */       VCRValueImpl value = this.elements.get(k);
/* 126 */       if (value.getDomainID().equals(domain.getDomainID())) {
/* 127 */         return value;
/*     */       }
/*     */     } 
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 134 */     StringBuffer label = new StringBuffer();
/* 135 */     for (int k = 0; k < this.elements.size(); k++) {
/* 136 */       VCRValue value = this.elements.get(k);
/* 137 */       if (label.length() > 0) {
/* 138 */         label.append(' ');
/*     */       }
/* 140 */       label.append(value);
/*     */     } 
/* 142 */     return label.toString();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 146 */     int ret = VCRConfigurationImpl.class.hashCode();
/* 147 */     ret = HashCalc.addHashCode(ret, this.config_id);
/* 148 */     return ret;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 152 */     if (this == obj)
/* 153 */       return true; 
/* 154 */     if (obj instanceof VCRConfigurationImpl) {
/* 155 */       return this.config_id.equals(((VCRConfigurationImpl)obj).config_id);
/*     */     }
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\implementation\io\db\VCRConfigurationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */