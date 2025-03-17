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
/*    */ public class DTCParser
/*    */   extends Parser
/*    */ {
/*    */   public DTCParser() {
/* 16 */     this.description.add((E)"Name");
/* 17 */     this.description.add((E)"Text");
/*    */   }
/*    */   
/*    */   public String getLabel(Element elem) {
/* 21 */     return elem.getAttribute("DTCNumber");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\DTCParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */