/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*    */ 
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParameterVcrParser
/*    */   extends RestrictionParser
/*    */ {
/*    */   public ParameterVcrParser(VCR vcr, VCR posVcr, VCR negVcr, ILVCAdapter.Retrieval lvcr) {
/* 19 */     super(vcr, posVcr, negVcr, lvcr);
/*    */   }
/*    */   
/*    */   public void parse(Element elem) {
/* 23 */     removeParams(elem, "VCRPara");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\ParameterVcrParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */