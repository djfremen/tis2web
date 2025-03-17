/*     */ package com.eoos.gm.tis2web.vcr.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class VCRTermImpl
/*     */   implements VCRTerm {
/*  11 */   protected ArrayList attributes = new ArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   public VCRTermImpl() {}
/*     */ 
/*     */   
/*     */   public VCRTermImpl(VCRAttribute attribute) {
/*  19 */     add(attribute);
/*     */   }
/*     */   
/*     */   public List getAttributes() {
/*  23 */     return this.attributes;
/*     */   }
/*     */   
/*     */   public void add(VCRAttributeImpl attribute) {
/*  27 */     if (this.attributes.size() > 0 && attribute.getDomainID() != getDomainID()) {
/*  28 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  31 */     for (int i = 0; i < this.attributes.size(); i++) {
/*  32 */       VCRAttributeImpl a = this.attributes.get(i);
/*  33 */       if (attribute.match(a)) {
/*     */         return;
/*     */       }
/*     */       
/*  37 */       if (a.gt(attribute)) {
/*  38 */         this.attributes.add(i, attribute);
/*     */         return;
/*     */       } 
/*     */     } 
/*  42 */     this.attributes.add(attribute);
/*     */   }
/*     */   
/*     */   public boolean match(VCRAttributeImpl a) {
/*  46 */     return matchAttribute(a);
/*     */   }
/*     */   
/*     */   void merge(VCRTerm term) {
/*  50 */     for (int i = 0; i < term.getAttributes().size(); i++) {
/*  51 */       add(term.getAttributes().get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   boolean gt(VCRTerm term) {
/*  56 */     return (getDomainID() > term.getDomainID());
/*     */   }
/*     */   
/*     */   public int getDomainID() {
/*  60 */     VCRAttribute attribute = (this.attributes.size() == 0) ? null : this.attributes.get(0);
/*  61 */     return (attribute == null) ? 0 : attribute.getDomainID();
/*     */   }
/*     */   
/*     */   long getHashCode() {
/*  65 */     Hash h = null;
/*  66 */     int noAttributes = this.attributes.size();
/*  67 */     for (int i = 0; i < noAttributes; i++) {
/*  68 */       if (h == null) {
/*  69 */         h = new Hash(((VCRAttributeImpl)this.attributes.get(i)).getHashCode());
/*     */       } else {
/*  71 */         h = (new Hash(((VCRAttributeImpl)this.attributes.get(i)).getHashCode())).sag(h);
/*     */       } 
/*     */     } 
/*  74 */     return (h == null) ? 0L : h.bag(1666666666661L);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  78 */     StringBuffer sb = new StringBuffer();
/*  79 */     int noAttributes = this.attributes.size();
/*  80 */     if (noAttributes > 1)
/*  81 */       sb.append("("); 
/*  82 */     for (int i = 0; i < noAttributes; i++) {
/*  83 */       if (i > 0) {
/*  84 */         sb.append(" or ");
/*     */       }
/*  86 */       sb.append(this.attributes.get(i).toString());
/*     */     } 
/*  88 */     if (noAttributes > 1)
/*  89 */       sb.append(")"); 
/*  90 */     return sb.toString();
/*     */   }
/*     */   
/*     */   boolean scan(StringTokenizer st, String s) {
/*  94 */     boolean valid = false;
/*     */     
/*     */     while (true) {
/*  97 */       if (s.equals("("))
/*  98 */       { if (st.hasMoreTokens())
/*  99 */           s = st.nextToken(); 
/* 100 */         VCRAttributeImpl a = new VCRAttributeImpl();
/* 101 */         valid = a.scan(st, s);
/* 102 */         if (valid) {
/* 103 */           this.attributes.add(a);
/*     */         } }
/* 105 */       else if (s.equals("or"))
/* 106 */       { valid = false; }
/* 107 */       else { if (s.equals("and"))
/* 108 */           return valid; 
/* 109 */         if (s.equals(")"))
/* 110 */           return valid; 
/* 111 */         if (s.equals("]"))
/* 112 */           return valid; 
/* 113 */         if (!s.equals(" ")) {
/*     */ 
/*     */           
/* 116 */           VCRAttributeImpl a = new VCRAttributeImpl();
/* 117 */           valid = a.scan(st, s);
/* 118 */           if (valid)
/* 119 */             add(a); 
/*     */         }  }
/*     */       
/* 122 */       if (st.hasMoreTokens()) {
/* 123 */         s = st.nextToken();
/*     */       }
/* 125 */       if (!st.hasMoreTokens()) {
/* 126 */         return valid;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean identical(VCRTerm other) {
/* 133 */     int noAttributes = this.attributes.size();
/* 134 */     if (noAttributes != other.getAttributes().size()) {
/* 135 */       return false;
/*     */     }
/* 137 */     for (int i = 0; i < noAttributes; i++) {
/* 138 */       if (!((VCRTermImpl)other).matchAttribute(this.attributes.get(i))) {
/* 139 */         return false;
/*     */       }
/*     */     } 
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInvalid() {
/* 147 */     return (this.attributes.size() == 0);
/*     */   }
/*     */   
/*     */   protected boolean matchDomains(VCRTermImpl t) {
/* 151 */     if (isInvalid()) {
/* 152 */       return false;
/*     */     }
/* 154 */     return t.matchDomain(this.attributes.get(0));
/*     */   }
/*     */   
/*     */   protected boolean matchDomain(VCRAttributeImpl a) {
/* 158 */     if (isInvalid())
/* 159 */       return false; 
/* 160 */     return a.matchDomain(this.attributes.get(0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean matchAttribute(VCRAttribute a) {
/* 166 */     int noAttributes = this.attributes.size();
/*     */     
/* 168 */     for (int i = 0; i < noAttributes; i++) {
/* 169 */       VCRAttribute b = this.attributes.get(i);
/* 170 */       if (a.match(b)) {
/* 171 */         return true;
/*     */       }
/*     */     } 
/* 174 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   VCRTerm diffTerm(VCRTerm t) {
/* 179 */     VCRTerm diff = new VCRTermImpl();
/* 180 */     int noAttributes = this.attributes.size();
/* 181 */     VCRAttributeImpl aAttribute = null;
/* 182 */     VCRAttributeImpl bAttribute = null;
/* 183 */     for (int k = 0; k < noAttributes; k++) {
/* 184 */       bAttribute = this.attributes.get(k);
/* 185 */       for (int j = 0; j < t.getAttributes().size(); j++) {
/* 186 */         aAttribute = t.getAttributes().get(j);
/* 187 */         if (bAttribute.match(aAttribute)) {
/* 188 */           bAttribute = null;
/*     */           break;
/*     */         } 
/*     */       } 
/* 192 */       if (bAttribute != null) {
/* 193 */         diff.add(bAttribute);
/*     */       }
/*     */     } 
/* 196 */     return diff;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean subset(VCRTermImpl other) {
/* 201 */     return other.superset(this);
/*     */   }
/*     */   
/*     */   boolean superset(VCRTermImpl other) {
/* 205 */     int noAttributes = this.attributes.size();
/* 206 */     for (int i = 0; i < noAttributes; i++) {
/* 207 */       if (!other.matchAttribute(this.attributes.get(i)))
/* 208 */         return false; 
/*     */     } 
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public VCRTermImpl intersect(VCRTermImpl other) {
/* 215 */     VCRTermImpl intersection = null;
/* 216 */     int noAttributes = this.attributes.size();
/* 217 */     for (int i = 0; i < noAttributes; i++) {
/* 218 */       if (other.matchAttribute(this.attributes.get(i))) {
/* 219 */         if (intersection == null)
/* 220 */           intersection = new VCRTermImpl(); 
/* 221 */         intersection.add(this.attributes.get(i));
/*     */       } 
/*     */     } 
/* 224 */     return intersection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCRTerm copy() {
/* 231 */     VCRTermImpl clone = new VCRTermImpl();
/* 232 */     int noAttributes = this.attributes.size();
/* 233 */     for (int i = 0; i < noAttributes; i++) {
/* 234 */       VCRAttributeImpl attribute = this.attributes.get(i);
/* 235 */       clone.add(attribute);
/*     */     } 
/* 237 */     return clone;
/*     */   }
/*     */   
/*     */   public void add(VCRAttribute attribute) {
/* 241 */     add((VCRAttributeImpl)attribute);
/*     */   }
/*     */   
/*     */   public boolean match(VCRAttribute a) {
/* 245 */     return match((VCRAttributeImpl)a);
/*     */   }
/*     */   
/*     */   public VCRTerm intersect(VCRTerm other) {
/* 249 */     return intersect((VCRTermImpl)other);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VCRTermImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */