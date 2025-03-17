/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CTOCSurrogateImpl
/*     */   implements CTOCSurrogate
/*     */ {
/*  31 */   protected static Logger log = Logger.getLogger(CTOCSurrogateImpl.class);
/*     */   
/*     */   protected Integer ctocID;
/*     */   
/*     */   protected CTOCType ctocType;
/*     */   
/*     */   protected String sit;
/*     */   
/*     */   protected CTOCElement parent;
/*     */   
/*     */   protected Integer labelID;
/*     */   
/*     */   protected int order;
/*     */   
/*     */   protected List children;
/*     */   
/*     */   protected CTOCCache cache;
/*     */   
/*     */   public Integer getID() {
/*  50 */     return this.ctocID;
/*     */   }
/*     */   
/*     */   public SITOCType getType() {
/*  54 */     return (SITOCType)this.ctocType;
/*     */   }
/*     */   
/*     */   public CTOCNode getParent() {
/*  58 */     return (CTOCNode)this.parent;
/*     */   }
/*     */   
/*     */   public boolean isSIO() {
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   protected String getSIT() {
/*  66 */     return this.sit;
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/*  70 */     if (this.cache == null)
/*  71 */       return null; 
/*  72 */     return this.cache.getLabel((CTOCNode)this, locale, this.labelID);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  76 */     return this.order;
/*     */   }
/*     */   
/*     */   public List getChildren() {
/*  80 */     return this.children;
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/*  84 */     List children = applyFilter(filterSITs, locale, country, vcr, 0);
/*  85 */     return children;
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr, int cutoff) {
/*  89 */     List children = applyFilter(filterSITs, locale, country, vcr, cutoff);
/*  90 */     return children;
/*     */   }
/*     */   
/*     */   public List filterSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/*  94 */     return filterSITs(sits);
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, VCR vcr) {
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public CTOCSurrogateImpl() {
/* 114 */     this.ctocID = null;
/* 115 */     this.ctocType = CTOCType.CTOC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCSurrogateImpl(CTOCElement proxy) {
/* 121 */     this.ctocID = proxy.getID();
/* 122 */     this.ctocType = CTOCType.get(proxy.getType().ord());
/* 123 */     this.sit = (String)proxy.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 124 */     this.labelID = proxy.getLabelID();
/* 125 */     this.order = proxy.getOrder();
/* 126 */     this.cache = ((CTOCElementImpl)proxy).getCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCSurrogateImpl(SI.Retrieval siRetrieval, ILVCAdapter.Retrieval lvcRetrieval, FTSService.Retrieval ftsRetrieval) {
/* 131 */     this.ctocID = null;
/* 132 */     this.ctocType = CTOCType.CTOC;
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCSurrogateImpl(CTOCElement proxy, SI.Retrieval siRetrieval, ILVCAdapter.Retrieval lvcRetrieval, FTSService.Retrieval ftsRetrieval) {
/* 137 */     this.ctocID = proxy.getID();
/* 138 */     this.ctocType = CTOCType.get(proxy.getType().ord());
/* 139 */     this.sit = (String)proxy.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 140 */     this.labelID = proxy.getLabelID();
/* 141 */     this.order = proxy.getOrder();
/* 142 */     this.cache = ((CTOCElementImpl)proxy).getCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParent(CTOCElement parent) {
/* 147 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public void add(SITOCElement node) {
/* 151 */     if (this.children == null) {
/* 152 */       this.children = new LinkedList();
/*     */     }
/* 154 */     if (this.children.contains(node)) {
/* 155 */       throw new IllegalArgumentException();
/*     */     }
/* 157 */     this.children.add(node);
/*     */   }
/*     */   
/*     */   public void add(List<SITOCElement> nodes) {
/* 161 */     if (this.children == null)
/* 162 */     { this.children = new LinkedList<SITOCElement>(nodes); }
/* 163 */     else if (this.children.size() == 0)
/* 164 */     { this.children.addAll(nodes); }
/* 165 */     else { if (CollectionUtil.haveIntersection(this.children, nodes)) {
/* 166 */         throw new IllegalArgumentException();
/*     */       }
/* 168 */       this.children.addAll(nodes); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void append(SITOCElement node) {
/* 174 */     if (this.children == null) {
/* 175 */       this.children = new LinkedList();
/*     */     }
/* 177 */     this.children.add(node);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchSIT(SITOCElement node, String sit) {
/* 182 */     String s = (String)node.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 183 */     return (s == null) ? false : sit.equals("SIT-" + s);
/*     */   }
/*     */   
/*     */   protected boolean checkChild(SITOCElement child, LocaleInfo locale, String country, VCR vcr) {
/* 187 */     if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && !child.getVCR().match(locale, vcr)) {
/* 188 */       return false;
/*     */     }
/* 190 */     return checkChildNMC(child, locale, country);
/*     */   }
/*     */   
/*     */   protected boolean checkChildBySearchNumber(SITOCElement child, LocaleInfo locale, String country, VCR vcr) {
/* 194 */     if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && child.getVCR() != VCR.NULL && child.getVCR().match(locale)) {
/* 195 */       String vcrChild = child.getVCR().getMakes().toString();
/* 196 */       String vcrContext = vcr.getMakes().toString().replace("[", "");
/* 197 */       vcrContext = vcrContext.replace("]", "");
/* 198 */       if (vcrChild.indexOf(vcrContext) == -1) {
/* 199 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return checkChildNMC(child, locale, country);
/*     */   }
/*     */   
/*     */   protected boolean checkChildNMC(SITOCElement child, LocaleInfo locale, String country) {
/* 207 */     if (child.isSIO() && child.hasProperty((SITOCProperty)SIOProperty.LU)) {
/*     */       
/* 209 */       if (child.getLabel(locale) == null) {
/* 210 */         return false;
/*     */       }
/* 212 */       if (country != null && child.hasProperty((SITOCProperty)SIOProperty.NonMarketConstraint)) {
/* 213 */         String nmc = (String)child.getProperty((SITOCProperty)SIOProperty.NonMarketConstraint);
/* 214 */         if (nmc.indexOf(country) >= 0) {
/* 215 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 219 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean checkChildbyLT(SITOCElement child, LocaleInfo locale, VCR vcr) {
/* 223 */     return checkChildVCR(child, locale, vcr);
/*     */   }
/*     */   
/*     */   protected boolean checkChildren(CTOCNode node, LocaleInfo locale, String country, VCR vcr) {
/* 227 */     List<SITOCElement> children = node.getChildren();
/* 228 */     if (children != null) {
/* 229 */       for (int i = 0; i < children.size(); i++) {
/* 230 */         SITOCElement child = children.get(i);
/* 231 */         if (child.isSIO())
/*     */         {
/* 233 */           if (checkChild(child, locale, country, vcr)) {
/* 234 */             return true;
/*     */           }
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 240 */     return false;
/*     */   }
/*     */   
/*     */   public List getChildren(CTOCNode node, LocaleInfo locale, String country, VCR vcr) {
/* 244 */     List<SITOCElement> children = node.getChildren();
/* 245 */     List<SITOCElement> result = new LinkedList();
/* 246 */     if (children != null) {
/* 247 */       for (int i = 0; i < children.size(); i++) {
/* 248 */         SITOCElement child = children.get(i);
/* 249 */         if (child.isSIO() && 
/* 250 */           checkChildBySearchNumber(child, locale, country, vcr)) {
/* 251 */           result.add(child);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 256 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean checkSITs(List<CTOCNode> sits, String fsit) {
/* 260 */     for (int i = 0; i < sits.size(); i++) {
/* 261 */       CTOCNode sit = sits.get(i);
/* 262 */       if (sit.hasProperty((SITOCProperty)CTOCProperty.SIT)) {
/*     */ 
/*     */         
/* 265 */         String s = (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 266 */         if (s.equals(fsit) || s.equals("SIT-" + fsit))
/*     */         {
/* 268 */           return true; } 
/*     */       } 
/*     */     } 
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List applyFilter(List<String> filterSITs, LocaleInfo locale, String country, VCR vcr, int cutoff) {
/* 276 */     List<SITOCElement> children = getChildren();
/*     */     
/* 278 */     if (children != null) {
/* 279 */       List<SITOCElement> result = new LinkedList();
/* 280 */       for (int i = 0; i < children.size(); i++) {
/* 281 */         SITOCElement child = children.get(i);
/*     */         
/* 283 */         if (child instanceof com.eoos.gm.tis2web.lt.implementation.io.db.SIOLTElement && !checkChildbyLT(child, locale, vcr)) {
/*     */           continue;
/*     */         }
/*     */         
/* 287 */         if (child.isSIO() && !checkChild(child, locale, country, vcr)) {
/*     */           continue;
/*     */         }
/* 290 */         if (filterSITs != null && !child.isSIO() && child instanceof CTOCSurrogateImpl) {
/* 291 */           String sit = ((CTOCSurrogateImpl)child).getSIT();
/* 292 */           boolean match = false;
/* 293 */           for (int s = 0; s < filterSITs.size(); s++) {
/* 294 */             String fsit = filterSITs.get(s);
/* 295 */             if (sit.equals(fsit) || fsit.equals("SIT-" + sit)) {
/*     */               
/* 297 */               match = checkChildren((CTOCNode)child, locale, country, vcr);
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 302 */           if (!match) {
/*     */             continue;
/*     */           }
/* 305 */         } else if (!child.isSIO() && child instanceof CTOCSurrogateImpl) {
/* 306 */           if (!checkChildren((CTOCNode)child, locale, country, vcr)) {
/*     */             continue;
/*     */           }
/* 309 */         } else if (filterSITs != null && child.isSIO()) {
/* 310 */           if (child instanceof CTOCSurrogateImpl)
/*     */           {
/* 312 */             throw new IllegalArgumentException();
/*     */           }
/*     */ 
/*     */           
/* 316 */           if (child instanceof com.eoos.gm.tis2web.si.service.cai.SIO) {
/* 317 */             List sits = (List)child.getProperty((SITOCProperty)SIOProperty.SIT);
/* 318 */             if (Util.isNullOrEmpty(sits)) {
/*     */               continue;
/*     */             }
/* 321 */             boolean match = false;
/* 322 */             for (int s = 0; s < filterSITs.size(); s++) {
/* 323 */               String fsit = filterSITs.get(s);
/* 324 */               if (checkSITs(sits, fsit)) {
/*     */                 
/* 326 */                 match = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 330 */             if (!match) {
/*     */               continue;
/*     */             }
/*     */           } else {
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */         
/* 338 */         result.add(child);
/* 339 */         if (cutoff > 0 && result.size() == cutoff)
/* 340 */           return result; 
/*     */         continue;
/*     */       } 
/* 343 */       return (result.size() > 0) ? result : null;
/*     */     } 
/* 345 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List filterSITs(CTOCNode sits) {
/* 351 */     List<CTOCNode> result = new LinkedList();
/* 352 */     Iterator<CTOCNode> it = sits.getChildren().iterator();
/* 353 */     while (it.hasNext()) {
/* 354 */       CTOCNode sit = it.next();
/* 355 */       result.add(sit);
/*     */     } 
/* 357 */     return result;
/*     */   }
/*     */   
/*     */   protected boolean checkChildVCR(SITOCElement child, LocaleInfo locale, VCR vcr) {
/* 361 */     if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && !child.getVCR().match(locale, vcr)) {
/* 362 */       return false;
/*     */     }
/* 364 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\CTOCSurrogateImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */