/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.basic;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.DocumentPageRetrieval;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.doc.page.DocumentPage;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BasicModePanel
/*    */   extends HtmlElementContainerBase
/*    */   implements DocumentPageRetrieval
/*    */ {
/*    */   private static String template;
/*    */   protected ClientContext context;
/*    */   protected MyHook hook;
/*    */   protected SelectedPathElement pathElement;
/*    */   
/*    */   static {
/*    */     try {
/* 33 */       template = ApplicationContext.getInstance().loadFile(BasicModePanel.class, "basicmodepanel.html", null).toString();
/* 34 */     } catch (Exception e) {
/* 35 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private class MyHook extends HtmlElementHook {
/*    */     protected TOCIFrame tocFrame;
/*    */     protected DocumentIFrame docFrame;
/*    */     
/*    */     public MyHook() {
/* 44 */       this.tocFrame = new TOCIFrame(BasicModePanel.this.context, 0);
/* 45 */       addElement((HtmlElement)this.tocFrame);
/*    */       
/* 47 */       this.docFrame = new DocumentIFrame(BasicModePanel.this.context);
/* 48 */       addElement((HtmlElement)this.docFrame);
/*    */     }
/*    */     
/*    */     protected HtmlElement getActiveElement() {
/* 52 */       ContentTreeControl treeControl = SpecialBrochuresContext.getInstance(BasicModePanel.this.context).getContentTreeControl();
/* 53 */       Node selectedNode = (Node)treeControl.getSelectedNode();
/*    */       
/* 55 */       if (selectedNode != null && selectedNode.content instanceof com.eoos.gm.tis2web.si.service.cai.SIO && treeControl.isLeaf(selectedNode)) {
/* 56 */         return (HtmlElement)this.docFrame;
/*    */       }
/* 58 */       return (HtmlElement)this.tocFrame;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicModePanel(ClientContext context) {
/* 69 */     this.context = context;
/*    */     
/* 71 */     this.hook = new MyHook();
/* 72 */     addElement((HtmlElement)this.hook);
/*    */     
/* 74 */     this.pathElement = new SelectedPathElement(context);
/* 75 */     addElement((HtmlElement)this.pathElement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 80 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 82 */     StringUtilities.replace(code, "{SELECTED_PATH}", this.pathElement.getHtmlCode(params));
/* 83 */     StringUtilities.replace(code, "{HOOK}", this.hook.getHtmlCode(params));
/*    */     
/* 85 */     return code.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DocumentPage getDocumentPage() {
/* 92 */     return this.hook.docFrame.getDocumentPage();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\basic\BasicModePanel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */