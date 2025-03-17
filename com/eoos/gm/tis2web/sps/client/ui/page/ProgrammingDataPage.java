/*     */ package com.eoos.gm.tis2web.sps.client.ui.page;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.SelectionResult;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.UIAgent;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.SPSFrame;
/*     */ import com.eoos.gm.tis2web.sps.client.ui.swing.ValueRetrieval;
/*     */ import com.eoos.gm.tis2web.sps.common.ProgrammingDataSelectionRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ControllerReference;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.AssignmentRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.CommonAttribute;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.ListValueImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMapExt;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ProgrammingDataPage
/*     */   extends DefaultPage {
/*     */   public ProgrammingDataPage(UIAgent agent, SPSFrame gui, AttributeValueMapExt data, AssignmentRequest request) {
/*  24 */     super(agent, gui, data, request);
/*     */   }
/*     */   
/*     */   public void activate(Object savepoint) {
/*  28 */     super.activate(savepoint);
/*  29 */     if (this.data.getValue(CommonAttribute.REPLACE_INSTRUCTIONS) != null) {
/*  30 */       this.gui.setBackButtonState(false);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean handle(SelectionResult result, ValueRetrieval data) {
/*  35 */     log.debug("handle programming data selection");
/*  36 */     ProgrammingDataSelectionRequest request = (ProgrammingDataSelectionRequest)getRequest();
/*  37 */     List selections = new ArrayList();
/*  38 */     if (request.getProgrammingSequence() != null) {
/*  39 */       ProgrammingSequence sequence = request.getProgrammingSequence();
/*  40 */       List<ControllerReference> controllers = sequence.getSequence();
/*  41 */       for (int i = 0; i < controllers.size(); i++) {
/*  42 */         ControllerReference controller = controllers.get(i);
/*  43 */         if (!controller.isType4Application()) {
/*  44 */           List modules = sequence.getProgrammingData(i);
/*  45 */           if (!checkModules(modules, selections)) {
/*  46 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*  51 */       List modules = request.getOptions();
/*  52 */       if (!checkModules(modules, selections)) {
/*  53 */         return true;
/*     */       }
/*     */     } 
/*  56 */     this.data.set(CommonAttribute.PROGRAMMING_DATA_SELECTION, (Value)new ListValueImpl(selections));
/*  57 */     this.gui.setNextButtonState(true);
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean checkModules(List<Module> modules, List<Part> selections) {
/*  62 */     for (int i = 0; i < modules.size(); i++) {
/*  63 */       Module module = modules.get(i);
/*  64 */       Part part = module.getSelectedPart();
/*  65 */       if (part == null) {
/*  66 */         return false;
/*     */       }
/*  68 */       selections.add(part);
/*     */     } 
/*  70 */     return true;
/*     */   }
/*     */   
/*     */   public UIPage undo() {
/*  74 */     resetProgrammingDataSelection((ProgrammingDataSelectionRequest)this.request);
/*  75 */     return super.undo();
/*     */   }
/*     */   
/*     */   protected void resetProgrammingDataSelection(ProgrammingDataSelectionRequest request) {
/*  79 */     if (request.getProgrammingSequence() != null) {
/*  80 */       ProgrammingSequence sequence = request.getProgrammingSequence();
/*  81 */       List controllers = sequence.getSequence();
/*  82 */       for (int i = 0; i < controllers.size(); i++) {
/*  83 */         List modules = sequence.getProgrammingData(i);
/*  84 */         resetProgrammingDataSelection(modules);
/*     */       } 
/*     */     } else {
/*  87 */       resetProgrammingDataSelection(request.getOptions());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resetProgrammingDataSelection(List<Module> modules) {
/*  92 */     for (int i = 1; i < modules.size(); i++) {
/*  93 */       Module module = modules.get(i);
/*  94 */       if (module.getSelectedPart() != null) {
/*  95 */         undoProgrammingDataSelectionSelection(module.getOriginPart().getCOP());
/*  96 */         module.setSelectedPart(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void undoProgrammingDataSelectionSelection(List<COP> cop) {
/* 102 */     if (cop == null) {
/*     */       return;
/*     */     }
/* 105 */     for (int i = 0; i < cop.size(); i++) {
/* 106 */       COP link = cop.get(i);
/* 107 */       if (link.getMode() == 3) {
/* 108 */         link.setMode(1);
/*     */         return;
/*     */       } 
/* 111 */       undoProgrammingDataSelectionSelection(link.getPart().getCOP());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\page\ProgrammingDataPage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */