/*    */ package com.eoos.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.TimerTask;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PeriodicTask
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(PeriodicTask.class);
/*    */   
/*    */   private class CustomTimerTask extends TimerTask {
/*    */     private int count;
/*    */     
/*    */     private CustomTimerTask(int maxCount) {
/* 25 */       this.count = maxCount;
/*    */     }
/*    */     
/*    */     public synchronized void run() {
/*    */       try {
/* 30 */         PeriodicTask.this.runnable.run();
/* 31 */       } catch (Throwable e) {
/* 32 */         PeriodicTask.log.error("execution exeception for " + String.valueOf(PeriodicTask.this.runnable) + " - exception:" + e, e);
/*    */       } finally {
/*    */         
/* 35 */         if (this.count != -1) {
/* 36 */           this.count--;
/* 37 */           if (this.count <= 0) {
/* 38 */             PeriodicTask.log.debug("maximal execution count reached, stopping periodic task");
/* 39 */             PeriodicTask.this.stop();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/* 47 */   private CustomTimerTask executionTask = null;
/*    */   
/* 49 */   private Runnable runnable = null;
/*    */   
/*    */   private Timespan interval;
/*    */   
/*    */   private static final int STARTED = 1;
/*    */   
/*    */   private static final int STOPPED = 2;
/*    */   
/* 57 */   private int status = 2;
/*    */   
/*    */   public static final int EXECUTION_COUNT_UNLIMITED = -1;
/* 60 */   private int executionCount = -1;
/*    */ 
/*    */   
/*    */   public PeriodicTask(Runnable runnable, long interval) {
/* 64 */     this(runnable, new Timespan(interval));
/*    */   }
/*    */   
/*    */   public PeriodicTask(Runnable runnable, long interval, int executionCount) {
/* 68 */     this(runnable, new Timespan(interval), executionCount);
/*    */   }
/*    */   
/*    */   public PeriodicTask(Runnable runnable, Timespan interval) {
/* 72 */     this(runnable, interval, -1);
/*    */   }
/*    */   
/*    */   public PeriodicTask(Runnable runnable, Timespan interval, int executionCount) {
/* 76 */     this.runnable = runnable;
/* 77 */     this.interval = interval;
/* 78 */     this.executionCount = executionCount;
/*    */   }
/*    */   
/*    */   public void start() {
/* 82 */     if (this.status != 1) {
/* 83 */       this.executionTask = new CustomTimerTask(this.executionCount);
/* 84 */       Util.getTimer().scheduleAtFixedRate(this.executionTask, this.interval.getAsMillis(), this.interval.getAsMillis());
/* 85 */       this.status = 1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stop() {
/* 90 */     if (this.status != 2) {
/* 91 */       this.executionTask.cancel();
/* 92 */       this.executionTask = null;
/* 93 */       this.status = 2;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 98 */     return super.toString() + "[interval=" + String.valueOf(this.interval) + ((this.executionCount != -1) ? ("max.exec=" + this.executionCount) : "") + ", status=" + ((this.status == 1) ? "STARTED" : "STOPPED") + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\PeriodicTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */