/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSVINCode
/*    */ {
/*    */   protected String wmi;
/*    */   protected String vds;
/*    */   protected String vis;
/*    */   
/*    */   public SPSVINCode(String wmi, String vds) {
/* 12 */     this.wmi = wmi;
/* 13 */     this.vds = vds;
/*    */   }
/*    */   
/*    */   public SPSVINCode(String wmi, String vds, String vis) {
/* 17 */     this.wmi = wmi;
/* 18 */     this.vds = vds;
/* 19 */     this.vis = vis;
/*    */   }
/*    */   
/*    */   public String getVis() {
/* 23 */     return this.vis;
/*    */   }
/*    */   
/*    */   public String getVds() {
/* 27 */     return this.vds;
/*    */   }
/*    */   
/*    */   public String getWmi() {
/* 31 */     return this.wmi;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     return this.wmi + "/" + this.vds + "/" + this.vis;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSVINCode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */