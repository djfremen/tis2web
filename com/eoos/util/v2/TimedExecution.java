/*     */ package com.eoos.util.v2;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.Time;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class TimedExecution
/*     */ {
/*  19 */   private static final Logger log = Logger.getLogger(TimedExecution.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List executionTimes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Operation operation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private final Object SYNC = new Object();
/*     */   
/*  39 */   private CustomTimerTask nextTask = null;
/*     */   
/*     */   private class CustomTimerTask extends TimerTask {
/*     */     private Time time;
/*     */     
/*     */     public CustomTimerTask(Time time) {
/*  45 */       this.time = time;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/*  50 */         synchronized (TimedExecution.this.SYNC) {
/*     */           
/*  52 */           TimedExecution.log.debug("running timed execution ...");
/*  53 */           if (TimedExecution.this.operation.execute(this.time)) {
/*  54 */             TimedExecution.log.debug("...finished timed execution, scheduling next execution...");
/*  55 */             TimedExecution.this.schedule(TimedExecution.this.getNextExecutionDate(this.time));
/*     */           } else {
/*  57 */             TimedExecution.log.debug("...finished timed execution");
/*     */           }
/*     */         
/*     */         } 
/*  61 */       } catch (Throwable t) {
/*  62 */         TimedExecution.log.error("unable to execute - exception: ", t);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TimedExecution(Operation operation, List executionTimes) {
/*  69 */     this.operation = operation;
/*  70 */     this.executionTimes = executionTimes;
/*  71 */     CollectionUtil.unify(this.executionTimes);
/*  72 */     Collections.sort(this.executionTimes);
/*     */   }
/*     */   
/*     */   public List getExecutionTimes() {
/*  76 */     return new ArrayList(this.executionTimes);
/*     */   }
/*     */   
/*     */   public void stop() {
/*  80 */     synchronized (this.SYNC) {
/*  81 */       if (this.nextTask != null) {
/*  82 */         this.nextTask.cancel();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void start() {
/*  88 */     schedule(getNextExecutionDate(Time.currentTime()));
/*     */   }
/*     */   
/*     */   private void schedule(Date date) {
/*  92 */     synchronized (this.SYNC) {
/*  93 */       this.nextTask = new CustomTimerTask(Time.forDate(date));
/*  94 */       log.debug("....scheduling next execution at:" + DateFormat.getDateTimeInstance(3, 2).format(date));
/*  95 */       Util.getTimer().schedule(this.nextTask, date);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Date getNextExecutionDate(Time currentTime) {
/* 101 */     Date nextExecutionDate = null;
/* 102 */     if (this.executionTimes.size() > 0) {
/* 103 */       Time nextExecutionTime = null;
/* 104 */       for (Iterator<Time> iter = this.executionTimes.iterator(); iter.hasNext() && nextExecutionTime == null; ) {
/* 105 */         Time time = iter.next();
/* 106 */         if (time.compareTo(currentTime) > 0) {
/* 107 */           nextExecutionTime = time;
/*     */         }
/*     */       } 
/*     */       
/* 111 */       Calendar c = Calendar.getInstance();
/* 112 */       if (nextExecutionTime == null) {
/* 113 */         nextExecutionTime = this.executionTimes.get(0);
/* 114 */         c.set(5, c.get(5) + 1);
/*     */       } 
/*     */       
/* 117 */       c.set(11, nextExecutionTime.getHour());
/* 118 */       c.set(12, nextExecutionTime.getMinute());
/* 119 */       c.set(13, nextExecutionTime.getSecond());
/* 120 */       c.set(14, nextExecutionTime.getMillisecond());
/*     */       
/* 122 */       nextExecutionDate = c.getTime();
/*     */     } 
/* 124 */     return nextExecutionDate;
/*     */   }
/*     */   
/*     */   public static interface Operation {
/*     */     boolean execute(Time param1Time);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\TimedExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */