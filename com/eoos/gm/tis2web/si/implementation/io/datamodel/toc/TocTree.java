/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.toc;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.SICTOCService;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.ctoctree.datamodel.CTOCTree;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.sitfilter.SIT;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.ui.html.document.page.DocumentPage;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Observable;
/*     */ import java.util.Observer;
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
/*  34 */   private static Logger log = Logger.getLogger(TocTree.class);
/*     */   
/*  36 */   protected VCR vcr = null;
/*     */   
/*  38 */   protected CTOCNode root = null;
/*     */   
/*  40 */   protected List sits = null;
/*     */   
/*  42 */   private DocumentPage docPage = null;
/*     */   
/*  44 */   private Observable rootObservable = new Observable() {
/*     */       public void notifyObservers(Object obj) {
/*  46 */         setChanged();
/*  47 */         super.notifyObservers(obj);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private TocTree(final ClientContext context) {
/*  53 */     super(context);
/*  54 */     this.sits = SIT.convertToStringList(SIT.getInstance(context).getSITS());
/*     */     try {
/*  56 */       VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */           {
/*     */             public void onVehicleConfigurationChange() {
/*     */               try {
/*  60 */                 ILVCAdapter lvc = TocTree.this.getLVCAdapter();
/*  61 */                 if (lvc == null) {
/*  62 */                   TocTree.log.warn("no ILVCAdapter found!");
/*     */                   return;
/*     */                 } 
/*  65 */                 TocTree.log.debug("...adapter: " + lvc);
/*     */                 
/*  67 */                 IConfiguration currentCfg = VCFacade.getInstance(context).getCfg();
/*  68 */                 TocTree.log.debug("...current cfg: " + currentCfg);
/*  69 */                 VCR vcr = lvc.toVCR(currentCfg);
/*  70 */                 TocTree.log.debug("...as vcr: " + vcr);
/*  71 */                 VCConfiguration vcCfg = lvc.getVC().getConfiguration(vcr);
/*  72 */                 TocTree.log.debug("...as legacy cfg: " + String.valueOf(vcCfg));
/*  73 */                 VCR rootVcr = lvc.makeVCR(vcCfg);
/*  74 */                 TocTree.log.debug("...rootVcr: " + rootVcr);
/*  75 */                 SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(context).getSICTOCService();
/*  76 */                 CTOCNode cTOCNode = siCTOCService.getCTOC().getCTOC(rootVcr);
/*  77 */                 TocTree.log.debug("...root node " + ((cTOCNode != null) ? "found" : "NOT FOUND"));
/*     */                 
/*  79 */                 Integer currentID = null;
/*     */                 try {
/*  81 */                   currentID = TocTree.this.root.getID();
/*  82 */                 } catch (NullPointerException e) {}
/*     */ 
/*     */                 
/*  85 */                 Integer newID = null;
/*     */                 try {
/*  87 */                   newID = (cTOCNode != null) ? cTOCNode.getID() : null;
/*  88 */                 } catch (NullPointerException e) {}
/*     */ 
/*     */                 
/*  91 */                 if ((currentID == null) ? (newID != null) : !currentID.equals(newID)) {
/*  92 */                   TocTree.this.init();
/*     */                 } else {
/*  94 */                   if (TocTree.this.docPage != null) {
/*  95 */                     TocTree.this.docPage.onOptionsChanged();
/*     */                   }
/*  97 */                   TocTree.this.vcr = vcr;
/*     */                   
/*  99 */                   List selectedPath = TocTree.this.getSelectedPath();
/* 100 */                   if (selectedPath != null && selectedPath.size() > 0 && cTOCNode != null && 
/* 101 */                     !TocTree.this.validPath(new CTOCTree.NodeWrapper(TocTree.this, cTOCNode, cTOCNode.getID().toString()), selectedPath)) {
/* 102 */                     TocTree.this.setSelectedNode((Object)null);
/*     */                   }
/*     */                 }
/*     */               
/* 106 */               } catch (Exception e) {
/* 107 */                 TocTree.log.error("onVehicleContextChange - error:" + e, e);
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/* 112 */       init();
/* 113 */     } catch (Exception e) {
/* 114 */       log.error("unable to create a new instance of TocTree - error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ILVCAdapter getLVCAdapter() {
/* 119 */     return SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/*     */   }
/*     */   
/*     */   public void addRootObserver(Observer o) {
/* 123 */     this.rootObservable.addObserver(o);
/*     */   }
/*     */   
/*     */   public static TocTree getInstance(ClientContext context) {
/* 127 */     synchronized (context.getLockObject()) {
/* 128 */       TocTree instance = (TocTree)context.getObject(TocTree.class);
/* 129 */       if (instance == null) {
/* 130 */         instance = new TocTree(context);
/* 131 */         context.storeObject(TocTree.class, instance);
/*     */       } 
/* 133 */       return instance;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*     */     try {
/* 140 */       ILVCAdapter lvc = getLVCAdapter();
/*     */       
/* 142 */       this.vcr = lvc.toVCR(VCFacade.getInstance(this.context).getCfg());
/*     */       
/* 144 */       VCR rootVCR = lvc.makeVCR(lvc.getVC().getConfiguration(this.vcr));
/*     */       
/* 146 */       SICTOCService siCTOCService = SIDataAdapterFacade.getInstance(this.context).getSICTOCService();
/* 147 */       this.root = siCTOCService.getCTOC().getCTOC(rootVCR);
/* 148 */       this.rootObservable.notifyObservers(this.root);
/* 149 */       setSelectedNode((Object)null);
/* 150 */       this.expandedNodes.clear();
/* 151 */     } catch (Exception e) {
/* 152 */       log.error("unable to create toc -error:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected VCR getVCR() {
/* 157 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public CTOCNode getRootNode() {
/* 161 */     return this.root;
/*     */   }
/*     */   
/*     */   protected List getSITFilter() {
/* 165 */     return this.sits;
/*     */   }
/*     */   
/*     */   public void setSITFilter(List<?> sits) {
/* 169 */     this.sits = sits;
/* 170 */     Collections.sort(sits, new Comparator() {
/*     */           public int compare(Object a, Object b) {
/*     */             try {
/* 173 */               String x = ((String)a).substring(((String)a).indexOf('-') + 1);
/* 174 */               String y = ((String)b).substring(((String)b).indexOf('-') + 1);
/* 175 */               return Integer.valueOf(x).compareTo(Integer.valueOf(y));
/* 176 */             } catch (Exception x) {
/*     */               
/* 178 */               return 0;
/*     */             }  }
/*     */         });
/* 181 */     List selectedPath = getSelectedPath();
/* 182 */     if (selectedPath != null && selectedPath.size() > 0 && 
/* 183 */       !validPath(getRootNodeWrapper(), selectedPath)) {
/* 184 */       setSelectedNode((Object)null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CTOCTree.NodeWrapper getRootNodeWrapper() {
/* 191 */     CTOCNode root = getRootNode();
/* 192 */     return new CTOCTree.NodeWrapper(this, root, root.getID().toString());
/*     */   }
/*     */   
/*     */   public void setSelectedNode(Object node) {
/* 196 */     if (node != null);
/*     */ 
/*     */     
/* 199 */     super.setSelectedNode(node);
/*     */   }
/*     */   
/*     */   public void setDocPage(DocumentPage docPage) {
/* 203 */     this.docPage = docPage;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\toc\TocTree.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */