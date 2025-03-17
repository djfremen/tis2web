/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.Node;
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
/*    */ 
/*    */ 
/*    */ public class DocumentCleaner
/*    */ {
/*    */   Map ElementsToYesLinks;
/*    */   Map ElementsToNoLinks;
/*    */   Map bookMarksToElements;
/* 29 */   Set usedElemnts = new HashSet();
/*    */   
/*    */   public DocumentCleaner(Map ElementsToYesLinks, Map ElementsToNoLinks, Map bookMarksToElements) {
/* 32 */     this.ElementsToYesLinks = ElementsToYesLinks;
/* 33 */     this.ElementsToNoLinks = ElementsToNoLinks;
/* 34 */     this.bookMarksToElements = bookMarksToElements;
/*    */   }
/*    */   
/*    */   public void clean(HistoryLink linkFrom) {
/* 38 */     clean(linkFrom.getTargetBookmark2());
/*    */   }
/*    */   
/*    */   public void clean(String bM) {
/* 42 */     findUsed(bM);
/* 43 */     removeNotUsed();
/*    */   }
/*    */   
/*    */   protected void findUsed(String bM) {
/* 47 */     if (bM != null) {
/* 48 */       Element elem = (Element)this.bookMarksToElements.get(bM);
/* 49 */       if (elem != null && 
/* 50 */         !this.usedElemnts.contains(elem)) {
/* 51 */         this.usedElemnts.add(elem);
/* 52 */         findForLinkMap(this.ElementsToYesLinks, elem);
/* 53 */         findForLinkMap(this.ElementsToNoLinks, elem);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void findForLinkMap(Map linkMap, Element elem) {
/* 60 */     HistoryLink hL = (HistoryLink)linkMap.get(elem);
/* 61 */     if (hL != null) {
/* 62 */       findUsed(hL.getTargetBookmark2());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void removeNotUsed() {
/* 69 */     Collection elems = this.bookMarksToElements.values();
/* 70 */     Iterator<Element> it = elems.iterator();
/* 71 */     while (it.hasNext()) {
/* 72 */       Element elem = it.next();
/*    */       try {
/* 74 */         if (!this.usedElemnts.contains(elem)) {
/* 75 */           Node parent = elem.getParentNode();
/* 76 */           if (parent != null) {
/* 77 */             parent.removeChild(elem);
/*    */           }
/*    */         } 
/* 80 */       } catch (Exception e) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\DocumentCleaner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */