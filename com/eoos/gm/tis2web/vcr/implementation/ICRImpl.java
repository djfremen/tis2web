/*     */ package com.eoos.gm.tis2web.vcr.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCValue;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.ICR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ICRImpl
/*     */   implements ICR
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(ICRImpl.class);
/*     */   
/*     */   static final String DELIMITER = ":";
/*     */   
/*     */   static final String START_LIST = "(";
/*     */   
/*     */   static final String CLOSE_LIST = ")";
/*     */   
/*     */   static final String START_LIST2 = "[";
/*     */   
/*     */   static final String CLOSE_LIST2 = "]";
/*     */   
/*     */   static final String AND_STRING = " and ";
/*     */   
/*     */   static final String OR_STRING = " or ";
/*     */   
/*     */   static final String AND_STRING2 = "and";
/*     */   
/*     */   static final String OR_STRING2 = "or";
/*     */   
/*     */   static final String SPACE = " ";
/*     */   
/*     */   static final int INVALID = -1;
/*     */   
/*     */   protected int id;
/*     */   
/*     */   protected int code;
/*     */   
/*     */   protected ICRListImpl expressions;
/*     */   
/*     */   public void setID(int id) {
/*  52 */     this.id = id;
/*     */   }
/*     */   
/*     */   ICRImpl(ICRListImpl expressions) {
/*  56 */     this(expressions, true);
/*     */   }
/*     */   
/*     */   public ICRImpl(ICRListImpl expressions, boolean simplify) {
/*  60 */     if (simplify) {
/*  61 */       ICROP.simplify(expressions);
/*     */     }
/*  63 */     this.expressions = expressions;
/*  64 */     this.code = computeHashCode();
/*     */   }
/*     */   
/*     */   ICRImpl(String v) {
/*  68 */     this.expressions = new ICRListImpl();
/*  69 */     scan(v);
/*  70 */     this.code = computeHashCode();
/*     */   }
/*     */   
/*     */   public int getID() {
/*  74 */     return this.id;
/*     */   }
/*     */   
/*     */   int getHashCode() {
/*  78 */     return this.code;
/*     */   }
/*     */   
/*     */   boolean match(VCR _vcr) {
/*  82 */     VCRImpl vcr = (VCRImpl)_vcr;
/*  83 */     if (vcr.getExpressions() == null || vcr.getExpressions().size() == 0) {
/*  84 */       return true;
/*     */     }
/*     */     
/*  87 */     if (vcr.getExpressions().size() > 1) {
/*  88 */       log.warn("!!! Illegal argument (VCR contains more than one expression, which means that it is actually a VCR filter)");
/*  89 */       log.warn("!!! Proceeding with first expression only (legacy code support)");
/*  90 */       log.warn("!!! this vcr: " + toString());
/*  91 */       log.warn("!!! vcr argument: " + String.valueOf(vcr));
/*     */     } 
/*     */     
/*  94 */     VCRExpression exprVCR = (VCRExpression)vcr.getExpressions().get(0);
/*  95 */     return matchExp(exprVCR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchExp(VCRExpression exprVCR) {
/* 100 */     if (this.expressions.size() == 0) {
/* 101 */       return true;
/*     */     }
/* 103 */     int noExpressions = this.expressions.size();
/* 104 */     VCRExpression expr = null;
/* 105 */     for (int i = 0; i < noExpressions; i++) {
/* 106 */       expr = (VCRExpression)getExpressions().get(i);
/* 107 */       if (match(expr, exprVCR.getTerms())) {
/* 108 */         return true;
/*     */       }
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean match(VCRExpression expr, List<VCRTermImpl> terms) {
/* 115 */     VCRTermImpl term = null;
/*     */     
/* 117 */     VCRTermImpl termVCR = null;
/* 118 */     for (int k = 0; k < expr.getTerms().size(); k++) {
/* 119 */       term = expr.getTerms().get(k);
/* 120 */       for (int n = 0; n < terms.size(); n++) {
/* 121 */         termVCR = terms.get(n);
/* 122 */         if (term.matchDomains(termVCR) && 
/* 123 */           term.intersect(termVCR) == null) {
/* 124 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean match(VCRExpression other) {
/* 137 */     int noExpressions = this.expressions.size();
/* 138 */     VCRExpression expr = null;
/* 139 */     for (int i = 0; i < noExpressions; i++) {
/* 140 */       expr = (VCRExpression)this.expressions.get(i);
/* 141 */       if (!((VCRExpressionImpl)other).match(expr)) {
/* 142 */         return false;
/*     */       }
/*     */     } 
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean match(VCRAttribute attribute) {
/* 153 */     int noExpressions = this.expressions.size();
/* 154 */     if (noExpressions == 0) {
/* 155 */       return true;
/*     */     }
/* 157 */     for (int i = 0; i < noExpressions; i++) {
/* 158 */       if (((VCRExpressionImpl)this.expressions.get(i)).matchAttribute(attribute)) {
/* 159 */         return true;
/*     */       }
/*     */     } 
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   public boolean match(VCValue value) {
/* 166 */     return match(new VCRAttributeImpl(value));
/*     */   }
/*     */   
/*     */   ICRListImpl getExpressions() {
/* 170 */     return this.expressions;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 174 */     if (obj != null && obj.getClass() == getClass()) {
/* 175 */       return identical((ICRImpl)obj);
/*     */     }
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 182 */     return this.code;
/*     */   }
/*     */   
/*     */   protected int computeHashCode() {
/* 186 */     Hash h = null;
/* 187 */     int noVCRs = this.expressions.size();
/* 188 */     for (int i = 0; i < noVCRs; i++) {
/* 189 */       if (h == null) {
/* 190 */         h = new Hash(((VCRExpressionImpl)this.expressions.get(i)).getHashCode());
/*     */       } else {
/* 192 */         h = (new Hash(((VCRExpressionImpl)this.expressions.get(i)).getHashCode())).sag(h);
/*     */       } 
/*     */     } 
/* 195 */     return (h == null) ? 0 : h.bag(1870585220);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 200 */     StringBuffer sb = new StringBuffer();
/* 201 */     int noVCRs = this.expressions.size();
/* 202 */     for (int i = 0; i < noVCRs; i++) {
/* 203 */       if (i > 0) {
/* 204 */         sb.append(" or ");
/*     */       }
/* 206 */       if (noVCRs > 1) {
/* 207 */         sb.append("[");
/*     */       }
/* 209 */       VCRExpression v = (VCRExpression)this.expressions.get(i);
/* 210 */       List<E> b = v.getTerms();
/* 211 */       int noblocks = b.size();
/* 212 */       for (int j = 0; j < noblocks; j++) {
/* 213 */         if (j > 0) {
/* 214 */           sb.append(" and ");
/*     */         }
/* 216 */         sb.append(b.get(j).toString());
/*     */       } 
/* 218 */       if (noVCRs > 1) {
/* 219 */         sb.append("]");
/*     */       }
/*     */     } 
/* 222 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected String encode(String vcr) {
/* 226 */     StringBuffer v = new StringBuffer(vcr.length());
/* 227 */     v.append('#');
/* 228 */     for (int i = 0; i < vcr.length(); i++) {
/* 229 */       char c = vcr.charAt(i);
/* 230 */       if (c != ' ')
/*     */       {
/* 232 */         if (c == 'a') {
/* 233 */           v.append('&');
/* 234 */           i += 2;
/* 235 */         } else if (c == 'o') {
/* 236 */           v.append('|');
/* 237 */           i++;
/*     */         } else {
/* 239 */           v.append(c);
/*     */         }  } 
/*     */     } 
/* 242 */     return v.toString();
/*     */   }
/*     */   
/*     */   protected String decode(String vcr) {
/* 246 */     StringBuffer v = new StringBuffer(vcr.length());
/* 247 */     for (int i = 1; i < vcr.length(); i++) {
/* 248 */       char c = vcr.charAt(i);
/* 249 */       if (c == '&') {
/* 250 */         v.append(" and ");
/* 251 */       } else if (c == '|') {
/* 252 */         v.append(" or ");
/*     */       } else {
/* 254 */         v.append(c);
/*     */       } 
/*     */     } 
/* 257 */     return v.toString();
/*     */   }
/*     */   
/*     */   protected String encode(List<String> mappings, Map<String, Integer> attributes, String attribute) {
/* 261 */     Integer id = (Integer)attributes.get(attribute);
/* 262 */     if (id == null) {
/* 263 */       id = new Integer(attributes.size() + 1);
/* 264 */       attributes.put(attribute, id);
/* 265 */       mappings.add(attribute);
/*     */     } 
/* 267 */     return Integer.toHexString(id.intValue());
/*     */   }
/*     */   
/*     */   protected String compress(String vcr) {
/* 271 */     Map<Object, Object> attributes = new HashMap<Object, Object>();
/* 272 */     List<E> mappings = new ArrayList();
/* 273 */     StringBuffer v = new StringBuffer(vcr.length());
/* 274 */     StringBuffer attribute = null;
/* 275 */     for (int i = 0; i < vcr.length(); i++) {
/* 276 */       char c = vcr.charAt(i);
/* 277 */       if (c == ':') {
/* 278 */         if (attribute == null) {
/* 279 */           throw new IllegalArgumentException();
/*     */         }
/* 281 */         attribute.append(c);
/* 282 */       } else if (Character.isDigit(c)) {
/* 283 */         if (attribute == null) {
/* 284 */           attribute = new StringBuffer();
/*     */         }
/* 286 */         attribute.append(c);
/*     */       } else {
/* 288 */         if (attribute != null) {
/* 289 */           v.append(encode(mappings, attributes, attribute.toString()));
/* 290 */           attribute = null;
/*     */         } 
/* 292 */         v.append(c);
/*     */       } 
/*     */     } 
/* 295 */     if (attribute != null) {
/* 296 */       v.append(encode(mappings, attributes, attribute.toString()));
/*     */     }
/* 298 */     StringBuffer map = new StringBuffer(attributes.size() * 8);
/* 299 */     map.append('@');
/* 300 */     for (int j = 0; j < mappings.size(); j++) {
/* 301 */       map.append(mappings.get(j).toString());
/* 302 */       if (j < mappings.size() - 1) {
/* 303 */         map.append('@');
/*     */       }
/*     */     } 
/* 306 */     map.append('#');
/* 307 */     return map.toString() + v.toString();
/*     */   }
/*     */   
/*     */   protected String decode(Map attributes, String attribute) {
/*     */     try {
/* 312 */       Integer id = new Integer(Integer.parseInt(attribute, 16));
/* 313 */       return (String)attributes.get(id);
/* 314 */     } catch (Exception x) {
/* 315 */       throw new IllegalArgumentException("invalid hex number: " + attribute);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String expand(String vcr) {
/* 320 */     Map<Object, Object> attributes = new HashMap<Object, Object>();
/* 321 */     StringBuffer v = new StringBuffer(vcr.length());
/* 322 */     v.append('#');
/* 323 */     StringBuffer attribute = new StringBuffer();
/* 324 */     for (int i = 1; i < vcr.length(); i++) {
/* 325 */       char c = vcr.charAt(i);
/* 326 */       if (c == '@' || c == '#') {
/* 327 */         attributes.put(new Integer(attributes.size() + 1), attribute.toString());
/* 328 */         attribute = new StringBuffer();
/* 329 */       } else if (Character.isLetterOrDigit(c) || c == ':') {
/* 330 */         if (attribute == null) {
/* 331 */           attribute = new StringBuffer();
/*     */         }
/* 333 */         attribute.append(c);
/*     */       } else {
/* 335 */         if (attribute != null && attribute.length() > 0) {
/* 336 */           v.append(decode(attributes, attribute.toString()));
/* 337 */           attribute = null;
/*     */         } 
/* 339 */         v.append(c);
/*     */       } 
/*     */     } 
/* 342 */     if (attribute != null && attribute.length() > 0) {
/* 343 */       v.append(decode(attributes, attribute.toString()));
/*     */     }
/* 345 */     return v.toString();
/*     */   }
/*     */   
/*     */   String compressVCR(String vcr) {
/* 349 */     return compress(encode(vcr));
/*     */   }
/*     */   
/*     */   String salvageVCR() {
/* 353 */     if (this.expressions == null || this.expressions.size() == 0) {
/* 354 */       throw new UnsupportedOperationException();
/*     */     }
/* 356 */     VCRExpressionImpl salvage = new VCRExpressionImpl();
/* 357 */     for (int i = 0; i < this.expressions.size(); i++) {
/* 358 */       VCRExpression expression = (VCRExpression)this.expressions.get(i);
/* 359 */       List<VCRTermImpl> terms = expression.getTerms();
/* 360 */       if (terms != null && terms.size() != 0)
/*     */       {
/*     */         
/* 363 */         for (int j = 0; j < terms.size(); j++) {
/* 364 */           VCRTermImpl term = terms.get(j);
/* 365 */           salvage.merge(term);
/*     */         }  } 
/*     */     } 
/* 368 */     this.expressions.clear();
/* 369 */     this.expressions.add((E)salvage);
/* 370 */     return toString();
/*     */   }
/*     */   
/*     */   protected void scan(String v) {
/* 374 */     if (v.startsWith("@")) {
/* 375 */       v = expand(v);
/*     */     }
/* 377 */     if (v.startsWith("#")) {
/* 378 */       v = decode(v);
/*     */     }
/* 380 */     StringTokenizer st = new StringTokenizer(v, "[](): ", true);
/* 381 */     String s = null;
/* 382 */     boolean valid = false;
/*     */     
/* 384 */     while (st.hasMoreTokens()) {
/* 385 */       s = st.nextToken();
/* 386 */       if (s.equals("[")) {
/* 387 */         VCRExpressionImpl vCRExpressionImpl = new VCRExpressionImpl();
/* 388 */         if (st.hasMoreTokens()) {
/* 389 */           s = st.nextToken();
/*     */         }
/* 391 */         valid = vCRExpressionImpl.scan(st, s);
/* 392 */         if (valid)
/* 393 */           this.expressions.add((E)vCRExpressionImpl);  continue;
/*     */       } 
/* 395 */       if (s.equals("or")) {
/* 396 */         valid = false; continue;
/* 397 */       }  if (s.equals(" ")) {
/*     */         continue;
/*     */       }
/* 400 */       VCRExpressionImpl e = new VCRExpressionImpl();
/* 401 */       valid = e.scan(st, s);
/* 402 */       if (valid) {
/* 403 */         this.expressions.add((E)e);
/*     */       }
/*     */     } 
/*     */     
/* 407 */     st = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean identical(ICRImpl other) {
/* 412 */     int noExpressions = this.expressions.size();
/* 413 */     if (noExpressions != other.getExpressions().size()) {
/* 414 */       return false;
/*     */     }
/* 416 */     for (int i = 0; i < noExpressions; i++) {
/* 417 */       if (!other.identicalExpression((VCRExpression)this.expressions.get(i))) {
/* 418 */         return false;
/*     */       }
/*     */     } 
/* 421 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean identicalExpression(VCRExpression e) {
/* 428 */     int noExpressions = this.expressions.size();
/* 429 */     for (int i = 0; i < noExpressions; i++) {
/* 430 */       if (e.identical((VCRExpressionImpl)this.expressions.get(i))) {
/* 431 */         return true;
/*     */       }
/*     */     } 
/* 434 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\implementation\ICRImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */