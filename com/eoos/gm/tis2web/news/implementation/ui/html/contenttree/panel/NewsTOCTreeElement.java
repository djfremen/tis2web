/*    */ package com.eoos.gm.tis2web.news.implementation.ui.html.contenttree.panel;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.news.implementation.datamodel.domain.toc.Node;
/*    */ import com.eoos.gm.tis2web.news.implementation.ui.html.main.MainPage;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.html.element.HtmlElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.HtmlTreeElement;
/*    */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
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
/*    */ 
/*    */ public abstract class NewsTOCTreeElement
/*    */   extends HtmlTreeElement
/*    */ {
/*    */   protected ClientContext context;
/*    */   
/*    */   public NewsTOCTreeElement(ClientContext context, TreeControl control) {
/* 28 */     super(control);
/* 29 */     this.context = context;
/*    */   }
/*    */   
/*    */   public TreeControl getTreeControl() {
/* 33 */     return this.control;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HtmlElement createIconElement(Object node) {
/* 43 */     if (((Node)node).content instanceof SIO) {
/* 44 */       return (HtmlElement)new IconElement(this.context, this, (Node)node) {
/*    */           protected String getTargetFrame() {
/* 46 */             return "_top";
/*    */           }
/*    */           
/*    */           public Object onClick(Map params) {
/* 50 */             super.onClick(params);
/* 51 */             NewsTOCTreeElement.this.setPage((SIO)this.node.content);
/*    */             
/* 53 */             return MainPage.getInstance(this.context);
/*    */           }
/*    */         };
/*    */     }
/* 57 */     return (HtmlElement)new IconElement(this.context, this, (Node)node);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected HtmlElement createLabelElement(Object node) {
/* 68 */     if (((Node)node).content instanceof SIO) {
/* 69 */       return (HtmlElement)new LabelElement(this.context, this, (Node)node) {
/*    */           protected String getTargetFrame() {
/* 71 */             return "_top";
/*    */           }
/*    */           
/*    */           public Object onClick(Map params) {
/* 75 */             super.onClick(params);
/* 76 */             NewsTOCTreeElement.this.setPage((SIO)this.node.content);
/*    */             
/* 78 */             return MainPage.getInstance(this.context);
/*    */           }
/*    */         };
/*    */     }
/* 82 */     return (HtmlElement)new LabelElement(this.context, this, (Node)node);
/*    */   }
/*    */   
/*    */   protected abstract void setPage(SIO paramSIO);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\news\implementatio\\ui\html\contenttree\panel\NewsTOCTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */