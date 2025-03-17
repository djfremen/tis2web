/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSControllerFunction;
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSSession;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class SPSControllerFunctionGME
/*    */   extends SPSControllerGME
/*    */   implements SPSControllerFunction {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Integer functionID;
/*    */   private String controller;
/*    */   private Integer reqInfoID;
/*    */   private String onSameSW;
/*    */   
/*    */   public Integer getFunctionID() {
/* 17 */     return this.functionID;
/*    */   }
/*    */   
/*    */   public Integer getRequestInfoID() {
/* 21 */     return this.reqInfoID;
/*    */   }
/*    */   
/*    */   public void setRequestInfoID(Integer reqInfoID) {
/* 25 */     this.reqInfoID = reqInfoID;
/*    */   }
/*    */   
/*    */   public String getOnSameSW() {
/* 29 */     return this.onSameSW;
/*    */   }
/*    */   
/*    */   public void setOnSameSW(String onSameSW) {
/* 33 */     this.onSameSW = onSameSW;
/*    */   }
/*    */   
/*    */   SPSControllerFunctionGME(SPSSession session, Integer vehicle, Integer functionID, Integer system, Integer ecu, SPSSchemaAdapterGME adapter) {
/* 37 */     super(session, vehicle, resolveControllerID(adapter, functionID), system, ecu, adapter);
/* 38 */     this.functionID = functionID;
/* 39 */     this.controller = this.description;
/* 40 */     this.description = getFunctionDescription((SPSLanguage)session.getLanguage(), this.id, functionID, adapter);
/* 41 */     fixFunctionDescription();
/*    */   }
/*    */   
/*    */   SPSControllerFunctionGME(SPSSession session, Integer vehicle, Integer controllerID, Integer functionID, Integer system, Integer ecu, SPSSchemaAdapterGME adapter) {
/* 45 */     super(session, vehicle, controllerID, system, ecu, adapter);
/* 46 */     this.functionID = functionID;
/* 47 */     this.controller = this.description;
/* 48 */     this.description = getFunctionDescription((SPSLanguage)session.getLanguage(), this.id, functionID, adapter);
/* 49 */     fixFunctionDescription();
/*    */   }
/*    */   
/*    */   protected static Integer resolveControllerID(SPSSchemaAdapterGME adapter, Integer functionID) {
/* 53 */     return SPSControllerGME.Descriptions.getInstance(adapter).getControllerID(functionID);
/*    */   }
/*    */   
/*    */   public String getControllerDescription() {
/* 57 */     return this.controller;
/*    */   }
/*    */   
/*    */   public String getDenotation(Locale locale) {
/* 61 */     return this.description;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSControllerFunctionGME.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */