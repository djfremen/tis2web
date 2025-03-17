/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.document.cpr.parsers;
/*    */ 
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SCTParser
/*    */   extends Parser
/*    */ {
/*    */   public SCTParser() {
/* 16 */     this.description.add((E)"WorkOrderDescSCT");
/* 17 */     this.description.add((E)"Text");
/*    */   }
/*    */   
/*    */   public String getLabel(Element elem) {
/* 21 */     return "&nbsp;";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\SCTParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */