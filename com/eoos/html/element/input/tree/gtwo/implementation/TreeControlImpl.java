/*    */ package com.eoos.html.element.input.tree.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.datatype.selection.SelectionControl;
/*    */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*    */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TreeControlImpl
/*    */   implements TreeControl
/*    */ {
/*    */   protected TreeNavigation2 navigation;
/*    */   protected SelectionControl selection;
/* 22 */   protected Set expandedNodes = new HashSet();
/*    */   
/*    */   public TreeControlImpl(TreeNavigation2 treeNavigation, SelectionControl selectionControl) {
/* 25 */     this.navigation = treeNavigation;
/* 26 */     this.selection = selectionControl;
/*    */   }
/*    */   
/*    */   public List getChilds(Object node) {
/* 30 */     return this.navigation.getChildren(node);
/*    */   }
/*    */   
/*    */   public void setSelectedNode(Object node) {
/* 34 */     this.selection.setSingleSelection(node);
/*    */   }
/*    */   
/*    */   public Object getSelectedNode() {
/* 38 */     Object retValue = null;
/* 39 */     Collection c = this.selection.getSelection();
/* 40 */     if (c.size() > 0) {
/* 41 */       retValue = c.toArray()[0];
/*    */     }
/* 43 */     return retValue;
/*    */   }
/*    */   
/*    */   public List getRoots() {
/* 47 */     return this.navigation.getRoots();
/*    */   }
/*    */   
/*    */   public List getSelectedPath() {
/* 51 */     return this.navigation.getPath(getSelectedNode());
/*    */   }
/*    */   
/*    */   public boolean isLeaf(Object node) {
/* 55 */     return this.navigation.isLeaf(node);
/*    */   }
/*    */   
/*    */   public Object getParent(Object node) {
/* 59 */     return this.navigation.getParent(node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\element\input\tree\gtwo\implementation\TreeControlImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */