/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.specialbrochures.basic;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.ContentTreeControl;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.Node;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures.SpecialBrochuresContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.common.Icon;
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
/*     */   extends HtmlElementContainerBase
/*     */ {
/*     */   protected ClientContext context;
/*     */   protected ContentTreeControl treeControl;
/*     */   private static final String template = "<span id=\"selectedtocpath\">{PATHELEMENTS}</span>";
/*     */   protected Icon OPEN_FOLDER_ICON;
/*     */   protected Icon LEAF_ICON;
/*     */   protected Icon OPEN_SIT_ICON;
/*     */   
/*     */   private class NodeLinkElement
/*     */     extends LinkElement {
/*     */     private Node node;
/*     */     
/*     */     public NodeLinkElement(Object node) {
/*  32 */       super(SelectedPathElement.this.context.createID(), null);
/*  33 */       this.node = (Node)node;
/*     */     }
/*     */     
/*     */     protected String getLabel() {
/*  37 */       return SelectedPathElement.this.treeControl.getLabel(this.node);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object onClick(Map submitParams) {
/*  43 */       SelectedPathElement.this.treeControl.setSelectedNode(this.node);
/*     */       
/*  45 */       HtmlElementContainer container = getContainer();
/*  46 */       while (container.getContainer() != null) {
/*  47 */         container = container.getContainer();
/*     */       }
/*  49 */       return container;
/*     */     }
/*     */     
/*     */     public String getHtmlCode(Map params) {
/*  53 */       Object selectedNode = SelectedPathElement.this.treeControl.getSelectedNode();
/*     */       
/*  55 */       StringBuffer code = new StringBuffer();
/*  56 */       final String linkCode = super.getHtmlCode(params);
/*     */ 
/*     */       
/*  59 */       if ((this.node == SelectedPathElement.this.treeControl.getSuperroot() && selectedNode == null) || this.node.equals(selectedNode)) {
/*  60 */         code.append(HtmlSpanRenderer.getInstance().getHtmlCode((HtmlSpanRenderer.Callback)new HtmlSpanRenderer.CallbackAdapter() {
/*     */                 protected String getClaZZ() {
/*  62 */                   return "selectednode";
/*     */                 }
/*     */                 
/*     */                 public String getContent() {
/*  66 */                   return linkCode;
/*     */                 }
/*     */               }));
/*     */       } else {
/*  70 */         code.append(linkCode);
/*     */       } 
/*  72 */       if (this.node != SelectedPathElement.this.treeControl.getSuperroot()) {
/*  73 */         if (this.node.content instanceof com.eoos.gm.tis2web.si.service.cai.SIO && SelectedPathElement.this.treeControl.isLeaf(this.node)) {
/*  74 */           code.insert(0, SelectedPathElement.this.LEAF_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         } else {
/*  76 */           code.insert(0, SelectedPathElement.this.OPEN_FOLDER_ICON.getHtmlCode(params) + "&nbsp;");
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  82 */       return code.toString();
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
/*     */   public SelectedPathElement(ClientContext context) {
/* 101 */     this.context = context;
/* 102 */     this.treeControl = SpecialBrochuresContext.getInstance(context).getContentTreeControl();
/* 103 */     this.OPEN_FOLDER_ICON = new Icon(context, "common/folder-open.gif", "image/gif", "");
/* 104 */     this.LEAF_ICON = new Icon(context, "common/leaf-icon.gif", "image/gif", "");
/* 105 */     this.OPEN_SIT_ICON = new Icon(context, "common/sit-open.gif", "image/gif", "");
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 109 */     StringBuffer code = new StringBuffer("<span id=\"selectedtocpath\">{PATHELEMENTS}</span>");
/* 110 */     removeAllElements();
/*     */     
/* 112 */     List nodes = this.treeControl.getSelectedPath();
/* 113 */     for (int i = 0; i < nodes.size(); i++) {
/* 114 */       Object node = nodes.get(i);
/* 115 */       NodeLinkElement element = new NodeLinkElement(node);
/* 116 */       addElement((HtmlElement)element);
/*     */       
/* 118 */       StringUtilities.replace(code, "{PATHELEMENTS}", "<nobr>" + element.getHtmlCode(params) + "</nobr>{PATHELEMENTS}");
/*     */     } 
/* 120 */     StringUtilities.replace(code, "{PATHELEMENTS}", "");
/*     */ 
/*     */     
/* 123 */     StringUtilities.replace(code, "</nobr><nobr>", "</nobr>&nbsp;/&nbsp;<nobr>");
/*     */     
/* 125 */     return code.toString();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\specialbrochures\basic\SelectedPathElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */