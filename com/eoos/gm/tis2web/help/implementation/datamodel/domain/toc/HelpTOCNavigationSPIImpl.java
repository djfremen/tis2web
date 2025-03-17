/*     */ package com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc;
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
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HelpTOCNavigationSPIImpl
/*     */   implements TreeNavigationSPI2
/*     */ {
/*  25 */   protected static Logger log = Logger.getLogger(HelpTOCNavigationSPIImpl.class);
/*     */   
/*  27 */   public static final List EMPTYLIST = new ArrayList(0);
/*     */   
/*  29 */   protected Node superroot = null;
/*     */   
/*  31 */   protected VCR vcr = null;
/*     */   
/*     */   protected ILVCAdapter.Retrieval lvcr;
/*     */ 
/*     */   
/*     */   public HelpTOCNavigationSPIImpl(SITOCElement superroot, VCR vcr, ILVCAdapter.Retrieval lvcr) {
/*  37 */     this.superroot = new Node();
/*  38 */     this.vcr = vcr;
/*  39 */     this.lvcr = lvcr;
/*     */     
/*  41 */     this.superroot.content = superroot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCRExpression expressionIO, List<VCRTerm> termsVCR) {
/*  48 */     List<VCRTerm> termsIO = expressionIO.getTerms();
/*  49 */     for (int i = 0; i < termsVCR.size(); i++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  55 */       VCRTerm termVCR = termsVCR.get(i);
/*  56 */       boolean satisfied = true;
/*  57 */       for (int j = 0; j < termsIO.size(); j++) {
/*  58 */         VCRTerm termIO = termsIO.get(j);
/*  59 */         if (termVCR.getDomainID() == termIO.getDomainID() && 
/*  60 */           termVCR.intersect(termIO) == null) {
/*  61 */           satisfied = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/*  67 */       if (!satisfied) {
/*  68 */         return true;
/*     */       }
/*     */     } 
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean contradictsVCR(VCR searchVCR, VCR ioVCR) {
/*  76 */     if (searchVCR == null || ioVCR == null || searchVCR == VCR.NULL || ioVCR == VCR.NULL) {
/*  77 */       return false;
/*     */     }
/*  79 */     List termsVCR = searchVCR.getTerms();
/*  80 */     ICRList<VCRExpression> iCRList = ioVCR.getExpressions();
/*  81 */     for (int i = 0; i < iCRList.size(); i++) {
/*  82 */       VCRExpression expressionIO = iCRList.get(i);
/*  83 */       if (!contradictsVCR(expressionIO, termsVCR)) {
/*  84 */         return false;
/*     */       }
/*     */     } 
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   protected List getChildren(SITOCElement ctocNode) {
/*  91 */     List<Object> retValue = new ArrayList();
/*     */     
/*     */     try {
/*  94 */       List children = ctocNode.getChildren();
/*  95 */       for (int i = 0; i < children.size(); i++) {
/*  96 */         Object child = children.get(i);
/*  97 */         if (child instanceof com.eoos.gm.tis2web.si.service.cai.SIO) {
/*  98 */           if (!contradictsVCR(this.vcr, ((SITOCElement)child).getVCR()))
/*     */           {
/*     */             
/* 101 */             retValue.add(child);
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 106 */         else if (!contradictsVCR(this.vcr, ((SITOCElement)child).getVCR())) {
/*     */ 
/*     */           
/* 109 */           retValue.add(child);
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 115 */     catch (Exception e) {}
/*     */     
/* 117 */     return (retValue.size() > 0) ? retValue : EMPTYLIST;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getChildren(Object node) {
/* 122 */     List<Node> retValue = new LinkedList();
/* 123 */     Iterator<SITOCElement> iter = getChildren(((Node)node).content).iterator();
/* 124 */     while (iter.hasNext()) {
/* 125 */       Node child = new Node();
/* 126 */       child.parent = (Node)node;
/* 127 */       child.content = iter.next();
/* 128 */       retValue.add(child);
/*     */     } 
/* 130 */     return retValue;
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 134 */     return ((Node)node).parent;
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
/* 145 */     return this.superroot;
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
/* 156 */     List<Node> retValue = new LinkedList();
/*     */     
/* 158 */     Node _node = (Node)node;
/* 159 */     while (!_node.equals(getSuperroot())) {
/* 160 */       retValue.add(0, _node);
/* 161 */       _node = _node.parent;
/*     */     } 
/* 163 */     return retValue;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementation\datamodel\domain\toc\HelpTOCNavigationSPIImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */