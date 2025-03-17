/*     */ package com.eoos.datatype.tree.test;
/*     */ 
/*     */ import com.eoos.datatype.tree.TreeVisualizer;
/*     */ import com.eoos.datatype.tree.construction.implementation.one.TreeConstructionImpl;
/*     */ import com.eoos.datatype.tree.construction.implementation.one.TreeConstructionSPI;
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationImpl;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class Tree
/*     */   implements TreeNavigationSPI, TreeConstructionSPI
/*     */ {
/*     */   public static class Node
/*     */   {
/*  20 */     public Object parent = null;
/*  21 */     public List childs = new LinkedList();
/*  22 */     public Object content = null;
/*     */     
/*     */     public Node(String content) {
/*  25 */       this.content = content;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  29 */       return String.valueOf(this.content);
/*     */     }
/*     */   }
/*     */   
/*  33 */   protected static final Node SUPERROOT = new Node("superroot");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized List getChildren(Object node) {
/*  39 */     return ((Node)node).childs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Object getParent(Object node) {
/*  46 */     return ((Node)node).parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void addRelation(Object parent, Object child) {
/*  53 */     ((Node)parent).childs.add(child);
/*  54 */     ((Node)child).parent = parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void removeRelation(Object parent, Object child) {
/*  62 */     ((Node)parent).childs.remove(child);
/*     */     
/*  64 */     Node _child = (Node)child;
/*  65 */     if (_child.parent != null && _child.parent.equals(parent)) {
/*  66 */       ((Node)child).parent = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  71 */     Tree tree = new Tree();
/*     */     
/*  73 */     TreeNavigationImpl treeNavigationImpl = new TreeNavigationImpl(tree);
/*  74 */     TreeConstructionImpl treeConstructionImpl = new TreeConstructionImpl(tree, (TreeNavigation)treeNavigationImpl);
/*     */     
/*  76 */     Node r1 = new Node("1");
/*  77 */     Node r11 = new Node("11");
/*  78 */     Node r12 = new Node("12");
/*  79 */     Node r2 = new Node("2");
/*  80 */     Node r3 = new Node("3");
/*  81 */     Node r21 = new Node("21");
/*     */     
/*  83 */     treeConstructionImpl.addRoot(r1);
/*  84 */     treeConstructionImpl.addRoot(r2);
/*  85 */     treeConstructionImpl.addRoot(r3);
/*  86 */     treeConstructionImpl.insertChildren(r1, Arrays.asList(new Node[] { r11, r12 }));
/*  87 */     treeConstructionImpl.addChild(r2, r21);
/*     */     
/*  89 */     PrintWriter pw = new PrintWriter(System.out);
/*  90 */     TreeVisualizer visu = new TreeVisualizer();
/*  91 */     visu.print((TreeNavigation)treeNavigationImpl, pw);
/*  92 */     pw.flush();
/*     */     
/*  94 */     System.out.println("");
/*  95 */     System.out.println("");
/*  96 */     System.out.println("");
/*     */     
/*  98 */     Node in = new Node("inserted parent for 1");
/*     */     
/* 100 */     treeConstructionImpl.insertParent(r1, in);
/* 101 */     visu.print((TreeNavigation)treeNavigationImpl, pw);
/* 102 */     pw.flush();
/*     */     
/* 104 */     List path = treeNavigationImpl.getPath(r12);
/* 105 */     System.out.println(path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSuperroot() {
/* 113 */     return SUPERROOT;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\test\Tree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */