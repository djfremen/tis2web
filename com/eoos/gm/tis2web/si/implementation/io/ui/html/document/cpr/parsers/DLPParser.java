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
/*    */ public class DLPParser
/*    */   extends Parser
/*    */ {
/* 15 */   protected ElementPath label = new ElementPath();
/*    */   
/*    */   public DLPParser() {
/* 18 */     this.description.add((E)"Name");
/* 19 */     this.description.add((E)"Text");
/* 20 */     this.label.add((E)"Label");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescription(Element elem) {
/* 25 */     return this.label.getValue(elem) + " " + this.description.getValue(elem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLabel(Element elem) {
/* 34 */     return elem.getAttribute("Priority");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\DLPParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */