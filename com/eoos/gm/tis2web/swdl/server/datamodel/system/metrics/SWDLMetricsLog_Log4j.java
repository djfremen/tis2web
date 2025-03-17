/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.HashCalc;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SWDLMetricsLog_Log4j
/*     */   implements SWDLMetricsLog
/*     */ {
/*  28 */   private static final Logger log = Logger.getLogger(SWDLMetricsLog_Log4j.class);
/*     */   private final Logger out;
/*     */   
/*     */   private static class MyEntry
/*     */     implements SWDLMetricsLog.Entry
/*     */   {
/*     */     private SWDLMetricsLog.Entry backend;
/*     */     
/*     */     private MyEntry(SWDLMetricsLog.Entry backend) {
/*  37 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  41 */       StringBuffer tmp = new StringBuffer("{TECH}, {APP}, {VERSION}, {LANGUAGE}");
/*  42 */       StringUtilities.replace(tmp, "{TECH}", String.valueOf(getDevice()));
/*  43 */       StringUtilities.replace(tmp, "{APP}", String.valueOf(getApplication()));
/*  44 */       StringUtilities.replace(tmp, "{VERSION}", String.valueOf(getVersion()));
/*  45 */       StringUtilities.replace(tmp, "{LANGUAGE}", String.valueOf(getLanguage()));
/*  46 */       return tmp.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  50 */       int retValue = MyEntry.class.hashCode();
/*  51 */       retValue = HashCalc.addHashCode(retValue, getApplication());
/*  52 */       retValue = HashCalc.addHashCode(retValue, getVersion());
/*  53 */       retValue = HashCalc.addHashCode(retValue, getDevice());
/*  54 */       retValue = HashCalc.addHashCode(retValue, getLanguage());
/*  55 */       return retValue;
/*     */     }
/*     */     
/*     */     public long getTimestamp() {
/*  59 */       return this.backend.getTimestamp();
/*     */     }
/*     */     
/*     */     public String getDevice() {
/*  63 */       return this.backend.getDevice();
/*     */     }
/*     */     
/*     */     public String getApplication() {
/*  67 */       return this.backend.getApplication();
/*     */     }
/*     */     
/*     */     public String getVersion() {
/*  71 */       return this.backend.getVersion();
/*     */     }
/*     */     
/*     */     public String getLanguage() {
/*  75 */       return this.backend.getLanguage();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  80 */       if (this == obj)
/*  81 */         return true; 
/*  82 */       if (obj instanceof MyEntry) {
/*  83 */         MyEntry other = (MyEntry)obj;
/*  84 */         boolean ret = Util.equals(getDevice(), other.getDevice());
/*  85 */         ret = (ret && Util.equals(getApplication(), other.getApplication()));
/*  86 */         ret = (ret && Util.equals(getVersion(), other.getVersion()));
/*  87 */         ret = (ret && Util.equals(getLanguage(), other.getLanguage()));
/*     */         
/*  89 */         return ret;
/*     */       } 
/*  91 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUserID() {
/*  96 */       return this.backend.getUserID();
/*     */     }
/*     */   }
/*     */   
/*     */   protected class Write
/*     */     implements Runnable {
/*     */     public void run() {
/* 103 */       synchronized (SWDLMetricsLog_Log4j.this.entryToCounter) {
/* 104 */         SWDLMetricsLog_Log4j.this.out.info("********** SWDL metrics follows (" + DateConvert.toDateString(System.currentTimeMillis(), DateFormat.getInstance()) + ") ****** ");
/* 105 */         Iterator<Map.Entry> iter = SWDLMetricsLog_Log4j.this.entryToCounter.entrySet().iterator();
/* 106 */         while (iter.hasNext()) {
/* 107 */           Map.Entry mapEntry = iter.next();
/* 108 */           SWDLMetricsLog.Entry entry = (SWDLMetricsLog.Entry)mapEntry.getKey();
/* 109 */           SWDLMetricsLog_Log4j.this.out.info(String.valueOf(entry) + ": " + String.valueOf(mapEntry.getValue()));
/*     */         } 
/* 111 */         SWDLMetricsLog_Log4j.this.out.info("********** end SWDL metrics ********** ");
/* 112 */         SWDLMetricsLog_Log4j.this.entryToCounter.clear();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 117 */   protected Map entryToCounter = Collections.synchronizedMap(new HashMap<Object, Object>());
/*     */   
/*     */   protected PeriodicTask writeTask;
/*     */   
/*     */   public SWDLMetricsLog_Log4j(Configuration configuration) throws Exception {
/* 122 */     long interval = (new TypeDecorator(configuration)).getNumber("output.interval").longValue();
/* 123 */     interval = interval * 60L * 1000L;
/* 124 */     if (interval <= 0L) {
/* 125 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 128 */     String loggerName = configuration.getProperty("logger");
/* 129 */     if (loggerName == null || loggerName.length() == 0) {
/* 130 */       loggerName = "tech.download.monitoring";
/*     */     }
/* 132 */     this.out = Logger.getLogger(loggerName);
/*     */     
/* 134 */     log.debug("starting write task with interval " + interval);
/* 135 */     this.writeTask = new PeriodicTask(new Write(), interval);
/* 136 */     this.writeTask.start();
/*     */     
/* 138 */     ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/* 141 */             SWDLMetricsLog_Log4j.this.writeTask.stop();
/* 142 */             SWDLMetricsLog_Log4j.this.writeTask = null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void _add(SWDLMetricsLog.Entry entry) throws Exception {
/* 150 */     Integer counter = (Integer)this.entryToCounter.get(entry);
/* 151 */     if (counter == null) {
/* 152 */       counter = Integer.valueOf(1);
/* 153 */       this.entryToCounter.put(entry, counter);
/*     */     } else {
/* 155 */       this.entryToCounter.put(entry, Integer.valueOf(counter.intValue() + 1));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void add(SWDLMetricsLog.Entry entry) throws Exception {
/* 160 */     _add(new MyEntry(entry));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\SWDLMetricsLog_Log4j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */