/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.tree.HtmlTreeElement;
/*    */ import com.eoos.html.element.input.tree.TreeControl;
/*    */ import java.util.List;
/*    */ import java.util.Map;
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
/*    */ public class TocTreeElement
/*    */   extends HtmlTreeElement
/*    */ {
/*    */   public static final String PREFIX_LEAFID = "leaf.";
/*    */   private ClientContext context;
/*    */   
/*    */   public TocTreeElement(ClientContext context, int mode) {
/* 29 */     super((TreeControl)TocTree.getInstance(context));
/* 30 */     this.mode = mode;
/* 31 */     this.context = context;
/*    */   }
/*    */   
/*    */   public TocTreeElement(ClientContext context, int mode, TreeControl treeControl) {
/* 35 */     super(treeControl);
/* 36 */     this.mode = mode;
/* 37 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected ClientContext getContext() {
/* 41 */     return this.context;
/*    */   }
/*    */   
/*    */   protected TreeControl getTreeControl() {
/* 45 */     return super.getTreeControl();
/*    */   }
/*    */   
/*    */   protected HtmlElement createIconElement(Object node) {
/* 49 */     if (getTreeControl().isLeaf(node)) {
/* 50 */       DefaultLeafIconElement oE = new DefaultLeafIconElement(this, node);
/* 51 */       if (node instanceof SIOLT) {
/* 52 */         SIOLT oelem = (SIOLT)node;
/* 53 */         if (!LTClientContext.getInstance(this.context).isMainWorkValid(oelem.getMajorOperationNumber())) {
/* 54 */           oE.setDisabled(new Boolean(true));
/*    */         }
/*    */       } 
/* 57 */       return (HtmlElement)oE;
/*    */     } 
/* 59 */     return (HtmlElement)new DefaultIconElement(this, node);
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement createLabelElement(Object node) {
/* 64 */     if (getTreeControl().isLeaf(node)) {
/* 65 */       DefaultLeafLabelElement oE = new DefaultLeafLabelElement(this, node);
/* 66 */       if (node instanceof SIOLT) {
/* 67 */         SIOLT oelem = (SIOLT)node;
/* 68 */         if (!LTClientContext.getInstance(this.context).isMainWorkValid(oelem.getMajorOperationNumber())) {
/* 69 */           oE.setDisabled(new Boolean(true));
/*    */         }
/*    */       } 
/* 72 */       return (HtmlElement)oE;
/*    */     } 
/* 74 */     return (HtmlElement)new DefaultLabelElement(this, node);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String determineID(Object node) {
/* 80 */     if (node instanceof SIOLT) {
/* 81 */       SIOLT oelem = (SIOLT)node;
/* 82 */       return oelem.getMajorOperationNumber() + ":" + getContext().createID();
/*    */     } 
/* 84 */     return getContext().createID();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 89 */     List roots = getTreeControl().getRoots();
/* 90 */     if (roots == null || roots.size() == 0) {
/* 91 */       return this.context.getMessage("no.information.available");
/*    */     }
/* 93 */     return super.getHtmlCode(params);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\toc\TocTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */