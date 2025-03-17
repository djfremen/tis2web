/*     */ package com.eoos.html.element.input.tree.gtwo;
/*     */ 
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.renderer.HtmlTreeRenderer;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HtmlTreeElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   protected TreeControl control;
/*     */   
/*     */   private class RenderingCallback
/*     */     extends HtmlTreeRenderer.CallbackAdapter
/*     */   {
/*  26 */     private List nodes = null;
/*     */     
/*     */     public RenderingCallback(List nodes) {
/*  29 */       this.nodes = nodes;
/*     */     }
/*     */     
/*     */     private Object getNode(int rowIndex) {
/*  33 */       return this.nodes.get(rowIndex);
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/*  37 */       return (this.nodes != null) ? this.nodes.size() : 0;
/*     */     }
/*     */     
/*     */     public HtmlTreeRenderer.Callback getSubLevelCallback(int rowIndex) {
/*  41 */       Object node = getNode(rowIndex);
/*  42 */       if (HtmlTreeElement.this.isExpanded(node) && !HtmlTreeElement.this.control.isLeaf(node)) {
/*  43 */         return (HtmlTreeRenderer.Callback)new RenderingCallback(HtmlTreeElement.this.control.getChilds(node));
/*     */       }
/*  45 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public HtmlElement getIconElement(int rowIndex) {
/*  50 */       Object node = getNode(rowIndex);
/*  51 */       HtmlElement element = HtmlTreeElement.this.getIconElement(node);
/*  52 */       HtmlTreeElement.this.addElement(element);
/*  53 */       return element;
/*     */     }
/*     */     
/*     */     public HtmlElement getLabelElement(int rowIndex) {
/*  57 */       Object node = getNode(rowIndex);
/*  58 */       HtmlElement element = HtmlTreeElement.this.getLabelElement(node);
/*  59 */       HtmlTreeElement.this.addElement(element);
/*  60 */       return element;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private Map nodeToLabelElement = new HashMap<Object, Object>();
/*  68 */   private Map nodeToIconElement = new HashMap<Object, Object>();
/*     */   
/*  70 */   protected Set expandedNodes = new HashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlTreeElement(TreeControl tree) {
/*  76 */     this.control = tree;
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/*  80 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/*  84 */     if (expanded) {
/*  85 */       this.expandedNodes.add(node);
/*     */     } else {
/*  87 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/*  92 */     if (isExpanded(node)) {
/*  93 */       setExpanded(node, false);
/*     */     } else {
/*  95 */       setExpanded(node, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 100 */     removeAllElements();
/*     */     
/* 102 */     List displayRoots = this.control.getRoots();
/* 103 */     return HtmlTreeRenderer.getInstance().getHtmlCode((HtmlTreeRenderer.Callback)new RenderingCallback(displayRoots));
/*     */   }
/*     */   
/*     */   protected HtmlElement getLabelElement(Object node) {
/* 107 */     HtmlElement element = (HtmlElement)this.nodeToLabelElement.get(node);
/* 108 */     if (element == null) {
/* 109 */       element = createLabelElement(node);
/* 110 */       this.nodeToLabelElement.put(node, element);
/*     */     } 
/* 112 */     return element;
/*     */   }
/*     */   
/*     */   protected HtmlElement getIconElement(Object node) {
/* 116 */     HtmlElement element = (HtmlElement)this.nodeToIconElement.get(node);
/* 117 */     if (element == null) {
/* 118 */       element = createIconElement(node);
/* 119 */       this.nodeToIconElement.put(node, element);
/*     */     } 
/* 121 */     return element;
/*     */   }
/*     */   
/*     */   protected abstract HtmlElement createIconElement(Object paramObject);
/*     */   
/*     */   protected abstract HtmlElement createLabelElement(Object paramObject);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\gtwo\HtmlTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */