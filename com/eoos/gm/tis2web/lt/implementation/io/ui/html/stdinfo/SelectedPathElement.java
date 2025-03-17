/*     */ package com.eoos.gm.tis2web.lt.implementation.io.ui.html.stdinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.ui.html.common.Icon;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class SelectedPathElement
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   private ClientContext context;
/*     */   private static final String template = "<span id=\"selectedtocpath\">{PATHELEMENTS}</span>";
/*     */   private TocTree tree;
/*     */   
/*     */   private class NodeLinkElement
/*     */     extends LinkElement
/*     */   {
/*     */     private Object node;
/*     */     
/*     */     public NodeLinkElement(Object node) {
/*  29 */       super(SelectedPathElement.this.context.createID(), null);
/*  30 */       this.node = node;
/*     */     }
/*     */     
/*     */     protected String getLabel() {
/*  34 */       if (this.node.equals(SelectedPathElement.SUPERROOT)) {
/*  35 */         return SelectedPathElement.this.context.getLabel("si.selectedpath.top");
/*     */       }
/*  37 */       return SelectedPathElement.this.tree.getLabel(this.node);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object onClick(Map submitParams) {
/*  42 */       if (this.node.equals(SelectedPathElement.SUPERROOT)) {
/*  43 */         SelectedPathElement.this.tree.setSelectedNode(null);
/*     */       } else {
/*  45 */         SelectedPathElement.this.tree.setSelectedNode(this.node);
/*     */       } 
/*     */       
/*  48 */       HtmlElementContainer container = getContainer();
/*  49 */       while (container.getContainer() != null) {
/*  50 */         container = container.getContainer();
/*     */       }
/*  52 */       return container;
/*     */     }
/*     */     
/*     */     public String getHtmlCode(Map params) {
/*  56 */       Object selectedNode = SelectedPathElement.this.tree.getSelectedNode();
/*     */       
/*  58 */       StringBuffer code = new StringBuffer();
/*     */       
/*  60 */       final String linkCode = super.getHtmlCode(params);
/*     */ 
/*     */       
/*  63 */       if ((this.node == SelectedPathElement.SUPERROOT && selectedNode == null) || this.node.equals(selectedNode)) {
/*  64 */         code.append(HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*     */                 protected String getClaZZ() {
/*  66 */                   return "selectednode";
/*     */                 }
/*     */                 
/*     */                 public String getContent() {
/*  70 */                   return linkCode;
/*     */                 }
/*     */               }));
/*     */       } else {
/*  74 */         code.append(linkCode);
/*     */       } 
/*  76 */       if (this.node != SelectedPathElement.SUPERROOT) {
/*  77 */         if (SelectedPathElement.this.tree.isLeaf(this.node)) {
/*  78 */           code.insert(0, SelectedPathElement.this.LEAF_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         }
/*  80 */         else if (SelectedPathElement.this.tree.getParent(this.node) != null) {
/*  81 */           code.insert(0, SelectedPathElement.this.OPEN_POSITION_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         } else {
/*  83 */           code.insert(0, SelectedPathElement.this.OPEN_GROUP_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         } 
/*     */       }
/*     */       
/*  87 */       return code.toString();
/*     */     }
/*     */   }
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
/* 100 */   private static final Object SUPERROOT = new Object();
/*     */   
/*     */   private Icon OPEN_GROUP_ICON;
/*     */   
/*     */   private Icon OPEN_POSITION_ICON;
/*     */   
/*     */   private Icon LEAF_ICON;
/*     */ 
/*     */   
/*     */   public SelectedPathElement(ClientContext context) {
/* 110 */     this.context = context;
/* 111 */     this.tree = TocTree.getInstance(context);
/* 112 */     this.OPEN_GROUP_ICON = new Icon(context, "lt/group-open.gif", "image/gif", "");
/* 113 */     this.LEAF_ICON = new Icon(context, "lt/mainwork.gif", "image/gif", "");
/* 114 */     this.OPEN_POSITION_ICON = new Icon(context, "lt/position-open.gif", "image/gif", "");
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 118 */     StringBuffer code = new StringBuffer("<span id=\"selectedtocpath\">{PATHELEMENTS}</span>");
/* 119 */     removeAllElements();
/*     */     
/* 121 */     List<Object> nodes = this.tree.getSelectedPath();
/* 122 */     nodes.add(0, SUPERROOT);
/* 123 */     for (int i = 0; i < nodes.size(); i++) {
/* 124 */       Object node = nodes.get(i);
/* 125 */       NodeLinkElement element = new NodeLinkElement(node);
/* 126 */       addElement((HtmlElement)element);
/*     */       
/* 128 */       StringUtilities.replace(code, "{PATHELEMENTS}", "<nobr>" + element.getHtmlCode(params) + "</nobr>{PATHELEMENTS}");
/*     */     } 
/*     */     
/* 131 */     StringUtilities.replace(code, "{PATHELEMENTS}", "");
/*     */ 
/*     */     
/* 134 */     StringUtilities.replace(code, "</nobr><nobr>", "</nobr>&nbsp;/&nbsp;<nobr>");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\i\\ui\html\stdinfo\SelectedPathElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */