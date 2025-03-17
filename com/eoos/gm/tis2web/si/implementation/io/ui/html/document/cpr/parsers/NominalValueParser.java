/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
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
/*    */ public class NominalValueParser
/*    */   extends RestrictionParser
/*    */ {
/*    */   public NominalValueParser(VCR vcr, VCR posVcr, VCR negVcr, ILVCAdapter.Retrieval lvcr) {
/* 22 */     super(vcr, posVcr, negVcr, lvcr);
/*    */   }
/*    */   
/*    */   public void parse(Element elem) {
/* 26 */     NodeList subNodes = elem.getElementsByTagName("WorkOrderDesc");
/* 27 */     for (int ind = 0; ind < subNodes.getLength(); ind++) {
/* 28 */       Element vcrPara = (Element)subNodes.item(ind);
/* 29 */       removeParams(vcrPara, "NominalValue");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\NominalValueParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */