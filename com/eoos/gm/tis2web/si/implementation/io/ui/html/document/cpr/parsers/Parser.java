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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Parser
/*    */ {
/* 17 */   protected ElementPath description = new ElementPath();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDescription(Element elem) {
/* 24 */     return this.description.getValue(elem);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getBookmark(Element elem) {
/* 29 */     return elem.getAttribute("Tag");
/*    */   }
/*    */   
/*    */   public abstract String getLabel(Element paramElement);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\document\cpr\parsers\Parser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */