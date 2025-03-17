/*     */ package com.eoos.jdbc;
/*     */ 
/*     */ import com.eoos.math.AverageCalculator;
/*     */ import com.eoos.scsm.v2.cache.Cache;
/*     */ import com.eoos.scsm.v2.cache.CacheHitRatioAdapter;
/*     */ import com.eoos.scsm.v2.objectpool.SetPool;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.v2.StopWatch;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CachedRetrievalSupportV3
/*     */ {
/*     */   public static interface Callback
/*     */   {
/*  27 */     public static final Object IGNORE = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object createKey(Object param1Object);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void initStatement(PreparedStatement param1PreparedStatement) throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     String getQuery();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setParameters(Object param1Object, PreparedStatement param1PreparedStatement) throws SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object createObject(Object param1Object, ResultSet param1ResultSet) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static interface EmptyResultFallback
/*     */     {
/*     */       Object createObject(Object param2Object) throws Exception;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Object NULL_MARKER = new Object();
/*     */   
/*     */   private Logger log;
/*     */   
/*  72 */   private Cache cache = null;
/*     */   
/*  74 */   private AverageCalculator average = new AverageCalculator(0);
/*     */   
/*     */   private Util.ShutdownListener listener;
/*     */   
/*     */   private IStatementManager stmtManager;
/*     */ 
/*     */   
/*     */   private CachedRetrievalSupportV3(String name, IStatementManager statementManager, Cache cache) {
/*  82 */     this.log = Logger.getLogger(CachedRetrievalSupportV3.class.getName() + "/" + name);
/*     */     
/*  84 */     this.stmtManager = statementManager;
/*  85 */     this.cache = (Cache)new CacheHitRatioAdapter(cache);
/*  86 */     this.listener = Util.addShutdownListener(new Util.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/*  89 */             synchronized (CachedRetrievalSupportV3.this.log) {
/*  90 */               CachedRetrievalSupportV3.this.log.info("statistics ...");
/*  91 */               CachedRetrievalSupportV3.this.log.info("...hit ratio: " + ((CacheHitRatioAdapter)CachedRetrievalSupportV3.this.cache).getHitRatio() + " hits/requests");
/*  92 */               CachedRetrievalSupportV3.this.log.info("...total requests: " + ((CacheHitRatioAdapter)CachedRetrievalSupportV3.this.cache).getRequestCount());
/*  93 */               CachedRetrievalSupportV3.this.log.info("...average retrieval time: " + CachedRetrievalSupportV3.this.average.getCurrentAverage() + " ms");
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public CachedRetrievalSupportV3(String name, ConnectionProvider connectionProvider, Cache cache) {
/* 101 */     this(name, new StatementManagerV2(connectionProvider), cache);
/*     */   } public static interface EmptyResultFallback {
/*     */     Object createObject(Object param1Object) throws Exception; }
/*     */   public Map getObjects(Collection identifiers, Callback callback) throws Exception {
/* 105 */     if (Util.isNullOrEmpty(identifiers)) {
/* 106 */       return Collections.EMPTY_MAP;
/*     */     }
/*     */     
/* 109 */     Map<Object, Object> ret = null;
/* 110 */     Set<Object> toLoad = SetPool.getThreadInstance().get();
/*     */     
/*     */     try {
/* 113 */       for (Iterator iter = identifiers.iterator(); iter.hasNext(); ) {
/* 114 */         Object identifier = iter.next();
/* 115 */         Object key = callback.createKey(identifier);
/* 116 */         Object data = this.cache.lookup(key);
/* 117 */         if (data == null) {
/* 118 */           toLoad.add(identifier); continue;
/* 119 */         }  if (data != NULL_MARKER) {
/* 120 */           if (ret == null) {
/* 121 */             if (identifiers.size() == 1) {
/* 122 */               ret = Collections.singletonMap(identifier, data); continue;
/*     */             } 
/* 124 */             ret = new HashMap<Object, Object>(identifiers.size());
/* 125 */             ret.put(identifier, data);
/*     */             continue;
/*     */           } 
/* 128 */           ret.put(identifier, data);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 133 */       if (toLoad.size() > 0) {
/* 134 */         Map<Object, Object> loaded = getObjectsPhase2(toLoad, callback);
/* 135 */         if (ret == null) {
/* 136 */           ret = loaded;
/*     */         } else {
/* 138 */           ret.putAll(loaded);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 142 */       SetPool.getThreadInstance().free(toLoad);
/*     */     } 
/*     */     
/* 145 */     return (ret != null) ? ret : Collections.EMPTY_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized Map getObjectsPhase2(Collection identifiers, Callback callback) throws Exception {
/* 150 */     if (this.log.isTraceEnabled()) {
/* 151 */       this.log.trace("retrieving " + identifiers.size() + " data");
/*     */     }
/* 153 */     if (Util.isNullOrEmpty(identifiers)) {
/* 154 */       return Collections.EMPTY_MAP;
/*     */     }
/* 156 */     StopWatch sw = StopWatch.getInstance().start();
/*     */     try {
/* 158 */       Map<Object, Object> ret = null;
/* 159 */       PreparedStatement stmt = null;
/*     */       try {
/* 161 */         for (Iterator iter = identifiers.iterator(); iter.hasNext(); ) {
/* 162 */           Object identifier = iter.next();
/* 163 */           Object key = callback.createKey(identifier);
/* 164 */           Object data = this.cache.lookup(key);
/* 165 */           if (data == null) {
/* 166 */             if (this.log.isTraceEnabled()) {
/* 167 */               this.log.trace("...loading data for id: " + String.valueOf(identifier));
/*     */             }
/* 169 */             if (stmt == null) {
/* 170 */               stmt = this.stmtManager.getStatement(callback.getQuery());
/* 171 */               callback.initStatement(stmt);
/*     */             } 
/* 173 */             callback.setParameters(identifier, stmt);
/* 174 */             ResultSet rs = stmt.executeQuery();
/*     */             try {
/* 176 */               if (rs.next()) {
/* 177 */                 data = callback.createObject(identifier, rs);
/*     */               } else {
/* 179 */                 Callback.EmptyResultFallback fallback = (Callback.EmptyResultFallback)Util.prepareCast(callback, Callback.EmptyResultFallback.class);
/* 180 */                 if (fallback != null) {
/* 181 */                   data = fallback.createObject(identifier);
/*     */                 }
/*     */               } 
/*     */             } finally {
/*     */               
/* 186 */               JDBCUtil.close(rs, this.log);
/*     */             } 
/* 188 */             if (data == Callback.IGNORE)
/*     */               continue; 
/* 190 */             if (data == null) {
/* 191 */               data = NULL_MARKER;
/*     */             }
/* 193 */             this.cache.store(key, data);
/*     */           } 
/*     */           
/* 196 */           if (data == NULL_MARKER) {
/* 197 */             data = null;
/*     */           }
/*     */           
/* 200 */           if (ret == null) {
/* 201 */             if (identifiers.size() == 1) {
/* 202 */               ret = Collections.singletonMap(identifier, data); continue;
/*     */             } 
/* 204 */             ret = new HashMap<Object, Object>(identifiers.size());
/* 205 */             ret.put(identifier, data);
/*     */             continue;
/*     */           } 
/* 208 */           ret.put(identifier, data);
/*     */         } 
/*     */ 
/*     */         
/* 212 */         long duration = sw.stop();
/* 213 */         if (this.log.isTraceEnabled()) {
/* 214 */           this.log.trace("...done in " + duration + " ms");
/*     */         }
/* 216 */         this.average.add(BigDecimal.valueOf(duration));
/* 217 */         return (ret.size() > 0) ? ret : null;
/*     */       } finally {
/* 219 */         if (stmt != null) {
/* 220 */           this.stmtManager.releaseStatement(stmt);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 224 */       StopWatch.freeInstance(sw);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\jdbc\CachedRetrievalSupportV3.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */