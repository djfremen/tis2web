/*     */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CTOCElementImpl
/*     */   implements CTOCElement
/*     */ {
/*  20 */   protected static Logger log = Logger.getLogger(CTOCElementImpl.class);
/*     */   
/*  22 */   public static final Comparator COMPARATOR = CTOCComparator.INSTANCE;
/*     */   
/*     */   protected Integer ctocID;
/*     */   
/*     */   protected CTOCType ctocType;
/*     */   
/*     */   protected CTOCElementImpl parent;
/*     */   
/*     */   protected Integer labelID;
/*     */   
/*     */   protected int order;
/*     */   
/*     */   protected List children;
/*     */   
/*     */   protected boolean hasChildren;
/*     */   
/*     */   protected boolean hasContent;
/*     */   
/*     */   protected VCR vcr;
/*     */   
/*     */   private Map properties;
/*     */   
/*     */   protected boolean sorted;
/*     */   
/*     */   private CTOCCache cache;
/*     */   protected int sitVCR;
/*     */   protected ILVCAdapter lvcAdapter;
/*     */   
/*     */   public Integer getID() {
/*  51 */     return this.ctocID;
/*     */   }
/*     */   
/*     */   public SITOCType getType() {
/*  55 */     return this.ctocType;
/*     */   }
/*     */   
/*     */   public CTOCNode getParent() {
/*  59 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isSIO() {
/*  63 */     return false;
/*     */   }
/*     */   
/*     */   public Integer getLabelID() {
/*  67 */     return this.labelID;
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/*  71 */     return getCache().getLabel(this, locale, this.labelID);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  75 */     return this.order;
/*     */   }
/*     */   
/*     */   public synchronized CTOCCache getCache() {
/*  79 */     if (this.cache == null && this.parent != null) {
/*  80 */       this.cache = this.parent.getCache();
/*     */     }
/*  82 */     return this.cache;
/*     */   }
/*     */   
/*     */   private void loadChildren() {
/*  86 */     if (this.children == null) {
/*  87 */       if (this.hasChildren) {
/*  88 */         getCache().loadChildren(this);
/*     */       }
/*  90 */       if (this.hasContent) {
/*  91 */         getCache().loadContent(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized List getChildren() {
/*  98 */     loadChildren();
/*  99 */     if (!this.sorted) {
/* 100 */       sort();
/*     */     }
/* 102 */     return this.children;
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 106 */     return applyFilter(filterSITs, locale, country, vcr);
/*     */   }
/*     */   
/*     */   public List filterSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/* 110 */     return checkSITs(sits, locale, country, vcr);
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/* 114 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public boolean existsChild(Object child) {
/* 118 */     if (this.children == null)
/* 119 */       return false; 
/* 120 */     return this.children.contains(child);
/*     */   }
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/* 124 */     if (property instanceof CTOCProperty) {
/* 125 */       return getProperties().containsKey(property);
/*     */     }
/* 127 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/* 132 */     if (property instanceof CTOCProperty) {
/* 133 */       if (property.ord() == CTOCProperty.ECM_LIST.ord()) {
/* 134 */         loadPropertyListECM();
/* 135 */       } else if (property.ord() == CTOCProperty.DTC_LIST.ord()) {
/* 136 */         loadPropertyListDTC();
/* 137 */       } else if (property.ord() == CTOCProperty.COMPONENT_LIST.ord()) {
/* 138 */         loadPropertyListComponents();
/*     */       } 
/* 140 */       return getProperties().get(property);
/*     */     } 
/* 142 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, VCR vcr) {
/* 147 */     return (this.vcr == null) ? true : this.vcr.match(locale, vcr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCElementImpl(int tocID, Integer labelID, int order, int ctocType, boolean hasChildren, boolean hasContent, VCR vcr, ILVCAdapter adapter) {
/* 153 */     this.lvcAdapter = adapter;
/* 154 */     this.ctocID = Integer.valueOf(tocID);
/* 155 */     this.labelID = labelID;
/* 156 */     this.order = order;
/* 157 */     this.ctocType = CTOCType.get(ctocType);
/* 158 */     if (this.ctocType == null) {
/* 159 */       log.error("invalid ctoc type '" + ctocType + "' encountered (ctoc=" + tocID + ").");
/*     */     }
/* 161 */     this.hasChildren = hasChildren;
/* 162 */     this.hasContent = hasContent;
/* 163 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public void setParent(CTOCElement parent) {
/* 167 */     this.parent = (CTOCElementImpl)parent;
/*     */   }
/*     */   
/*     */   public void setCache(CTOCCache cache) {
/* 171 */     this.cache = cache;
/*     */   }
/*     */   
/*     */   public void add(CTOCProperty property, Object value) {
/* 175 */     getProperties().put(property, value);
/*     */   }
/*     */   
/*     */   public void setProperties(Map properties) {
/* 179 */     this.properties = properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(SITOCElement node) {
/* 184 */     if (this.children == null) {
/* 185 */       this.children = new LinkedList();
/*     */     }
/* 187 */     if (this.ctocID.intValue() == 0 && 
/* 188 */       hasChild(node)) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     this.children.add(node);
/* 193 */     if (node instanceof CTOCElementImpl) {
/* 194 */       ((CTOCElementImpl)node).setParent(this);
/* 195 */       ((CTOCElementImpl)node).setCache(getCache());
/*     */     } 
/* 197 */     this.sorted = false;
/*     */   }
/*     */   
/*     */   protected boolean hasChild(SITOCElement node) {
/* 201 */     Iterator<SITOCElement> it = this.children.iterator();
/* 202 */     while (it.hasNext()) {
/* 203 */       SITOCElement object = it.next();
/* 204 */       if (node.getID().intValue() == object.getID().intValue())
/* 205 */         return true; 
/*     */     } 
/* 207 */     return false;
/*     */   }
/*     */   
/*     */   public void shareChildren(List children) {
/* 211 */     this.children = children;
/* 212 */     this.hasChildren = (children != null);
/* 213 */     this.sorted = true;
/*     */   }
/*     */   
/*     */   public void swap(CTOCElement node, CTOCElement update) {
/* 217 */     if (this.children == null) {
/* 218 */       throw new IllegalArgumentException();
/*     */     }
/* 220 */     for (int i = 0; i < this.children.size(); i++) {
/* 221 */       if (this.children.get(i) == node) {
/* 222 */         this.children.set(i, update);
/*     */         return;
/*     */       } 
/*     */     } 
/* 226 */     throw new IllegalArgumentException("swap update node failed");
/*     */   }
/*     */   
/*     */   public synchronized Map getProperties() {
/* 230 */     if (this.properties == null) {
/* 231 */       if (getCache() != null) {
/* 232 */         this.properties = getCache().getProperties(this);
/*     */       }
/* 234 */       if (this.properties == null) {
/* 235 */         this.properties = new HashMap<Object, Object>();
/*     */       }
/*     */     } 
/* 238 */     return this.properties;
/*     */   }
/*     */   
/*     */   protected void sort() {
/* 242 */     if (this.children != null && this.children.size() > 1) {
/* 243 */       Collections.sort(this.children, CTOCComparator.INSTANCE);
/*     */     }
/* 245 */     this.sorted = true;
/*     */   }
/*     */   
/*     */   protected List applyFilter(List<String> filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 249 */     List<SITOCElement> result = new LinkedList();
/* 250 */     List<SITOCElement> children = getChildren();
/*     */     
/* 252 */     if (children != null) {
/* 253 */       for (int i = 0; i < children.size(); i++) {
/* 254 */         SITOCElement child = children.get(i);
/* 255 */         if (!checkChild(child, locale, country, vcr)) {
/*     */           continue;
/*     */         }
/* 258 */         if (filterSITs != null && !child.isSIO()) {
/* 259 */           if (child.hasProperty(CTOCProperty.AssemblyGroup)) {
/*     */             
/* 261 */             boolean match = false;
/* 262 */             for (int s = 0; s < filterSITs.size(); s++) {
/* 263 */               String sit = filterSITs.get(s);
/* 264 */               if (checkSIT((CTOCNode)child, sit, locale, country, vcr)) {
/* 265 */                 match = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 269 */             if (!match) {
/* 270 */               debug("assembly group w/o matching sit / applicable sio", child);
/*     */               
/*     */               continue;
/*     */             } 
/* 274 */           } else if (child.hasProperty(CTOCProperty.SIT)) {
/*     */             
/* 276 */             String sit = (String)child.getProperty(CTOCProperty.SIT);
/* 277 */             boolean match = false;
/* 278 */             for (int s = 0; s < filterSITs.size(); s++) {
/* 279 */               String fsit = filterSITs.get(s);
/* 280 */               if (sit.equals(fsit) || fsit.equals("SIT-" + sit)) {
/*     */                 
/* 282 */                 match = checkSIT((CTOCNode)child, locale, country, vcr);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 287 */             if (!match) {
/* 288 */               debug("no applicable sit/sio", child);
/*     */               continue;
/*     */             } 
/* 291 */           } else if (child.hasProperty(CTOCProperty.SITQ)) {
/*     */             
/* 293 */             if (!checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 294 */               debug("sitq w/o applicable sio", child);
/*     */               continue;
/*     */             } 
/*     */           } 
/* 298 */         } else if (!child.isSIO()) {
/*     */           
/* 300 */           if (!checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 301 */             debug("ctoc-node w/o applicable sio", child);
/*     */             continue;
/*     */           } 
/*     */         } 
/* 305 */         result.add(child);
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*     */     
/* 311 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   protected boolean isSCDS(SITOCElement child) {
/* 315 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkChild(SITOCElement child, LocaleInfo locale, String country, VCR vcr) {
/* 320 */     if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && !child.getVCR().match(locale, vcr)) {
/* 321 */       debug("non-matching vcr = " + child.getVCR(), child);
/* 322 */       return false;
/*     */     } 
/* 324 */     if (child.isSIO() && child.hasProperty((SITOCProperty)SIOProperty.LU)) {
/*     */       
/* 326 */       if (isSCDS(child) && !getCache().isSupportedLocaleSCDS(locale)) {
/* 327 */         return false;
/*     */       }
/* 329 */       if (child.getLabel(locale) == null) {
/* 330 */         debug("no available subject.", child);
/* 331 */         return false;
/*     */       } 
/* 333 */       if (country != null && child.hasProperty((SITOCProperty)SIOProperty.NonMarketConstraint)) {
/* 334 */         String nmc = (String)child.getProperty((SITOCProperty)SIOProperty.NonMarketConstraint);
/* 335 */         if (nmc.indexOf(country) >= 0) {
/* 336 */           debug("non-applicable market(s) = " + nmc, child);
/* 337 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   public List getChildrenSIT(LocaleInfo locale, String country, VCR vcr) {
/* 345 */     List<SITOCElement> result = new LinkedList();
/* 346 */     List<SITOCElement> children = getChildren();
/* 347 */     if (children != null) {
/* 348 */       for (int i = 0; i < children.size(); i++) {
/* 349 */         SITOCElement child = children.get(i);
/* 350 */         if (checkChild(child, locale, country, vcr)) {
/* 351 */           if (child.isSIO()) {
/* 352 */             result.clear();
/* 353 */             return result;
/*     */           } 
/* 355 */           result.add(child);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 360 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   public List getChildren(LocaleInfo locale, String country, VCR vcr) {
/* 364 */     List<SITOCElement> result = new LinkedList();
/* 365 */     List<SITOCElement> children = getChildren();
/* 366 */     if (children != null)
/* 367 */       for (int i = 0; i < children.size(); i++) {
/* 368 */         SITOCElement child = children.get(i);
/* 369 */         if (checkChild(child, locale, country, vcr))
/*     */         {
/*     */           
/* 372 */           result.add(child);
/*     */         }
/*     */       }  
/* 375 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   protected List checkSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/* 379 */     if (sits != null) {
/* 380 */       List<CTOCNode> result = new LinkedList();
/* 381 */       Iterator<CTOCNode> it = sits.getChildren().iterator();
/* 382 */       while (it.hasNext()) {
/* 383 */         CTOCNode sit = it.next();
/* 384 */         String candidate = (String)sit.getProperty(CTOCProperty.SIT);
/* 385 */         if (checkSIT(this, candidate, locale, country, vcr)) {
/* 386 */           result.add(sit);
/*     */         }
/*     */       } 
/* 389 */       return (result.size() > 0) ? result : null;
/*     */     } 
/* 391 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchSIT(SITOCElement node, String sit) {
/* 396 */     String s = (String)node.getProperty(CTOCProperty.SIT);
/* 397 */     return (s == null) ? false : sit.equals("SIT-" + s);
/*     */   }
/*     */   
/*     */   protected boolean checkSIT(CTOCNode node, String sit, LocaleInfo locale, String country, VCR vcr) {
/* 401 */     List<SITOCElement> children = node.getChildren();
/* 402 */     if (children != null) {
/* 403 */       for (int i = 0; i < children.size(); i++) {
/* 404 */         SITOCElement child = children.get(i);
/* 405 */         if (!child.isSIO()) {
/* 406 */           if (child.hasProperty(CTOCProperty.AssemblyGroup)) {
/*     */             
/* 408 */             if (checkSIT((CTOCNode)child, sit, locale, country, vcr)) {
/* 409 */               return true;
/*     */             }
/* 411 */           } else if (matchSIT(child, sit)) {
/*     */             
/* 413 */             if (checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 414 */               return true;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 420 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkSIT(CTOCNode node, LocaleInfo locale, String country, VCR vcr) {
/* 425 */     List<SITOCElement> children = ((CTOCElementImpl)node).getChildrenSIT(locale, country, vcr);
/* 426 */     if (children != null) {
/* 427 */       if (children.size() == 0)
/* 428 */         return true; 
/* 429 */       for (int i = 0; i < children.size(); i++) {
/* 430 */         SITOCElement child = children.get(i);
/* 431 */         if (child.isSIO() || checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 432 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   public void release() {
/* 440 */     this.ctocID = null;
/* 441 */     this.ctocType = null;
/* 442 */     this.parent = null;
/* 443 */     this.labelID = null;
/*     */     
/* 445 */     if (this.children != null) {
/* 446 */       this.children.clear();
/*     */     }
/* 448 */     this.children = null;
/* 449 */     this.vcr = null;
/* 450 */     this.properties = null;
/* 451 */     this.cache = null;
/*     */   }
/*     */   
/*     */   protected void loadPropertyListECM() {
/* 455 */     if (hasProperty(CTOCProperty.ECM_LIST) && 
/* 456 */       getProperties().get(CTOCProperty.ECM_LIST) instanceof String) {
/* 457 */       String key = (String)getProperties().get(CTOCProperty.ECM_LIST);
/* 458 */       Integer tocid = Integer.valueOf(key);
/* 459 */       CTOCNode ecms = getCache().loadNode(tocid);
/* 460 */       getProperties().put(CTOCProperty.ECM_LIST, ecms);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadPropertyListDTC() {
/* 466 */     if (hasProperty(CTOCProperty.DTC_LIST) && 
/* 467 */       getProperties().get(CTOCProperty.DTC_LIST) instanceof String) {
/* 468 */       String key = (String)getProperties().get(CTOCProperty.DTC_LIST);
/* 469 */       Integer tocid = Integer.valueOf(key);
/* 470 */       CTOCNode dtcs = getCache().loadNode(tocid);
/* 471 */       getProperties().put(CTOCProperty.DTC_LIST, dtcs);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadPropertyListComponents() {
/* 477 */     if (hasProperty(CTOCProperty.COMPONENT_LIST) && 
/* 478 */       getProperties().get(CTOCProperty.COMPONENT_LIST) instanceof String) {
/* 479 */       String key = (String)getProperties().get(CTOCProperty.COMPONENT_LIST);
/* 480 */       Integer tocid = Integer.valueOf(key);
/* 481 */       CTOCNode components = getCache().loadNode(tocid);
/* 482 */       getProperties().put(CTOCProperty.COMPONENT_LIST, components);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void debug(String msg, SITOCElement node) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureExistingChildren() {
/* 501 */     if (this.hasChildren && (this.children == null || this.children.size() == 0)) {
/* 502 */       this.hasChildren = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVCRSIT() {
/* 508 */     return this.sitVCR;
/*     */   }
/*     */   
/*     */   public void setVCRSIT(int sit) {
/* 512 */     this.sitVCR = sit;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */