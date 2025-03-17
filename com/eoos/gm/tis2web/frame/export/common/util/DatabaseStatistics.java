/*     */ package com.eoos.gm.tis2web.frame.export.common.util;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.ConfigurationService;
/*     */ import com.eoos.instantiation.Singleton;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class DatabaseStatistics
/*     */   implements Singleton, IDatabaseStatistics
/*     */ {
/*  23 */   private static final Logger log = Logger.getLogger(DatabaseStatistics.class);
/*     */   
/*  25 */   private static IDatabaseStatistics instance = null;
/*     */   
/*  27 */   private Map queries = null;
/*     */   
/*     */   private final long interval;
/*     */   
/*     */   private final int entryCount;
/*     */   
/*  33 */   private long lastReport = System.currentTimeMillis();
/*     */   private Configuration conf;
/*     */   private boolean reduceQuery = false;
/*     */   
/*     */   private DatabaseStatistics(Configuration config) throws Exception {
/*  38 */     this.queries = new ConcurrentHashMap<Object, Object>();
/*  39 */     this.interval = ConfigurationUtil.getNumber("interval", config).longValue();
/*  40 */     this.entryCount = Math.max(0, ConfigurationUtil.getNumber("entry.count", config).intValue());
/*  41 */     this.conf = config;
/*  42 */     this.reduceQuery = ConfigurationUtil.isTrue("reduce.queries", this.conf);
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized IDatabaseStatistics getInstance() {
/*  47 */     if (instance == null) {
/*     */       try {
/*  49 */         final ConfigurationService cfgService = ConfigurationServiceProvider.getService();
/*  50 */         final SubConfigurationWrapper config = new SubConfigurationWrapper((Configuration)cfgService, "frame.database.statistics.");
/*  51 */         final int configHash = ConfigurationUtil.configurationHash((Configuration)subConfigurationWrapper);
/*     */         
/*  53 */         cfgService.addObserver(new ConfigurationService.Observer()
/*     */             {
/*     */               public void onModification() {
/*  56 */                 if (configHash != ConfigurationUtil.configurationHash(config)) {
/*  57 */                   cfgService.removeObserver(this);
/*  58 */                   DatabaseStatistics.reset();
/*     */                 } 
/*     */               }
/*     */             });
/*  62 */         if (ConfigurationUtil.isTrue("activated", (Configuration)subConfigurationWrapper)) {
/*  63 */           instance = new DatabaseStatistics((Configuration)subConfigurationWrapper);
/*     */         } else {
/*  65 */           log.debug("database statistics are not enabled, returning disabled instance");
/*  66 */           instance = IDatabaseStatistics.DISABLED;
/*     */         } 
/*  68 */       } catch (Exception e) {
/*  69 */         log.error("unable to create instance, returning disabled instance - exception: " + e, e);
/*  70 */         return IDatabaseStatistics.DISABLED;
/*     */       } 
/*     */     }
/*  73 */     return instance;
/*     */   }
/*     */   
/*     */   public static synchronized void reset() {
/*  77 */     log.debug("resetting");
/*  78 */     instance = null;
/*     */   }
/*     */   
/*     */   public String register(String query) {
/*  82 */     if (!Util.isNullOrEmpty(query)) {
/*  83 */       String key = query;
/*  84 */       if (this.reduceQuery) {
/*  85 */         int pos = query.toUpperCase().indexOf("WHERE");
/*  86 */         if (pos > 0) {
/*  87 */           key = query.substring(0, pos - 1);
/*     */         }
/*     */       } 
/*  90 */       key = Util.toMultiton(key);
/*  91 */       synchronized (key) {
/*  92 */         Counter counter = (Counter)this.queries.get(key);
/*  93 */         if (counter == null) {
/*  94 */           this.queries.put(key, new Counter(BigInteger.ONE));
/*     */         } else {
/*  96 */           counter.inc();
/*     */         } 
/*     */       } 
/*     */     } 
/* 100 */     synchronized (this) {
/* 101 */       if (this.lastReport == -1L || System.currentTimeMillis() - this.lastReport > this.interval) {
/* 102 */         final Map reportEntries = this.queries;
/* 103 */         this.queries = new ConcurrentHashMap<Object, Object>();
/* 104 */         Util.executeAsynchronous(new Runnable()
/*     */             {
/*     */               public void run() {
/*     */                 try {
/* 108 */                   DatabaseStatistics.this.report(reportEntries);
/* 109 */                 } catch (Exception e) {
/* 110 */                   DatabaseStatistics.log.error("unable to create report - exception: " + e, e);
/*     */                 } 
/*     */               }
/*     */             });
/* 114 */         this.lastReport = System.currentTimeMillis();
/*     */       } 
/*     */     } 
/* 117 */     return query;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void report(Map _entries) {
/* 122 */     log.info("top queries ******************************************");
/*     */     
/* 124 */     List<?> entries = new LinkedList(_entries.entrySet());
/* 125 */     Collections.sort(entries, new Comparator()
/*     */         {
/*     */           public int compare(Object o1, Object o2) {
/* 128 */             Map.Entry entry1 = (Map.Entry)o1;
/* 129 */             Map.Entry entry2 = (Map.Entry)o2;
/*     */             
/* 131 */             return ((Counter)entry2.getValue()).compareTo(entry1.getValue());
/*     */           }
/*     */         });
/*     */     
/* 135 */     for (int i = 0; i < Math.min(this.entryCount, entries.size()); i++) {
/* 136 */       Map.Entry entry = (Map.Entry)entries.get(i);
/* 137 */       log.info("#" + entry.getValue() + "\t query: " + entry.getKey());
/*     */     } 
/* 139 */     log.info("****************************************** top queries");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\DatabaseStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */