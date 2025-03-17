/*    */ package com.eoos.gm.tis2web.swdl.common.domain.device;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaudRate
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 16 */   private String baudRate = null;
/*    */ 
/*    */   
/*    */   public BaudRate(String baudRate) {
/* 20 */     this.baudRate = baudRate;
/*    */   }
/*    */   
/*    */   public String getBaudRate() {
/* 24 */     return this.baudRate;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 28 */     return this.baudRate.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 33 */     if (this == obj)
/* 34 */       return true; 
/* 35 */     if (obj instanceof BaudRate) {
/* 36 */       BaudRate other = (BaudRate)obj;
/* 37 */       boolean ret = Util.equals(this.baudRate, other.baudRate);
/* 38 */       return ret;
/*    */     } 
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return this.baudRate.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\device\BaudRate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */