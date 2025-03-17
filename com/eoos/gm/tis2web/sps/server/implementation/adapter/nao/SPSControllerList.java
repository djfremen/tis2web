/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSController;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSControllerList
/*     */   extends SPSControllerList
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   protected transient SPSOption controllerTypeSelection;
/*     */   protected transient List options;
/*     */   protected transient SPSSession session;
/*     */   protected transient boolean hasControllerVCI;
/*     */   protected transient boolean hasControllerPROM;
/*     */   
/*     */   void registerSession(SPSSession session) {
/*  28 */     this.session = session;
/*     */   }
/*     */   
/*     */   void insert(SPSController controller) {
/*  32 */     if (controller instanceof SPSControllerVCI || controller instanceof SPSProgrammingSequence) {
/*  33 */       this.hasControllerVCI = true;
/*  34 */     } else if (controller instanceof SPSControllerPROM) {
/*  35 */       this.hasControllerPROM = true;
/*     */     } 
/*  37 */     for (int i = 0; i < size(); i++) {
/*  38 */       SPSControllerReference ref = (SPSControllerReference)get(i);
/*  39 */       if (ref.accept(controller)) {
/*  40 */         ref.add(controller);
/*     */         return;
/*     */       } 
/*     */     } 
/*  44 */     add(new SPSControllerReference(controller));
/*     */   }
/*     */   
/*     */   public List getOptions() {
/*  48 */     return this.options;
/*     */   }
/*     */   
/*     */   public SPSOption getControllerTypeSelection() {
/*  52 */     return this.controllerTypeSelection;
/*     */   }
/*     */   
/*     */   public boolean match(AttributeValueMap data) {
/*  56 */     if (this.controllerTypeSelection != null) {
/*  57 */       SPSOption selection = (SPSOption)data.getValue((Attribute)this.controllerTypeSelection.getType());
/*  58 */       if (selection != null && !selection.equals(this.controllerTypeSelection)) {
/*  59 */         this.controllerTypeSelection = selection;
/*  60 */         this.options = new ArrayList();
/*  61 */         this.options.add(selection);
/*  62 */         this.hasControllerVCI = this.hasControllerPROM = true;
/*  63 */         this.session.getVehicle().getOptions().clear();
/*  64 */         return false;
/*     */       } 
/*     */     } 
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   protected void qualify(List options, SPSSchemaAdapter adapter) throws Exception {
/*  71 */     if (this.hasControllerVCI && this.hasControllerPROM) {
/*  72 */       if (options == null || options.size() == 0) {
/*  73 */         this.options = new ArrayList();
/*  74 */         this.options.add(SPSOption.getControllerOption((SPSLanguage)this.session.getLanguage(), "pluggable"));
/*  75 */         this.options.add(SPSOption.getControllerOption((SPSLanguage)this.session.getLanguage(), "reprogrammable"));
/*     */         return;
/*     */       } 
/*  78 */       this.hasControllerVCI = this.hasControllerPROM = false;
/*  79 */       String ctype = getSelectedControllerType(options);
/*  80 */       Iterator<SPSControllerReference> it = iterator();
/*  81 */       while (it.hasNext()) {
/*  82 */         SPSControllerReference reference = it.next();
/*  83 */         if (!ctype.equals(reference.getType())) {
/*  84 */           it.remove();
/*     */         }
/*     */       } 
/*  87 */       this.session.getVehicle().getOptions().clear();
/*     */     } 
/*     */     
/*  90 */     if (options != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       Iterator<SPSControllerReference> it = iterator();
/*  96 */       while (it.hasNext()) {
/*  97 */         SPSControllerReference reference = it.next();
/*  98 */         SPSController controller = reference.qualify(options, adapter);
/*  99 */         if (controller == null && 
/* 100 */           reference.getControllers().size() == 0) {
/* 101 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     this.options = SPSOption.filter(options, getPreSelectionOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getSelectedControllerType(List<SPSOption> options) {
/* 111 */     for (int i = 0; i < options.size(); i++) {
/* 112 */       SPSOption option = options.get(i);
/* 113 */       if (SPSOption.isControllerOption(option)) {
/* 114 */         this.controllerTypeSelection = option;
/* 115 */         return option.getID().endsWith("pluggable") ? "pluggable" : "reprogrammable";
/*     */       } 
/*     */     } 
/* 118 */     return "reprogrammable";
/*     */   }
/*     */   
/*     */   protected void removeProgrammingSequences() {
/* 122 */     Iterator<SPSControllerReference> it = iterator();
/* 123 */     while (it.hasNext()) {
/* 124 */       SPSControllerReference reference = it.next();
/* 125 */       List controllers = reference.getControllers();
/* 126 */       if (controllers != null && controllers.size() > 0 && controllers.get(0) instanceof SPSProgrammingSequence) {
/* 127 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void suppressProgrammingSequenceControllers() {
/* 133 */     Iterator<SPSControllerReference> it = iterator();
/* 134 */     while (it.hasNext()) {
/* 135 */       SPSControllerReference reference = it.next();
/* 136 */       List controllers = reference.getControllers();
/* 137 */       if (controllers != null && controllers.size() > 0) {
/* 138 */         Iterator<SPSController> it2 = controllers.iterator();
/* 139 */         int instances = controllers.size();
/* 140 */         while (it2.hasNext()) {
/* 141 */           SPSController controller = it2.next();
/* 142 */           if (controller instanceof SPSControllerVCI && ((SPSControllerVCI)controller).getSuppressSequenceControllerFlag()) {
/* 143 */             instances--;
/*     */           }
/*     */         } 
/* 146 */         if (instances == 0)
/* 147 */           it.remove(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSControllerList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */