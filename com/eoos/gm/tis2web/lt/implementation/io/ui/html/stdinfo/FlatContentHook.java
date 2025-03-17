/*    */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.ltview.LTViewHook;
/*    */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.toc.TocPanel;
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
/*    */ public class FlatContentHook
/*    */   extends HtmlElementContainerBase
/*    */ {
/*    */   private static String template;
/* 30 */   private static final Logger log = Logger.getLogger(FlatContentHook.class);
/*    */   private ClientContext context;
/*    */   
/*    */   static {
/*    */     try {
/* 35 */       template = ApplicationContext.getInstance().loadFile(FlatContentHook.class, "flatcontenthook.html", null).toString();
/* 36 */     } catch (Exception e) {
/* 37 */       log.error("unable to load template - error:" + e, e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private SelectedPathElement ieSelectedPath;
/*    */   private MyHook hook;
/*    */   
/*    */   private class MyHook extends HtmlElementHook {
/*    */     private TocPanel tocPanel;
/* 46 */     private TocTree tree = TocTree.getInstance(FlatContentHook.this.context); private LTViewHook docPanel;
/*    */     
/*    */     public MyHook() {
/* 49 */       this.tocPanel = new TocPanel(FlatContentHook.this.context, -1);
/* 50 */       addElement((HtmlElement)this.tocPanel);
/*    */       
/* 52 */       this.docPanel = new LTViewHook(FlatContentHook.this.context);
/* 53 */       addElement((HtmlElement)this.docPanel);
/*    */     }
/*    */     
/*    */     protected HtmlElement getActiveElement() {
/* 57 */       Object selectedNode = this.tree.getSelectedNode();
/* 58 */       if (selectedNode != null && this.tree.isLeaf(selectedNode)) {
/* 59 */         return (HtmlElement)this.docPanel;
/*    */       }
/* 61 */       return (HtmlElement)this.tocPanel;
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
/* 74 */     this.context = context;
/*    */     
/* 76 */     this.ieSelectedPath = new SelectedPathElement(context);
/* 77 */     addElement((HtmlElement)this.ieSelectedPath);
/*    */     
/* 79 */     this.hook = new MyHook();
/* 80 */     addElement((HtmlElement)this.hook);
/*    */   }
/*    */   
/*    */   public String getHtmlCode(Map params) {
/* 84 */     StringBuffer code = new StringBuffer(template);
/*    */     
/* 86 */     StringUtilities.replace(code, "{SELECTED_PATH}", this.ieSelectedPath.getHtmlCode(params));
/* 87 */     StringUtilities.replace(code, "{VIEW}", this.hook.getHtmlCode(params));
/*    */     
/* 89 */     return code.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\FlatContentHook.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */