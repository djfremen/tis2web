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
/*    */ public class FIPParser
/*    */   extends Parser
/*    */ {
/*    */   public FIPParser() {
/* 16 */     this.description.add((E)"FIPResult");
/*    */   }
/*    */   
/*    */   public String getLabel(Element elem) {
/* 20 */     return elem.getAttribute("ResultNr");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\FIPParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */