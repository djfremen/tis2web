/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.document.DocumentIFramePanel;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.ltlist.LTListIFramePanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementHook;
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
/*    */ 
/*    */ public class LTViewHook
/*    */   extends HtmlElementHook
/*    */ {
/*    */   private ClientContext context;
/*    */   private DocumentIFramePanel docView;
/*    */   private LTListIFramePanel ltView;
/*    */   
/*    */   public LTViewHook(ClientContext context) {
/* 29 */     this.context = context;
/*    */     
/* 31 */     this.docView = new DocumentIFramePanel(context);
/* 32 */     addElement((HtmlElement)this.docView);
/*    */     
/* 34 */     this.ltView = new LTListIFramePanel(context);
/* 35 */     addElement((HtmlElement)this.ltView);
/*    */   }
/*    */ 
/*    */   
/*    */   protected HtmlElement getActiveElement() {
/* 40 */     TocTree oT = (TocTree)this.context.getObject(TocTree.class);
/* 41 */     if (oT.getSelectedNode() instanceof com.eoos.gm.tis2web.lt.service.cai.SIOLT) {
/* 42 */       return (HtmlElement)this.ltView;
/*    */     }
/* 44 */     return (HtmlElement)this.docView;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\ltview\LTViewHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */