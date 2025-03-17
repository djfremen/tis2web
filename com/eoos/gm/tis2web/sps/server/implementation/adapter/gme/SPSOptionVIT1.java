/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSOptionVIT1 extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient String value;
/*    */   
/*    */   SPSOptionVIT1(Integer id, String value) {
/*  8 */     super(id);
/*  9 */     this.value = value;
/* 10 */     this.descriptions = null;
/*    */   }
/*    */   
/*    */   String getVIT1Value() {
/* 14 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionVIT1.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */