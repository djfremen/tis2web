/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.export.lt;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SPSEvent
/*    */   implements Serializable {
/*    */   private static final long serialVersionUID = -3162037614272635237L;
/*    */   public static final int NA = 0;
/*    */   private int actualDownloadTime;
/*    */   private int actualProgrammingTime;
/*    */   private int actualType4Time;
/*    */   private int totalTime;
/*    */   
/*    */   public SPSEvent(int actualDownloadTime, int actualProgrammingTime, int actualType4Time) {
/* 15 */     this.actualDownloadTime = actualDownloadTime;
/* 16 */     this.actualProgrammingTime = actualProgrammingTime;
/* 17 */     this.actualType4Time = actualType4Time;
/* 18 */     if (actualProgrammingTime != 0 && actualType4Time != 0) {
/* 19 */       throw new IllegalArgumentException();
/*    */     }
/*    */   }
/*    */   
/*    */   public static SPSEvent createSPSProgrammingEvent(int actualDownloadTime, int actualProgrammingTime) {
/* 24 */     return new SPSEvent(actualDownloadTime, actualProgrammingTime, 0);
/*    */   }
/*    */   
/*    */   public static SPSEvent createSPSType4Event(int actualDownloadTime, int actualType4Time) {
/* 28 */     return new SPSEvent(actualDownloadTime, 0, actualType4Time);
/*    */   }
/*    */   
/*    */   public int getTotalTime() {
/* 32 */     return this.totalTime;
/*    */   }
/*    */   
/*    */   public void setTotalTime(int totalTime) {
/* 36 */     this.totalTime = totalTime;
/*    */   }
/*    */   
/*    */   public int getActualDownloadTime() {
/* 40 */     return this.actualDownloadTime;
/*    */   }
/*    */   
/*    */   public int getActualProgrammingTime() {
/* 44 */     return this.actualProgrammingTime;
/*    */   }
/*    */   
/*    */   public int getActualType4Time() {
/* 48 */     return this.actualType4Time;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\lt\SPSEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */