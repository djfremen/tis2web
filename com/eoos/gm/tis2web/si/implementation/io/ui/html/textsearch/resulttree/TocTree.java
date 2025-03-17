/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.SitSurrogateImpl;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.db.FTSSIElement;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TocTree
/*     */   implements TreeControl
/*     */ {
/*     */   private ClientContext context;
/*  27 */   private Object selectedNode = null;
/*     */   
/*  29 */   private Set expandedNodes = new HashSet();
/*     */   
/*  31 */   private SitSurrogateImpl sitSurrogate = null;
/*     */   
/*  33 */   CTOCNode sitSelected = null;
/*     */ 
/*     */   
/*     */   public TocTree(ClientContext context, SitSurrogateImpl sitSurrogate, CTOCNode sitSelected) {
/*  37 */     this.context = context;
/*  38 */     this.sitSurrogate = sitSurrogate;
/*  39 */     this.sitSelected = sitSelected;
/*     */   }
/*     */   
/*     */   public List getRoots() {
/*  43 */     if (this.sitSelected != null) {
/*  44 */       return this.sitSurrogate.searchSIOs(this.sitSelected);
/*     */     }
/*  46 */     return this.sitSurrogate.searchSITs();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List getChilds(Object node) {
/*  52 */     List ret = null;
/*  53 */     if (node instanceof CTOCNode) {
/*  54 */       ret = this.sitSurrogate.searchSIOs((CTOCNode)node);
/*     */     }
/*  56 */     return (ret != null) ? ret : Collections.EMPTY_LIST;
/*     */   }
/*     */   
/*     */   public String getLabel(Object node) {
/*  60 */     if (node instanceof String)
/*  61 */       return (String)node; 
/*  62 */     if (node instanceof FTSSIElement) {
/*  63 */       return ((FTSSIElement)node).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */     }
/*  65 */     return ((SITOCElement)node).getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getSelectedNode() {
/*  70 */     return this.selectedNode;
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/*  74 */     List<Object> retValue = new LinkedList();
/*  75 */     Object node = getSelectedNode();
/*  76 */     while (node != null) {
/*  77 */       retValue.add(0, node);
/*  78 */       node = getParent(node);
/*     */     } 
/*  80 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/*  84 */     if (node instanceof String) {
/*  85 */       return true;
/*     */     }
/*  87 */     List childs = getChilds(node);
/*  88 */     return (childs == null || childs.size() == 0);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/*  92 */     this.selectedNode = node;
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/*  96 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/* 100 */     if (this.expandedNodes.contains(node)) {
/* 101 */       this.expandedNodes.remove(node);
/*     */     } else {
/* 103 */       this.expandedNodes.add(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/* 108 */     if (expanded) {
/* 109 */       this.expandedNodes.add(node);
/*     */     } else {
/* 111 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 116 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\TocTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */