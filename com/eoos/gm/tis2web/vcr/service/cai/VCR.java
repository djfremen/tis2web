/*     */ package com.eoos.gm.tis2web.vcr.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface VCR
/*     */ {
/*  14 */   public static final VCR NULL = new VCR()
/*     */     {
/*     */       public ICRList getExpressions() {
/*  17 */         return null;
/*     */       }
/*     */       
/*     */       public int getID() {
/*  21 */         return -1;
/*     */       }
/*     */       
/*     */       public void add(VCRExpression expression) {
/*  25 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public void add(VCRTerm term) {
/*  29 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public void add(VCRAttribute attribute) {
/*  33 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public VCR fold(VCR vcr) {
/*  37 */         return vcr;
/*     */       }
/*     */ 
/*     */       
/*     */       public VCR append(VCR vcr) {
/*  42 */         return vcr;
/*     */       }
/*     */       
/*     */       public VCR union(VCR vcr) {
/*  46 */         return this;
/*     */       }
/*     */       
/*     */       public VCR intersect(VCR _vcr) {
/*  50 */         return _vcr;
/*     */       }
/*     */       
/*     */       public boolean match(LocaleInfo locale, VCR vcr) {
/*  54 */         return true;
/*     */       }
/*     */       
/*     */       public boolean match(LocaleInfo locale) {
/*  58 */         return true;
/*     */       }
/*     */       
/*     */       public boolean match(VCR vcr) {
/*  62 */         return true;
/*     */       }
/*     */       
/*     */       public boolean match(VCRExpression expression) {
/*  66 */         return true;
/*     */       }
/*     */       
/*     */       public boolean match(VCRAttribute attribute) {
/*  70 */         return true;
/*     */       }
/*     */       
/*     */       public boolean match(VCValue value) {
/*  74 */         return true;
/*     */       }
/*     */       
/*     */       public boolean equals(Object obj) {
/*  78 */         return (obj == this);
/*     */       }
/*     */ 
/*     */       
/*     */       public String toString() {
/*  83 */         return "[null-VCR]";
/*     */       }
/*     */       
/*     */       public boolean contradicts(VCR vcr, VCR negativeOptionVCR) {
/*  87 */         return false;
/*     */       }
/*     */       
/*     */       public VCR diff(VCR vcr) {
/*  91 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public VCR fold(VCRTerm arg0) {
/*  95 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public List getAttributes() {
/*  99 */         return Collections.EMPTY_LIST;
/*     */       }
/*     */       
/*     */       public List getTerms() {
/* 103 */         return Collections.EMPTY_LIST;
/*     */       }
/*     */       
/*     */       public boolean identical(VCR vcr) {
/* 107 */         return (this == vcr);
/*     */       }
/*     */       
/*     */       public boolean isReducedSuperset(VCR vcr, VCR superset) {
/* 111 */         return false;
/*     */       }
/*     */       
/*     */       public VCR join(VCR vcr) {
/* 115 */         return vcr;
/*     */       }
/*     */       
/*     */       public List listAttributes() {
/* 119 */         return Collections.EMPTY_LIST;
/*     */       }
/*     */       
/*     */       public boolean match(VCR vcr, VCR positiveOptionVCR) {
/* 123 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public boolean match(VCR vcr, VCR positiveOptionVCR, VCR negativeOptionVCR) {
/* 127 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public VCR merge(VCR vcr) {
/* 131 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public String getModelExpression() {
/* 135 */         throw new UnsupportedOperationException();
/*     */       }
/*     */       
/*     */       public Set getMakes() {
/* 139 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*     */   
/*     */   ICRList getExpressions();
/*     */   
/*     */   int getID();
/*     */   
/*     */   void add(VCRExpression paramVCRExpression);
/*     */   
/*     */   void add(VCRTerm paramVCRTerm);
/*     */   
/*     */   void add(VCRAttribute paramVCRAttribute);
/*     */   
/*     */   VCR fold(VCR paramVCR);
/*     */   
/*     */   VCR fold(VCRTerm paramVCRTerm);
/*     */   
/*     */   VCR merge(VCR paramVCR);
/*     */   
/*     */   VCR diff(VCR paramVCR);
/*     */   
/*     */   VCR union(VCR paramVCR);
/*     */   
/*     */   VCR intersect(VCR paramVCR);
/*     */   
/*     */   VCR join(VCR paramVCR);
/*     */   
/*     */   boolean match(LocaleInfo paramLocaleInfo, VCR paramVCR);
/*     */   
/*     */   boolean match(LocaleInfo paramLocaleInfo);
/*     */   
/*     */   List getAttributes();
/*     */   
/*     */   List listAttributes();
/*     */   
/*     */   List getTerms();
/*     */   
/*     */   boolean match(VCR paramVCR);
/*     */   
/*     */   boolean match(VCRExpression paramVCRExpression);
/*     */   
/*     */   boolean match(VCRAttribute paramVCRAttribute);
/*     */   
/*     */   boolean match(VCValue paramVCValue);
/*     */   
/*     */   boolean match(VCR paramVCR1, VCR paramVCR2);
/*     */   
/*     */   boolean contradicts(VCR paramVCR1, VCR paramVCR2);
/*     */   
/*     */   boolean match(VCR paramVCR1, VCR paramVCR2, VCR paramVCR3);
/*     */   
/*     */   boolean equals(Object paramObject);
/*     */   
/*     */   boolean identical(VCR paramVCR);
/*     */   
/*     */   String toString();
/*     */   
/*     */   boolean isReducedSuperset(VCR paramVCR1, VCR paramVCR2);
/*     */   
/*     */   String getModelExpression();
/*     */   
/*     */   Set getMakes();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\cai\VCR.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */