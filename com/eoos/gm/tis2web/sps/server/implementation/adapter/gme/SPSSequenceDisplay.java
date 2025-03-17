/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSDescription;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class SPSSequenceDisplay
/*    */ {
/*    */   private Integer sequenceID;
/*    */   private SPSDescription description;
/*    */   private Map displayOrder;
/*    */   
/*    */   public Integer getSequenceID() {
/* 15 */     return this.sequenceID;
/*    */   }
/*    */   
/*    */   public Integer getDisplayOrder(SPSLanguage language) {
/* 19 */     return (Integer)this.displayOrder.get(language);
/*    */   }
/*    */   
/*    */   public String getDescription(SPSLanguage language) {
/* 23 */     return this.description.get(language);
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 27 */     return this.description.getDefaultLabel();
/*    */   }
/*    */   
/*    */   public SPSSequenceDisplay(Integer sequenceID) {
/* 31 */     this.sequenceID = sequenceID;
/* 32 */     this.description = new SPSDescription();
/* 33 */     this.displayOrder = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void add(SPSLanguage language, String label, Integer order) {
/* 37 */     this.description.add(language, label);
/* 38 */     this.displayOrder.put(language, order);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return "sequenceID=" + this.sequenceID + " " + this.description.getDefaultLabel();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSSequenceDisplay.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */