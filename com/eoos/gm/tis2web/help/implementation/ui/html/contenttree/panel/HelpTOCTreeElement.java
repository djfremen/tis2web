/*     */ package com.eoos.gm.tis2web.help.implementation.ui.html.contenttree.panel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.implementation.common.db.IONode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.IIOElement;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.help.implementation.datamodel.domain.toc.Node;
/*     */ import com.eoos.gm.tis2web.help.implementation.ui.html.main.MainPage;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.tree.gtwo.HtmlTreeElement;
/*     */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HelpTOCTreeElement
/*     */   extends HtmlTreeElement
/*     */ {
/*     */   protected ClientContext context;
/*     */   
/*     */   public class HelpLabel
/*     */     extends LabelElement
/*     */   {
/*     */     public HelpLabel(ClientContext context, HelpTOCTreeElement treeElement, Node node) {
/*  35 */       super(context, treeElement, node);
/*     */     }
/*     */     
/*     */     protected String getTargetFrame() {
/*  39 */       return "_top";
/*     */     }
/*     */ 
/*     */     
/*     */     public Object onClick(Map params) {
/*  44 */       super.onClick(params);
/*     */       
/*  46 */       HelpTOCTreeElement.this.setPage((IIOElement)this.node.content);
/*  47 */       return MainPage.getInstance(this.context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class IDHelpLabel
/*     */     extends HelpLabel
/*     */   {
/*     */     private String id;
/*     */ 
/*     */ 
/*     */     
/*     */     public IDHelpLabel(ClientContext context, HelpTOCTreeElement treeElement, Node node, String idPref) {
/*  61 */       super(context, treeElement, node);
/*  62 */       this.id = idPref + ":" + this.parameterName;
/*     */     }
/*     */     
/*     */     protected void getAdditionalAttributes(Map<String, String> map) {
/*  66 */       super.getAdditionalAttributes(map);
/*  67 */       map.put("id", this.id);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public HelpTOCTreeElement(ClientContext context, TreeControl control) {
/*  74 */     super(control);
/*  75 */     this.context = context;
/*     */   }
/*     */   
/*     */   public TreeControl getTreeControl() {
/*  79 */     return this.control;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement createIconElement(Object node) {
/*  89 */     if (((Node)node).content instanceof IIOElement) {
/*  90 */       return (HtmlElement)new IconElement(this.context, this, (Node)node) {
/*     */           protected String getTargetFrame() {
/*  92 */             return "_top";
/*     */           }
/*     */           
/*     */           public Object onClick(Map params) {
/*  96 */             super.onClick(params);
/*     */             
/*  98 */             HelpTOCTreeElement.this.setPage((IIOElement)this.node.content);
/*  99 */             return MainPage.getInstance(this.context);
/*     */           }
/*     */         };
/*     */     }
/* 103 */     return (HtmlElement)new IconElement(this.context, this, (Node)node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HtmlElement createLabelElement(Object node) {
/* 114 */     if (((Node)node).content instanceof IIOElement) {
/* 115 */       if (((IIOElement)((Node)node).content).getType() == SIOType.VERSION) {
/* 116 */         return (HtmlElement)new IDHelpLabel(this.context, this, (Node)node, "help.Version");
/*     */       }
/*     */       
/* 119 */       return (HtmlElement)new HelpLabel(this.context, this, (Node)node);
/*     */     } 
/*     */     
/* 122 */     if (node instanceof IONode && ((IONode)node).getType() == CTOCType.VERSION)
/*     */     {
/* 124 */       throw new ClassCastException();
/*     */     }
/* 126 */     return (HtmlElement)new LabelElement(this.context, this, (Node)node);
/*     */   }
/*     */   
/*     */   protected abstract void setPage(IIOElement paramIIOElement);
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\help\implementatio\\ui\html\contenttree\panel\HelpTOCTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */