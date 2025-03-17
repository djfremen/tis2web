/*    */ package com.eoos.gm.tis2web.rpo.ri;
/*    */ 
/*    */ import com.eoos.gm.tis2web.rpo.api.RPOContainer;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RPOContainerRI
/*    */   implements RPOContainer
/*    */ {
/*    */   private String modelDesignator;
/*    */   private String vehicleNumber;
/*    */   private List rpos;
/*    */   
/*    */   public RPOContainerRI(String modelDesignator, String vehicleNumber, Collection<?> rpos) {
/* 21 */     this.vehicleNumber = vehicleNumber;
/* 22 */     this.modelDesignator = modelDesignator;
/* 23 */     this.rpos = new ArrayList(rpos);
/* 24 */     Collections.sort(this.rpos);
/*    */   }
/*    */   
/*    */   public String getModelDesignator() {
/* 28 */     return this.modelDesignator;
/*    */   }
/*    */   
/*    */   public String getVehicleNumber() {
/* 32 */     return this.vehicleNumber;
/*    */   }
/*    */   
/*    */   public Collection getRPOs() {
/* 36 */     return this.rpos;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 40 */     int ret = RPOContainerRI.class.hashCode();
/* 41 */     ret = HashCalc.addHashCode(ret, this.modelDesignator);
/* 42 */     ret = HashCalc.addHashCode(ret, this.vehicleNumber);
/* 43 */     ret = HashCalc.addHashCode(ret, this.rpos);
/* 44 */     return ret;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 48 */     if (this == obj)
/* 49 */       return true; 
/* 50 */     if (obj instanceof RPOContainerRI) {
/* 51 */       RPOContainerRI other = (RPOContainerRI)obj;
/* 52 */       boolean retmodel = Util.equals(this.modelDesignator, other.modelDesignator);
/* 53 */       boolean retvehic = Util.equals(this.vehicleNumber, other.vehicleNumber);
/* 54 */       boolean ret = (retmodel && Util.collectionEquals(this.rpos, other.rpos));
/* 55 */       ret = (ret && retvehic);
/* 56 */       return ret;
/*    */     } 
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\ri\RPOContainerRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */