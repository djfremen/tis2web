/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSOptionRNG extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient SPSVINRange range;
/*    */   
/*    */   SPSOptionRNG(Integer id, SPSVINRange value) {
/*  8 */     super(id);
/*  9 */     this.range = value;
/* 10 */     this.descriptions = null;
/*    */   }
/*    */   
/*    */   SPSVINRange getVINRange() {
/* 14 */     return this.range;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return this.id + ":" + this.range;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionRNG.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */