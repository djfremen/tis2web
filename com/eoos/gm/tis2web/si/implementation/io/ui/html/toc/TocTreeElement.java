/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.toc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.toc.TocTree;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOCPR;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.HtmlElement;
/*     */ import com.eoos.html.element.input.tree.HtmlTreeElement;
/*     */ import com.eoos.html.element.input.tree.TreeControl;
/*     */ import java.util.List;
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
/*     */ public class TocTreeElement
/*     */   extends HtmlTreeElement
/*     */ {
/*     */   public static final String PREFIX_LEAFID = "leaf.";
/*     */   private ClientContext context;
/*     */   
/*     */   public TocTreeElement(ClientContext context, int mode, TocTree tree) {
/*  34 */     super((TreeControl)tree);
/*  35 */     this.mode = mode;
/*  36 */     this.context = context;
/*     */   }
/*     */   
/*     */   protected ClientContext getContext() {
/*  40 */     return this.context;
/*     */   }
/*     */   
/*     */   protected TreeControl getTreeControl() {
/*  44 */     return super.getTreeControl();
/*     */   }
/*     */   
/*     */   protected HtmlElement createIconElement(Object wrapper) {
/*  48 */     SITOCElement node = ((CTOCTree.NodeWrapper)wrapper).node;
/*     */     
/*  50 */     if (node instanceof CTOCNode) {
/*  51 */       CTOCNode _node = (CTOCNode)node;
/*  52 */       if (!_node.hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*  53 */         return (HtmlElement)new SITIconElement(this, (CTOCTree.NodeWrapper)wrapper);
/*     */       }
/*  55 */       return (HtmlElement)new AssemblyGroupIconElement(this, (CTOCTree.NodeWrapper)wrapper);
/*     */     } 
/*     */     
/*  58 */     return (HtmlElement)new SIOIconElement(this, (CTOCTree.NodeWrapper)wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   protected HtmlElement createLabelElement(Object wrapper) {
/*  63 */     SITOCElement node = ((CTOCTree.NodeWrapper)wrapper).node;
/*  64 */     if (node instanceof CTOCNode) {
/*  65 */       if (node.getType() == CTOCType.CPR) {
/*  66 */         SITOCElement atree = findATree(node);
/*  67 */         if (atree != null) {
/*  68 */           List<CTOCTree.NodeWrapper> children = getTreeControl().getChilds(wrapper);
/*  69 */           if (children != null) {
/*  70 */             for (int i = 0; i < children.size(); i++) {
/*  71 */               CTOCTree.NodeWrapper child = children.get(i);
/*  72 */               if (child.node.getID().equals(atree.getID())) {
/*  73 */                 return (HtmlElement)new CPRLabelElement(this, (CTOCTree.NodeWrapper)wrapper, child);
/*     */               }
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*  79 */       return (HtmlElement)new DefaultLabelElement(this, (CTOCTree.NodeWrapper)wrapper);
/*     */     } 
/*  81 */     return (HtmlElement)new SIOLabelElement(this, (CTOCTree.NodeWrapper)wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public SITOCElement findATree(SITOCElement es) {
/*  86 */     List<SITOCElement> children = es.getChildren();
/*  87 */     if (children == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     for (int i = 0; i < children.size(); i++) {
/*  91 */       SITOCElement child = children.get(i);
/*  92 */       if (child instanceof SIOCPR && "A".equals(((SIOCPR)child).getProperty((SITOCProperty)SIOProperty.CPRSection))) {
/*  93 */         return child;
/*     */       }
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public String getHtmlCode(Map params) {
/* 100 */     List roots = getTreeControl().getRoots();
/* 101 */     if (roots == null || roots.size() == 0) {
/* 102 */       return this.context.getMessage("no.information.available");
/*     */     }
/* 104 */     return super.getHtmlCode(params);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\toc\TocTreeElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */