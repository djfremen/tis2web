/*     */ package com.eoos.gm.tis2web.swdl.server.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
/*     */ import com.eoos.gm.tis2web.swdl.common.domain.application.File;
/*     */ import com.eoos.ref.v3.IReference;
/*     */ import com.eoos.ref.v3.TimedMutationReference;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class DatabaseAdapter_CacheImpl
/*     */   implements DatabaseAdapter
/*     */ {
/*  29 */   private static final Logger log = Logger.getLogger(DatabaseAdapter_CacheImpl.class);
/*     */   
/*     */   protected DatabaseAdapterImpl impl;
/*     */   
/*  33 */   protected Map filecache = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   private long ttl;
/*     */   
/*     */   private class FileCacheCleanup implements Runnable {
/*     */     public void run() {
/*  38 */       synchronized (DatabaseAdapter_CacheImpl.this.filecache) {
/*  39 */         DatabaseAdapter_CacheImpl.log.debug("performing filecache cleanup (current size:" + DatabaseAdapter_CacheImpl.this.filecache.size() + ")");
/*     */         try {
/*  41 */           Iterator<Map.Entry> iter = DatabaseAdapter_CacheImpl.this.filecache.entrySet().iterator();
/*  42 */           while (iter.hasNext()) {
/*  43 */             Map.Entry entry = iter.next();
/*  44 */             IReference ref = (IReference)entry.getValue();
/*  45 */             if (((IReference.Snoopable)ref).snoop() == null) {
/*  46 */               iter.remove();
/*     */             }
/*     */           } 
/*  49 */         } catch (Exception e) {
/*  50 */           DatabaseAdapter_CacheImpl.log.warn("unable to cleanup filecache - error:" + e, e);
/*     */         } 
/*  52 */         DatabaseAdapter_CacheImpl.log.debug("done filecache cleanup (new size:" + DatabaseAdapter_CacheImpl.this.filecache.size() + ")");
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private FileCacheCleanup() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public DatabaseAdapter_CacheImpl(DatabaseAdapterImpl impl) {
/*  63 */     this.impl = impl;
/*     */     
/*     */     try {
/*  66 */       String _ttl = ApplicationContext.getInstance().getProperty("component.swdl.filecache.timetolive");
/*  67 */       this.ttl = Long.parseLong(_ttl) * 60L * 1000L;
/*  68 */     } catch (Exception e) {
/*  69 */       this.ttl = 1800000L;
/*     */     } 
/*     */     
/*  72 */     long cleanupPeriod = Math.max(this.ttl * 2L, 1800000L);
/*  73 */     (new PeriodicTask(new FileCacheCleanup(), cleanupPeriod)).start();
/*     */     
/*  75 */     log.debug("initialized swdl filecache adapter with TTL:" + this.ttl + "ms (cleanup interval is:" + cleanupPeriod + "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getApplications() {
/*  80 */     return this.impl.getApplications();
/*     */   }
/*     */   
/*     */   protected File lookupFileCache(String fileID) {
/*  84 */     File retValue = null;
/*  85 */     TimedMutationReference ref = (TimedMutationReference)this.filecache.get(fileID);
/*  86 */     if (ref != null) {
/*  87 */       retValue = (File)ref.get();
/*     */     }
/*  89 */     return retValue;
/*     */   }
/*     */   
/*     */   protected void cacheFile(String fileID, File file) {
/*  93 */     TimedMutationReference ref = new TimedMutationReference(file, TimedMutationReference.Type.SOFT, TimedMutationReference.Type.WEAK, this.ttl, true);
/*  94 */     this.filecache.put(fileID, ref);
/*     */   }
/*     */   
/*     */   public File getFile(String fileID) {
/*  98 */     File retValue = lookupFileCache(fileID);
/*  99 */     if (retValue == null) {
/* 100 */       log.debug("file not in cache");
/* 101 */       retValue = this.impl.getFile(fileID);
/* 102 */       cacheFile(fileID, retValue);
/*     */     } else {
/* 104 */       log.debug("file found in cache");
/*     */     } 
/*     */     
/* 107 */     return retValue;
/*     */   }
/*     */   
/*     */   public DBVersionInformation getVersionInfo() {
/* 111 */     return this.impl.getVersionInfo();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 115 */     return super.toString() + "[" + this.impl.toString() + "]";
/*     */   }
/*     */   
/*     */   public void clearCache() {
/* 119 */     synchronized (this.filecache) {
/* 120 */       this.filecache.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int cacheSize() {
/* 125 */     synchronized (this.filecache) {
/* 126 */       return this.filecache.size();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\db\DatabaseAdapter_CacheImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */