/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.VIT1Data;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSVehicle
/*    */ {
/*    */   protected SPSVIN vin;
/*    */   protected VIT1Data vit1;
/*    */   protected List options;
/*    */   
/*    */   public SPSVehicle(SPSVIN vin) {
/* 16 */     this.vin = vin;
/*    */   }
/*    */   
/*    */   public SPSVIN getVIN() {
/* 20 */     return this.vin;
/*    */   }
/*    */   
/*    */   public VIT1Data getVIT1() {
/* 24 */     return this.vit1;
/*    */   }
/*    */   
/*    */   public void setVIT1(VIT1Data vit1) {
/* 28 */     this.vit1 = vit1;
/*    */   }
/*    */   
/*    */   public List getOptions() {
/* 32 */     return this.options;
/*    */   }
/*    */   
/*    */   public void setOption(SPSOption option) {
/* 36 */     if (this.options == null) {
/* 37 */       this.options = new ArrayList();
/*    */     }
/* 39 */     if (!this.options.contains(option)) {
/* 40 */       this.options.add(option);
/*    */     }
/*    */   }
/*    */   
/*    */   void reset() {
/* 45 */     this.options = null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSVehicle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */