/*     */ package com.eoos.gm.tis2web.si.implementation.io.datamodel.specialbrochures;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOCProperty;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.vcr.VCRUtil;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VehicleCfgStorage;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.LinkedList;
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
/*     */ public class SpecialBrochuresContext
/*     */ {
/*     */   protected ClientContext context;
/*  27 */   protected LinkedList selectedSIOs = new LinkedList();
/*     */   
/*  29 */   protected ContentTreeControl treeControl = null;
/*     */   
/*     */   protected boolean inspectionMandatorySet = false;
/*     */ 
/*     */   
/*     */   private SpecialBrochuresContext(ClientContext context) {
/*  35 */     this.context = context;
/*  36 */     VCFacade.getInstance(context).addObserver(new VehicleCfgStorage.Observer()
/*     */         {
/*     */           public void onVehicleConfigurationChange() {
/*  39 */             SpecialBrochuresContext.this.checkInspectionMandatory();
/*  40 */             SpecialBrochuresContext.this.selectedSIOs.clear();
/*     */           }
/*     */         });
/*     */     
/*  44 */     checkInspectionMandatory();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkInspectionMandatory() {
/*  49 */     this.inspectionMandatorySet = VCRUtil.checkMandatory_Inspections(VCFacade.getInstance(this.context).getCfg());
/*     */   }
/*     */   
/*     */   public static SpecialBrochuresContext getInstance(ClientContext context) {
/*  53 */     synchronized (context.getLockObject()) {
/*  54 */       SpecialBrochuresContext instance = (SpecialBrochuresContext)context.getObject(SpecialBrochuresContext.class);
/*  55 */       if (instance == null) {
/*  56 */         instance = new SpecialBrochuresContext(context);
/*  57 */         context.storeObject(SpecialBrochuresContext.class, instance);
/*     */       } 
/*  59 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelectedSIO(SIO sio, boolean historyMode) {
/*  64 */     if (historyMode) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  69 */       int index = this.selectedSIOs.lastIndexOf(sio);
/*  70 */       if (index >= 0) {
/*  71 */         this.selectedSIOs = new LinkedList(this.selectedSIOs.subList(0, index + 1));
/*     */       }
/*     */     } else {
/*  74 */       this.selectedSIOs.add(sio);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSelectedSIO(SIO sio) {
/*  79 */     this.selectedSIOs.clear();
/*  80 */     this.selectedSIOs.add(sio);
/*     */   }
/*     */   
/*     */   public SIO getSelectedSIO() {
/*  84 */     if (this.selectedSIOs.isEmpty()) {
/*  85 */       return null;
/*     */     }
/*  87 */     return this.selectedSIOs.getLast();
/*     */   }
/*     */ 
/*     */   
/*     */   public SIO getPredessor(SIO sio) {
/*  92 */     int index = this.selectedSIOs.indexOf(sio);
/*  93 */     if (index <= 0)
/*     */     {
/*  95 */       return null;
/*     */     }
/*  97 */     return this.selectedSIOs.get(index - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ContentTreeControl getContentTreeControl() {
/* 103 */     if (this.treeControl == null) {
/* 104 */       this.treeControl = new ContentTreeControl(this.context);
/*     */     }
/* 106 */     return this.treeControl;
/*     */   }
/*     */   
/*     */   public static boolean isInspectionNode(Node node) {
/* 110 */     boolean retValue = false;
/*     */     try {
/* 112 */       String sit = (String)node.content.getProperty((SITOCProperty)CTOCProperty.SIT);
/* 113 */       if (sit != null && sit.equalsIgnoreCase("SIT-13")) {
/* 114 */         retValue = true;
/*     */       }
/* 116 */     } catch (Exception e) {
/* 117 */       retValue = false;
/*     */     } 
/*     */     
/* 120 */     if (!retValue && node.parent != null) {
/* 121 */       retValue = isInspectionNode(node.parent);
/*     */     }
/* 123 */     return retValue;
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/* 127 */     ILVCAdapter adapter = SIDataAdapterFacade.getInstance(this.context).getLVCAdapter();
/* 128 */     VCR vcr = null;
/* 129 */     if (adapter != null) {
/* 130 */       vcr = adapter.toVCR(VCFacade.getInstance(this.context).getCfg());
/*     */     }
/* 132 */     return (vcr == null) ? VCR.NULL : vcr;
/*     */   }
/*     */   
/*     */   public boolean inspectionMandatorySet() {
/* 136 */     return this.inspectionMandatorySet;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\datamodel\specialbrochures\SpecialBrochuresContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */