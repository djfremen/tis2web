/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSSequenceFunction
/*    */ {
/*    */   private Integer controllerID;
/*    */   private Integer functionID;
/*    */   private Integer reqInfoID;
/*    */   private Integer functionOrder;
/*    */   private String onSameSW;
/*    */   
/*    */   public Integer getControllerID() {
/* 12 */     return this.controllerID;
/*    */   }
/*    */   
/*    */   public Integer getFunctionID() {
/* 16 */     return this.functionID;
/*    */   }
/*    */   
/*    */   public Integer getRequestInfoID() {
/* 20 */     return this.reqInfoID;
/*    */   }
/*    */   
/*    */   public Integer getFunctionOrder() {
/* 24 */     return this.functionOrder;
/*    */   }
/*    */   
/*    */   public String getOnSameSW() {
/* 28 */     return this.onSameSW;
/*    */   }
/*    */   
/*    */   public SPSSequenceFunction(Integer controllerID, Integer functionID, Integer reqInfoID, Integer functionOrder, String onSameSW) {
/* 32 */     this.controllerID = controllerID;
/* 33 */     this.functionID = functionID;
/* 34 */     this.reqInfoID = reqInfoID;
/* 35 */     this.functionOrder = functionOrder;
/* 36 */     this.onSameSW = onSameSW;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return "functionID=" + this.functionID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSequenceFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */