/*     */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.textsearch.resulttree;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
/*     */ import com.eoos.html.element.HtmlElementContainer;
/*     */ import com.eoos.html.element.input.LinkElement;
/*     */ import com.eoos.html.renderer.HtmlImgRenderer;
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
/*     */ public class DefaultIconElement
/*     */   extends LinkElement
/*     */ {
/*     */   protected TocTreeElement treeElement;
/*     */   protected Object node;
/*     */   
/*     */   public DefaultIconElement(TocTreeElement treeElement, Object node) {
/*  30 */     super(treeElement.getContext().createID(), null);
/*  31 */     this.treeElement = treeElement;
/*  32 */     this.node = node;
/*     */   }
/*     */   
/*     */   protected boolean flatMode() {
/*  36 */     return (this.treeElement.getMode() == -1);
/*     */   }
/*     */   
/*     */   protected String getLabel() {
/*  40 */     StringBuffer code = new StringBuffer();
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
/*  54 */     code.append(HtmlImgRenderer.getInstance().getHtmlCode((HtmlImgRenderer.Callback)new HtmlImgRenderer.CallbackAdapter() {
/*     */             public String getImageSource() {
/*  56 */               String image = null;
/*  57 */               if (DefaultIconElement.this.treeElement.getTreeControl().isLeaf(DefaultIconElement.this.node)) {
/*  58 */                 image = "common/leaf-icon.gif";
/*     */               } else {
/*  60 */                 SITOCElement sitocElement = (SITOCElement)DefaultIconElement.this.node;
/*  61 */                 if (DefaultIconElement.this.treeElement.getTreeControl().isExpanded(DefaultIconElement.this.node) && !DefaultIconElement.this.flatMode()) {
/*  62 */                   if (sitocElement instanceof CTOCNode && !((CTOCNode)DefaultIconElement.this.node).hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*  63 */                     image = "common/sit-open.gif";
/*     */                   } else {
/*  65 */                     image = "common/folder-open.gif";
/*     */                   }
/*     */                 
/*  68 */                 } else if (sitocElement instanceof CTOCNode && !((CTOCNode)DefaultIconElement.this.node).hasProperty((SITOCProperty)CTOCProperty.AssemblyGroup)) {
/*  69 */                   image = "common/sit-closed.gif";
/*     */                 } else {
/*  71 */                   image = "common/folder-closed.gif";
/*     */                 } 
/*     */               } 
/*     */ 
/*     */               
/*  76 */               return "pic/" + image;
/*     */             }
/*     */             
/*     */             public String getAlternativeText() {
/*  80 */               String retValue = null;
/*     */               try {
/*  82 */                 SIO sio = (SIO)DefaultIconElement.this.node;
/*  83 */                 if (sio.hasProperty((SITOCProperty)SIOProperty.LU)) {
/*  84 */                   retValue = (String)sio.getProperty((SITOCProperty)SIOProperty.LU);
/*     */                 }
/*  86 */               } catch (Exception e) {}
/*     */               
/*  88 */               return retValue;
/*     */             }
/*     */             
/*     */             public void getAdditionalAttributes(Map<String, String> map) {
/*  92 */               map.put("border", "0");
/*     */             }
/*     */           }));
/*     */     
/*  96 */     return code.toString();
/*     */   }
/*     */   
/*     */   public Object onClick(Map submitParams) {
/* 100 */     this.treeElement.getTreeControl().toggleExpanded(this.node);
/*     */ 
/*     */     
/* 103 */     if (flatMode()) {
/* 104 */       this.treeElement.getTreeControl().setSelectedNode(this.node);
/*     */     }
/*     */     
/* 107 */     HtmlElementContainer container = getContainer();
/* 108 */     while (container.getContainer() != null) {
/* 109 */       container = container.getContainer();
/*     */     }
/* 111 */     return container;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\textsearch\resulttree\DefaultIconElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */