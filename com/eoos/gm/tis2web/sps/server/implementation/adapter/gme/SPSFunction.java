/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*    */ 
/*    */ public class SPSFunction
/*    */ {
/*    */   private Integer functionID;
/*    */   private SPSDescription description;
/*    */   
/*    */   public Integer getFunctionID() {
/* 11 */     return this.functionID;
/*    */   }
/*    */   
/*    */   public String getDescription(SPSLanguage language) {
/* 15 */     return this.description.get(language);
/*    */   }
/*    */   
/*    */   public SPSFunction(Integer functionID) {
/* 19 */     this.functionID = functionID;
/* 20 */     this.description = new SPSDescription();
/*    */   }
/*    */   
/*    */   public void add(SPSLanguage language, String label) {
/* 24 */     this.description.add(language, label);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 28 */     return "functionID=" + this.functionID + " " + this.description.getDefaultLabel();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */