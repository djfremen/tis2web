/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSOptionVIN extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient SPSVINCode vcode;
/*    */   
/*    */   SPSOptionVIN(Integer id, SPSVINCode value) {
/*  8 */     super(id);
/*  9 */     this.vcode = value;
/* 10 */     this.descriptions = null;
/*    */   }
/*    */   
/*    */   SPSVINCode getVINCode() {
/* 14 */     return this.vcode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return this.id + ":" + this.vcode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionVIN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */