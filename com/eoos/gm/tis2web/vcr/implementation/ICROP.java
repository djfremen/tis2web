/*     */ package com.eoos.gm.tis2web.vcr.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ public class ICROP
/*     */ {
/*     */   public static VCR union(ICRListImpl expressionsA, ICRListImpl expressionsB, VCRFactory factory) {
/*  26 */     if (expressionsA == null && expressionsB == null) {
/*  27 */       return VCR.NULL;
/*     */     }
/*  29 */     return new VCRImpl(concat(expressionsA, expressionsB), factory);
/*     */   }
/*     */   
/*     */   static ICRListImpl concat(ICRListImpl expressionsA, ICRListImpl expressionsB) {
/*  33 */     if (expressionsB == null || (expressionsA != null && expressionsB.isSubset(expressionsA))) {
/*  34 */       return copy(expressionsA);
/*     */     }
/*  36 */     if (expressionsA == null || (expressionsB != null && expressionsA.isSubset(expressionsB))) {
/*  37 */       return copy(expressionsB);
/*     */     }
/*  39 */     ICRListImpl expressions = copy(expressionsA);
/*  40 */     int noblocks = expressionsB.size();
/*  41 */     for (int j = 0; j < noblocks; j++) {
/*  42 */       VCRExpression expressionB = (VCRExpression)expressionsB.get(j);
/*  43 */       expressions.add((E)expressionB.copy());
/*     */     } 
/*  45 */     simplify(expressions);
/*  46 */     return expressions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static VCR intersect(ICRListImpl expressionsA, ICRListImpl expressionsB, VCRFactory factory) {
/*  58 */     if (expressionsA == null) {
/*  59 */       if (expressionsB == null) {
/*  60 */         return VCR.NULL;
/*     */       }
/*  62 */       return new VCRImpl(copy(expressionsB), factory);
/*     */     } 
/*  64 */     if (expressionsB == null) {
/*  65 */       return new VCRImpl(copy(expressionsA), factory);
/*     */     }
/*  67 */     ICRListImpl intersection = null;
/*  68 */     ICRListImpl resultVCR = null;
/*  69 */     int noExpressions = expressionsA.size();
/*  70 */     for (int i = 0; i < noExpressions; i++) {
/*  71 */       intersection = expressionsB.intersectExpression((VCRExpressionImpl)expressionsA.get(i));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       if (intersection != null) {
/*  81 */         if (resultVCR == null) {
/*  82 */           resultVCR = intersection;
/*     */         } else {
/*  84 */           resultVCR = concat(resultVCR, intersection);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  93 */         intersection = null;
/*     */       } 
/*     */     } 
/*  96 */     return (resultVCR == null) ? VCR.NULL : new VCRImpl(resultVCR, factory);
/*     */   }
/*     */ 
/*     */   
/*     */   static ICRListImpl copy(ICRListImpl expressions) {
/* 101 */     ICRListImpl clone = new ICRListImpl();
/* 102 */     int noblocks = expressions.size();
/* 103 */     for (int j = 0; j < noblocks; j++) {
/* 104 */       VCRExpression expression = (VCRExpression)expressions.get(j);
/* 105 */       clone.add((E)expression.copy());
/*     */     } 
/* 107 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void simplify(ICRListImpl expressions) {
/* 115 */     int noExpressions = expressions.size();
/* 116 */     VCRExpression simplified = null;
/* 117 */     VCRExpressionImpl a = null;
/* 118 */     VCRExpressionImpl b = null;
/* 119 */     for (int i = 0; i < noExpressions; i++) {
/* 120 */       a = (VCRExpressionImpl)expressions.get(i);
/* 121 */       for (int j = i + 1; j < noExpressions; j++) {
/* 122 */         b = (VCRExpressionImpl)expressions.get(j);
/* 123 */         simplified = simplify(a, b);
/* 124 */         if (simplified == null) {
/* 125 */           simplified = simplify(b, a);
/*     */         }
/* 127 */         if (simplified != null) {
/* 128 */           expressions.remove(a);
/* 129 */           expressions.remove(b);
/* 130 */           expressions.add((E)simplified);
/* 131 */           simplify(expressions);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static VCRExpression simplify(VCRExpressionImpl a, VCRExpressionImpl b) {
/* 139 */     VCRExpression commonA = a.commonTerms(b);
/* 140 */     VCRExpressionImpl commonB = b.commonTerms(a);
/* 141 */     if (commonA.getTerms().size() == 0 || commonB.getTerms().size() == 0)
/* 142 */       return null; 
/* 143 */     if (a.getTerms().size() == commonA.getTerms().size()) {
/* 144 */       if (b.getTerms().size() == commonB.getTerms().size()) {
/*     */ 
/*     */ 
/*     */         
/* 148 */         if (a.isSubset(b))
/* 149 */           return b; 
/* 150 */         if (b.isSubset(a))
/* 151 */           return a; 
/* 152 */         if (a.getTerms().size() == 1) {
/*     */           
/* 154 */           VCRExpressionImpl simplified = new VCRExpressionImpl();
/* 155 */           simplified.merge(a.getTerms().get(0));
/* 156 */           simplified.merge(b.getTerms().get(0));
/* 157 */           return simplified;
/* 158 */         }  if (a.getTerms().size() == 2) {
/*     */ 
/*     */ 
/*     */           
/* 162 */           VCRExpressionImpl simplified = new VCRExpressionImpl();
/*     */ 
/*     */           
/* 165 */           VCRTermImpl a0 = a.getTerms().get(0);
/* 166 */           VCRTermImpl a1 = a.getTerms().get(1);
/* 167 */           VCRTermImpl b0 = b.getTerms().get(0);
/* 168 */           VCRTermImpl b1 = b.getTerms().get(1);
/* 169 */           if (a0.matchDomains(b1)) {
/* 170 */             a0 = a1;
/* 171 */             a1 = a.getTerms().get(0);
/*     */           } 
/* 173 */           if (a0.identical(b0)) {
/* 174 */             simplified.merge(b0);
/* 175 */             simplified.merge(a1);
/* 176 */             simplified.merge(b1);
/* 177 */             return simplified;
/*     */           } 
/* 179 */           if (a1.identical(b1)) {
/* 180 */             simplified.merge(b1);
/* 181 */             simplified.merge(a0);
/* 182 */             simplified.merge(b0);
/* 183 */             return simplified;
/*     */           } 
/* 185 */           if (a1.subset(b1)) {
/*     */ 
/*     */             
/* 188 */             simplified = null;
/* 189 */             b.getTerms().add(b0.diffTerm(a0));
/* 190 */             b.getTerms().remove(b0);
/* 191 */             return null;
/* 192 */           }  if (b1.subset(a1)) {
/* 193 */             simplified = null;
/* 194 */             a.getTerms().add(a0.diffTerm(b0));
/*     */             
/* 196 */             a.getTerms().remove(a0);
/* 197 */             return null;
/* 198 */           }  if (a0.subset(b0)) {
/* 199 */             simplified = null;
/* 200 */             b.getTerms().add(b1.diffTerm(a1));
/* 201 */             b.getTerms().remove(b1);
/* 202 */             return null;
/* 203 */           }  if (b0.subset(a0)) {
/* 204 */             simplified = null;
/* 205 */             a.getTerms().add(a1.diffTerm(b1));
/* 206 */             a.getTerms().remove(a1);
/* 207 */             return null;
/*     */           } 
/* 209 */           simplified = null;
/* 210 */           return null;
/*     */         } 
/*     */         
/* 213 */         if (a.getTerms().size() == 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 218 */           VCRTermImpl a0 = a.getTerms().get(0);
/* 219 */           VCRTermImpl a1 = a.getTerms().get(1);
/* 220 */           VCRTermImpl a2 = a.getTerms().get(2);
/* 221 */           VCRTermImpl b0 = (VCRTermImpl)b.getTerm(a0.getDomainID());
/* 222 */           VCRTermImpl b1 = (VCRTermImpl)b.getTerm(a1.getDomainID());
/* 223 */           VCRTermImpl b2 = (VCRTermImpl)b.getTerm(a2.getDomainID());
/* 224 */           int identicalTerms = 0;
/* 225 */           if (a0.identical(b0)) {
/* 226 */             identicalTerms++;
/*     */           }
/* 228 */           if (a1.identical(b1)) {
/* 229 */             identicalTerms++;
/*     */           }
/* 231 */           if (a2.identical(b2)) {
/* 232 */             identicalTerms++;
/*     */           }
/* 234 */           if (identicalTerms >= 2) {
/* 235 */             VCRExpressionImpl simplified = new VCRExpressionImpl();
/* 236 */             if (a0.identical(b0)) {
/* 237 */               simplified.merge(a0);
/*     */             } else {
/* 239 */               simplified.merge(a0);
/* 240 */               simplified.merge(b0);
/*     */             } 
/* 242 */             if (a1.identical(b1)) {
/* 243 */               simplified.merge(a1);
/*     */             } else {
/* 245 */               simplified.merge(a1);
/* 246 */               simplified.merge(b1);
/*     */             } 
/* 248 */             if (a2.identical(b2)) {
/* 249 */               simplified.merge(a2);
/*     */             } else {
/* 251 */               simplified.merge(a2);
/* 252 */               simplified.merge(b2);
/*     */             } 
/* 254 */             return simplified;
/*     */           } 
/*     */         } 
/* 257 */         return mergeSingleDiffTerm(a, b);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 263 */       if (commonB.isSubset(a))
/* 264 */         return a; 
/* 265 */       if (a.isSubset(commonB))
/*     */       {
/*     */         
/* 268 */         return simplifyCommonSubset(a, b);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 273 */       if (a.intersect(commonB) != null)
/*     */       {
/* 275 */         return simplifyCommonSubset(a, b);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 280 */       return null;
/*     */     } 
/*     */     
/* 283 */     if (b.getTerms().size() == commonB.getTerms().size())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       return null;
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static VCRExpression mergeSingleDiffTerm(VCRExpression _a, VCRExpression _b) {
/* 299 */     VCRExpressionImpl a = (VCRExpressionImpl)_a;
/* 300 */     VCRExpressionImpl b = (VCRExpressionImpl)_b;
/*     */     
/* 302 */     int noTerms = a.getTerms().size();
/* 303 */     VCRTermImpl aTerm = null;
/* 304 */     VCRTermImpl bTerm = null;
/* 305 */     int diffDomain = -1;
/* 306 */     for (int i = 0; i < noTerms; i++) {
/* 307 */       aTerm = a.getTerms().get(i);
/* 308 */       bTerm = (VCRTermImpl)b.getTerm(aTerm.getDomainID());
/* 309 */       if (bTerm == null) {
/* 310 */         return null;
/*     */       }
/* 312 */       if (!aTerm.identical(bTerm)) {
/* 313 */         if (diffDomain > 0) {
/* 314 */           return null;
/*     */         }
/* 316 */         diffDomain = aTerm.getDomainID();
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     if (diffDomain >= 0) {
/* 321 */       aTerm = (VCRTermImpl)a.getTerm(diffDomain);
/* 322 */       bTerm = (VCRTermImpl)b.getTerm(diffDomain);
/* 323 */       VCRExpressionImpl simplified = new VCRExpressionImpl();
/* 324 */       for (int j = 0; j < noTerms; j++) {
/* 325 */         VCRTermImpl xTerm = a.getTerms().get(j);
/* 326 */         if (xTerm == aTerm) {
/* 327 */           simplified.merge(aTerm);
/* 328 */           simplified.merge(bTerm);
/*     */         } else {
/* 330 */           simplified.add(xTerm);
/*     */         } 
/*     */       } 
/* 333 */       return simplified;
/*     */     } 
/* 335 */     return null;
/*     */   }
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
/*     */   protected static VCRExpression simplifyCommonSubset(VCRExpression commonA, VCRExpression b) {
/* 368 */     int noTerms = commonA.getTerms().size();
/* 369 */     VCRTermImpl aTerm = null;
/* 370 */     VCRTermImpl bTerm = null;
/* 371 */     VCRTerm simplifiedTerm = null;
/* 372 */     int diffDomain = -1;
/* 373 */     boolean subset = false;
/*     */     
/*     */     int i;
/*     */     
/* 377 */     for (i = 0; i < noTerms; i++) {
/* 378 */       aTerm = commonA.getTerms().get(i);
/* 379 */       bTerm = (VCRTermImpl)b.getTerm(aTerm.getDomainID());
/* 380 */       if (bTerm == null) {
/* 381 */         return null;
/*     */       }
/* 383 */       if (!aTerm.identical(bTerm)) {
/* 384 */         if (!bTerm.subset(aTerm)) {
/* 385 */           subset = false;
/*     */         }
/* 387 */         if (diffDomain > 0) {
/* 388 */           if (!subset) {
/* 389 */             return null;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 394 */           diffDomain = aTerm.getDomainID();
/*     */         } 
/*     */       } 
/*     */     } 
/* 398 */     if (subset) {
/*     */       
/* 400 */       for (i = 0; i < noTerms; i++) {
/* 401 */         aTerm = commonA.getTerms().get(i);
/* 402 */         bTerm = (VCRTermImpl)b.getTerm(aTerm.getDomainID());
/* 403 */         if (!aTerm.identical(bTerm)) {
/* 404 */           simplifiedTerm = bTerm.diffTerm(aTerm);
/*     */ 
/*     */ 
/*     */           
/* 408 */           if (simplifiedTerm.getAttributes() == null) {
/* 409 */             return null;
/*     */           }
/* 411 */           b.getTerms().remove(bTerm);
/* 412 */           b.getTerms().add(simplifiedTerm);
/*     */         } 
/*     */       } 
/* 415 */     } else if (diffDomain >= 0) {
/*     */ 
/*     */       
/* 418 */       aTerm = (VCRTermImpl)commonA.getTerm(diffDomain);
/* 419 */       bTerm = (VCRTermImpl)b.getTerm(diffDomain);
/* 420 */       simplifiedTerm = bTerm.diffTerm(aTerm);
/*     */       
/* 422 */       if (simplifiedTerm.getAttributes() == null) {
/* 423 */         return null;
/*     */       }
/* 425 */       b.getTerms().remove(bTerm);
/* 426 */       b.getTerms().add(simplifiedTerm);
/*     */     } 
/* 428 */     return null;
/*     */   }
/*     */   
/*     */   protected static class VCRTermComparator
/*     */     implements Comparator {
/*     */     public int compare(Object o1, Object o2) {
/* 434 */       VCRTerm m1 = (VCRTerm)o1;
/* 435 */       VCRTerm m2 = (VCRTerm)o2;
/* 436 */       return m1.getDomainID() - m2.getDomainID();
/*     */     }
/*     */   }
/*     */   
/* 440 */   static VCRTermComparator termComparator = new VCRTermComparator();
/*     */   
/*     */   protected static void expand(List<String> permutations, Object[] elements, List<VCRTerm> terms, int term) {
/* 443 */     List<VCRAttribute> attributes = ((VCRTerm)terms.get(term)).getAttributes();
/* 444 */     if (term == terms.size() - 1) {
/* 445 */       for (int k = 0; k < attributes.size(); k++) {
/* 446 */         String permutation = "";
/* 447 */         VCRAttribute a = attributes.get(k);
/* 448 */         for (int i = 0; i < elements.length; i++) {
/* 449 */           String p = (String)elements[i];
/* 450 */           permutation = permutation + p + "-";
/*     */         } 
/* 452 */         permutation = permutation + a.toString();
/* 453 */         permutations.add(permutation);
/*     */       } 
/*     */     } else {
/* 456 */       for (int k = 0; k < attributes.size(); k++) {
/* 457 */         VCRAttribute a = attributes.get(k);
/* 458 */         elements[term] = a.toString();
/* 459 */         expand(permutations, elements, terms, term + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static boolean check(List<String> permutations, Object[] elements, List<VCRTerm> terms, int term) {
/* 465 */     List<VCRAttribute> attributes = ((VCRTerm)terms.get(term)).getAttributes();
/* 466 */     if (term == terms.size() - 1) {
/* 467 */       for (int k = 0; k < attributes.size(); k++) {
/* 468 */         String permutation = "";
/* 469 */         VCRAttribute a = attributes.get(k);
/* 470 */         for (int i = 0; i < elements.length; i++) {
/* 471 */           String p = (String)elements[i];
/* 472 */           permutation = permutation + p + "-";
/*     */         } 
/* 474 */         permutation = permutation + a.toString();
/* 475 */         int idx = permutations.indexOf(permutation);
/* 476 */         if (idx >= 0) {
/*     */           
/* 478 */           permutations.set(idx, "");
/*     */         } else {
/*     */           
/* 481 */           return false;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 485 */       for (int k = 0; k < attributes.size(); k++) {
/* 486 */         VCRAttribute a = attributes.get(k);
/* 487 */         elements[term] = a.toString();
/* 488 */         if (!check(permutations, elements, terms, term + 1)) {
/* 489 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 493 */     return true;
/*     */   }
/*     */   
/*     */   static boolean identical(ICRListImpl expressionsA, ICRListImpl expressionsB) {
/* 497 */     if (expressionsA == null)
/* 498 */       return (expressionsB == null); 
/* 499 */     if (expressionsB == null) {
/* 500 */       return false;
/*     */     }
/* 502 */     List<String> permutations = new ArrayList(); int i;
/* 503 */     for (i = 0; i < expressionsA.size(); i++) {
/* 504 */       VCRExpression e = (VCRExpression)expressionsA.get(i);
/* 505 */       List terms = e.getTerms();
/* 506 */       Object[] elements = new Object[terms.size() - 1];
/* 507 */       expand(permutations, elements, terms, 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 513 */     for (i = 0; i < expressionsB.size(); i++) {
/* 514 */       VCRExpression e = (VCRExpression)expressionsB.get(i);
/* 515 */       List<?> terms = e.getTerms();
/* 516 */       Collections.sort(terms, termComparator);
/*     */ 
/*     */       
/* 519 */       Object[] elements = new Object[terms.size() - 1];
/* 520 */       if (!check(permutations, elements, terms, 0)) {
/* 521 */         return false;
/*     */       }
/*     */     } 
/* 524 */     for (i = 0; i < permutations.size(); i++) {
/* 525 */       if (permutations.get(i) != "") {
/* 526 */         return false;
/*     */       }
/*     */     } 
/* 529 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\ICROP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */