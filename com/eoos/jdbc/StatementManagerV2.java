/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatementManagerV2
/*     */   implements IStatementManager
/*     */ {
/*     */   private final Logger log;
/*  20 */   private static Timer TIMER = new Timer("Timer for StmtMngrV2", true);
/*     */ 
/*     */ 
/*     */   
/*  24 */   private static Util.ShutdownListener sdlistener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */       {
/*     */         public void onShutdown() {
/*  27 */           StatementManagerV2.TIMER.cancel();
/*  28 */           StatementManagerV2.TIMER = null;
/*     */         }
/*     */       });
/*     */   
/*     */   private static final int CLOSING_DELAY = 500;
/*     */   
/*     */   private static final class Bucket
/*     */   {
/*     */     private StatementManagerV2.MyPreparedStatementWrapper stmt;
/*     */     private long ts;
/*     */     
/*     */     public Bucket(StatementManagerV2.MyPreparedStatementWrapper stmt) {
/*  40 */       this.stmt = stmt;
/*  41 */       this.ts = System.currentTimeMillis();
/*     */     }
/*     */     
/*     */     public boolean matchesQuery(String query) {
/*  45 */       return this.stmt.query.equals(query);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MyPreparedStatementWrapper extends PreparedStatementWrapper {
/*     */     private String query;
/*     */     
/*     */     public MyPreparedStatementWrapper(PreparedStatement stmt, String query) {
/*  53 */       super(stmt);
/*  54 */       this.query = query;
/*     */     } }
/*     */   
/*     */   private final class Release implements Runnable {
/*     */     private Release() {}
/*     */     
/*     */     public void run() {
/*  61 */       int priority = Util.incPriority();
/*     */       try {
/*  63 */         _run();
/*     */       } finally {
/*  65 */         Thread.currentThread().setPriority(priority);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void _run() {
/*  70 */       StatementManagerV2.this.log.trace("executing bucket invalidity check...");
/*  71 */       StatementManagerV2.Bucket bucket = StatementManagerV2.this.buckets.peek();
/*  72 */       if (bucket != null) {
/*  73 */         long diff = System.currentTimeMillis() - bucket.ts;
/*  74 */         if (diff < 500L) {
/*  75 */           StatementManagerV2.this.log.trace("...front bucket is still valid, re-scheduling invalidity check");
/*  76 */           StatementManagerV2.this.ttRelease = Util.createTimerTask(new Release());
/*  77 */           StatementManagerV2.TIMER.schedule(StatementManagerV2.this.ttRelease, 500L - diff + 100L);
/*     */         } else {
/*  79 */           StatementManagerV2.this.log.trace("...front bucket is invalid, removing it (closing statement) and repeating check");
/*  80 */           synchronized (StatementManagerV2.this.buckets) {
/*  81 */             if (StatementManagerV2.this.buckets.contains(bucket)) {
/*  82 */               StatementManagerV2.this.buckets.remove(bucket);
/*  83 */               Connection connection = null;
/*     */               try {
/*  85 */                 connection = bucket.stmt.getConnection();
/*  86 */               } catch (SQLException e) {
/*  87 */                 StatementManagerV2.this.log.warn("failed to retrieve connection (in order to close it), ignoring - exception:", e);
/*     */               } 
/*  89 */               JDBCUtil.close(bucket.stmt);
/*  90 */               if (connection != null) {
/*  91 */                 StatementManagerV2.this.connectionProvider.releaseConnection(connection);
/*     */               }
/*     */             } 
/*     */           } 
/*  95 */           _run();
/*     */         } 
/*     */       } else {
/*  98 */         StatementManagerV2.this.ttRelease = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 104 */   private final Queue buckets = new ConcurrentLinkedQueue();
/*     */   
/*     */   private ConnectionProvider connectionProvider;
/*     */   
/* 108 */   private TimerTask ttRelease = null;
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */ 
/*     */   
/*     */   public StatementManagerV2(ConnectionProvider connectionProvider) {
/* 114 */     this.connectionProvider = connectionProvider;
/* 115 */     Throwable t = new Throwable();
/* 116 */     String caller = t.getStackTrace()[1].getClassName();
/* 117 */     if (caller.equals(StatementManagerSupport.class.getName())) {
/* 118 */       caller = (new Throwable()).getStackTrace()[2].getClassName();
/*     */     }
/* 120 */     caller = caller.substring(caller.lastIndexOf('.') + 1);
/* 121 */     this.log = Logger.getLogger(StatementManagerV2.class.getName() + "/" + caller);
/*     */     
/* 123 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/*     */             try {
/*     */               try {
/* 128 */                 if (StatementManagerV2.this.ttRelease != null) {
/*     */                   
/* 130 */                   StatementManagerV2.this.ttRelease.cancel();
/* 131 */                   StatementManagerV2.this.ttRelease = null;
/*     */                 } 
/* 133 */               } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */               
/* 137 */               for (Iterator<StatementManagerV2.Bucket> iter = StatementManagerV2.this.buckets.iterator(); iter.hasNext(); ) {
/* 138 */                 StatementManagerV2.Bucket bucket = iter.next();
/*     */                 try {
/* 140 */                   Connection connection = bucket.stmt.getConnection();
/* 141 */                   JDBCUtil.close(bucket.stmt, StatementManagerV2.this.log);
/* 142 */                   StatementManagerV2.this.connectionProvider.releaseConnection(connection);
/* 143 */                 } catch (Exception e) {
/* 144 */                   StatementManagerV2.this.log.warn("unable to clean bucket, skipping - exception: ", e);
/*     */                 } 
/*     */               } 
/* 147 */               StatementManagerV2.this.buckets.clear();
/*     */             }
/* 149 */             catch (Exception e) {
/* 150 */               StatementManagerV2.this.log.error("unable to shutdown properly - exception: ", e);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement getStatement(String query) throws Exception {
/* 159 */     PreparedStatement ret = null;
/*     */     
/* 161 */     Bucket bucket = null;
/* 162 */     for (Iterator<Bucket> iter = this.buckets.iterator(); iter.hasNext() && bucket == null; ) {
/* 163 */       Bucket currentBucket = iter.next();
/* 164 */       if (currentBucket.matchesQuery(query)) {
/* 165 */         synchronized (this.buckets) {
/* 166 */           if (this.buckets.contains(currentBucket)) {
/* 167 */             bucket = currentBucket;
/* 168 */             iter.remove();
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 174 */     if (bucket == null) {
/* 175 */       this.log.trace("retrieving connection");
/* 176 */       Connection connection = this.connectionProvider.getConnection();
/* 177 */       ret = new MyPreparedStatementWrapper(connection.prepareStatement(query), query);
/*     */     } else {
/* 179 */       this.log.debug("reusing open statement: " + bucket.stmt.query);
/* 180 */       ret = bucket.stmt;
/*     */     } 
/* 182 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private void scheduleRelease() {
/* 187 */     if (this.ttRelease == null) {
/* 188 */       synchronized (this) {
/* 189 */         if (this.ttRelease == null) {
/* 190 */           this.ttRelease = Util.createTimerTask(new Release());
/* 191 */           TIMER.schedule(this.ttRelease, 600L);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseStatement(PreparedStatement stmt) {
/*     */     try {
/* 199 */       stmt.clearParameters();
/* 200 */     } catch (SQLException e) {
/* 201 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 204 */     Bucket bucket = new Bucket((MyPreparedStatementWrapper)stmt);
/* 205 */     this.buckets.add(bucket);
/* 206 */     scheduleRelease();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\StatementManagerV2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */