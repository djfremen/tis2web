/*     */ package com.eoos.gm.tis2web.fts.implementation;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.apache.lucene.store.InputStream;
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
/*     */ class SQLInputStream
/*     */   extends InputStream
/*     */   implements Cloneable
/*     */ {
/*     */   private Integer locale;
/*     */   private String filename;
/*     */   private long pointer;
/*     */   protected AtomicBoolean closed;
/*     */   private SQLDirectory directory;
/*     */   private String query;
/*     */   
/*     */   public SQLInputStream(SQLDirectory directory, Integer locale, String fileTable, String bufferTable, String filename, long length) {
/* 265 */     this.locale = locale;
/* 266 */     this.query = "select buffer from " + bufferTable + " where locale = ? and filename = ? and buffernumber = ?";
/* 267 */     this.filename = filename;
/* 268 */     this.length = length;
/* 269 */     this.pointer = 0L;
/* 270 */     this.directory = directory;
/* 271 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 276 */     this.closed = new AtomicBoolean(false);
/*     */     
/* 278 */     if (SQLDirectory.log.isDebugEnabled())
/* 279 */       SQLDirectory.log.debug("SqlInputStream(" + this.filename + ")"); 
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 283 */     SQLInputStream ret = (SQLInputStream)super.clone();
/* 284 */     ret.init();
/* 285 */     return ret;
/*     */   }
/*     */   
/*     */   private String toCacheKey(Long bufferNr) {
/* 289 */     return this.filename + "#" + this.locale + "#" + bufferNr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void readInternal(byte[] dest, int destOffset, int len) {
/* 296 */     if (SQLDirectory.log.isDebugEnabled()) {
/* 297 */       SQLDirectory.log.debug("SqlInputStream.readInternal(" + this.filename + ":" + (this.pointer + destOffset) + "-" + (this.pointer + destOffset + len) + ") ...");
/*     */     }
/*     */     try {
/* 300 */       byte[] buffer = null;
/*     */       
/* 302 */       int remainder = len;
/* 303 */       long start = this.pointer;
/*     */       
/* 305 */       PreparedStatement stmt = null;
/*     */       try {
/* 307 */         while (remainder != 0) {
/* 308 */           long bufferNumber = start / 1024L;
/* 309 */           Long bufferNr = Long.valueOf(bufferNumber);
/* 310 */           int bufferOffset = (int)(start % 1024L);
/* 311 */           int bytesInBuffer = 1024 - bufferOffset;
/* 312 */           int bytesToCopy = (bytesInBuffer >= remainder) ? remainder : bytesInBuffer;
/* 313 */           if ((buffer = (byte[])this.directory.getCache().lookup(toCacheKey(bufferNr))) == null) {
/* 314 */             if (SQLDirectory.log.isDebugEnabled()) {
/* 315 */               SQLDirectory.log.debug("...loading buffer number: " + bufferNumber + " of " + this.filename + " (lcid:" + this.locale + ") from database");
/*     */             }
/* 317 */             if (stmt == null) {
/* 318 */               stmt = this.directory.getStatementManager().getStatement(this.query);
/* 319 */               stmt.setInt(1, this.locale.intValue());
/* 320 */               stmt.setString(2, this.filename);
/*     */             } 
/* 322 */             stmt.setLong(3, bufferNumber);
/*     */ 
/*     */             
/* 325 */             ResultSet rs = stmt.executeQuery();
/*     */             try {
/* 327 */               rs.next();
/* 328 */               buffer = rs.getBytes(1);
/* 329 */               this.directory.getCache().store(toCacheKey(bufferNr), buffer);
/* 330 */             } catch (Exception x) {
/* 331 */               throw new IllegalStateException(x.getMessage());
/*     */             } finally {
/* 333 */               rs.close();
/*     */             } 
/*     */           } 
/*     */           
/* 337 */           System.arraycopy(buffer, bufferOffset, dest, destOffset, bytesToCopy);
/* 338 */           destOffset += bytesToCopy;
/* 339 */           start += bytesToCopy;
/* 340 */           remainder -= bytesToCopy;
/*     */         } 
/*     */       } finally {
/* 343 */         if (stmt != null) {
/* 344 */           this.directory.getStatementManager().releaseStatement(stmt);
/*     */         }
/*     */       } 
/*     */       
/* 348 */       this.pointer += len;
/*     */     }
/* 350 */     catch (Exception e) {
/* 351 */       throw new IllegalStateException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 359 */     if (SQLDirectory.log.isDebugEnabled())
/* 360 */       SQLDirectory.log.debug("InputStream.close(" + this.filename + ") ..."); 
/* 361 */     this.closed.set(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void seekInternal(long position) {
/* 370 */     this.pointer = position;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\SQLInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */