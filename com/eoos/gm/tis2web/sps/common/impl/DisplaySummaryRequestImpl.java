/*     */ package com.eoos.gm.tis2web.sps.common.impl;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.DisplaySummaryRequest;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.request.RequestGroup;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.implementation.request.ConfirmationRequestImpl;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Attribute;
/*     */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*     */ import java.util.List;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class DisplaySummaryRequestImpl
/*     */   extends ConfirmationRequestImpl
/*     */   implements DisplaySummaryRequest
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private TableModel descriptionData;
/*     */   private TableModel moduleData;
/*     */   private List controllers;
/*     */   private List moduleDataList;
/*     */   private List moduleDataListGME;
/*     */   private List descriptionDataListGME;
/*     */   private TableModel vehicleData;
/*     */   private Summary summary;
/*     */   private List sequenceGME;
/*     */   private ProgrammingSequence sequenceNAO;
/*     */   private boolean hasGMEMoreSequence = false;
/*     */   private String label;
/*     */   
/*     */   public DisplaySummaryRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, TableModel moduleData, TableModel vehicleData) {
/*  32 */     super(requestGroup, attribute, confirmationValue);
/*  33 */     this.moduleData = moduleData;
/*  34 */     this.vehicleData = vehicleData;
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, Summary summary) {
/*  38 */     super(requestGroup, attribute, confirmationValue);
/*  39 */     this.summary = summary;
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, List sequence) {
/*  43 */     super(requestGroup, attribute, confirmationValue);
/*  44 */     this.sequenceGME = sequence;
/*     */   }
/*     */   
/*     */   public DisplaySummaryRequestImpl(RequestGroup requestGroup, Attribute attribute, Value confirmationValue, ProgrammingSequence sequence) {
/*  48 */     super(requestGroup, attribute, confirmationValue);
/*  49 */     this.sequenceNAO = sequence;
/*     */   }
/*     */   
/*     */   public ProgrammingSequence getProgrammingSequence() {
/*  53 */     return this.sequenceNAO;
/*     */   }
/*     */   
/*     */   public List getSequenceSummary() {
/*  57 */     return this.sequenceGME;
/*     */   }
/*     */   
/*     */   public void setModuleData(TableModel moduleData) {
/*  61 */     this.moduleData = moduleData;
/*     */   }
/*     */   
/*     */   public TableModel getModuleData() {
/*  65 */     return this.moduleData;
/*     */   }
/*     */   
/*     */   public void setSummary(Summary summary) {
/*  69 */     this.summary = summary;
/*     */   }
/*     */   
/*     */   public void setModuleData(List controllers, List moduleDataList) {
/*  73 */     this.controllers = controllers;
/*  74 */     this.moduleDataList = moduleDataList;
/*     */   }
/*     */   
/*     */   public List getControllers() {
/*  78 */     return this.controllers;
/*     */   }
/*     */   
/*     */   public List getModuleDataList() {
/*  82 */     return this.moduleDataList;
/*     */   }
/*     */   
/*     */   public void setVehicleData(TableModel vehicleData) {
/*  86 */     this.vehicleData = vehicleData;
/*     */   }
/*     */   
/*     */   public TableModel getVehicleData() {
/*  90 */     return this.vehicleData;
/*     */   }
/*     */   
/*     */   public Summary getSummary() {
/*  94 */     return this.summary;
/*     */   }
/*     */   
/*     */   public void setDescriptionData(TableModel descriptionData) {
/*  98 */     this.descriptionData = descriptionData;
/*     */   }
/*     */   
/*     */   public TableModel getDescriptionData() {
/* 102 */     return this.descriptionData;
/*     */   }
/*     */   
/*     */   public boolean isGMEMoreSequence() {
/* 106 */     return this.hasGMEMoreSequence;
/*     */   }
/*     */   
/*     */   public void setGMEMoreSequence(boolean hasGMEMoreSequence) {
/* 110 */     this.hasGMEMoreSequence = hasGMEMoreSequence;
/*     */   }
/*     */   
/*     */   public List getDescriptionDataListGME() {
/* 114 */     return this.descriptionDataListGME;
/*     */   }
/*     */   
/*     */   public List getModuleDataListGME() {
/* 118 */     return this.moduleDataListGME;
/*     */   }
/*     */   
/*     */   public void setModuleDataListGME(List controllers, List descriptionDataListGME, List moduleDataListGME) {
/* 122 */     this.descriptionDataListGME = descriptionDataListGME;
/* 123 */     this.moduleDataListGME = moduleDataListGME;
/* 124 */     this.controllers = controllers;
/*     */   }
/*     */   
/*     */   public String getControllerLabel() {
/* 128 */     return this.label;
/*     */   }
/*     */   
/*     */   public void setControllerLabel(String label) {
/* 132 */     this.label = label;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\impl\DisplaySummaryRequestImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */