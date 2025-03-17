/*     */ package com.eoos.gm.tis2web.vcr.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class VCRExpressionImpl
/*     */   implements VCRExpression {
/*  12 */   protected ArrayList terms = new ArrayList();
/*     */ 
/*     */   
/*     */   public VCRExpressionImpl() {}
/*     */   
/*     */   VCRExpressionImpl(VCRTerm term) {
/*  18 */     add(term);
/*     */   }
/*     */   
/*     */   VCRExpressionImpl(VCRAttribute attribute) {
/*  22 */     add(attribute);
/*     */   }
/*     */   
/*     */   public List getTerms() {
/*  26 */     return this.terms;
/*     */   }
/*     */   
/*     */   public void add(VCRAttributeImpl attribute) {
/*  30 */     add(new VCRTermImpl(attribute));
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(VCRTermImpl term) {
/*  35 */     for (int i = 0; i < this.terms.size(); i++) {
/*  36 */       VCRTermImpl t = this.terms.get(i);
/*  37 */       if (term.getDomainID() == t.getDomainID()) {
/*  38 */         throw new IllegalArgumentException();
/*     */       }
/*  40 */       if (t.gt(term)) {
/*  41 */         this.terms.add(i, term);
/*     */         return;
/*     */       } 
/*     */     } 
/*  45 */     this.terms.add(term);
/*     */   }
/*     */ 
/*     */   
/*     */   void merge(VCRTerm term) {
/*  50 */     for (int i = 0; i < this.terms.size(); i++) {
/*  51 */       VCRTermImpl t = this.terms.get(i);
/*  52 */       if (term.getDomainID() == t.getDomainID()) {
/*  53 */         t.merge(term);
/*     */         return;
/*     */       } 
/*     */     } 
/*  57 */     add(term);
/*     */   }
/*     */ 
/*     */   
/*     */   public VCRTerm getTerm(int domainID) {
/*  62 */     for (int i = 0; i < this.terms.size(); i++) {
/*  63 */       VCRTermImpl t = this.terms.get(i);
/*  64 */       if (domainID == t.getDomainID()) {
/*  65 */         return t;
/*     */       }
/*     */     } 
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public boolean gt(VCRExpression expression) {
/*  72 */     return (getOrderID() > ((VCRExpressionImpl)expression).getOrderID());
/*     */   }
/*     */   
/*     */   long getHashCode() {
/*  76 */     Hash h = null;
/*  77 */     int noTerms = this.terms.size();
/*  78 */     for (int i = 0; i < noTerms; i++) {
/*  79 */       if (h == null) {
/*  80 */         h = new Hash(((VCRTermImpl)this.terms.get(i)).getHashCode());
/*     */       } else {
/*  82 */         h = (new Hash(((VCRTermImpl)this.terms.get(i)).getHashCode())).sag(h);
/*     */       } 
/*     */     } 
/*  85 */     return (h == null) ? 0L : h.bag(1666666666661L);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  89 */     StringBuffer sb = new StringBuffer();
/*  90 */     int noTerms = this.terms.size();
/*  91 */     for (int i = 0; i < noTerms; i++) {
/*  92 */       if (i > 0) {
/*  93 */         sb.append(" and ");
/*     */       }
/*  95 */       sb.append(this.terms.get(i).toString());
/*     */     } 
/*  97 */     return sb.toString();
/*     */   }
/*     */   
/*     */   boolean scan(StringTokenizer st, String s) {
/* 101 */     boolean valid = false;
/*     */     
/*     */     while (true) {
/* 104 */       if (s.equals("]"))
/* 105 */         return valid; 
/* 106 */       if (s.equals("or"))
/* 107 */         return valid; 
/* 108 */       if (s.equals("and")) {
/* 109 */         valid = false;
/* 110 */       } else if (!s.equals(" ")) {
/*     */ 
/*     */         
/* 113 */         VCRTermImpl t = new VCRTermImpl();
/* 114 */         valid = t.scan(st, s);
/* 115 */         if (valid) {
/* 116 */           this.terms.add(t);
/*     */         }
/*     */       } 
/* 119 */       if (st.hasMoreTokens()) {
/* 120 */         s = st.nextToken();
/*     */       }
/* 122 */       if (!st.hasMoreTokens()) {
/* 123 */         return valid;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean identical(VCRExpressionImpl other) {
/* 130 */     int noTerms = this.terms.size();
/* 131 */     if (noTerms != other.getTerms().size()) {
/* 132 */       return false;
/*     */     }
/* 134 */     for (int i = 0; i < noTerms; i++) {
/* 135 */       if (!other.identicalTerm(this.terms.get(i))) {
/* 136 */         return false;
/*     */       }
/*     */     } 
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean isInvalid() {
/* 143 */     return (this.terms.size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean identicalTerm(VCRTermImpl t) {
/* 148 */     int noTerms = this.terms.size();
/* 149 */     for (int i = 0; i < noTerms; i++) {
/* 150 */       if (t.matchDomains(this.terms.get(i)) && 
/* 151 */         t.identical(this.terms.get(i))) {
/* 152 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   boolean matchDomains(VCRTermImpl t) {
/* 160 */     int noTerms = this.terms.size();
/* 161 */     for (int i = 0; i < noTerms; i++) {
/* 162 */       if (t.matchDomains(this.terms.get(i))) {
/* 163 */         return true;
/*     */       }
/*     */     } 
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   int matchDomain(VCRTermImpl t) {
/* 170 */     int noTerms = this.terms.size();
/* 171 */     for (int i = 0; i < noTerms; i++) {
/* 172 */       if (t.matchDomains(this.terms.get(i))) {
/* 173 */         return i;
/*     */       }
/*     */     } 
/* 176 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean matchAttribute(VCRAttribute a) {
/* 183 */     int noTerms = this.terms.size();
/* 184 */     for (int i = 0; i < noTerms; i++) {
/* 185 */       VCRTermImpl term = this.terms.get(i);
/* 186 */       if (term.getDomainID() == a.getDomainID()) {
/* 187 */         return term.matchAttribute(a);
/*     */       }
/*     */     } 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   VCRExpressionImpl commonTerms(VCRExpressionImpl other) {
/* 196 */     VCRExpressionImpl c = new VCRExpressionImpl();
/* 197 */     int noTerms = this.terms.size();
/* 198 */     for (int i = 0; i < noTerms; i++) {
/* 199 */       if (other.matchDomains(this.terms.get(i))) {
/* 200 */         c.add(this.terms.get(i));
/*     */       }
/*     */     } 
/* 203 */     return c;
/*     */   }
/*     */   
/*     */   boolean subsetTerm(VCRTermImpl t) {
/* 207 */     int noTerms = this.terms.size();
/* 208 */     for (int i = 0; i < noTerms; i++) {
/* 209 */       if (t.matchDomains(this.terms.get(i)) && 
/* 210 */         t.subset(this.terms.get(i))) {
/* 211 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isSubset(VCRExpressionImpl other) {
/* 220 */     return other.isSuperset(this);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isSuperset(VCRExpressionImpl other) {
/* 225 */     int noTerms = this.terms.size();
/* 226 */     for (int i = 0; i < noTerms; i++) {
/*     */       
/* 228 */       if (!other.subsetTerm(this.terms.get(i))) {
/* 229 */         return false;
/*     */       }
/*     */     } 
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean match(VCRExpression other) {
/* 239 */     int noTerms = this.terms.size();
/* 240 */     VCRTerm term = null;
/*     */     
/* 242 */     for (int i = 0; i < noTerms; i++) {
/* 243 */       term = this.terms.get(i);
/* 244 */       if (((VCRExpressionImpl)other).contradictedBy(term)) {
/* 245 */         return false;
/*     */       }
/*     */     } 
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean contradictedBy(VCRTerm t) {
/* 253 */     int noTerms = this.terms.size();
/* 254 */     VCRTermImpl term = null;
/*     */     
/* 256 */     for (int i = 0; i < noTerms; i++) {
/* 257 */       term = this.terms.get(i);
/* 258 */       if (t.identical(term)) {
/* 259 */         return true;
/*     */       }
/*     */     } 
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   VCRExpression intersect(VCRExpressionImpl other) {
/* 267 */     VCRExpressionImpl commonA = commonTerms(other);
/* 268 */     VCRExpressionImpl commonB = other.commonTerms(this);
/* 269 */     if (commonA.getTerms().size() == 0 || commonB.getTerms().size() == 0) {
/* 270 */       return null;
/*     */     }
/* 272 */     VCRExpression intersection = new VCRExpressionImpl();
/* 273 */     VCRTermImpl a = null;
/* 274 */     VCRTermImpl b = null;
/* 275 */     VCRTermImpl x = null;
/* 276 */     int noTerms = commonA.getTerms().size(); int i;
/* 277 */     for (i = 0; i < noTerms; i++) {
/* 278 */       a = commonA.getTerms().get(i);
/* 279 */       b = commonB.getTerms().get(commonB.matchDomain(a));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       x = a.intersect(b);
/* 285 */       if (x == null) {
/* 286 */         intersection = null;
/* 287 */         return null;
/*     */       } 
/* 289 */       intersection.add(x);
/* 290 */       x = null;
/*     */       
/* 292 */       a = null;
/* 293 */       b = null;
/*     */     } 
/* 295 */     if (commonA.getTerms().size() < this.terms.size()) {
/* 296 */       noTerms = getTerms().size();
/* 297 */       for (i = 0; i < noTerms; i++) {
/* 298 */         x = getTerms().get(i);
/* 299 */         if (!commonA.matchDomains(x))
/* 300 */           intersection.add(x); 
/* 301 */         x = null;
/*     */       } 
/*     */     } 
/* 304 */     if (other.getTerms().size() > commonB.getTerms().size()) {
/* 305 */       noTerms = other.getTerms().size();
/* 306 */       for (i = 0; i < noTerms; i++) {
/* 307 */         x = other.getTerms().get(i);
/* 308 */         if (!commonB.matchDomains(x))
/* 309 */           intersection.add(x); 
/* 310 */         x = null;
/*     */       } 
/*     */     } 
/* 313 */     return intersection;
/*     */   }
/*     */ 
/*     */   
/*     */   public VCRExpression copy() {
/* 318 */     VCRExpressionImpl clone = new VCRExpressionImpl();
/* 319 */     int noTerms = this.terms.size();
/* 320 */     for (int i = 0; i < noTerms; i++) {
/* 321 */       VCRTerm term = this.terms.get(i);
/* 322 */       clone.add(term.copy());
/*     */     } 
/* 324 */     return clone;
/*     */   }
/*     */   
/*     */   protected int getOrderID() {
/* 328 */     VCRTerm term = (this.terms.size() == 0) ? null : this.terms.get(0);
/* 329 */     return (term == null) ? 0 : term.getDomainID();
/*     */   }
/*     */   
/*     */   public void add(VCRAttribute attribute) {
/* 333 */     add((VCRAttributeImpl)attribute);
/*     */   }
/*     */   
/*     */   public void add(VCRTerm term) {
/* 337 */     add((VCRTermImpl)term);
/*     */   }
/*     */   
/*     */   public boolean identical(VCRExpression other) {
/* 341 */     return identical((VCRExpressionImpl)other);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VCRExpressionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */