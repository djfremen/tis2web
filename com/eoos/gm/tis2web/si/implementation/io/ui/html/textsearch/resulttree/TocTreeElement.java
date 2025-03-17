/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.tree.HtmlTreeElement;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TocTreeElement
/*    */   extends HtmlTreeElement
/*    */ {
/*    */   public static final String PREFIX_LEAFID = "leaf.";
/*    */   private ClientContext context;
/*    */   
/*    */   public TocTreeElement(ClientContext context, TocTree control) {
/* 24 */     super(control);
/* 25 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected ClientContext getContext() {
/* 29 */     return this.context;
/*    */   }
/*    */   
/*    */   protected TreeControl getTreeControl() {
/* 33 */     return super.getTreeControl();
/*    */   }
/*    */   
/*    */   protected HtmlElement createIconElement(Object node) {
/* 37 */     if (node instanceof String)
/* 38 */       return (HtmlElement)new NullIconElement(this); 
/* 39 */     if (node instanceof CTOCNode) {
/* 40 */       return (HtmlElement)new SITIconElement(this, (CTOCNode)node);
/*    */     }
/* 42 */     return (HtmlElement)new SIOIconElement(this, node);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected HtmlElement createLabelElement(Object node) {
/* 48 */     if (node instanceof String)
/* 49 */       return (HtmlElement)new LabelOnlyElement(this, (String)node); 
/* 50 */     if (node instanceof CTOCNode) {
/* 51 */       return (HtmlElement)new DefaultLabelElement(this, node);
/*    */     }
/* 53 */     return (HtmlElement)new DefaultLeafLabelElement(this, node);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\TocTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */