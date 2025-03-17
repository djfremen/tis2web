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
/*     */ public abstract class HtmlFlatTreeElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   protected TreeControl tree;
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
/*  41 */       return null;
/*     */     }
/*     */     
/*     */     public HtmlElement getIconElement(int rowIndex) {
/*  45 */       Object node = getNode(rowIndex);
/*  46 */       HtmlElement element = HtmlFlatTreeElement.this.getIconElement(node);
/*  47 */       HtmlFlatTreeElement.this.addElement(element);
/*  48 */       return element;
/*     */     }
/*     */     
/*     */     public HtmlElement getLabelElement(int rowIndex) {
/*  52 */       Object node = getNode(rowIndex);
/*  53 */       HtmlElement element = HtmlFlatTreeElement.this.getLabelElement(node);
/*  54 */       HtmlFlatTreeElement.this.addElement(element);
/*  55 */       return element;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   protected Map nodeToLabelElement = new HashMap<Object, Object>();
/*  63 */   protected Map nodeToIconElement = new HashMap<Object, Object>();
/*     */   
/*  65 */   protected Set expandedNodes = new HashSet();
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlFlatTreeElement(TreeControl tree) {
/*  70 */     this.tree = tree;
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/*  74 */     return this.expandedNodes.contains(node);
/*     */   }
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/*  78 */     if (expanded) {
/*  79 */       this.expandedNodes.add(node);
/*     */     } else {
/*  81 */       this.expandedNodes.remove(node);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/*  86 */     if (isExpanded(node)) {
/*  87 */       setExpanded(node, false);
/*     */     } else {
/*  89 */       setExpanded(node, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  94 */     removeAllElements();
/*     */     
/*  96 */     List displayRoots = null;
/*  97 */     if (this.tree.getSelectedNode() == null) {
/*  98 */       displayRoots = this.tree.getRoots();
/*     */     } else {
/* 100 */       Object selectedNode = this.tree.getSelectedNode();
/* 101 */       if (this.tree.isLeaf(selectedNode)) {
/*     */         
/* 103 */         Object parent = this.tree.getParent(selectedNode);
/* 104 */         if (parent == null) {
/*     */           
/* 106 */           displayRoots = this.tree.getRoots();
/*     */         } else {
/* 108 */           displayRoots = this.tree.getChilds(parent);
/*     */         } 
/*     */       } else {
/*     */         
/* 112 */         displayRoots = this.tree.getChilds(selectedNode);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     return HtmlTreeRenderer.getInstance().getHtmlCode((HtmlTreeRenderer.Callback)new RenderingCallback(displayRoots));
/*     */   }
/*     */   
/*     */   protected HtmlElement getLabelElement(Object node) {
/* 120 */     HtmlElement element = (HtmlElement)this.nodeToLabelElement.get(node);
/* 121 */     if (element == null) {
/* 122 */       element = createLabelElement(node);
/* 123 */       this.nodeToLabelElement.put(node, element);
/*     */     } 
/* 125 */     return element;
/*     */   }
/*     */   
/*     */   protected HtmlElement getIconElement(Object node) {
/* 129 */     HtmlElement element = (HtmlElement)this.nodeToIconElement.get(node);
/* 130 */     if (element == null) {
/* 131 */       element = createIconElement(node);
/* 132 */       this.nodeToIconElement.put(node, element);
/*     */     } 
/* 134 */     return element;
/*     */   }
/*     */   
/*     */   protected abstract HtmlElement createIconElement(Object paramObject);
/*     */   
/*     */   protected abstract HtmlElement createLabelElement(Object paramObject);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\gtwo\HtmlFlatTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */