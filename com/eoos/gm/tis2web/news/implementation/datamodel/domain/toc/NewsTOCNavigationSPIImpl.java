/*     */ package com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc;
/*     */ 
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI2;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.ICRList;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewsTOCNavigationSPIImpl
/*     */   implements TreeNavigationSPI2
/*     */ {
/*  23 */   public static final List EMPTYLIST = new ArrayList(0);
/*     */   
/*  25 */   protected Node superroot = null;
/*     */   
/*  27 */   protected VCR vcr = null;
/*     */   
/*     */   protected ILVCAdapter.Retrieval lvcr;
/*     */ 
/*     */   
/*     */   public NewsTOCNavigationSPIImpl(SITOCElement superroot, VCR vcr, ILVCAdapter.Retrieval lvcr) {
/*  33 */     this.superroot = new Node();
/*  34 */     this.vcr = vcr;
/*  35 */     this.lvcr = lvcr;
/*  36 */     this.superroot.content = superroot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCRExpression expressionIO, List<VCRTerm> termsVCR) {
/*  43 */     List<VCRTerm> termsIO = expressionIO.getTerms();
/*  44 */     for (int i = 0; i < termsVCR.size(); i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  50 */       VCRTerm termVCR = termsVCR.get(i);
/*  51 */       boolean satisfied = true;
/*  52 */       for (int j = 0; j < termsIO.size(); j++) {
/*  53 */         VCRTerm termIO = termsIO.get(j);
/*  54 */         if (termVCR.getDomainID() == termIO.getDomainID() && 
/*  55 */           termVCR.intersect(termIO) == null) {
/*  56 */           satisfied = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  62 */       if (!satisfied) {
/*  63 */         return true;
/*     */       }
/*     */     } 
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCR searchVCR, VCR ioVCR) {
/*  71 */     if (searchVCR == null || ioVCR == null || searchVCR == VCR.NULL || ioVCR == VCR.NULL) {
/*  72 */       return false;
/*     */     }
/*  74 */     List termsVCR = searchVCR.getTerms();
/*  75 */     ICRList<VCRExpression> iCRList = ioVCR.getExpressions();
/*  76 */     for (int i = 0; i < iCRList.size(); i++) {
/*  77 */       VCRExpression expressionIO = iCRList.get(i);
/*  78 */       if (!contradictsVCR(expressionIO, termsVCR)) {
/*  79 */         return false;
/*     */       }
/*     */     } 
/*  82 */     return true;
/*     */   }
/*     */   
/*     */   protected List getChildren(SITOCElement ctocNode) {
/*  86 */     List<Object> retValue = new LinkedList();
/*     */     
/*     */     try {
/*  89 */       List children = ctocNode.getChildren();
/*  90 */       for (int i = 0; i < children.size(); i++) {
/*  91 */         Object child = children.get(i);
/*  92 */         if (child instanceof com.eoos.gm.tis2web.si.service.cai.SIO) {
/*  93 */           if (!contradictsVCR(this.vcr, ((SITOCElement)child).getVCR()))
/*     */           {
/*     */             
/*  96 */             retValue.add(child);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 101 */         else if (!contradictsVCR(this.vcr, ((SITOCElement)child).getVCR())) {
/*     */ 
/*     */           
/* 104 */           retValue.add(child);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 110 */     catch (Exception e) {}
/*     */     
/* 112 */     return (retValue.size() > 0) ? retValue : EMPTYLIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getChildren(Object node) {
/* 123 */     List<Node> retValue = new LinkedList();
/* 124 */     Iterator<SITOCElement> iter = getChildren(((Node)node).content).iterator();
/* 125 */     while (iter.hasNext()) {
/* 126 */       Node child = new Node();
/* 127 */       child.parent = (Node)node;
/* 128 */       child.content = iter.next();
/* 129 */       retValue.add(child);
/*     */     } 
/* 131 */     return retValue;
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 135 */     return ((Node)node).parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSuperroot() {
/* 146 */     return this.superroot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getPath(Object node) {
/* 157 */     List<Node> retValue = new LinkedList();
/*     */     
/* 159 */     Node _node = (Node)node;
/* 160 */     while (!_node.equals(getSuperroot())) {
/* 161 */       retValue.add(0, _node);
/* 162 */       _node = _node.parent;
/*     */     } 
/* 164 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementation\datamodel\domain\toc\NewsTOCNavigationSPIImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */