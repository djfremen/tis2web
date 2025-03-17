/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Queue;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ConNvent
/*     */   implements ConnectionProvider
/*     */ {
/*     */   private final ConnectionProvider backend;
/*  17 */   private static final Logger log = Logger.getLogger(ConNvent.class);
/*     */   private final long maxIdleDuration;
/*     */   
/*     */   private static final class Bucket { private Connection connection;
/*     */     private long ts;
/*     */     
/*     */     private Bucket(Connection connection) {
/*  24 */       this.connection = connection;
/*  25 */       this.ts = System.currentTimeMillis();
/*     */     } }
/*     */ 
/*     */   
/*     */   private final class TTConnectionRelease implements Runnable {
/*     */     public void run() {
/*  31 */       int prio = Util.incPriority();
/*     */       try {
/*  33 */         _run();
/*     */       } finally {
/*  35 */         Thread.currentThread().setPriority(prio);
/*     */       } 
/*     */     }
/*     */     private TTConnectionRelease() {}
/*     */     public void _run() {
/*  40 */       ConNvent.log.trace("executing bucket invalidity check...");
/*  41 */       synchronized (ConNvent.this) {
/*     */         
/*  43 */         ConNvent.Bucket bucket = ConNvent.this.buckets.peek();
/*  44 */         if (bucket != null) {
/*  45 */           long diff = System.currentTimeMillis() - bucket.ts;
/*  46 */           if (diff < ConNvent.this.maxIdleDuration) {
/*  47 */             ConNvent.log.trace("...front bucket is still valid, re-scheduling invalidity check");
/*  48 */             ConNvent.this.ttConnectionRelease = Util.createTimerTask(new TTConnectionRelease());
/*  49 */             Util.getTimer().schedule(ConNvent.this.ttConnectionRelease, ConNvent.this.maxIdleDuration - diff);
/*     */           } else {
/*  51 */             ConNvent.log.trace("...front bucket is invalid, removing it and repeating check");
/*  52 */             ConNvent.this.buckets.remove();
/*  53 */             ConNvent.this.backend.releaseConnection(bucket.connection);
/*  54 */             _run();
/*     */           } 
/*     */         } else {
/*  57 */           ConNvent.log.trace("...no buckets to check");
/*  58 */           ConNvent.this.ttConnectionRelease = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final Queue buckets = new ConcurrentLinkedQueue();
/*     */   
/*  70 */   private TimerTask ttConnectionRelease = null;
/*     */   
/*  72 */   private AverageCalculator hitRatio = new AverageCalculator(2);
/*     */   
/*  74 */   private int peek = 0;
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */ 
/*     */   
/*     */   public ConNvent(ConnectionProvider backend, long maxIdleDuration) {
/*  80 */     this.backend = backend;
/*  81 */     this.maxIdleDuration = maxIdleDuration;
/*  82 */     if (isEnabled()) {
/*  83 */       final CharSequence trace = Util.compactStackTrace(new Throwable(), 1, 5);
/*  84 */       this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */           {
/*     */             public void onShutdown() {
/*  87 */               ConNvent.this.clear();
/*  88 */               synchronized (ConNvent.log) {
/*  89 */                 ConNvent.log.info("hit ratio for connection pool (creation trace: " + trace + "): " + ConNvent.this.hitRatio.getCurrentAverage() + " hit/request, total request count: " + ConNvent.this.hitRatio.getValueCount() + ", maximal pool size:" + ConNvent.this.peek);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  99 */     Connection ret = null;
/* 100 */     if (isEnabled()) {
/* 101 */       Bucket bucket = this.buckets.poll();
/* 102 */       if (bucket != null) {
/* 103 */         this.hitRatio.add(BigDecimal.ONE);
/* 104 */         ret = bucket.connection;
/*     */       } else {
/* 106 */         this.hitRatio.add(BigDecimal.ZERO);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return (ret != null) ? ret : this.backend.getConnection();
/*     */   }
/*     */   
/*     */   private void scheduleConnectionRelease() {
/* 114 */     if (this.ttConnectionRelease == null) {
/* 115 */       this.ttConnectionRelease = Util.createTimerTask(new TTConnectionRelease());
/* 116 */       Util.getTimer().schedule(this.ttConnectionRelease, this.maxIdleDuration);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isEnabled() {
/* 121 */     return true;
/*     */   }
/*     */   
/*     */   public void releaseConnection(Connection connection) {
/* 125 */     if (isEnabled()) {
/*     */       try {
/* 127 */         if (!connection.isClosed()) {
/*     */ 
/*     */ 
/*     */           
/* 131 */           connection.commit();
/*     */           
/* 133 */           this.buckets.offer(new Bucket(connection));
/* 134 */           this.peek = Math.max(this.peek, this.buckets.size());
/* 135 */           scheduleConnectionRelease();
/*     */         } else {
/*     */           
/* 138 */           log.warn("connection is closed, releasing");
/* 139 */           this.backend.releaseConnection(connection);
/*     */         } 
/* 141 */       } catch (SQLException e) {
/* 142 */         log.warn("connection seems to be brocken, releasing");
/* 143 */         this.backend.releaseConnection(connection);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 148 */       this.backend.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void clear() {
/* 154 */     log.debug("clearing the pool ...");
/* 155 */     Bucket bucket = null;
/* 156 */     while ((bucket = this.buckets.poll()) != null)
/* 157 */       this.backend.releaseConnection(bucket.connection); 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\ConNvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */