/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSVINRange
/*    */ {
/*    */   protected String fromSN;
/*    */   protected String toSN;
/*    */   
/*    */   public SPSVINRange(String fromSN, String toSN) {
/*  9 */     this.fromSN = fromSN;
/* 10 */     this.toSN = toSN;
/*    */   }
/*    */   
/*    */   public String getFromSN() {
/* 14 */     return this.fromSN;
/*    */   }
/*    */   
/*    */   public String getToSN() {
/* 18 */     return this.toSN;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 22 */     return this.fromSN + "-" + this.toSN;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSVINRange.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */