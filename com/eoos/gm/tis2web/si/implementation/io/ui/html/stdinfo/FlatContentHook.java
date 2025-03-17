/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.DocumentIFramePanel;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.toc.TocPanel;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.HtmlElementContainerBase;
/*    */ import com.eoos.html.element.HtmlElementHook;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ 
/*    */ public class FlatContentHook
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private static String template;
/* 32 */   private static final Logger log = Logger.getLogger(FlatContentHook.class);
/*    */   private ClientContext context;
/*    */   
/*    */   static {
/*    */     try {
/* 37 */       template = ApplicationContext.getInstance().loadFile(FlatContentHook.class, "flatcontenthook.html", null).toString();
/* 38 */     } catch (Exception e) {
/* 39 */       log.error("unable to load template - error:" + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private SelectedPathElement ieSelectedPath;
/*    */   private MyHook hook;
/*    */   
/*    */   private class MyHook extends HtmlElementHook {
/*    */     private TocPanel tocPanel;
/* 48 */     private TocTree tree = TocTree.getInstance(FlatContentHook.this.context); private DocumentIFramePanel docPanel;
/*    */     
/*    */     public MyHook() {
/* 51 */       this.tocPanel = new TocPanel(FlatContentHook.this.context, -1);
/* 52 */       addElement((HtmlElement)this.tocPanel);
/*    */       
/* 54 */       this.docPanel = new DocumentIFramePanel(FlatContentHook.this.context);
/* 55 */       addElement((HtmlElement)this.docPanel);
/*    */     }
/*    */     
/*    */     protected HtmlElement getActiveElement() {
/* 59 */       Object selectedNode = this.tree.getSelectedNode();
/* 60 */       if (selectedNode != null && ((CTOCTree.NodeWrapper)selectedNode).node instanceof com.eoos.gm.tis2web.si.service.cai.SIO && this.tree.isLeaf(selectedNode)) {
/* 61 */         return (HtmlElement)this.docPanel;
/*    */       }
/* 63 */       return (HtmlElement)this.tocPanel;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FlatContentHook(ClientContext context) {
/* 76 */     this.context = context;
/*    */     
/* 78 */     this.hook = new MyHook();
/* 79 */     addElement((HtmlElement)this.hook);
/*    */     
/* 81 */     this.ieSelectedPath = new SelectedPathElement(context, TocTree.getInstance(context));
/* 82 */     addElement((HtmlElement)this.ieSelectedPath);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 87 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 89 */     StringUtilities.replace(code, "{SELECTED_PATH}", this.ieSelectedPath.getHtmlCode(params));
/* 90 */     StringUtilities.replace(code, "{VIEW}", this.hook.getHtmlCode(params));
/*    */     
/* 92 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\stdinfo\FlatContentHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */