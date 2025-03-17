/*    */ package com.eoos.datatype.tree.construction.implementation.one;
/*    */ 
/*    */ import com.eoos.datatype.tree.construction.TreeConstruction;
/*    */ import com.eoos.datatype.tree.navigation.TreeNavigation;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeConstructionImpl
/*    */   implements TreeConstruction
/*    */ {
/*    */   protected TreeConstructionSPI spi;
/*    */   protected TreeNavigation navigation;
/*    */   
/*    */   public TreeConstructionImpl(TreeConstructionSPI spi, TreeNavigation navigation) {
/* 22 */     this.spi = spi;
/* 23 */     this.navigation = navigation;
/*    */   }
/*    */   
/*    */   public void insertParent(Object child, Object newParent) {
/* 27 */     Object oldParent = this.navigation.getParent(child);
/* 28 */     this.spi.removeRelation(oldParent, child);
/* 29 */     this.spi.addRelation(oldParent, newParent);
/* 30 */     this.spi.addRelation(newParent, child);
/*    */   }
/*    */   
/*    */   public void addChild(Object parent, Object newChild) {
/* 34 */     this.spi.addRelation(parent, newChild);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeNode(Object node) {
/* 39 */     Object parent = this.navigation.getParent(node);
/* 40 */     List children = this.navigation.getChildren(node);
/*    */     
/* 42 */     this.spi.removeRelation(parent, node);
/* 43 */     Iterator iter = children.iterator();
/* 44 */     while (iter.hasNext()) {
/* 45 */       Object child = iter.next();
/* 46 */       this.spi.removeRelation(node, child);
/* 47 */       this.spi.addRelation(parent, child);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeSubTree(Object node) {
/* 53 */     Object parent = this.navigation.getParent(node);
/* 54 */     this.spi.removeRelation(parent, node);
/*    */     
/* 56 */     Iterator iter = this.navigation.getChildren(node).iterator();
/* 57 */     while (iter.hasNext()) {
/* 58 */       removeSubTree(iter.next());
/*    */     }
/*    */   }
/*    */   
/*    */   public void insertChildren(Object parent, List newChildren) {
/* 63 */     Iterator iter = newChildren.iterator();
/* 64 */     while (iter.hasNext()) {
/* 65 */       addChild(parent, iter.next());
/*    */     }
/*    */   }
/*    */   
/*    */   public void addRoot(Object node) {
/* 70 */     this.spi.addRelation(this.spi.getSuperroot(), node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\construction\implementation\one\TreeConstructionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */