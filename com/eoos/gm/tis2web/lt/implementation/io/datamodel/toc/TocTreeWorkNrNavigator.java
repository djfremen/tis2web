/*    */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TocTreeWorkNrNavigator
/*    */ {
/*    */   TocTree tree;
/*    */   
/*    */   public TocTreeWorkNrNavigator(TocTree c) {
/* 25 */     this.tree = c;
/*    */   }
/*    */   
/*    */   public boolean navigateToMainWork(String nr) {
/* 29 */     nr = LTDataWork.getMainWorkNr(nr);
/* 30 */     if (nr.length() < 7) {
/* 31 */       return false;
/*    */     }
/*    */     
/* 34 */     if (this.tree.getRoots() == null) {
/* 35 */       return false;
/*    */     }
/*    */     
/* 38 */     for (Iterator it = this.tree.getRoots().iterator(); it.hasNext(); ) {
/* 39 */       Object cur = it.next();
/* 40 */       if (findNodeAndSelect(cur, nr)) {
/* 41 */         this.tree.setExpanded(cur, true);
/* 42 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */   
/*    */   private boolean findNodeAndSelect(Object node, String nr) {
/* 50 */     if (node instanceof SIOLT) {
/* 51 */       SIOLT e = (SIOLT)node;
/* 52 */       if (nr.equalsIgnoreCase(e.getMajorOperationNumber())) {
/* 53 */         this.tree.setSelectedNode(e);
/* 54 */         return true;
/*    */       } 
/*    */     } else {
/* 57 */       SITOCElement e = (SITOCElement)node;
/* 58 */       List childs = this.tree.getChilds(e);
/* 59 */       if (childs != null) {
/* 60 */         for (Iterator it = childs.iterator(); it.hasNext(); ) {
/* 61 */           Object cur = it.next();
/* 62 */           if (findNodeAndSelect(cur, nr)) {
/* 63 */             this.tree.setExpanded(cur, true);
/* 64 */             return true;
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\toc\TocTreeWorkNrNavigator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */