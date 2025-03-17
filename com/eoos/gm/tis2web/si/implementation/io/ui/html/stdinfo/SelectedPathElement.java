/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.stdinfo;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.Icon;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.HtmlElementContainerBase;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlSpanRenderer;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SelectedPathElement
/*     */   extends HtmlElementContainerBase {
/*     */   private ClientContext context;
/*     */   private static final String template = "<span id=\"selectedtocpath\">{PATHELEMENTS}</span>";
/*     */   private TocTree tree;
/*     */   private Icon OPEN_FOLDER_ICON;
/*     */   private Icon LEAF_ICON;
/*     */   private Icon OPEN_SIT_ICON;
/*     */   
/*     */   private class NodeLinkElement
/*     */     extends LinkElement {
/*     */     private CTOCTree.NodeWrapper wrapper;
/*     */     
/*     */     public NodeLinkElement(CTOCTree.NodeWrapper wrapper) {
/*  34 */       super(SelectedPathElement.this.context.createID(), null);
/*  35 */       this.wrapper = wrapper;
/*     */     }
/*     */     
/*     */     protected String getLabel() {
/*  39 */       if (this.wrapper.equals(SelectedPathElement.this.tree.getRootNodeWrapper())) {
/*  40 */         return SelectedPathElement.this.context.getLabel("si.selectedpath.top");
/*     */       }
/*  42 */       return SelectedPathElement.this.tree.getLabel(this.wrapper);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object onClick(Map submitParams) {
/*  47 */       if (this.wrapper.equals(SelectedPathElement.this.tree.getRootNodeWrapper())) {
/*  48 */         SelectedPathElement.this.tree.setSelectedNode(null);
/*     */       } else {
/*  50 */         SelectedPathElement.this.tree.setSelectedNode(this.wrapper);
/*  51 */         if (this.wrapper.node instanceof com.eoos.gm.tis2web.si.service.cai.SIO && SelectedPathElement.this.tree.isLeaf(this.wrapper)) {
/*  52 */           DocumentPage.getInstance(SelectedPathElement.this.context).setPage(this.wrapper.node, null);
/*     */         }
/*     */       } 
/*     */       
/*  56 */       HtmlElementContainer container = getContainer();
/*  57 */       while (container.getContainer() != null) {
/*  58 */         container = container.getContainer();
/*     */       }
/*  60 */       return container;
/*     */     }
/*     */     
/*     */     public String getHtmlCode(Map params) {
/*  64 */       Object selectedNode = SelectedPathElement.this.tree.getSelectedNode();
/*     */       
/*  66 */       StringBuffer code = new StringBuffer();
/*     */       
/*  68 */       final String linkCode = super.getHtmlCode(params);
/*     */ 
/*     */       
/*  71 */       if ((this.wrapper.equals(SelectedPathElement.this.tree.getRootNodeWrapper()) && selectedNode == null) || this.wrapper.equals(selectedNode)) {
/*  72 */         code.append(HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*     */                 protected String getClaZZ() {
/*  74 */                   return "selectednode";
/*     */                 }
/*     */                 
/*     */                 public String getContent() {
/*  78 */                   return linkCode;
/*     */                 }
/*     */               }));
/*     */       } else {
/*  82 */         code.append(linkCode);
/*     */       } 
/*  84 */       if (!this.wrapper.equals(SelectedPathElement.this.tree.getRootNodeWrapper())) {
/*  85 */         if (this.wrapper.node instanceof com.eoos.gm.tis2web.si.service.cai.SIO && SelectedPathElement.this.tree.isLeaf(this.wrapper)) {
/*  86 */           code.insert(0, SelectedPathElement.this.LEAF_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         }
/*  88 */         else if (this.wrapper.node instanceof CTOCNode) {
/*  89 */           CTOCNode tmp = (CTOCNode)this.wrapper.node;
/*  90 */           if (!tmp.hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*  91 */             code.insert(0, SelectedPathElement.this.OPEN_SIT_ICON.getHtmlCode(params) + "&nbsp;");
/*     */           } else {
/*  93 */             code.insert(0, SelectedPathElement.this.OPEN_FOLDER_ICON.getHtmlCode(params) + "&nbsp;");
/*     */           } 
/*     */         } else {
/*  96 */           code.insert(0, SelectedPathElement.this.OPEN_FOLDER_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         } 
/*     */       }
/*     */       
/* 100 */       return code.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SelectedPathElement(ClientContext context, TocTree tree) {
/* 119 */     this.context = context;
/* 120 */     this.tree = tree;
/* 121 */     this.OPEN_FOLDER_ICON = new Icon(context, "common/folder-open.gif", "image/gif", "");
/* 122 */     this.LEAF_ICON = new Icon(context, "common/leaf-icon.gif", "image/gif", "");
/* 123 */     this.OPEN_SIT_ICON = new Icon(context, "common/sit-open.gif", "image/gif", "");
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 127 */     StringBuffer code = new StringBuffer("<span id=\"selectedtocpath\">{PATHELEMENTS}</span>");
/* 128 */     removeAllElements();
/*     */     
/* 130 */     List nodes = this.tree.getSelectedPath();
/* 131 */     for (int i = 0; i < nodes.size(); i++) {
/* 132 */       Object node = nodes.get(i);
/* 133 */       NodeLinkElement element = new NodeLinkElement((CTOCTree.NodeWrapper)node);
/* 134 */       addElement((HtmlElement)element);
/*     */       
/* 136 */       StringUtilities.replace(code, "{PATHELEMENTS}", "<nobr>" + element.getHtmlCode(params) + "</nobr>{PATHELEMENTS}");
/*     */     } 
/*     */     
/* 139 */     StringUtilities.replace(code, "{PATHELEMENTS}", "");
/*     */ 
/*     */     
/* 142 */     StringUtilities.replace(code, "</nobr><nobr>", "</nobr>&nbsp;/&nbsp;<nobr>");
/*     */     
/* 144 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\stdinfo\SelectedPathElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */