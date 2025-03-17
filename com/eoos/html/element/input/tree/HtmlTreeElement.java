/*     */ package com.eoos.html.element.input.tree;
/*     */ 
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.renderer.HtmlTreeRenderer;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */   private TreeControl tree;
/*     */   
/*     */   private class RenderingCallback
/*     */     extends HtmlTreeRenderer.CallbackAdapter
/*     */   {
/*  25 */     private List nodes = null;
/*     */     
/*     */     public RenderingCallback(List nodes) {
/*  28 */       this.nodes = nodes;
/*     */     }
/*     */     
/*     */     private Object getNode(int rowIndex) {
/*  32 */       return this.nodes.get(rowIndex);
/*     */     }
/*     */     
/*     */     public int getRowCount() {
/*  36 */       return (this.nodes != null) ? this.nodes.size() : 0;
/*     */     }
/*     */     
/*     */     public HtmlTreeRenderer.Callback getSubLevelCallback(int rowIndex) {
/*  40 */       if (HtmlTreeElement.this.mode == 1) {
/*  41 */         Object node = getNode(rowIndex);
/*  42 */         if (HtmlTreeElement.this.tree.isExpanded(node) && !HtmlTreeElement.this.tree.isLeaf(node)) {
/*  43 */           return (HtmlTreeRenderer.Callback)new RenderingCallback(HtmlTreeElement.this.tree.getChilds(node));
/*     */         }
/*     */       } 
/*  46 */       return null;
/*     */     }
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
/*     */   public static final int MODE_FLAT = -1;
/*     */   public static final int MODE_DEEP = 1;
/*  72 */   protected int mode = 1;
/*     */ 
/*     */ 
/*     */   
/*     */   public HtmlTreeElement(TreeControl tree) {
/*  77 */     this.tree = tree;
/*     */   }
/*     */   
/*     */   public int getMode() {
/*  81 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(int mode) {
/*  85 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void toggleMode() {
/*  89 */     this.mode *= -1;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/*  93 */     removeAllElements();
/*     */     
/*  95 */     List displayRoots = null;
/*  96 */     if (this.mode == 1) {
/*  97 */       displayRoots = this.tree.getRoots();
/*     */     }
/*  99 */     else if (this.tree.getSelectedNode() == null) {
/* 100 */       displayRoots = this.tree.getRoots();
/*     */     } else {
/* 102 */       Object selectedNode = this.tree.getSelectedNode();
/* 103 */       if (this.tree.isLeaf(selectedNode)) {
/*     */         
/* 105 */         Object parent = this.tree.getParent(selectedNode);
/* 106 */         if (parent == null) {
/*     */           
/* 108 */           displayRoots = this.tree.getRoots();
/*     */         } else {
/* 110 */           displayRoots = this.tree.getChilds(parent);
/*     */         } 
/*     */       } else {
/*     */         
/* 114 */         displayRoots = this.tree.getChilds(selectedNode);
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return HtmlTreeRenderer.getInstance().getHtmlCode((HtmlTreeRenderer.Callback)new RenderingCallback(displayRoots));
/*     */   }
/*     */   
/*     */   protected HtmlElement getLabelElement(Object node) {
/* 122 */     HtmlElement element = (HtmlElement)this.nodeToLabelElement.get(node);
/* 123 */     if (element == null) {
/* 124 */       element = createLabelElement(node);
/* 125 */       this.nodeToLabelElement.put(node, element);
/*     */     } 
/* 127 */     return element;
/*     */   }
/*     */   
/*     */   protected HtmlElement getIconElement(Object node) {
/* 131 */     HtmlElement element = (HtmlElement)this.nodeToIconElement.get(node);
/* 132 */     if (element == null) {
/* 133 */       element = createIconElement(node);
/* 134 */       this.nodeToIconElement.put(node, element);
/*     */     } 
/* 136 */     return element;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TreeControl getTreeControl() {
/* 141 */     final TreeControl tr = this.tree;
/* 142 */     return new TreeControl()
/*     */       {
/*     */         public String getLabel(Object node) {
/* 145 */           return Util.escapeReservedHTMLChars(tr.getLabel(node));
/*     */         }
/*     */         
/*     */         public List getChilds(Object node) {
/* 149 */           return tr.getChilds(node);
/*     */         }
/*     */         
/*     */         public void setSelectedNode(Object node) {
/* 153 */           tr.setSelectedNode(node);
/*     */         }
/*     */         
/*     */         public Object getSelectedNode() {
/* 157 */           return tr.getSelectedNode();
/*     */         }
/*     */         
/*     */         public List getRoots() {
/* 161 */           return tr.getRoots();
/*     */         }
/*     */         
/*     */         public List getSelectedPath() {
/* 165 */           return tr.getSelectedPath();
/*     */         }
/*     */         
/*     */         public boolean isLeaf(Object node) {
/* 169 */           return tr.isLeaf(node);
/*     */         }
/*     */         
/*     */         public boolean isExpanded(Object node) {
/* 173 */           return tr.isExpanded(node);
/*     */         }
/*     */         
/*     */         public void toggleExpanded(Object node) {
/* 177 */           tr.toggleExpanded(node);
/*     */         }
/*     */         
/*     */         public void setExpanded(Object node, boolean expanded) {
/* 181 */           tr.setExpanded(node, expanded);
/*     */         }
/*     */         
/*     */         public Object getParent(Object node) {
/* 185 */           return tr.getParent(node);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   protected abstract HtmlElement createIconElement(Object paramObject);
/*     */   
/*     */   protected abstract HtmlElement createLabelElement(Object paramObject);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\HtmlTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */