/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*     */ 
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.DomUtil;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RestrictionParser
/*     */   extends DomUtil.ElemWorker
/*     */ {
/*  21 */   private static Logger log = Logger.getLogger(RestrictionParser.class);
/*     */   
/*     */   private VCR posVcr;
/*     */   
/*     */   private VCR negVcr;
/*     */   
/*     */   private VCR vcr;
/*     */   
/*     */   protected ILVCAdapter.Retrieval lvcr;
/*     */ 
/*     */   
/*     */   public RestrictionParser(VCR vcr, VCR posVcr, VCR negVcr, ILVCAdapter.Retrieval lvcr) {
/*  33 */     this.posVcr = posVcr;
/*  34 */     this.negVcr = negVcr;
/*  35 */     this.vcr = vcr;
/*  36 */     this.lvcr = lvcr;
/*     */   }
/*     */   
/*     */   public int hasRestrictions(Element elem) {
/*  40 */     int ret = 0;
/*  41 */     String restr = elem.getAttribute("VCRText");
/*  42 */     if (restr != "") {
/*     */       
/*     */       try {
/*  45 */         VCR elemVcr = this.lvcr.getLVCAdapter().makeVCR(restr);
/*     */         
/*  47 */         if (elemVcr.match(this.vcr, this.posVcr)) {
/*  48 */           ret = 1;
/*  49 */         } else if (elemVcr.contradicts(this.vcr, this.negVcr)) {
/*  50 */           ret = -1;
/*     */         } 
/*  52 */       } catch (Exception e) {
/*  53 */         log.error("unable to process restrictions: " + restr, e);
/*     */       } 
/*     */     }
/*  56 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public void work(Element elem) {
/*  61 */     if (this.vcr != null) {
/*  62 */       parse(elem);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract void parse(Element paramElement);
/*     */   
/*     */   public NodeList removeParams(Element elem, String group) {
/*  69 */     NodeList subNodes = elem.getElementsByTagName(group);
/*  70 */     List<VCR> vcrs = new LinkedList();
/*     */     try {
/*  72 */       for (int ind = 0; ind < subNodes.getLength(); ind++) {
/*  73 */         Element vcrPara = (Element)subNodes.item(ind);
/*  74 */         Element vcrElem = DomUtil.firstChild(vcrPara, "VCR");
/*  75 */         if (vcrElem != null) {
/*  76 */           vcrs.add(this.lvcr.getLVCAdapter().makeVCR(vcrElem.getAttribute("VCRText")));
/*     */         } else {
/*  78 */           vcrs.add(VCR.NULL);
/*     */         } 
/*     */       } 
/*  81 */     } catch (Exception e) {
/*  82 */       log.error("unable to process param restrictions.", e);
/*  83 */       return subNodes;
/*     */     } 
/*  85 */     List<Node> discardedNodes = new LinkedList();
/*  86 */     List<VCR> result = this.lvcr.getLVCAdapter().checkOptionRestriction(vcrs, this.vcr, this.posVcr, this.negVcr);
/*  87 */     if (isNullResult(result))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       return subNodes; } 
/*     */     int i;
/*  96 */     for (i = 0; i < result.size(); i++) {
/*  97 */       VCR element = result.get(i);
/*  98 */       if (element == null) {
/*  99 */         discardedNodes.add(subNodes.item(i));
/*     */       }
/*     */     } 
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
/* 112 */     for (i = 0; i < discardedNodes.size(); i++) {
/* 113 */       Element vcrPara = (Element)discardedNodes.get(i);
/* 114 */       elem.removeChild(vcrPara);
/*     */     } 
/* 116 */     subNodes = elem.getElementsByTagName(group);
/* 117 */     deleteVcr(subNodes);
/* 118 */     return subNodes;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNullResult(List result) {
/* 123 */     if (result == null || result.size() == 0) {
/* 124 */       return true;
/*     */     }
/* 126 */     for (int i = 0; i < result.size(); i++) {
/* 127 */       if (result.get(i) != null) {
/* 128 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   void deleteVcr(NodeList subNodes) {
/* 136 */     if (subNodes.getLength() == 1) {
/* 137 */       Element vcrPara = (Element)subNodes.item(0);
/* 138 */       Element vcr = DomUtil.firstChild(vcrPara, "VCR");
/* 139 */       if (vcr != null) {
/*     */         
/* 141 */         Element parent = (Element)vcr.getParentNode();
/* 142 */         if (parent != null)
/* 143 */           parent.removeChild(vcr); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\RestrictionParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */