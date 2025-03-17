/*      */ package com.eoos.gm.tis2web.vcr.implementation;
/*      */ 
/*      */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*      */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.ICR;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.ICRList;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*      */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*      */ import com.eoos.scsm.v2.util.HashCalc;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ 
/*      */ public class VCRImpl
/*      */   implements VCR
/*      */ {
/*      */   public static boolean DEFAULT_MODE = true;
/*      */   public static boolean LITERAL = false;
/*      */   protected ICRListImpl expressions;
/*      */   protected ICRImpl icr;
/*   25 */   protected boolean mode = DEFAULT_MODE;
/*      */   
/*      */   protected VCRFactory factory;
/*      */   
/*      */   public VCRImpl(VCRFactory factory) {
/*   30 */     this.factory = factory;
/*   31 */     this.expressions = new ICRListImpl();
/*      */   }
/*      */ 
/*      */   
/*      */   public VCRImpl(ICRImpl icr, VCRFactory factory) {
/*   36 */     this.factory = factory;
/*   37 */     this.icr = icr;
/*      */   }
/*      */   
/*      */   VCRImpl(ICRListImpl expressions, VCRFactory factory) {
/*   41 */     this.factory = factory;
/*   42 */     this.expressions = expressions;
/*      */   }
/*      */   
/*      */   public VCRImpl(VCRExpression expression, VCRFactory factory) {
/*   46 */     this(factory);
/*   47 */     addExpression(expression);
/*      */   }
/*      */   
/*      */   public VCRImpl(VCRTerm term, VCRFactory factory) {
/*   51 */     this(factory);
/*   52 */     add(term);
/*      */   }
/*      */   
/*      */   public VCRImpl(VCRAttribute attribute, VCRFactory factory) {
/*   56 */     this(factory);
/*   57 */     add(attribute);
/*      */   }
/*      */   
/*      */   public ICRList getExpressions() {
/*   61 */     return (this.icr == null) ? this.expressions : this.icr.getExpressions();
/*      */   }
/*      */   
/*      */   ICR getICR() {
/*   65 */     return this.icr;
/*      */   }
/*      */   
/*      */   public void setMode(boolean mode) {
/*   69 */     this.mode = mode;
/*      */   }
/*      */   
/*      */   void setICR(ICRImpl icr) {
/*   73 */     this.icr = icr;
/*      */   }
/*      */   
/*      */   public int getID() {
/*   77 */     checkICR();
/*   78 */     return this.icr.getID();
/*      */   }
/*      */   
/*      */   public int getHashCode() {
/*   82 */     checkICR();
/*   83 */     return this.icr.getHashCode();
/*      */   }
/*      */   
/*      */   public void add(VCRExpression expression) {
/*   87 */     addExpression(expression);
/*      */   }
/*      */   
/*      */   public void add(VCRTerm term) {
/*   91 */     addExpression(new VCRExpressionImpl(term));
/*      */   }
/*      */   
/*      */   public void add(VCRAttribute attribute) {
/*   95 */     addExpression(new VCRExpressionImpl(attribute));
/*      */   }
/*      */   
/*      */   public VCR fold(VCR vcr) {
/*   99 */     if (this.icr != null || ((VCRImpl)vcr).getICR() != null) {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*  102 */     ICRListImpl expressionsVCR = (ICRListImpl)vcr.getExpressions();
/*  103 */     if (expressionsVCR.size() != 1) {
/*  104 */       throw new IllegalArgumentException();
/*      */     }
/*  106 */     VCRExpression expressionVCR = (VCRExpression)expressionsVCR.get(0);
/*  107 */     List<VCRTermImpl> termsVCR = expressionVCR.getTerms();
/*  108 */     if (termsVCR == null || termsVCR.size() == 0) {
/*  109 */       throw new IllegalArgumentException();
/*      */     }
/*  111 */     for (int i = 0; i < termsVCR.size(); i++) {
/*  112 */       VCRTermImpl t = termsVCR.get(i);
/*  113 */       for (int j = 0; j < this.expressions.size(); j++) {
/*  114 */         VCRExpressionImpl e = (VCRExpressionImpl)this.expressions.get(j);
/*  115 */         e.merge(t);
/*      */       } 
/*      */     } 
/*  118 */     return this;
/*      */   }
/*      */   
/*      */   public VCR fold(VCRTerm term) {
/*  122 */     if (term == null) {
/*  123 */       throw new UnsupportedOperationException();
/*      */     }
/*  125 */     VCRImpl result = this;
/*  126 */     if (this.icr != null) {
/*  127 */       result = new VCRImpl(this.factory);
/*  128 */       ICRListImpl expressionsVCR = (ICRListImpl)getExpressions();
/*  129 */       for (int j = 0; j < expressionsVCR.size(); j++) {
/*  130 */         VCRExpression e = (VCRExpression)expressionsVCR.get(j);
/*  131 */         result.add(e.copy());
/*      */       } 
/*      */     } 
/*  134 */     for (int i = 0; i < result.expressions.size(); i++) {
/*  135 */       VCRExpressionImpl e = (VCRExpressionImpl)result.expressions.get(i);
/*  136 */       e.merge(term);
/*      */     } 
/*  138 */     return result;
/*      */   }
/*      */   
/*      */   public VCR merge(VCR vcr) {
/*  142 */     if (getExpressions() == null || getExpressions().size() == 0) {
/*  143 */       throw new IllegalArgumentException();
/*      */     }
/*  145 */     ICRListImpl expressionsVCR = (ICRListImpl)vcr.getExpressions();
/*  146 */     if (expressionsVCR == null || expressionsVCR.size() == 0) {
/*  147 */       throw new IllegalArgumentException();
/*      */     }
/*  149 */     VCRImpl result = new VCRImpl(this.factory);
/*  150 */     for (int i = 0; i < ((ICRListImpl)getExpressions()).size(); i++) {
/*  151 */       VCRExpression expression = (VCRExpression)getExpressions().get(i);
/*  152 */       for (int j = 0; j < expressionsVCR.size(); j++) {
/*  153 */         VCRExpression expressionVCR = (VCRExpression)expressionsVCR.get(j);
/*  154 */         VCRExpressionImpl constraint = (VCRExpressionImpl)expression.copy();
/*  155 */         List<VCRTermImpl> termsVCR = expressionVCR.getTerms();
/*  156 */         if (termsVCR == null || termsVCR.size() == 0) {
/*  157 */           throw new IllegalArgumentException();
/*      */         }
/*  159 */         for (int k = 0; k < termsVCR.size(); k++) {
/*  160 */           VCRTermImpl termVCR = termsVCR.get(k);
/*  161 */           constraint.merge(termVCR);
/*      */         } 
/*  163 */         result.add(constraint);
/*      */       } 
/*      */     } 
/*  166 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public VCR minus(VCR vcr) {
/*  171 */     if (vcr == null || vcr == VCR.NULL) {
/*  172 */       throw new IllegalArgumentException();
/*      */     }
/*  174 */     ICRList expressionsVCR = vcr.getExpressions();
/*      */     
/*  176 */     if (expressionsVCR == null || expressionsVCR.size() != 1) {
/*  177 */       throw new IllegalArgumentException();
/*      */     }
/*  179 */     VCRExpression expressionVCR = (VCRExpression)expressionsVCR.get(0);
/*  180 */     VCR result = null;
/*      */     
/*  182 */     for (int i = 0; i < getExpressions().size(); i++) {
/*  183 */       VCRExpression expression = (VCRExpression)getExpressions().get(i);
/*  184 */       List<VCRTerm> termsVCR = expressionVCR.getTerms();
/*      */       
/*  186 */       if (termsVCR == null || termsVCR.size() == 0) {
/*  187 */         throw new IllegalArgumentException();
/*      */       }
/*  189 */       boolean match = true;
/*      */       
/*  191 */       for (int k = 0; k < termsVCR.size(); k++) {
/*  192 */         VCRTerm termVCR = termsVCR.get(k);
/*      */         
/*  194 */         if (!match(expression, termVCR)) {
/*  195 */           match = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  199 */       if (match) {
/*  200 */         VCRExpression subresult = minus(expression, expressionVCR.getTerms());
/*      */         
/*  202 */         if (subresult == null) {
/*  203 */           return VCR.NULL;
/*      */         }
/*      */         
/*  206 */         if (result == null) {
/*  207 */           result = new VCRImpl(this.factory);
/*      */         }
/*  209 */         result.add(subresult);
/*      */       } 
/*      */     } 
/*  212 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCRExpression minus(VCRExpression expression, List termsVCR) {
/*  222 */     VCRExpression result = null;
/*  223 */     List<VCRTerm> terms = expression.getTerms();
/*      */     
/*  225 */     for (int j = 0; j < terms.size(); j++) {
/*  226 */       VCRTerm term = terms.get(j);
/*      */       
/*  228 */       if (!covered(term, termsVCR)) {
/*  229 */         if (result == null) {
/*  230 */           result = new VCRExpressionImpl();
/*      */         }
/*  232 */         result.add(term);
/*      */       } 
/*      */     } 
/*  235 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean covered(VCRTerm term, List<VCRTerm> terms) {
/*  242 */     for (int i = 0; i < terms.size(); i++) {
/*  243 */       VCRTerm t = terms.get(i);
/*      */       
/*  245 */       if (t.getDomainID() == term.getDomainID()) {
/*  246 */         return true;
/*      */       }
/*      */     } 
/*  249 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean match(VCRExpression expression, VCRTerm term) {
/*  253 */     List<VCRTerm> terms = expression.getTerms();
/*      */     
/*  255 */     for (int i = 0; i < terms.size(); i++) {
/*  256 */       VCRTerm t = terms.get(i);
/*      */       
/*  258 */       if (t.getDomainID() == term.getDomainID()) {
/*  259 */         List<VCRAttribute> attributes = t.getAttributes();
/*      */         
/*  261 */         for (int j = 0; j < attributes.size(); j++) {
/*  262 */           if (term.match(attributes.get(j))) {
/*  263 */             return true;
/*      */           }
/*      */         } 
/*  266 */         return false;
/*      */       } 
/*      */     } 
/*  269 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VCR diff(VCR vcr) {
/*  276 */     if (vcr == null || vcr == VCR.NULL) {
/*  277 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  280 */     checkICR();
/*  281 */     ((VCRImpl)vcr).checkICR();
/*  282 */     ICRListImpl expressionsVCR = (ICRListImpl)vcr.getExpressions();
/*  283 */     if (expressionsVCR == null || expressionsVCR.size() == 0) {
/*  284 */       throw new IllegalArgumentException();
/*      */     }
/*  286 */     VCRImpl result = new VCRImpl(this.factory);
/*  287 */     boolean contradiction = true;
/*      */ 
/*      */ 
/*      */     
/*  291 */     for (int i = 0; i < ((ICRListImpl)getExpressions()).size(); i++) {
/*  292 */       VCRExpression expression = (VCRExpression)((ICRListImpl)getExpressions()).get(i);
/*  293 */       for (int j = 0; j < expressionsVCR.size(); j++) {
/*  294 */         VCRExpression expressionVCR = (VCRExpression)expressionsVCR.get(j);
/*  295 */         VCRExpressionImpl diff = diff(expression, expressionVCR);
/*  296 */         if (diff != null && diff.getTerms() != null && diff.getTerms().size() > 0) {
/*      */ 
/*      */ 
/*      */           
/*  300 */           result.add(diff);
/*  301 */           contradiction = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  312 */     return contradiction ? VCR.NULL : result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCRExpressionImpl diff(VCRExpression vcr, VCRExpression vcc) {
/*  321 */     List<VCRTerm> termsVCR = vcr.getTerms();
/*  322 */     if (termsVCR == null || termsVCR.size() == 0) {
/*  323 */       throw new IllegalArgumentException();
/*      */     }
/*  325 */     List<VCRTermImpl> termsVCC = vcc.getTerms();
/*  326 */     if (termsVCC == null || termsVCC.size() == 0) {
/*  327 */       throw new IllegalArgumentException();
/*      */     }
/*  329 */     VCRExpressionImpl result = new VCRExpressionImpl();
/*  330 */     for (int k = 0; k < termsVCR.size(); k++) {
/*  331 */       VCRTerm termVCR = termsVCR.get(k);
/*  332 */       boolean match = false;
/*  333 */       for (int j = 0; j < termsVCC.size(); j++) {
/*  334 */         VCRTermImpl termVCC = termsVCC.get(j);
/*  335 */         if (termVCR.getDomainID() == termVCC.getDomainID()) {
/*  336 */           match = true;
/*  337 */           VCRTermImpl diff = (VCRTermImpl)termVCR.intersect(termVCC);
/*  338 */           if (diff != null && diff.getAttributes().size() > 0) {
/*  339 */             result.add(diff);
/*      */           } else {
/*      */             
/*  342 */             return null;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  347 */       if (!match)
/*      */       {
/*  349 */         result.add(termVCR.copy());
/*      */       }
/*      */     } 
/*  352 */     for (int m = 0; m < termsVCC.size(); m++) {
/*  353 */       VCRTerm termVCC = termsVCC.get(m);
/*  354 */       boolean match = false;
/*  355 */       for (int n = 0; n < termsVCR.size(); n++) {
/*  356 */         VCRTerm termVCR = termsVCR.get(n);
/*  357 */         if (termVCR.getDomainID() == termVCC.getDomainID()) {
/*  358 */           match = true;
/*      */         }
/*      */       } 
/*  361 */       if (!match)
/*      */       {
/*  363 */         result.add(termVCC.copy());
/*      */       }
/*      */     } 
/*  366 */     return result;
/*      */   }
/*      */   
/*      */   public VCR union(VCR vcr) {
/*  370 */     if (vcr == VCR.NULL) {
/*  371 */       return ICROP.union((ICRListImpl)getExpressions(), null, this.factory);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  376 */     return ICROP.union((ICRListImpl)getExpressions(), (ICRListImpl)vcr.getExpressions(), this.factory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VCR append(VCR vcr) {
/*  386 */     if (vcr != null && vcr != VCR.NULL) {
/*  387 */       if (this.icr != null) {
/*  388 */         throw new UnsupportedOperationException();
/*      */       }
/*  390 */       ICRList expressionsVCR = vcr.getExpressions();
/*      */       
/*  392 */       if (expressionsVCR == null || expressionsVCR.size() == 0) {
/*  393 */         throw new IllegalArgumentException();
/*      */       }
/*  395 */       for (int i = 0; i < expressionsVCR.size(); i++) {
/*  396 */         VCRExpression expression = (VCRExpression)expressionsVCR.get(i);
/*      */         
/*  398 */         add(expression);
/*      */       } 
/*      */     } 
/*  401 */     return this;
/*      */   }
/*      */   
/*      */   public VCR intersect(VCR vcr) {
/*  405 */     if (vcr == VCR.NULL) {
/*  406 */       return ICROP.intersect((ICRListImpl)getExpressions(), null, this.factory);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  411 */     return ICROP.intersect((ICRListImpl)getExpressions(), (ICRListImpl)vcr.getExpressions(), this.factory);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VCR join(VCR vcr) {
/*  421 */     if (vcr == null || vcr == VCR.NULL)
/*  422 */       return this; 
/*  423 */     if (getExpressions() == null || ((ICRListImpl)getExpressions()).size() == 0 || getExpressions().size() > 1) {
/*  424 */       throw new IllegalArgumentException();
/*      */     }
/*  426 */     ICRListImpl expressionsVCR = (ICRListImpl)vcr.getExpressions();
/*  427 */     if (expressionsVCR == null || expressionsVCR.size() == 0 || expressionsVCR.size() > 1) {
/*  428 */       throw new IllegalArgumentException();
/*      */     }
/*  430 */     VCR result = new VCRImpl(this.factory);
/*  431 */     VCRExpressionImpl expressionVCR = (VCRExpressionImpl)expressionsVCR.get(0);
/*  432 */     expressionVCR = (VCRExpressionImpl)expressionVCR.copy();
/*  433 */     result.add(expressionVCR);
/*  434 */     VCRExpression expression = (VCRExpression)((ICRListImpl)getExpressions()).get(0);
/*  435 */     List<VCRTermImpl> terms = expression.getTerms();
/*  436 */     for (int i = 0; i < terms.size(); i++) {
/*  437 */       VCRTermImpl term = terms.get(i);
/*  438 */       expressionVCR.add(term);
/*      */     } 
/*  440 */     return result;
/*      */   }
/*      */   
/*      */   public boolean match(LocaleInfo locale) {
/*  444 */     return this.icr.match(new VCRImpl(new VCRAttributeImpl(VCRFactory.LOCALE, locale.getLocaleID().intValue()), this.factory));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean match(LocaleInfo locale, VCR vcr) {
/*  452 */     if (vcr == VCR.NULL) {
/*  453 */       return this.icr.match(new VCRImpl(new VCRAttributeImpl(VCRFactory.LOCALE, locale.getLocaleID().intValue()), this.factory));
/*      */     }
/*  455 */     List<VCRExpression> expressions = (ICRListImpl)vcr.getExpressions();
/*  456 */     if (expressions.size() != 1) {
/*  457 */       throw new IllegalArgumentException();
/*      */     }
/*  459 */     VCRExpression expression = expressions.get(0);
/*  460 */     VCRExpression newExpression = expression.copy();
/*  461 */     VCRTermImpl localeTerm = new VCRTermImpl(new VCRAttributeImpl(VCRFactory.LOCALE, locale.getLocaleID().intValue()));
/*      */     
/*  463 */     newExpression.add(localeTerm);
/*  464 */     boolean result = this.icr.matchExp(newExpression);
/*      */     
/*  466 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List getAttributes() {
/*  472 */     List elements = new ArrayList();
/*  473 */     List<VCRExpression> expressions = (ICRListImpl)getExpressions();
/*  474 */     if (expressions == null || expressions.size() != 1) {
/*  475 */       throw new IllegalArgumentException();
/*      */     }
/*  477 */     VCRExpression expression = expressions.get(0);
/*  478 */     List<VCRTerm> terms = expression.getTerms();
/*  479 */     if (terms == null) {
/*  480 */       throw new IllegalArgumentException();
/*      */     }
/*  482 */     for (int i = 0; i < terms.size(); i++) {
/*  483 */       VCRTerm term = terms.get(i);
/*  484 */       List attributes = term.getAttributes();
/*      */ 
/*      */ 
/*      */       
/*  488 */       if (attributes == null) {
/*  489 */         throw new IllegalArgumentException();
/*      */       }
/*  491 */       for (int j = 0; j < attributes.size(); j++) {
/*  492 */         elements.add(attributes.get(j));
/*      */       }
/*      */     } 
/*      */     
/*  496 */     return elements;
/*      */   }
/*      */   
/*      */   public List listAttributes() {
/*  500 */     List elements = new ArrayList();
/*  501 */     List<VCRExpression> expressions = (ICRListImpl)getExpressions();
/*  502 */     if (expressions == null || expressions.size() != 1) {
/*  503 */       throw new IllegalArgumentException();
/*      */     }
/*  505 */     VCRExpression expression = expressions.get(0);
/*  506 */     List<VCRTerm> terms = expression.getTerms();
/*  507 */     if (terms == null) {
/*  508 */       throw new IllegalArgumentException();
/*      */     }
/*  510 */     for (int i = 0; i < terms.size(); i++) {
/*  511 */       VCRTerm term = terms.get(i);
/*  512 */       List attributes = term.getAttributes();
/*  513 */       for (int j = 0; j < attributes.size(); j++) {
/*  514 */         elements.add(attributes.get(j));
/*      */       }
/*      */     } 
/*  517 */     return elements;
/*      */   }
/*      */ 
/*      */   
/*      */   public List getTerms() {
/*  522 */     List<VCRExpression> expressions = (ICRListImpl)getExpressions();
/*  523 */     if (expressions == null || expressions.size() != 1) {
/*  524 */       throw new IllegalArgumentException();
/*      */     }
/*  526 */     VCRExpression expression = expressions.get(0);
/*  527 */     return expression.getTerms();
/*      */   }
/*      */   
/*      */   public String getModelExpression() {
/*  531 */     int DOMAINID_MODEL = 6;
/*  532 */     List terms = getTerms();
/*  533 */     for (Iterator<VCRTerm> it = terms.iterator(); it.hasNext(); ) {
/*  534 */       VCRTerm term = it.next();
/*  535 */       if (term.getDomainID() == DOMAINID_MODEL) {
/*  536 */         return term.toString();
/*      */       }
/*      */     } 
/*  539 */     return null;
/*      */   }
/*      */   
/*      */   public Set getMakes() {
/*  543 */     int DOMAINID_MAKE = 2;
/*  544 */     List<VCRExpressionImpl> expressions = (ICRListImpl)getExpressions();
/*  545 */     if (expressions == null) {
/*  546 */       return null;
/*      */     }
/*  548 */     Set<VCRTerm> makes = new HashSet();
/*  549 */     for (int i = 0; i < expressions.size(); i++) {
/*  550 */       VCRExpressionImpl expression = expressions.get(i);
/*  551 */       List terms = expression.getTerms();
/*  552 */       for (Iterator<VCRTerm> it = terms.iterator(); it.hasNext(); ) {
/*  553 */         VCRTerm term = it.next();
/*  554 */         if (term.getDomainID() == DOMAINID_MAKE) {
/*  555 */           makes.add(term);
/*      */         }
/*      */       } 
/*      */     } 
/*  559 */     return makes;
/*      */   }
/*      */   
/*      */   public boolean match(VCR vcr) {
/*  563 */     checkICR();
/*  564 */     return this.icr.match(vcr);
/*      */   }
/*      */   
/*      */   public boolean match(VCRExpression expression) {
/*  568 */     checkICR();
/*  569 */     return this.icr.match(expression);
/*      */   }
/*      */   
/*      */   public boolean match(VCRAttribute attribute) {
/*  573 */     checkICR();
/*  574 */     return this.icr.match(attribute);
/*      */   }
/*      */   
/*      */   public boolean match(VCValue value) {
/*  578 */     checkICR();
/*  579 */     return this.icr.match(value);
/*      */   }
/*      */   
/*      */   public boolean match(VCR vcr, VCR positiveOptionVCR) {
/*  583 */     checkICR();
/*  584 */     if (!match(vcr)) {
/*  585 */       return false;
/*      */     }
/*  587 */     for (int i = 0; i < ((ICRListImpl)getExpressions()).size(); ) {
/*  588 */       VCRExpression expression = (VCRExpression)getExpressions().get(i);
/*  589 */       if (!satisfied(expression, vcr, positiveOptionVCR)) {
/*      */         i++; continue;
/*      */       } 
/*  592 */       return true;
/*      */     } 
/*  594 */     return false;
/*      */   }
/*      */   
/*      */   public boolean contradicts(VCR vcr, VCR negativeOptionVCR) {
/*  598 */     checkICR();
/*  599 */     if (!match(vcr)) {
/*  600 */       return true;
/*      */     }
/*  602 */     for (int i = 0; i < getExpressions().size(); i++) {
/*  603 */       VCRExpression expression = (VCRExpression)getExpressions().get(i);
/*  604 */       if (contradicted(expression, negativeOptionVCR)) {
/*  605 */         return true;
/*      */       }
/*      */     } 
/*  608 */     return false;
/*      */   }
/*      */   
/*      */   public boolean match(VCR vcr, VCR positiveOptionVCR, VCR negativeOptionVCR) {
/*  612 */     checkICR();
/*  613 */     if (!match(vcr, positiveOptionVCR)) {
/*  614 */       return false;
/*      */     }
/*  616 */     return !contradicts(vcr, negativeOptionVCR);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean satisfied(VCRExpression expression, VCR vcr, VCR positiveOptionVCR) {
/*  621 */     List vcAttributes = vcr.getAttributes();
/*  622 */     boolean hasPositiveOptionVCR = (positiveOptionVCR != null && positiveOptionVCR != VCR.NULL);
/*  623 */     List positiveAttributes = hasPositiveOptionVCR ? positiveOptionVCR.getAttributes() : null;
/*  624 */     List<VCRTerm> terms = expression.getTerms();
/*  625 */     for (int i = 0; i < terms.size(); i++) {
/*  626 */       VCRTerm term = terms.get(i);
/*  627 */       List<VCRAttributeImpl> attributes = term.getAttributes();
/*  628 */       boolean match = false;
/*  629 */       for (int j = 0; j < attributes.size(); j++) {
/*  630 */         VCRAttributeImpl a = attributes.get(j);
/*  631 */         if (contains(vcAttributes, a)) {
/*  632 */           match = true;
/*      */           
/*      */           break;
/*      */         } 
/*  636 */         if (hasPositiveOptionVCR && contains(positiveAttributes, a)) {
/*  637 */           match = true;
/*      */         }
/*      */       } 
/*      */       
/*  641 */       if (!match) {
/*  642 */         return false;
/*      */       }
/*      */     } 
/*  645 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean contradicted(VCRExpression expression, VCR negativeOptionVCR) {
/*  666 */     checkICR();
/*  667 */     if (negativeOptionVCR == null || negativeOptionVCR == VCR.NULL) {
/*  668 */       return false;
/*      */     }
/*  670 */     List<VCRAttributeImpl> negativeAttributes = negativeOptionVCR.getAttributes();
/*  671 */     List positiveAttributes = getAttributes(expression);
/*  672 */     for (int i = 0; i < negativeAttributes.size(); i++) {
/*  673 */       VCRAttributeImpl a = negativeAttributes.get(i);
/*  674 */       if (contains(positiveAttributes, a)) {
/*  675 */         return true;
/*      */       }
/*      */     } 
/*  678 */     return false;
/*      */   }
/*      */   
/*      */   protected static boolean contains(List<VCRAttributeImpl> positiveAttributes, VCRAttributeImpl negativeAttribute) {
/*  682 */     for (int i = 0; i < positiveAttributes.size(); i++) {
/*  683 */       VCRAttributeImpl a = positiveAttributes.get(i);
/*  684 */       if (negativeAttribute.match(a)) {
/*  685 */         return true;
/*      */       }
/*      */     } 
/*  688 */     return false;
/*      */   }
/*      */   
/*      */   protected static List getAttributes(VCRExpression expression) {
/*  692 */     List elements = new ArrayList();
/*  693 */     List<VCRTerm> terms = expression.getTerms();
/*  694 */     for (int i = 0; i < terms.size(); i++) {
/*  695 */       VCRTerm term = terms.get(i);
/*  696 */       List attributes = term.getAttributes();
/*  697 */       for (int j = 0; j < attributes.size(); j++) {
/*  698 */         elements.add(attributes.get(j));
/*      */       }
/*      */     } 
/*  701 */     return elements;
/*      */   }
/*      */   
/*      */   protected List getAttributes(VCRExpression expression, int domainID) {
/*  705 */     List elements = new ArrayList();
/*  706 */     List<VCRTerm> terms = expression.getTerms();
/*  707 */     for (int i = 0; i < terms.size(); i++) {
/*  708 */       VCRTerm term = terms.get(i);
/*  709 */       if (term.getDomainID() == domainID) {
/*      */ 
/*      */         
/*  712 */         List attributes = term.getAttributes();
/*  713 */         for (int j = 0; j < attributes.size(); j++)
/*  714 */           elements.add(attributes.get(j)); 
/*      */       } 
/*      */     } 
/*  717 */     return elements;
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj) {
/*  721 */     if (obj != null && obj.getClass() == getClass()) {
/*  722 */       checkICR();
/*  723 */       ((VCRImpl)obj).checkICR();
/*  724 */       return this.icr.equals(((VCRImpl)obj).getICR());
/*      */     } 
/*  726 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  732 */     checkICR();
/*  733 */     int ret = VCRImpl.class.hashCode();
/*  734 */     ret = HashCalc.addHashCode(ret, this.icr);
/*  735 */     return ret;
/*      */   }
/*      */   
/*      */   public boolean identical(VCR vcr) {
/*  739 */     return ICROP.identical((ICRListImpl)getExpressions(), (ICRListImpl)vcr.getExpressions());
/*      */   }
/*      */   
/*      */   public String toString() {
/*  743 */     checkICR();
/*  744 */     return this.icr.toString();
/*      */   }
/*      */   
/*      */   protected void checkICR() {
/*  748 */     if (this.icr == null) {
/*  749 */       this.icr = this.factory.createICR(this, this.expressions);
/*  750 */       this.expressions = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addExpression(VCRExpression expression) {
/*  755 */     if (this.expressions == null) {
/*  756 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*  759 */     for (int i = 0; i < this.expressions.size(); i++) {
/*  760 */       VCRExpression e = (VCRExpressionImpl)this.expressions.get(i);
/*  761 */       if (e.gt(expression)) {
/*  762 */         this.expressions.add(i, (E)expression);
/*      */         return;
/*      */       } 
/*      */     } 
/*  766 */     this.expressions.add((E)expression);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCRTerm reduce(VCRTerm term, VCRTerm options) {
/*  774 */     if (term.getDomainID() != options.getDomainID()) {
/*  775 */       return term;
/*      */     }
/*  777 */     VCRTermImpl reduced = new VCRTermImpl();
/*  778 */     boolean valid = false;
/*  779 */     for (int i = 0; i < term.getAttributes().size(); i++) {
/*  780 */       VCRAttributeImpl attribute = term.getAttributes().get(i);
/*  781 */       if (!options.match(attribute)) {
/*      */         try {
/*  783 */           reduced.add(attribute);
/*  784 */         } catch (Exception e) {
/*      */           
/*  786 */           reduced.merge(term);
/*      */         } 
/*  788 */         valid = true;
/*      */       } 
/*      */     } 
/*  791 */     return valid ? reduced : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCRExpressionImpl reduce(VCRExpression expression, VCR options) {
/*  802 */     if (options.getExpressions() == null || options.getExpressions().size() != 1) {
/*  803 */       throw new IllegalArgumentException();
/*      */     }
/*  805 */     VCRExpression selectedOptions = (VCRExpression)options.getExpressions().get(0);
/*  806 */     if (selectedOptions.getTerms() == null || selectedOptions.getTerms().size() != 1) {
/*  807 */       throw new IllegalArgumentException();
/*      */     }
/*  809 */     VCRTerm selection = selectedOptions.getTerms().get(0);
/*  810 */     VCRExpressionImpl reduced = new VCRExpressionImpl();
/*  811 */     boolean valid = false;
/*  812 */     for (int i = 0; i < expression.getTerms().size(); i++) {
/*  813 */       VCRTerm term = expression.getTerms().get(i);
/*  814 */       term = reduce(term, selection);
/*  815 */       if (term != null) {
/*  816 */         reduced.add(term);
/*  817 */         valid = true;
/*      */       } 
/*      */     } 
/*  820 */     return valid ? reduced : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCRExpressionImpl reduce2(VCRExpression expression, VCR vcr) {
/*  832 */     if (vcr.getExpressions() == null || vcr.getExpressions().size() != 1) {
/*  833 */       throw new IllegalArgumentException();
/*      */     }
/*  835 */     VCRExpression vehicle = (VCRExpression)vcr.getExpressions().get(0);
/*  836 */     if (vehicle.getTerms() == null) {
/*  837 */       throw new IllegalArgumentException();
/*      */     }
/*  839 */     VCRExpressionImpl reduced = new VCRExpressionImpl();
/*  840 */     boolean valid = false;
/*  841 */     boolean match = false;
/*  842 */     for (int t = 0; t < expression.getTerms().size(); t++) {
/*  843 */       VCRTermImpl term = expression.getTerms().get(t);
/*  844 */       boolean matchedTerm = false;
/*  845 */       List<VCRTerm> terms = vehicle.getTerms();
/*  846 */       for (int i = 0; i < terms.size(); i++) {
/*  847 */         VCRTerm selection = terms.get(i);
/*  848 */         if (term.getDomainID() == selection.getDomainID()) {
/*  849 */           matchedTerm = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*  854 */       if (!matchedTerm) {
/*      */         
/*  856 */         reduced.merge(term);
/*  857 */         valid = true;
/*      */       } else {
/*  859 */         match = true;
/*      */       } 
/*      */     } 
/*      */     
/*  863 */     return (valid || match) ? reduced : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCR reduce(VCR vcr, VCR options) {
/*  870 */     if (options == null) {
/*  871 */       options = VCR.NULL;
/*      */     }
/*  873 */     VCR reduced = new VCRImpl(this.factory);
/*  874 */     boolean valid = false;
/*  875 */     for (int i = 0; i < getExpressions().size(); i++) {
/*  876 */       VCRExpressionImpl expression = (VCRExpressionImpl)getExpressions().get(i);
/*  877 */       if (options != VCR.NULL) {
/*  878 */         expression = reduce(expression, options);
/*      */       }
/*      */       
/*  881 */       if (expression != null) {
/*  882 */         expression = reduce2(expression, vcr);
/*      */         
/*  884 */         if (expression != null) {
/*  885 */           if (expression.isInvalid()) {
/*  886 */             return VCR.NULL;
/*      */           }
/*      */ 
/*      */           
/*  890 */           reduced.add(expression);
/*  891 */           valid = true;
/*      */         } 
/*      */       } 
/*      */     } 
/*  895 */     return valid ? reduced : VCR.NULL;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected VCR reduce(VCR vcr) {
/*  901 */     if (vcr.getExpressions() == null || vcr.getExpressions().size() != 1) {
/*  902 */       throw new IllegalArgumentException();
/*      */     }
/*  904 */     VCRExpression vehicle = (VCRExpression)vcr.getExpressions().get(0);
/*  905 */     VCRImpl reduced = new VCRImpl(this.factory);
/*  906 */     boolean valid = false;
/*  907 */     for (int i = 0; i < getExpressions().size(); i++) {
/*  908 */       VCRExpression expression = (VCRExpression)getExpressions().get(i);
/*  909 */       VCRExpressionImpl reduced2 = new VCRExpressionImpl();
/*  910 */       boolean contradiction = false;
/*  911 */       List<VCRTermImpl> terms = expression.getTerms();
/*  912 */       for (int t = 0; t < terms.size(); t++) {
/*  913 */         VCRTermImpl term = terms.get(t);
/*  914 */         List<VCRTermImpl> vterms = vehicle.getTerms();
/*  915 */         for (int v = 0; v < vterms.size(); v++) {
/*  916 */           VCRTermImpl vterm = vterms.get(v);
/*  917 */           if (vterm.getDomainID() == term.getDomainID()) {
/*  918 */             if (!vterm.superset(term)) {
/*      */ 
/*      */               
/*  921 */               contradiction = true; break;
/*      */             } 
/*  923 */             term = vterm;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         try {
/*  929 */           reduced2.add(term);
/*  930 */         } catch (Exception e) {
/*      */ 
/*      */           
/*  933 */           reduced2.merge(term);
/*      */         } 
/*      */       } 
/*  936 */       if (!contradiction) {
/*  937 */         reduced.add(reduced2);
/*  938 */         valid = true;
/*      */       } 
/*      */     } 
/*  941 */     return valid ? reduced : VCR.NULL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSuperset(VCRExpression superset, VCRExpression expression) {
/*  956 */     int missing = 0;
/*      */ 
/*      */     
/*  959 */     List<VCRTermImpl> terms = expression.getTerms();
/*  960 */     for (int i = 0; i < terms.size(); i++) {
/*  961 */       VCRTermImpl term = terms.get(i);
/*  962 */       VCRTermImpl ssterm = (VCRTermImpl)superset.getTerm(term.getDomainID());
/*  963 */       if (ssterm == null) {
/*  964 */         missing++;
/*      */       
/*      */       }
/*  967 */       else if (!term.identical(ssterm)) {
/*      */         
/*  969 */         if (term.superset(ssterm))
/*      */         {
/*      */ 
/*      */           
/*  973 */           return false; } 
/*      */       } 
/*      */     } 
/*  976 */     return (superset.getTerms().size() + missing == terms.size());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isSuperset(VCR vcr, VCRExpression expression) {
/*  989 */     for (int i = 0; i < vcr.getExpressions().size(); i++) {
/*  990 */       VCRExpression expr = (VCRExpression)vcr.getExpressions().get(i);
/*  991 */       if (isSuperset(expr, expression)) {
/*  992 */         return true;
/*      */       }
/*      */     } 
/*  995 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReducedSuperset(VCR vcr, VCR superset) {
/* 1004 */     if (superset == null || superset == VCR.NULL) {
/* 1005 */       return true;
/*      */     }
/* 1007 */     VCR subset = reduce(vcr);
/* 1008 */     if (subset == null || subset == VCR.NULL) {
/* 1009 */       return false;
/*      */     }
/* 1011 */     superset = ((VCRImpl)superset).reduce(vcr);
/* 1012 */     if (superset == null || superset == VCR.NULL) {
/* 1013 */       return true;
/*      */     }
/* 1015 */     for (int i = 0; i < subset.getExpressions().size(); i++) {
/* 1016 */       VCRExpression expression = (VCRExpression)subset.getExpressions().get(i);
/* 1017 */       if (!isSuperset(superset, expression)) {
/* 1018 */         return false;
/*      */       }
/*      */     } 
/* 1021 */     return true;
/*      */   }
/*      */   
/*      */   public static List checkOptionRestriction(List<VCR> elements, VCR vcr, VCR positiveOptions, VCR negativeOptions) {
/* 1025 */     List<VCR> result = new ArrayList();
/* 1026 */     for (int i = 0; i < elements.size(); i++) {
/* 1027 */       VCR element = elements.get(i);
/* 1028 */       if (element == VCR.NULL) {
/* 1029 */         result.add(VCR.NULL);
/* 1030 */       } else if (element.contradicts(vcr, negativeOptions)) {
/* 1031 */         result.add(null);
/* 1032 */       } else if (((VCRImpl)element).match(vcr, positiveOptions)) {
/* 1033 */         result.add(((VCRImpl)element).reduce(vcr, positiveOptions));
/*      */       } else {
/*      */         
/* 1036 */         result.add(((VCRImpl)element).reduce(vcr, positiveOptions));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1043 */     List<VCR> supersets = new ArrayList(); int j;
/* 1044 */     for (j = 0; j < elements.size() - 1; j++) {
/* 1045 */       VCR elementA = elements.get(j);
/* 1046 */       for (int k = j + 1; k < elements.size(); k++) {
/* 1047 */         VCR elementB = elements.get(k);
/* 1048 */         if (result.get(j) == VCR.NULL && result.get(k) == VCR.NULL) {
/*      */ 
/*      */           
/* 1051 */           if (elementA.isReducedSuperset(vcr, elementB)) {
/* 1052 */             supersets.add(elementB);
/* 1053 */           } else if (elementB.isReducedSuperset(vcr, elementA)) {
/* 1054 */             supersets.add(elementA);
/*      */           }
/*      */         
/*      */         }
/* 1058 */         else if (result.get(j) != null && result.get(k) != null) {
/*      */           
/* 1060 */           if (((VCR)result.get(j)).identical(result.get(k))) {
/* 1061 */             if (elementA.isReducedSuperset(vcr, elementB)) {
/* 1062 */               supersets.add(elementB);
/* 1063 */             } else if (elementB.isReducedSuperset(vcr, elementA)) {
/* 1064 */               supersets.add(elementA);
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/* 1070 */     for (j = 0; j < supersets.size(); j++) {
/* 1071 */       VCR element = supersets.get(j);
/* 1072 */       result.set(elements.indexOf(element), null);
/*      */     } 
/* 1074 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\VCRImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */