/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SPSSequence
/*    */ {
/*    */   private Integer sequenceID;
/*    */   private List functions;
/*    */   private SPSSequenceDisplay display;
/*    */   
/*    */   public Integer getSequenceID() {
/* 13 */     return this.sequenceID;
/*    */   }
/*    */   
/*    */   public List getFunctions() {
/* 17 */     return this.functions;
/*    */   }
/*    */   
/*    */   public SPSSequenceDisplay getDisplay() {
/* 21 */     return this.display;
/*    */   }
/*    */   
/*    */   public void setDisplay(SPSSequenceDisplay display) {
/* 25 */     this.display = display;
/*    */   }
/*    */   
/*    */   public SPSSequence(Integer sequenceID) {
/* 29 */     this.sequenceID = sequenceID;
/* 30 */     this.functions = new ArrayList();
/*    */   }
/*    */   
/*    */   public void add(SPSSequenceFunction function) {
/* 34 */     this.functions.add(function);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     return "sequenceID=" + this.sequenceID + " " + this.display.getDescription() + "\r\n" + this.functions;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSequence.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */