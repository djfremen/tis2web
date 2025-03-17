/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures;
/*     */ 
/*     */ import com.eoos.datatype.tree.navigation.TreeNavigation2;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationImpl;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI;
/*     */ import com.eoos.datatype.tree.navigation.implementation.one.TreeNavigationSPI2;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.html.element.input.tree.gtwo.TreeControl;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class ContentTreeControl
/*     */   implements TreeControl
/*     */ {
/*     */   protected ClientContext context;
/*     */   protected TreeNavigation2 navigation;
/*  32 */   private Node selectedNode = null;
/*     */   
/*     */   protected Node superroot;
/*     */ 
/*     */   
/*     */   public ContentTreeControl(ClientContext context) {
/*  38 */     this.context = context;
/*  39 */     TreeNavigationSPI2 spi = new ContentTreeNavigationSPIImpl(context);
/*     */     
/*  41 */     this.navigation = (TreeNavigation2)new TreeNavigationImpl((TreeNavigationSPI)spi);
/*     */     
/*  43 */     this.superroot = (Node)spi.getSuperroot();
/*  44 */     this.selectedNode = this.superroot;
/*     */     
/*  46 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  49 */             ContentTreeControl.this.selectedNode = ContentTreeControl.this.superroot;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getIdentifier(Object node) {
/*  57 */     return ((Node)node).getIdentifier();
/*     */   }
/*     */   
/*     */   public String getLabel(Object node) {
/*  61 */     if (node.equals(getSuperroot())) {
/*  62 */       return this.context.getLabel("si.selectedpath.top");
/*     */     }
/*  64 */     return ((Node)node).content.getLabel(LocaleInfoProvider.getInstance().getLocale(this.context.getLocale()));
/*     */   }
/*     */ 
/*     */   
/*     */   public List getChilds(Object node) {
/*  69 */     return this.navigation.getChildren(node);
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/*  73 */     this.selectedNode = (Node)node;
/*  74 */     if (this.selectedNode != null && this.selectedNode.content instanceof SIO) {
/*  75 */       SpecialBrochuresContext.getInstance(this.context).setSelectedSIO((SIO)this.selectedNode.content);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getSelectedNode() {
/*  80 */     return this.selectedNode;
/*     */   }
/*     */   
/*     */   public List getRoots() {
/*  84 */     return this.navigation.getRoots();
/*     */   }
/*     */   
/*     */   public List getSelectedPath() {
/*  88 */     List<Node> retValue = new LinkedList();
/*  89 */     retValue.addAll(this.navigation.getPath(this.selectedNode));
/*  90 */     retValue.add(this.selectedNode);
/*     */     
/*  92 */     return retValue;
/*     */   }
/*     */   
/*     */   public boolean isLeaf(Object node) {
/*  96 */     boolean retValue = false;
/*     */     try {
/*  98 */       if (((Node)node).content.isSIO()) {
/*  99 */         retValue = true;
/*     */       }
/* 101 */     } catch (Exception e) {
/* 102 */       retValue = false;
/*     */     } 
/* 104 */     return retValue;
/*     */   }
/*     */   
/*     */   public Object getParent(Object node) {
/* 108 */     return this.navigation.getParent(node);
/*     */   }
/*     */   
/*     */   public Object getSuperroot() {
/* 112 */     return this.superroot;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\specialbrochures\ContentTreeControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */