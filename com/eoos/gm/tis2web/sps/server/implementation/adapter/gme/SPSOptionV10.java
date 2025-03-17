/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ public class SPSOptionV10 extends SPSOption {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected transient String vcode;
/*    */   
/*    */   SPSOptionV10(Integer id, String value) {
/*  8 */     super(id);
/*  9 */     this.vcode = value;
/* 10 */     this.descriptions = null;
/*    */   }
/*    */   
/*    */   String getV10Code() {
/* 14 */     return this.vcode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return this.id + ":" + this.vcode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSOptionV10.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */