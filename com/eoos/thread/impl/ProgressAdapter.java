/*    */ package com.eoos.thread.impl;
/*    */ 
/*    */ import com.eoos.math.AverageCalculator;
/*    */ import com.eoos.thread.CustomThread;
/*    */ import java.math.BigDecimal;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressAdapter
/*    */ {
/* 13 */   private CustomThread thread = null;
/*    */   
/* 15 */   private ProgressInfoImpl progressInfo = null;
/*    */   
/* 17 */   private static AverageCalculator averageCalc = new AverageCalculator(0);
/*    */   
/* 19 */   private int tickCount = 0;
/*    */   
/*    */   public ProgressAdapter() {
/* 22 */     this(Thread.currentThread());
/*    */   }
/*    */   
/*    */   public ProgressAdapter(Thread t) {
/* 26 */     if (t instanceof CustomThread) {
/* 27 */       this.thread = (CustomThread)t;
/*    */     }
/*    */   }
/*    */   
/*    */   public void tick() {
/* 32 */     tick(null);
/*    */   }
/*    */   
/*    */   public void tick(String message) {
/* 36 */     tick(message, -1);
/*    */   }
/*    */   
/*    */   public void tick(String message, int total) {
/* 40 */     tick(message, total, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(String message, int total, boolean createNew) {
/* 45 */     this.tickCount++;
/* 46 */     if (this.thread != null) {
/* 47 */       if (this.progressInfo == null || createNew) {
/* 48 */         this.progressInfo = new ProgressInfoImpl();
/*    */       }
/* 50 */       if (message != null) {
/* 51 */         this.progressInfo.setMessage(message);
/*    */       }
/* 53 */       if (total != -1) {
/* 54 */         this.progressInfo.setTotalTicks(total);
/*    */       }
/*    */       
/* 57 */       this.progressInfo.incProcessedTicks();
/* 58 */       this.thread.setProgress(this.progressInfo);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void finish() {
/* 64 */     if (this.progressInfo.getTotalTicks() != null) {
/* 65 */       this.progressInfo.setProcessedTicks(this.progressInfo.getTotalTicks().intValue());
/*    */     }
/* 67 */     synchronized (averageCalc) {
/* 68 */       averageCalc.add(BigDecimal.valueOf(this.tickCount));
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getAverageTickCount() {
/* 73 */     synchronized (averageCalc) {
/* 74 */       return averageCalc.getCurrentAverage().intValue();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\thread\impl\ProgressAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */