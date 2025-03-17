/*     */ package com.eoos.gm.tis2web.sids.implementation;
/*     */ 
/*     */ import com.eoos.datatype.gtwo.Pair;
/*     */ import com.eoos.datatype.gtwo.PairImpl;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class VehicleQualification
/*     */ {
/*     */   protected Integer id;
/*     */   protected List qualifiers;
/*     */   
/*     */   public Integer getID() {
/*  16 */     return this.id;
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/*  20 */     List<VehicleAttribute> attributes = new ArrayList();
/*  21 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/*  22 */       VehicleAttribute attribute = (VehicleAttribute)((Pair)this.qualifiers.get(i)).getFirst();
/*  23 */       attributes.add(attribute);
/*     */     } 
/*  25 */     return attributes;
/*     */   }
/*     */   
/*     */   public VehicleQualification(Integer id) {
/*  29 */     this.id = id;
/*  30 */     this.qualifiers = new ArrayList();
/*     */   }
/*     */   
/*     */   public void add(VehicleAttribute attribute, VehicleValue value) {
/*  34 */     if (hasAttribute(attribute)) {
/*  35 */       throw new DataModelException("duplicate vehicle attribute (add_veh_id=" + this.id + ",attr=" + attribute.getID() + ")");
/*     */     }
/*  37 */     this.qualifiers.add(new PairImpl(attribute, value));
/*     */   }
/*     */   
/*     */   protected boolean hasAttribute(VehicleAttribute attribute) {
/*  41 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/*  42 */       VehicleAttribute a = (VehicleAttribute)((Pair)this.qualifiers.get(i)).getFirst();
/*  43 */       if (attribute.getID().equals(a.getID())) {
/*  44 */         return true;
/*     */       }
/*     */     } 
/*  47 */     return false;
/*     */   }
/*     */   
/*     */   public boolean match(VehicleAttribute attribute, Integer valueID) {
/*  51 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/*  52 */       VehicleAttribute a = (VehicleAttribute)((Pair)this.qualifiers.get(i)).getFirst();
/*  53 */       if (attribute.getID().equals(a.getID())) {
/*  54 */         VehicleValue v = (VehicleValue)((Pair)this.qualifiers.get(i)).getSecond();
/*  55 */         return valueID.equals(v.getID());
/*     */       } 
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public VehicleValue getValue(VehicleAttribute attribute) {
/*  62 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/*  63 */       VehicleAttribute a = (VehicleAttribute)((Pair)this.qualifiers.get(i)).getFirst();
/*  64 */       if (attribute.getID().equals(a.getID())) {
/*  65 */         return (VehicleValue)((Pair)this.qualifiers.get(i)).getSecond();
/*     */       }
/*     */     } 
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  72 */     if (this == obj)
/*  73 */       return true; 
/*  74 */     if (obj == null)
/*  75 */       return false; 
/*  76 */     if (getClass() != obj.getClass())
/*  77 */       return false; 
/*  78 */     VehicleQualification other = (VehicleQualification)obj;
/*  79 */     if (this.qualifiers == null) {
/*  80 */       if (other.qualifiers != null)
/*  81 */         return false; 
/*  82 */     } else if (!equal(this.qualifiers, other.qualifiers)) {
/*  83 */       return false;
/*  84 */     }  return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  89 */     int ret = VehicleQualification.class.hashCode();
/*  90 */     ret = HashCalc.addHashCode(ret, this.qualifiers);
/*  91 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean equal(List<Pair> qualifiersA, List qualifiersB) {
/*  95 */     if (qualifiersA.size() < qualifiersB.size()) {
/*  96 */       return equal(qualifiersB, qualifiersA);
/*     */     }
/*  98 */     for (int i = 0; i < qualifiersA.size(); i++) {
/*  99 */       VehicleAttribute attributeA = (VehicleAttribute)((Pair)qualifiersA.get(i)).getFirst();
/* 100 */       VehicleValue valueA = (VehicleValue)((Pair)qualifiersA.get(i)).getSecond();
/* 101 */       VehicleValue valueB = lookupValue(qualifiersB, attributeA);
/* 102 */       if (valueB == null)
/* 103 */         return false; 
/* 104 */       if (valueA != valueB) {
/* 105 */         return false;
/*     */       }
/*     */     } 
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   private VehicleValue lookupValue(List<Pair> qualifiers, VehicleAttribute attribute) {
/* 112 */     for (int i = 0; i < qualifiers.size(); i++) {
/* 113 */       VehicleAttribute a = (VehicleAttribute)((Pair)qualifiers.get(i)).getFirst();
/* 114 */       if (attribute.getID().equals(a.getID())) {
/* 115 */         return (VehicleValue)((Pair)qualifiers.get(i)).getSecond();
/*     */       }
/*     */     } 
/* 118 */     return null;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 122 */     StringBuffer buffer = new StringBuffer();
/* 123 */     for (int i = 0; i < this.qualifiers.size(); i++) {
/* 124 */       VehicleAttribute a = (VehicleAttribute)((Pair)this.qualifiers.get(i)).getFirst();
/* 125 */       VehicleValue v = (VehicleValue)((Pair)this.qualifiers.get(i)).getSecond();
/* 126 */       if (buffer.length() > 0) {
/* 127 */         buffer.append('+');
/*     */       }
/* 129 */       buffer.append("[" + a + '=' + v + ']');
/*     */     } 
/* 131 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sids\implementation\VehicleQualification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */