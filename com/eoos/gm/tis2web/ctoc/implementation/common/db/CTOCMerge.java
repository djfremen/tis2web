/*     */ package com.eoos.gm.tis2web.ctoc.implementation.common.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CTOCMerge
/*     */ {
/*  15 */   protected static int key = 0;
/*     */   
/*     */   public static int getID() {
/*  18 */     return key++;
/*     */   }
/*     */   
/*     */   public static void merge(CTOCRootElement result, CTOCNode parentA, CTOCNode parentB, ILVCAdapter.Retrieval lvcr) {
/*  22 */     merge((CTOCNode)result, parentA.getChildren(), parentB.getChildren(), lvcr);
/*     */   }
/*     */   public static void merge(CTOCNode result, List<SITOCElement> childrenA, List<SITOCElement> childrenB, ILVCAdapter.Retrieval lvcr) {
/*     */     int i;
/*  26 */     for (i = 0; i < childrenA.size(); i++) {
/*  27 */       SITOCElement child = childrenA.get(i);
/*  28 */       if (child.isSIO()) {
/*  29 */         merge(result, (IOElement)child, childrenB);
/*     */       } else {
/*  31 */         IONode nodeA = (IONode)child;
/*  32 */         IONode nodeB = (IONode)lookup(childrenB, nodeA.getProperty((SITOCProperty)CTOCProperty.ID).toString());
/*  33 */         if (nodeB == null) {
/*  34 */           add(result, (SITOCElement)nodeA.clone(getID()));
/*     */         } else {
/*  36 */           IONode node = select(nodeA, nodeB, lvcr);
/*  37 */           add(result, (SITOCElement)node);
/*  38 */           merge(node, nodeA.getChildren(), nodeB.getChildren(), lvcr);
/*     */         } 
/*     */       } 
/*     */     } 
/*  42 */     for (i = 0; i < childrenB.size(); i++) {
/*  43 */       SITOCElement elementB = childrenB.get(i);
/*  44 */       SITOCElement elementA = elementB.isSIO() ? lookup(childrenA, elementB.getProperty((SITOCProperty)SIOProperty.IO).toString()) : lookup(childrenA, elementB.getProperty((SITOCProperty)CTOCProperty.ID).toString());
/*  45 */       if (elementA == null) {
/*  46 */         if (elementB.isSIO()) {
/*  47 */           add(result, (SITOCElement)((IOElement)elementB).clone(getID()));
/*     */         } else {
/*  49 */           add(result, (SITOCElement)((IONode)elementB).clone(getID()));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void add(CTOCNode result, SITOCElement child) {
/*  56 */     if (result instanceof IONode) {
/*  57 */       ((IONode)result).add(child);
/*     */     } else {
/*  59 */       ((CTOCElement)result).add(child);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static IONode select(IONode nodeA, IONode nodeB, ILVCAdapter.Retrieval lvcRetrieval) {
/*  64 */     Version a = new Version(nodeA.getProperty((SITOCProperty)CTOCProperty.VERSION).toString());
/*  65 */     Version b = new Version(nodeB.getProperty((SITOCProperty)CTOCProperty.VERSION).toString());
/*  66 */     if (a.compareTo(b) < 0) {
/*  67 */       return nodeB.copy(getID(), mergeVCR(nodeA, nodeB, lvcRetrieval));
/*     */     }
/*  69 */     return nodeA.copy(getID(), mergeVCR(nodeA, nodeB, lvcRetrieval));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static VCR mergeVCR(CTOCNode nodeA, CTOCNode nodeB, ILVCAdapter.Retrieval lvcRetrieval) {
/*  74 */     VCR vcrA = nodeA.getVCR();
/*  75 */     VCR vcrB = nodeB.getVCR();
/*  76 */     VCR NULLVCR = VCR.NULL;
/*  77 */     if (vcrA == null || vcrB == null || vcrA == NULLVCR || vcrB == NULLVCR) {
/*  78 */       return NULLVCR;
/*     */     }
/*  80 */     return vcrA.union(vcrB);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void merge(CTOCNode result, IOElement childA, List childrenB) {
/*  85 */     IOElement childB = (IOElement)lookup(childrenB, childA.getProperty((SITOCProperty)SIOProperty.IO).toString());
/*  86 */     if (childB != null) {
/*  87 */       Version a = new Version(childA.getProperty((SITOCProperty)SIOProperty.VERSION).toString());
/*  88 */       Version b = new Version(childB.getProperty((SITOCProperty)SIOProperty.VERSION).toString());
/*  89 */       if (a.compareTo(b) < 0) {
/*  90 */         add(result, (SITOCElement)childB.clone(getID()));
/*     */       } else {
/*  92 */         add(result, (SITOCElement)childA.clone(getID()));
/*     */       } 
/*     */     } else {
/*  95 */       add(result, (SITOCElement)childA.clone(getID()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SITOCElement lookup(List<SITOCElement> elements, String id) {
/* 131 */     for (int i = 0; i < elements.size(); i++) {
/* 132 */       SITOCElement element = elements.get(i);
/* 133 */       if (element.isSIO()) {
/* 134 */         if (id.equals(((IOElement)element).getProperty((SITOCProperty)SIOProperty.IO))) {
/* 135 */           return element;
/*     */         }
/* 137 */       } else if (id.equals(((CTOCNode)element).getProperty((SITOCProperty)CTOCProperty.ID))) {
/* 138 */         return element;
/*     */       } 
/*     */     } 
/* 141 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\implementation\common\db\CTOCMerge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */