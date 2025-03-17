/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.CTOCImpl2;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCLabel;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCType;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IONode
/*     */   implements CTOCNode
/*     */ {
/*  32 */   protected static Logger log = Logger.getLogger(CTOCImpl2.class);
/*     */   
/*     */   protected Integer ctocID;
/*     */   
/*     */   protected CTOCType ctocType;
/*     */   
/*     */   protected CTOCNode parent;
/*     */   
/*     */   protected CTOCLabel label;
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
/*     */   protected Map properties;
/*     */   
/*     */   protected boolean sorted;
/*     */   
/*     */   protected CTOCCache cache;
/*     */   
/*     */   private ILVCAdapter.Retrieval lvcr;
/*     */   
/*     */   public IONode clone(int id) {
/*  61 */     IONode copy = new IONode(id, this.label, this.order, this.ctocType.ord(), this.hasChildren, this.hasContent, this.vcr, this.lvcr);
/*  62 */     copy.properties = this.properties;
/*  63 */     copy.children = this.children;
/*  64 */     return copy;
/*     */   }
/*     */   
/*     */   public IONode copy(int id, VCR vcr) {
/*  68 */     IONode copy = new IONode(id, this.label, this.order, this.ctocType.ord(), this.hasChildren, this.hasContent, vcr, this.lvcr);
/*  69 */     copy.properties = this.properties;
/*  70 */     copy.children = new LinkedList();
/*  71 */     return copy;
/*     */   }
/*     */   
/*     */   public Integer getID() {
/*  75 */     return this.ctocID;
/*     */   }
/*     */   
/*     */   public SITOCType getType() {
/*  79 */     return (SITOCType)this.ctocType;
/*     */   }
/*     */   
/*     */   public CTOCNode getParent() {
/*  83 */     return this.parent;
/*     */   }
/*     */   
/*     */   public boolean isSIO() {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/*  92 */     return this.label.get(locale);
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  96 */     return this.order;
/*     */   }
/*     */   
/*     */   public List getChildren() {
/* 100 */     if (!this.sorted) {
/* 101 */       sort();
/*     */     }
/* 103 */     return this.children;
/*     */   }
/*     */   
/*     */   public List getChildren(List filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 107 */     return applyFilter(filterSITs, locale, country, vcr);
/*     */   }
/*     */   
/*     */   public List filterSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/* 111 */     return checkSITs(sits, locale, country, vcr);
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/* 115 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public void modifyVCR(VCR vcr) {
/* 119 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/* 123 */     if (property instanceof CTOCProperty) {
/* 124 */       if (this.properties == null) {
/* 125 */         loadProperties();
/*     */       }
/* 127 */       return this.properties.containsKey(property);
/*     */     } 
/* 129 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/* 134 */     if (property instanceof CTOCProperty) {
/* 135 */       if (this.properties == null) {
/* 136 */         loadProperties();
/*     */       }
/* 138 */       return this.properties.get(property);
/*     */     } 
/* 140 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, VCR vcr) {
/* 145 */     return (this.vcr == null) ? true : this.vcr.match(locale, vcr);
/*     */   }
/*     */   
/*     */   public IONode(CTOCType ctocType) {
/* 149 */     this.ctocType = ctocType;
/*     */   }
/*     */   
/*     */   public IONode(int tocID, CTOCLabel label, int order, int ctocType, boolean hasChildren, boolean hasContent, VCR vcr, ILVCAdapter.Retrieval lvcr) {
/* 153 */     this.ctocID = Integer.valueOf(tocID);
/* 154 */     this.label = label;
/* 155 */     this.order = order;
/* 156 */     this.ctocType = CTOCType.get(ctocType);
/* 157 */     if (this.ctocType == null) {
/* 158 */       log.error("invalid ctoc type '" + ctocType + "' encountered (ctoc=" + tocID + ").");
/*     */     }
/* 160 */     this.hasChildren = hasChildren;
/* 161 */     this.hasContent = hasContent;
/* 162 */     this.vcr = vcr;
/* 163 */     this.lvcr = lvcr;
/*     */   }
/*     */   
/*     */   public void setParent(CTOCNode parent) {
/* 167 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public void setCache(CTOCCache cache) {
/* 171 */     this.cache = cache;
/*     */   }
/*     */   
/*     */   public void setOrder(int order) {
/* 175 */     this.order = order;
/*     */   }
/*     */   
/*     */   public void add(CTOCProperty property, Object value) {
/* 179 */     if (this.properties == null) {
/* 180 */       this.properties = new HashMap<Object, Object>();
/*     */     }
/* 182 */     this.properties.put(property, value);
/*     */   }
/*     */   
/*     */   public void add(SITOCElement node) {
/* 186 */     if (this.children == null) {
/* 187 */       this.children = new LinkedList();
/*     */     }
/* 189 */     if (this.children.contains(node)) {
/* 190 */       throw new IllegalArgumentException();
/*     */     }
/* 192 */     this.children.add(node);
/* 193 */     if (node instanceof IONode) {
/* 194 */       ((IONode)node).setParent(this);
/* 195 */       ((IONode)node).setCache(this.cache);
/*     */     } 
/* 197 */     this.sorted = false;
/*     */   }
/*     */   
/*     */   public void shareChildren(ArrayList children) {
/* 201 */     this.children = children;
/* 202 */     this.sorted = true;
/*     */   }
/*     */   
/*     */   public void swap(CTOCElement node, CTOCElement update) {
/* 206 */     if (this.children == null) {
/* 207 */       throw new IllegalArgumentException();
/*     */     }
/* 209 */     for (int i = 0; i < this.children.size(); i++) {
/* 210 */       if (this.children.get(i) == node) {
/* 211 */         this.children.set(i, update);
/*     */         return;
/*     */       } 
/*     */     } 
/* 215 */     throw new IllegalArgumentException("swap update node failed");
/*     */   }
/*     */   
/*     */   protected void loadProperties() {
/* 219 */     this.properties = new HashMap<Object, Object>();
/*     */     
/* 221 */     this.cache.loadProperties(this);
/*     */   }
/*     */   
/*     */   protected void sort() {
/* 225 */     if (this.children != null && this.children.size() > 1) {
/* 226 */       Collections.sort(this.children, CTOCElementImpl.COMPARATOR);
/*     */     }
/* 228 */     this.sorted = true;
/*     */   }
/*     */   
/*     */   protected List applyFilter(List<String> filterSITs, LocaleInfo locale, String country, VCR vcr) {
/* 232 */     debug("apply filter", (SITOCElement)this);
/* 233 */     List<SITOCElement> result = new LinkedList();
/* 234 */     List<SITOCElement> children = getChildren();
/* 235 */     if (children != null) {
/* 236 */       for (int i = 0; i < children.size(); i++) {
/* 237 */         SITOCElement child = children.get(i);
/* 238 */         if (!checkChild(child, locale, country, vcr)) {
/*     */           continue;
/*     */         }
/* 241 */         if (filterSITs != null && !child.isSIO()) {
/* 242 */           if (child.hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*     */             
/* 244 */             boolean match = false;
/* 245 */             for (int s = 0; s < filterSITs.size(); s++) {
/* 246 */               String sit = filterSITs.get(s);
/* 247 */               if (checkSIT((CTOCNode)child, sit, locale, country, vcr)) {
/* 248 */                 match = true;
/*     */                 break;
/*     */               } 
/*     */             } 
/* 252 */             if (!match) {
/* 253 */               debug("assembly group w/o matching sit / applicable sio", child);
/*     */               
/*     */               continue;
/*     */             } 
/* 257 */           } else if (child.hasProperty((SITOCProperty)CTOCProperty.SIT)) {
/*     */             
/* 259 */             String sit = (String)child.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 260 */             boolean match = false;
/* 261 */             for (int s = 0; s < filterSITs.size(); s++) {
/* 262 */               String fsit = filterSITs.get(s);
/* 263 */               if (sit.equals(fsit) || fsit.equals("SIT-" + sit)) {
/*     */                 
/* 265 */                 match = checkSIT((CTOCNode)child, locale, country, vcr);
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 270 */             if (!match) {
/* 271 */               debug("no applicable sit/sio", child);
/*     */               continue;
/*     */             } 
/* 274 */           } else if (child.hasProperty((SITOCProperty)CTOCProperty.SITQ)) {
/*     */             
/* 276 */             if (!checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 277 */               debug("sitq w/o applicable sio", child);
/*     */               continue;
/*     */             } 
/*     */           } 
/* 281 */         } else if (!child.isSIO()) {
/*     */           
/* 283 */           if (!checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 284 */             debug("ctoc-node w/o applicable sio", child);
/*     */             continue;
/*     */           } 
/*     */         } 
/* 288 */         result.add(child);
/*     */         
/*     */         continue;
/*     */       } 
/*     */     }
/*     */     
/* 294 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkChild(SITOCElement child, LocaleInfo locale, String country, VCR vcr) {
/* 299 */     if (vcr != null && vcr != VCR.NULL && child.getVCR() != null && !child.getVCR().match(locale, vcr)) {
/* 300 */       debug("non-matching vcr = " + child.getVCR(), child);
/* 301 */       return false;
/*     */     } 
/* 303 */     if (child.isSIO() && child.hasProperty((SITOCProperty)SIOProperty.LU)) {
/*     */       
/* 305 */       if (child.getLabel(locale) == null) {
/* 306 */         debug("no available subject.", child);
/* 307 */         return false;
/*     */       } 
/* 309 */       if (country != null && child.hasProperty((SITOCProperty)SIOProperty.NonMarketConstraint)) {
/* 310 */         String nmc = (String)child.getProperty((SITOCProperty)SIOProperty.NonMarketConstraint);
/* 311 */         if (nmc.indexOf(country) >= 0) {
/* 312 */           debug("non-applicable market(s) = " + nmc, child);
/* 313 */           return false;
/*     */         } 
/*     */       } 
/*     */     } 
/* 317 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List getChildren(LocaleInfo locale, String country, VCR vcr) {
/* 322 */     List<SITOCElement> result = new LinkedList();
/* 323 */     List<SITOCElement> children = getChildren();
/* 324 */     if (children != null)
/* 325 */       for (int i = 0; i < children.size(); i++) {
/* 326 */         SITOCElement child = children.get(i);
/* 327 */         if (checkChild(child, locale, country, vcr))
/*     */         {
/*     */           
/* 330 */           result.add(child);
/*     */         }
/*     */       }  
/* 333 */     return (result.size() > 0) ? result : null;
/*     */   }
/*     */   
/*     */   protected List checkSITs(CTOCNode sits, LocaleInfo locale, String country, VCR vcr) {
/* 337 */     if (sits != null) {
/* 338 */       List<CTOCNode> result = new LinkedList();
/* 339 */       Iterator<CTOCNode> it = sits.getChildren().iterator();
/* 340 */       while (it.hasNext()) {
/* 341 */         CTOCNode sit = it.next();
/* 342 */         String candidate = (String)sit.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 343 */         if (checkSIT(this, candidate, locale, country, vcr)) {
/* 344 */           result.add(sit);
/*     */         }
/*     */       } 
/* 347 */       return (result.size() > 0) ? result : null;
/*     */     } 
/* 349 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean matchSIT(SITOCElement node, String sit) {
/* 354 */     String s = (String)node.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 355 */     return (s == null) ? false : sit.equals("SIT-" + s);
/*     */   }
/*     */   
/*     */   protected boolean checkSIT(CTOCNode node, String sit, LocaleInfo locale, String country, VCR vcr) {
/* 359 */     List<SITOCElement> children = node.getChildren();
/* 360 */     if (children != null) {
/* 361 */       for (int i = 0; i < children.size(); i++) {
/* 362 */         SITOCElement child = children.get(i);
/* 363 */         if (!child.isSIO()) {
/* 364 */           if (child.hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*     */             
/* 366 */             if (checkSIT((CTOCNode)child, sit, locale, country, vcr)) {
/* 367 */               return true;
/*     */             }
/* 369 */           } else if (matchSIT(child, sit)) {
/*     */             
/* 371 */             if (checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 372 */               return true;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/* 378 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkSIT(CTOCNode node, LocaleInfo locale, String country, VCR vcr) {
/* 382 */     List<SITOCElement> children = ((CTOCElementImpl)node).getChildrenSIT(locale, country, vcr);
/* 383 */     if (children != null) {
/* 384 */       if (children.size() == 0)
/* 385 */         return true; 
/* 386 */       for (int i = 0; i < children.size(); i++) {
/* 387 */         SITOCElement child = children.get(i);
/* 388 */         if (child.isSIO() || checkSIT((CTOCNode)child, locale, country, vcr)) {
/* 389 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 393 */     return false;
/*     */   }
/*     */   
/*     */   protected void debug(String msg, SITOCElement node) {}
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\IONode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */