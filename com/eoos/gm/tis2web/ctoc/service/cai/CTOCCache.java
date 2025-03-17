/*     */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CTOCCache
/*     */ {
/*  12 */   protected static HashMap nodes = new HashMap<Object, Object>(100000, 0.9F);
/*     */   
/*     */   protected CTOCElementImpl root;
/*     */   
/*     */   protected CTOCStore store;
/*     */   
/*     */   public static String makeKey(int ctocType, int vcrID) {
/*  19 */     return ctocType + ":" + vcrID;
/*     */   }
/*     */   
/*     */   public static String makeKey(int ctocType) {
/*  23 */     return ctocType + ":" + Character.MIN_VALUE;
/*     */   }
/*     */   
/*     */   public CTOCNode getRoot() {
/*  27 */     return this.root;
/*     */   }
/*     */   
/*     */   public static CTOCNode getNode(Integer tocID) {
/*  31 */     return (CTOCNode)nodes.get(tocID);
/*     */   }
/*     */   
/*     */   public CTOCCache(CTOCElementImpl root) {
/*  35 */     this.store = null;
/*  36 */     this.root = root;
/*  37 */     add(root);
/*     */   }
/*     */   
/*     */   public CTOCCache(CTOCStore store, CTOCElementImpl root) {
/*  41 */     this.store = store;
/*  42 */     this.root = root;
/*  43 */     add(root);
/*     */   }
/*     */   
/*     */   public static void add(CTOCNode node) {
/*  47 */     nodes.put(node.getID(), node);
/*     */   }
/*     */   
/*     */   public static void remove(CTOCNode node) {
/*  51 */     nodes.remove(node.getID());
/*     */   }
/*     */ 
/*     */   
/*     */   public CTOCLabel getLabel(Integer labelID) {
/*  56 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getLabel(CTOCNode node, LocaleInfo locale, Integer labelID) {
/*  60 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public void loadChildren(CTOCNode node) {
/*  64 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getChildren(CTOCNode node) {
/*  68 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public void loadContent(CTOCNode node) {
/*  72 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public void loadProperties(CTOCNode node) {
/*  76 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public CTOCNode loadNode(Integer ctocID) {
/*  80 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public Map loadNodes(List ctocs) {
/*  84 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public boolean isSupportedLocaleSCDS(LocaleInfo locale) {
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   public static void reset() {
/*  92 */     nodes = null;
/*     */   }
/*     */   
/*     */   public Map getProperties(CTOCElement element) {
/*  96 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public List getContentChilds(CTOCElement element) {
/* 100 */     throw new IllegalArgumentException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */