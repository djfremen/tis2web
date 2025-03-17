/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.toc.search;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TocTree
/*     */   extends CTOCTree
/*     */ {
/*  26 */   private static Logger log = Logger.getLogger(TocTree.class);
/*     */   
/*  28 */   protected VCR vcr = null;
/*     */   
/*  30 */   protected CTOCNode root = null;
/*     */ 
/*     */   
/*     */   private TocTree(final ClientContext context) {
/*  34 */     super(context);
/*     */     try {
/*  36 */       VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */           {
/*     */             
/*     */             public void onVehicleConfigurationChange()
/*     */             {
/*     */               try {
/*  42 */                 ILVCAdapter lvc = TocTree.this.getLVCAdapter();
/*     */                 
/*  44 */                 VCR vcr = lvc.toVCR(VCFacade.getInstance(context).getCfg());
/*     */                 
/*  46 */                 VCR rootVcr = lvc.makeVCR(lvc.getVC().getConfiguration(vcr));
/*     */                 
/*  48 */                 LTCTOCService ltCTOCservice = LTDataAdapterFacade.getInstance(context).getLTCTOCService();
/*  49 */                 CTOCNode cTOCNode = ltCTOCservice.getCTOC().getCTOC(rootVcr);
/*     */ 
/*     */                 
/*  52 */                 Integer currentID = null;
/*     */                 try {
/*  54 */                   currentID = TocTree.this.root.getID();
/*  55 */                 } catch (NullPointerException e) {}
/*     */ 
/*     */                 
/*  58 */                 Integer newID = null;
/*     */                 try {
/*  60 */                   newID = cTOCNode.getID();
/*  61 */                 } catch (NullPointerException e) {}
/*     */ 
/*     */                 
/*  64 */                 if ((currentID == null) ? (newID != null) : !currentID.equals(newID)) {
/*  65 */                   TocTree.this.init();
/*     */                 } else {
/*  67 */                   TocTree.this.vcr = vcr;
/*     */                   
/*  69 */                   List selectedPath = TocTree.this.getSelectedPath();
/*  70 */                   if (!TocTree.this.validPath(new CTOCTree.NodeWrapper(TocTree.this, cTOCNode, cTOCNode.getID().toString()), selectedPath)) {
/*  71 */                     TocTree.this.setSelectedNode((Object)null);
/*     */                   }
/*     */                 } 
/*  74 */               } catch (Exception e) {
/*  75 */                 TocTree.log.error("onVehicleContextChange - error:" + e, e);
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  80 */       init();
/*  81 */     } catch (Exception e) {
/*  82 */       log.error("unable to create a new instance of TocTree - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/*  87 */     return LTDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static TocTree getInstance(ClientContext context) {
/*  93 */     synchronized (context.getLockObject()) {
/*  94 */       TocTree instance = (TocTree)context.getObject(TocTree.class);
/*  95 */       if (instance == null) {
/*  96 */         instance = new TocTree(context);
/*  97 */         context.storeObject(TocTree.class, instance);
/*     */       } 
/*  99 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() {
/*     */     try {
/* 105 */       ILVCAdapter lvc = getLVCAdapter();
/*     */       
/* 107 */       this.vcr = lvc.toVCR(VCFacade.getInstance(this.context).getCfg());
/*     */       
/* 109 */       VCR rootVCR = lvc.makeVCR(lvc.getVC().getConfiguration(this.vcr));
/*     */       
/* 111 */       LTCTOCService ltCTOCservice = LTDataAdapterFacade.getInstance(this.context).getLTCTOCService();
/* 112 */       this.root = ltCTOCservice.getCTOC().getCTOC(rootVCR);
/* 113 */       setSelectedNode((Object)null);
/* 114 */       this.expandedNodes.clear();
/* 115 */     } catch (Exception e) {
/* 116 */       log.error("unable to create toc -error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCR getVCR() {
/* 121 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public CTOCNode getRootNode() {
/* 125 */     return this.root;
/*     */   }
/*     */   
/*     */   public CTOCTree.NodeWrapper getRootNodeWrapper() {
/* 129 */     CTOCNode root = getRootNode();
/* 130 */     return new CTOCTree.NodeWrapper(this, root, root.getID().toString());
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/* 134 */     if (node != null);
/*     */ 
/*     */     
/* 137 */     super.setSelectedNode(node);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List getSITFilter() {
/* 145 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\toc\search\TocTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */