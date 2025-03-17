/*     */ package com.eoos.gm.tis2web.ctoc.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.CTOCRootElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCCache;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElementImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class CTOCCache
/*     */   extends CTOCCache
/*     */ {
/*     */   protected CTOCStore store;
/*  25 */   protected static HashSet languagesSCDS = new HashSet(); static {
/*  26 */     String languages = ApplicationContext.getInstance().getProperty("component.si.languages.scds");
/*  27 */     if (languages != null) {
/*  28 */       StringTokenizer tokenizer = new StringTokenizer(languages, ",");
/*  29 */       while (tokenizer.hasMoreTokens()) {
/*  30 */         LocaleInfo locale = LocaleInfoProvider.getInstance().getLocale(tokenizer.nextToken());
/*  31 */         if (locale != null) {
/*  32 */           languagesSCDS.add(locale);
/*     */         }
/*     */       } 
/*     */     } else {
/*  36 */       languagesSCDS.addAll(LocaleInfoProvider.getInstance().getLocales());
/*     */     } 
/*     */   }
/*     */   
/*     */   public CTOCNode getRoot() {
/*  41 */     return (CTOCNode)this.root;
/*     */   }
/*     */   
/*     */   public static CTOCNode getNode(Integer tocID) {
/*  45 */     return (CTOCNode)nodes.get(tocID);
/*     */   }
/*     */   
/*     */   CTOCCache(CTOCStore store, CTOCRootElement root) {
/*  49 */     super(store, (CTOCElementImpl)root);
/*  50 */     this.store = store;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLabel(CTOCNode node, LocaleInfo locale, Integer labelID) {
/*  55 */     return this.store.getLabel(locale, labelID);
/*     */   }
/*     */   
/*     */   public void loadChildren(CTOCNode node) {
/*  59 */     this.store.loadChildren((CTOCElement)node);
/*     */   }
/*     */   
/*     */   public void loadContent(CTOCNode node) {
/*  63 */     this.store.loadContent((CTOCElement)node);
/*     */   }
/*     */   
/*     */   public void loadProperties(CTOCNode node) {
/*  67 */     ((CTOCElement)node).setProperties(this.store.getProperties((CTOCElement)node));
/*     */   }
/*     */   
/*     */   public Map getProperties(CTOCElement element) {
/*  71 */     return this.store.getProperties(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCNode loadNode(Integer ctocID) {
/*  76 */     CTOCNode node = this.store.loadNode(ctocID);
/*     */     
/*  78 */     if (node != null) {
/*  79 */       ((CTOCElementImpl)node).setCache(this);
/*     */     }
/*  81 */     return node;
/*     */   }
/*     */   
/*     */   public Map loadNodes(List ctocs) {
/*  85 */     Map<Integer, CTOCNode> nodes = null;
/*  86 */     if (ctocs.size() != 0) {
/*  87 */       nodes = this.store.getNodes(ctocs);
/*  88 */       if (!Util.isNullOrEmpty(nodes)) {
/*  89 */         Map<Integer, Map> tmp = this.store.getProperties(ctocs);
/*  90 */         for (Iterator<Integer> iter = ctocs.iterator(); iter.hasNext(); ) {
/*  91 */           Integer id = iter.next();
/*  92 */           CTOCElementImpl node = (CTOCElementImpl)nodes.get(id);
/*  93 */           node.setCache(this);
/*  94 */           node.setProperties(tmp.get(id));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return nodes;
/*     */   }
/*     */   
/*     */   public boolean isSupportedLocaleSCDS(LocaleInfo locale) {
/* 104 */     return languagesSCDS.contains(locale);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\io\db\CTOCCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */