/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class JDBCUtil
/*     */ {
/*  20 */   private static final Logger log = Logger.getLogger(JDBCUtil.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResultSet asyncExecuteQuery(final PreparedStatement stmt) throws InterruptedException, SQLException {
/*  28 */     Future<ResultSet> future = Util.executeAsynchronous(new Callable<ResultSet>()
/*     */         {
/*     */           public ResultSet call() throws Exception {
/*  31 */             return stmt.executeQuery();
/*     */           }
/*     */         });
/*     */     try {
/*  35 */       return future.get();
/*  36 */     } catch (InterruptedException e) {
/*  37 */       stmt.cancel();
/*  38 */       throw e;
/*  39 */     } catch (ExecutionException e) {
/*  40 */       rethrowSQLException(e.getCause());
/*  41 */       throw new IllegalStateException();
/*     */     } 
/*     */   } public static interface UpdateCallback {
/*     */     String getQuery(); boolean setParameters(PreparedStatement param1PreparedStatement) throws SQLException; void checkResult(int param1Int); }
/*     */   public static void close(Connection connection) {
/*  46 */     close(connection, (Logger)null);
/*     */   } public static interface QueryCallback {
/*     */     String getQuery(); void setParameters(PreparedStatement param1PreparedStatement) throws SQLException; Object evaluateResult(ResultSet param1ResultSet) throws SQLException; }
/*     */   public static void close(Connection connection, Logger log) {
/*     */     try {
/*  51 */       connection.close();
/*  52 */     } catch (Exception e) {
/*  53 */       Logger _log = (log != null) ? log : JDBCUtil.log;
/*  54 */       _log.error("unable to close " + String.valueOf(connection) + " - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void close(Statement stmt) {
/*  59 */     close(stmt, (Logger)null);
/*     */   }
/*     */   
/*     */   public static void close(Statement stmt, Logger log) {
/*     */     try {
/*  64 */       stmt.close();
/*  65 */     } catch (Exception e) {
/*  66 */       Logger _log = (log != null) ? log : JDBCUtil.log;
/*  67 */       _log.error("unable to close " + String.valueOf(stmt) + " - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void close(ResultSet rs) {
/*  72 */     close(rs, (Logger)null);
/*     */   }
/*     */   
/*     */   public static void close(ResultSet rs, Logger log) {
/*     */     try {
/*  77 */       rs.close();
/*  78 */     } catch (Exception e) {
/*  79 */       Logger _log = (log != null) ? log : JDBCUtil.log;
/*  80 */       _log.error("unable to close " + String.valueOf(rs) + " - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object executeQuery(Connection connection, QueryCallback callback) throws SQLException {
/*  93 */     PreparedStatement stmt = connection.prepareStatement(callback.getQuery());
/*     */     try {
/*  95 */       callback.setParameters(stmt);
/*  96 */       ResultSet rs = stmt.executeQuery();
/*     */       try {
/*  98 */         return callback.evaluateResult(rs);
/*     */       } finally {
/* 100 */         close(rs);
/*     */       } 
/*     */     } finally {
/*     */       
/* 104 */       close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Object executeQuery(ConnectionProvider connectionProvider, QueryCallback callback) throws SQLException {
/* 109 */     Connection connection = connectionProvider.getConnection();
/*     */     try {
/* 111 */       connection.setReadOnly(true);
/* 112 */       connection.setAutoCommit(false);
/* 113 */       return executeQuery(connection, callback);
/*     */     } finally {
/* 115 */       connectionProvider.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void executeBatchUpdate(Connection connection, UpdateCallback callback) throws SQLException {
/* 138 */     PreparedStatement stmt = connection.prepareStatement(callback.getQuery());
/*     */ 
/*     */     
/*     */     try { while (true) {
/* 142 */         boolean next = callback.setParameters(stmt);
/* 143 */         stmt.addBatch();
/* 144 */         if (!next) {
/* 145 */           int[] results = stmt.executeBatch();
/* 146 */           for (int i = 0; i < results.length; i++)
/* 147 */             callback.checkResult(results[i]);  return;
/*     */         } 
/*     */       }  }
/* 150 */     finally { close(stmt); }
/*     */   
/*     */   }
/*     */   
/*     */   public static void executeUpdate(ConnectionProvider connectionProvider, UpdateCallback callback, boolean asBatchUpdate) throws SQLException {
/* 155 */     Connection connection = connectionProvider.getConnection();
/*     */     try {
/* 157 */       connection.setReadOnly(false);
/* 158 */       connection.setAutoCommit(false);
/* 159 */       if (asBatchUpdate) {
/* 160 */         executeBatchUpdate(connection, callback);
/*     */       } else {
/* 162 */         executeUpdate(connection, callback);
/*     */       } 
/* 164 */       connection.commit();
/* 165 */     } catch (Exception e) {
/* 166 */       connection.rollback();
/* 167 */       rethrowSQLException(e);
/*     */     } finally {
/* 169 */       connectionProvider.releaseConnection(connection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void rethrowSQLException(Exception e) throws SQLException {
/* 174 */     if (e instanceof SQLException) {
/* 175 */       throw (SQLException)e;
/*     */     }
/* 177 */     Util.throwRuntimeException(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void executeUpdate(Connection connection, UpdateCallback callback) throws SQLException {
/* 182 */     PreparedStatement stmt = connection.prepareStatement(callback.getQuery());
/*     */     try {
/*     */       boolean next;
/*     */       do {
/* 186 */         next = callback.setParameters(stmt);
/* 187 */         callback.checkResult(stmt.executeUpdate());
/* 188 */       } while (next);
/*     */     } finally {
/*     */       
/* 191 */       close(stmt);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean checkBatchResult(int[] result) {
/* 196 */     boolean ret = true;
/* 197 */     for (int i = 0; i < result.length && ret; i++) {
/* 198 */       ret = (result[i] != -3);
/*     */     }
/* 200 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getUpdateCount(int[] batchResult) {
/* 216 */     int ret = 0;
/* 217 */     for (int i = 0; i < batchResult.length; i++) {
/* 218 */       if (batchResult[i] == -3 || batchResult[i] == -2) {
/* 219 */         return batchResult[i];
/*     */       }
/* 221 */       ret += batchResult[i];
/*     */     } 
/*     */     
/* 224 */     return ret;
/*     */   }
/*     */   
/*     */   public static void rollback(Connection connection, Logger log) {
/*     */     try {
/* 229 */       connection.rollback();
/* 230 */     } catch (Exception e) {
/* 231 */       Logger _log = (log != null) ? log : JDBCUtil.log;
/* 232 */       _log.error("unable to rollback " + String.valueOf(connection) + " - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean existsTable(Connection connection, String catalog, String schema, String tableName) throws SQLException {
/* 237 */     ResultSet rs = connection.getMetaData().getTables(catalog, schema, tableName, new String[] { "TABLE" });
/*     */     try {
/* 239 */       return rs.next();
/*     */     } finally {
/* 241 */       close(rs);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void rethrowSQLException(Throwable t) throws SQLException {
/* 246 */     if (t instanceof SQLException)
/* 247 */       throw (SQLException)t; 
/* 248 */     if (t instanceof Error) {
/* 249 */       throw (Error)t;
/*     */     }
/* 251 */     throw Util.toRuntimeException((Exception)t);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConnectionProvider createLimitedConnectionProvider(final ConnectionProvider backend, final Semaphore semaphore) {
/* 256 */     return new ConnectionProvider()
/*     */       {
/*     */         public void releaseConnection(Connection connection) {
/* 259 */           backend.releaseConnection(connection);
/* 260 */           semaphore.release();
/*     */         }
/*     */         
/*     */         public Connection getConnection() {
/*     */           try {
/* 265 */             semaphore.acquire();
/* 266 */             return backend.getConnection();
/* 267 */           } catch (InterruptedException e) {
/* 268 */             throw new RuntimeException(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static ConnectionProvider createWeakLimitedConnectionProvider(final ConnectionProvider backend, final Semaphore semaphore, final long maxWait) {
/* 276 */     return new ConnectionProvider()
/*     */       {
/* 278 */         private AtomicInteger tailgators = new AtomicInteger(0);
/*     */         
/*     */         public void releaseConnection(Connection connection) {
/* 281 */           backend.releaseConnection(connection);
/* 282 */           if (this.tailgators.get() == 0) {
/* 283 */             semaphore.release();
/*     */           } else {
/* 285 */             this.tailgators.decrementAndGet();
/*     */           } 
/*     */         }
/*     */         
/*     */         public Connection getConnection() {
/*     */           try {
/* 291 */             if (!semaphore.tryAcquire(maxWait, TimeUnit.MILLISECONDS)) {
/* 292 */               JDBCUtil.log.warn("unable to aquire permit within " + maxWait + " ms, continuing (requesting connection)");
/* 293 */               JDBCUtil.log.warn("...request trace: " + Util.compactStackTrace(new Throwable(), 0, 10));
/* 294 */               JDBCUtil.log.warn("...current number of additional connection requests: " + this.tailgators.incrementAndGet());
/*     */             } 
/*     */             try {
/* 297 */               return backend.getConnection();
/* 298 */             } catch (Throwable t) {
/* 299 */               semaphore.release();
/* 300 */               throw Util.toRuntimeException(t);
/*     */             } 
/* 302 */           } catch (InterruptedException e) {
/* 303 */             throw new RuntimeException(e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\JDBCUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */