/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.html.element.input.LinkElement;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LabelOnlyElement
/*    */   extends LinkElement {
/*    */   public LabelOnlyElement(TocTreeElement treeElement, String node) {
/*  9 */     super(treeElement.getContext().createID(), null);
/* 10 */     this.label = node;
/* 11 */     setDisabled(Boolean.TRUE);
/*    */   }
/*    */   protected String label;
/*    */   protected String getLabel() {
/* 15 */     return null;
/*    */   }
/*    */   
/*    */   public Object onClick(Map params) {
/* 19 */     return null;
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 23 */     return this.label;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\LabelOnlyElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */