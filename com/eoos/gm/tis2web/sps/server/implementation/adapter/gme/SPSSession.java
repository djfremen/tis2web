/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerList;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSLanguage;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSModel;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSProgrammingData;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSchemaAdapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSVehicle;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import edu.umd.cs.findbugs.annotations.SuppressWarnings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ @SuppressWarnings({"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"})
/*     */ public class SPSSession
/*     */   extends SPSSession
/*     */ {
/*     */   protected Integer vinid;
/*  25 */   protected long vci = -1L;
/*     */   
/*     */   protected boolean isCALID = false;
/*     */   
/*     */   protected boolean disabledHWOCheck = false;
/*     */   
/*  31 */   protected List optionsHWO = null;
/*     */   
/*     */   public SPSSession(long signature, long tag, SPSModel model, SPSLanguage language, SPSVehicle vehicle, String sessionID) {
/*  34 */     super(signature, tag, model, language, vehicle, sessionID);
/*     */   }
/*     */   
/*     */   public SPSControllerList getControllers(List devices, Value mode, String toolType) throws Exception {
/*  38 */     if (this.controllers == null) {
/*     */ 
/*     */       
/*  41 */       this.controllers = this.model.getControllers(this, devices, mode, toolType);
/*  42 */       if (this.controllers != null) {
/*  43 */         Collections.sort((List<?>)this.controllers, SPSControllerReference.COMPARATOR_DESCRIPTION);
/*     */       }
/*  45 */       this.mode = mode;
/*     */     } 
/*  47 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public Integer getAsBuiltVINID() {
/*  51 */     return this.vinid;
/*     */   }
/*     */   
/*     */   public void setAsBuiltVINID(Integer vinid) {
/*  55 */     this.vinid = vinid;
/*     */   }
/*     */   
/*     */   public long getDealerVCI() {
/*  59 */     return this.vci;
/*     */   }
/*     */   
/*     */   public void setDealerVCI(long vci) {
/*  63 */     this.vci = vci;
/*     */   }
/*     */   
/*     */   public boolean isCALID() {
/*  67 */     return this.isCALID;
/*     */   }
/*     */   
/*     */   public void setMode(boolean isCALID) {
/*  71 */     this.isCALID = isCALID;
/*     */   }
/*     */   
/*     */   public void disableHWOCheck() {
/*  75 */     this.disabledHWOCheck = true;
/*     */   }
/*     */   
/*     */   public void enableHWOCheck() {
/*  79 */     this.disabledHWOCheck = false;
/*     */   }
/*     */   
/*     */   public boolean isHWOCheckDisabled() {
/*  83 */     return this.disabledHWOCheck;
/*     */   }
/*     */   
/*     */   public void resetHWOCategory() {
/*  87 */     this.optionsHWO = null;
/*     */   }
/*     */   
/*     */   public void storeHWOCategory(SPSBaseCategory category) {
/*     */     try {
/*  92 */       SPSOptionCategory scategory = category.getCategory();
/*  93 */       if (!scategory.isHWOCategory()) {
/*     */         return;
/*     */       }
/*  96 */       List options = scategory.getOptions();
/*  97 */       if (options == null || options.size() == 0) {
/*  98 */         if (category.getGroup() != null && SPSBaseCategory.hasOptions(category.getGroup())) {
/*  99 */           options = category.getGroup().getOptions();
/* 100 */           addOptions(options);
/*     */         } 
/*     */       } else {
/* 103 */         addOptions(options);
/*     */       } 
/* 105 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addOptions(List<SPSOptionHWO> options) {
/* 110 */     if (this.optionsHWO == null) {
/* 111 */       this.optionsHWO = new ArrayList();
/* 112 */       this.optionsHWO.addAll(options);
/*     */     } else {
/* 114 */       for (int i = 0; i < options.size(); i++) {
/* 115 */         SPSOptionHWO option = options.get(i);
/* 116 */         boolean exists = false;
/* 117 */         for (int j = 0; j < this.optionsHWO.size(); j++) {
/* 118 */           SPSOptionHWO optionHWO = this.optionsHWO.get(j);
/* 119 */           if (option.getHWName().equals(optionHWO.getHWName())) {
/* 120 */             exists = true;
/*     */           }
/*     */         } 
/* 123 */         if (!exists) {
/* 124 */           this.optionsHWO.add(option);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List retrieveHWOCategory() {
/* 131 */     return this.optionsHWO;
/*     */   }
/*     */   
/*     */   public boolean selectControllerByVehicleID(SPSSchemaAdapter adapter, List vehicles) throws Exception {
/* 135 */     if (this.reference != null) {
/* 136 */       SPSControllerList controllers = null;
/* 137 */       if (((SPSSchemaAdapterGME)adapter).supportsSPSFunctions()) {
/* 138 */         controllers = ((SPSModel)this.model).getVehicleControllerFunctionsVCI(this, vehicles);
/*     */       } else {
/* 140 */         controllers = ((SPSModel)this.model).getVehicleControllersVCI(this, vehicles);
/*     */       } 
/* 142 */       if (controllers != null) {
/* 143 */         SPSControllerGME controller = ((SPSControllerReference)this.reference).selectControllerByVehicleID(adapter, this, controllers);
/* 144 */         if (controller != null) {
/* 145 */           this.controller = controller;
/* 146 */           return true;
/* 147 */         }  if (this.reference.getOptions() != null) {
/* 148 */           this.controller = null;
/* 149 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   public void checkACLResourceNoHWK(SPSProgrammingData data) {
/*     */     try {
/* 158 */       ClientContext context = ClientContextProvider.getInstance().getContext(this.sessionID, false);
/* 159 */       Collection acl = SPSHardwareKey.getAuthorizedResources(context);
/* 160 */       if (acl != null) {
/* 161 */         Iterator it = acl.iterator();
/* 162 */         while (it.hasNext()) {
/* 163 */           Object resource = it.next();
/* 164 */           if ("no-hwk".equalsIgnoreCase(resource.toString())) {
/* 165 */             data.setRepairShopCode(null);
/*     */           }
/*     */         } 
/*     */       } 
/* 169 */     } catch (Exception x) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public SPSProgrammingData getProgrammingData(SPSSchemaAdapter adapter) throws Exception {
/* 174 */     if (this.data == null && this.controller != null) {
/* 175 */       this.data = this.controller.getProgrammingData(adapter);
/* 176 */       if (this.data != null) {
/* 177 */         checkACLResourceNoHWK(this.data);
/*     */       }
/*     */     } 
/* 180 */     return this.data;
/*     */   }
/*     */   
/*     */   public void done() {
/* 184 */     this.optionsHWO = null;
/* 185 */     super.done();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSession.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */