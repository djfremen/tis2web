/*     */ package com.eoos.html.element.input.tree.implementation;
/*     */ 
/*     */ import com.eoos.datatype.selection.SelectionControl;
/*     */ import com.eoos.datatype.selection.implementation.SelectionControlImpl;
/*     */ import com.eoos.datatype.selection.implementation.SelectionControlSPI;
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationImpl;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI_Adapter;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TreeControlImpl
/*     */   implements TreeControl
/*     */ {
/*     */   protected TreeNavigation2 navigation;
/*     */   protected SelectionControl selection;
/*     */   protected SelectionControl expansion;
/*     */   protected TreeNavigation2 navigationBackend;
/*  30 */   protected Set expandedNodes = new HashSet();
/*     */ 
/*     */ 
/*     */   
/*     */   public TreeControlImpl(TreeNavigationSPI navigationSPI, SelectionControlSPI selectionSPI) {
/*  35 */     this.expansion = (SelectionControl)new SelectionControlImpl(new SelectionControlSPI() {
/*     */           public void addSelection(Object obj) {
/*  37 */             TreeControlImpl.this.expandedNodes.add(obj);
/*     */           }
/*     */           
/*     */           public void removeSelection(Object obj) {
/*  41 */             TreeControlImpl.this.expandedNodes.remove(obj);
/*     */           }
/*     */           
/*     */           public Collection getSelection() {
/*  45 */             return TreeControlImpl.this.expandedNodes;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  50 */     final TreeNavigationSPI_Adapter navigationSPIAdapter = new TreeNavigationSPI_Adapter(navigationSPI);
/*  51 */     navigationSPIAdapter.setFilter(new Filter()
/*     */         {
/*     */           public boolean include(Object node) {
/*  54 */             Object parent = navigationSPIAdapter.getParent(node);
/*     */             
/*  56 */             return TreeControlImpl.this.isExpanded(parent);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  61 */     this.expansion.setSelected(navigationSPIAdapter.getSuperroot(), true);
/*  62 */     this.navigation = (TreeNavigation2)new TreeNavigationImpl((TreeNavigationSPI)navigationSPIAdapter);
/*  63 */     this.navigationBackend = (TreeNavigation2)new TreeNavigationImpl(navigationSPI);
/*  64 */     this.selection = (SelectionControl)new SelectionControlImpl(selectionSPI);
/*     */   }
/*     */ 
/*     */   
/*     */   public List getChilds(Object node) {
/*  69 */     return this.navigation.getChildren(node);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/*  73 */     this.selection.setSingleSelection(node);
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/*  77 */     Object retValue = null;
/*  78 */     Collection c = this.selection.getSelection();
/*  79 */     if (c.size() > 0) {
/*  80 */       retValue = c.toArray()[0];
/*     */     }
/*  82 */     return retValue;
/*     */   }
/*     */   
/*     */   public List getRoots() {
/*  86 */     return this.navigation.getRoots();
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/*  90 */     return this.navigation.getPath(getSelectedNode());
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/*  94 */     return this.navigationBackend.isLeaf(node);
/*     */   }
/*     */   
/*     */   public boolean isExpanded(Object node) {
/*  98 */     return this.expansion.isSelected(node);
/*     */   }
/*     */   
/*     */   public void toggleExpanded(Object node) {
/* 102 */     this.expansion.toggleSelection(node);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpanded(Object node, boolean expanded) {
/* 107 */     this.expansion.setSelected(node, expanded);
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 111 */     return this.navigation.getParent(node);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\implementation\TreeControlImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */