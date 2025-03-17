/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class SPSFunctions
/*    */   extends ArrayList {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Integer controllerID;
/*    */   
/*    */   public Integer getControllerID() {
/* 11 */     return this.controllerID;
/*    */   }
/*    */ 
/*    */   
/*    */   public SPSFunctions(Integer controllerID) {
/* 16 */     this.controllerID = controllerID;
/*    */   }
/*    */   
/*    */   public void add(Integer functionID, SPSLanguage language, String label) {
/* 20 */     for (int i = 0; i < size(); i++) {
/* 21 */       SPSFunction sPSFunction = (SPSFunction)get(i);
/* 22 */       if (sPSFunction.getFunctionID().equals(functionID)) {
/* 23 */         sPSFunction.add(language, label);
/*    */         return;
/*    */       } 
/*    */     } 
/* 27 */     SPSFunction function = new SPSFunction(functionID);
/* 28 */     function.add(language, label);
/* 29 */     add((E)function);
/*    */   }
/*    */   
/*    */   public String getDescription(SPSLanguage language, Integer functionID) {
/* 33 */     for (int i = 0; i < size(); i++) {
/* 34 */       SPSFunction function = (SPSFunction)get(i);
/* 35 */       if (function.getFunctionID().equals(functionID)) {
/* 36 */         return function.getDescription(language);
/*    */       }
/*    */     } 
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     StringBuilder tmp = new StringBuilder();
/* 44 */     tmp.append("ControllerID = " + this.controllerID);
/* 45 */     for (int i = 0; i < size(); i++) {
/* 46 */       SPSFunction function = (SPSFunction)get(i);
/* 47 */       tmp.append("\r\n" + function.toString());
/*    */     } 
/* 49 */     return tmp.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSFunctions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */