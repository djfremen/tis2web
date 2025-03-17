/*    */ package com.eoos.gm.tis2web.swdl.common.system;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class LCI implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private long serverTimestamp;
/*    */   private long maxClockDiff;
/*    */   private long licenseDuration;
/*    */   
/*    */   public LCI(long serverTimestamp, long maxClockDiff, long licenseDuration) {
/* 12 */     this.serverTimestamp = serverTimestamp;
/* 13 */     this.maxClockDiff = maxClockDiff;
/* 14 */     this.licenseDuration = licenseDuration;
/*    */   }
/*    */   
/*    */   public long getServerTimestamp() {
/* 18 */     return this.serverTimestamp;
/*    */   }
/*    */   
/*    */   public long getMaxClockDifference() {
/* 22 */     return this.maxClockDiff;
/*    */   }
/*    */   
/*    */   public long getLicenseDuration() {
/* 26 */     return this.licenseDuration;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\system\LCI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */