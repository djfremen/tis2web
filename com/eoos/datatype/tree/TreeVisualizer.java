/*    */ package com.eoos.datatype.tree;
/*    */ 
/*    */ import com.eoos.datatype.tree.navigation.TreeNavigation;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TreeVisualizer
/*    */ {
/*    */   public void print(TreeNavigation tree, int indent, PrintWriter pw) {
/*    */     try {
/* 22 */       Iterator iter = tree.getRoots().iterator();
/* 23 */       while (iter.hasNext()) {
/* 24 */         Object node = iter.next();
/* 25 */         StringBuffer tmp = new StringBuffer();
/* 26 */         for (int i = 0; i < indent; i++) {
/* 27 */           tmp.append("\t");
/*    */         }
/* 29 */         tmp.append(getNodeRepresentation(node));
/* 30 */         pw.println(tmp.toString());
/* 31 */         print(tree.getSubTreeNavigation(node), indent + 1, pw);
/*    */       } 
/* 33 */     } catch (NullPointerException e) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public void print(TreeNavigation tree, PrintWriter pw) {
/* 38 */     print(tree, 0, pw);
/*    */   }
/*    */   
/*    */   protected String getNodeRepresentation(Object node) {
/* 42 */     return String.valueOf(node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\tree\TreeVisualizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */