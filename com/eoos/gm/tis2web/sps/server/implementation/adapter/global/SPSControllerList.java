/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
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
/*     */   
/*     */   void registerSession(SPSSession session) {
/*  27 */     this.session = session;
/*     */   }
/*     */   
/*     */   void insert(SPSController controller) {
/*  31 */     for (int i = 0; i < size(); i++) {
/*  32 */       SPSControllerReference ref = (SPSControllerReference)get(i);
/*  33 */       if (ref.accept(controller)) {
/*  34 */         ref.add(controller);
/*     */         return;
/*     */       } 
/*     */     } 
/*  38 */     add(new SPSControllerReference(controller));
/*     */   }
/*     */   
/*     */   public List getOptions() {
/*  42 */     return this.options;
/*     */   }
/*     */   
/*     */   public SPSOption getControllerTypeSelection() {
/*  46 */     return this.controllerTypeSelection;
/*     */   }
/*     */   
/*     */   public boolean match(AttributeValueMap data) {
/*  50 */     if (this.controllerTypeSelection != null) {
/*  51 */       SPSOption selection = (SPSOption)data.getValue((Attribute)this.controllerTypeSelection.getType());
/*  52 */       if (selection != null && !selection.equals(this.controllerTypeSelection)) {
/*  53 */         this.controllerTypeSelection = selection;
/*  54 */         this.options = new ArrayList();
/*  55 */         this.options.add(selection);
/*  56 */         this.hasControllerVCI = true;
/*  57 */         this.session.getVehicle().getOptions().clear();
/*  58 */         return false;
/*     */       } 
/*     */     } 
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   protected void qualify(List options, SPSSchemaAdapter adapter) throws Exception {
/*  65 */     if (options == null || options.size() == 0) {
/*  66 */       this.options = new ArrayList();
/*  67 */       this.options.add(SPSOption.getControllerOption((SPSLanguage)this.session.getLanguage(), "reprogrammable"));
/*     */       return;
/*     */     } 
/*  70 */     this.session.getVehicle().getOptions().clear();
/*     */     
/*  72 */     if (options != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       Iterator<SPSControllerReference> it = iterator();
/*  78 */       while (it.hasNext()) {
/*  79 */         SPSControllerReference reference = it.next();
/*  80 */         SPSController controller = reference.qualify(options, adapter);
/*  81 */         if (controller == null && 
/*  82 */           reference.getControllers().size() == 0) {
/*  83 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     this.options = SPSOption.filter(options, getPreSelectionOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void removeProgrammingSequences() {
/*  93 */     Iterator<SPSControllerReference> it = iterator();
/*  94 */     while (it.hasNext()) {
/*  95 */       SPSControllerReference reference = it.next();
/*  96 */       List controllers = reference.getControllers();
/*  97 */       if (controllers != null && controllers.size() > 0 && controllers.get(0) instanceof SPSProgrammingSequence) {
/*  98 */         it.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void suppressProgrammingSequenceControllers() {
/* 104 */     Iterator<SPSControllerReference> it = iterator();
/* 105 */     while (it.hasNext()) {
/* 106 */       SPSControllerReference reference = it.next();
/* 107 */       List controllers = reference.getControllers();
/* 108 */       if (controllers != null && controllers.size() > 0) {
/* 109 */         Iterator<SPSController> it2 = controllers.iterator();
/* 110 */         int instances = controllers.size();
/* 111 */         while (it2.hasNext()) {
/* 112 */           SPSController controller = it2.next();
/* 113 */           if (controller instanceof SPSControllerVCI && ((SPSControllerVCI)controller).getSuppressSequenceControllerFlag()) {
/* 114 */             instances--;
/*     */           }
/*     */         } 
/* 117 */         if (instances == 0)
/* 118 */           it.remove(); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSControllerList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */