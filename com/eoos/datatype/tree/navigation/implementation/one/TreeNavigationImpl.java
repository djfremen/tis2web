/*     */ package com.eoos.datatype.tree.navigation.implementation.one;
/*     */ 
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation;
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TreeNavigationImpl
/*     */   implements TreeNavigation2
/*     */ {
/*     */   protected TreeNavigationSPI spi;
/*     */   
/*     */   public TreeNavigationImpl(TreeNavigationSPI spi) {
/*  23 */     this.spi = spi;
/*     */   }
/*     */   
/*     */   public List getChildren(Object node) {
/*  27 */     return this.spi.getChildren(node);
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/*  31 */     return this.spi.getParent(node);
/*     */   }
/*     */   
/*     */   public List getSiblings(Object node, boolean includeNode) {
/*  35 */     Object parent = getParent(node);
/*     */     
/*  37 */     List retValue = (parent == null) ? getRoots() : getChildren(parent);
/*     */     
/*  39 */     if (!includeNode) {
/*  40 */       retValue.remove(node);
/*     */     }
/*     */     
/*  43 */     return retValue;
/*     */   }
/*     */   
/*     */   public Object getNextSibling(Object node) {
/*  47 */     return getSibling(node, 1);
/*     */   }
/*     */   
/*     */   public Object getPreviousSibling(Object node) {
/*  51 */     return getSibling(node, -1);
/*     */   }
/*     */   
/*     */   public List getRoots() {
/*  55 */     return getChildren(this.spi.getSuperroot());
/*     */   }
/*     */   
/*     */   public Object getSibling(Object node, int offset) {
/*  59 */     Object retValue = null;
/*     */     
/*  61 */     List allSiblings = getSiblings(node, true);
/*  62 */     int index = allSiblings.indexOf(node) + offset;
/*  63 */     if (index >= 0 && index < allSiblings.size()) {
/*  64 */       retValue = allSiblings.get(index);
/*     */     }
/*  66 */     return retValue;
/*     */   }
/*     */   
/*     */   public TreeNavigation getSubTreeNavigation(Object node) {
/*  70 */     final Object superroot = node;
/*     */     
/*  72 */     return (TreeNavigation)new TreeNavigationImpl(this.spi) {
/*     */         public List getRoots() {
/*  74 */           return this.spi.getChildren(superroot);
/*     */         }
/*     */         
/*     */         public Object getParent(Object node) {
/*  78 */           Object retValue = this.spi.getParent(node);
/*  79 */           if (retValue != null && retValue.equals(superroot)) {
/*  80 */             retValue = null;
/*     */           }
/*  82 */           return retValue;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/*  88 */     return (getChildren(node).size() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getPath(Object node) {
/*  97 */     if (this.spi instanceof TreeNavigationSPI2) {
/*  98 */       return ((TreeNavigationSPI2)this.spi).getPath(node);
/*     */     }
/* 100 */     List retValue = new LinkedList();
/* 101 */     search(retValue, node, this.spi.getSuperroot());
/* 102 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean search(List<Object> path, Object searchNode, Object currentNode) {
/* 107 */     boolean retValue = false;
/* 108 */     if (currentNode.equals(searchNode)) {
/* 109 */       path.add(0, currentNode);
/* 110 */       retValue = true;
/*     */     } else {
/* 112 */       Iterator iter = this.spi.getChildren(currentNode).iterator();
/* 113 */       while (iter.hasNext()) {
/* 114 */         Object child = iter.next();
/* 115 */         if (search(path, searchNode, child)) {
/* 116 */           if (!currentNode.equals(this.spi.getSuperroot())) {
/* 117 */             path.add(0, currentNode);
/*     */           }
/* 119 */           retValue = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 123 */     return retValue;
/*     */   }
/*     */   
/*     */   protected void getNodes(Collection<Object> nodes, Object currentNode) {
/* 127 */     if (!currentNode.equals(this.spi.getSuperroot())) {
/* 128 */       nodes.add(currentNode);
/*     */     }
/* 130 */     if (!isLeaf(currentNode)) {
/* 131 */       Iterator iter = getChildren(currentNode).iterator();
/* 132 */       while (iter.hasNext()) {
/* 133 */         getNodes(nodes, iter.next());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection getNodes() {
/* 140 */     Collection retValue = new LinkedList();
/* 141 */     getNodes(retValue, this.spi.getSuperroot());
/* 142 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\navigation\implementation\one\TreeNavigationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */