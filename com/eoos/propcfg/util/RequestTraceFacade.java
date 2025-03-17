/*     */ package com.eoos.propcfg.util;
/*     */ 
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class RequestTraceFacade
/*     */   extends ConfigurationWrapperBase
/*     */   implements Configuration
/*     */ {
/*     */   private static final class Mode
/*     */   {
/*     */     private Mode() {}
/*     */   }
/*     */   
/*  19 */   public static final Mode MODE_SYNC = new Mode();
/*  20 */   public static final Mode MODE_ASYNC = new Mode();
/*     */   
/*     */   private Callback callback;
/*     */   
/*     */   private Mode mode;
/*     */ 
/*     */   
/*     */   public static class LogCallback
/*     */     implements Callback
/*     */   {
/*     */     private Logger[] loggers;
/*     */     private Logger traceLog;
/*  32 */     private volatile long traceID = 0L;
/*     */     
/*     */     public LogCallback(Logger logMessage, Logger traceLog) {
/*  35 */       this(new Logger[] { logMessage }, traceLog);
/*     */     }
/*     */     
/*     */     public LogCallback(Logger[] loggersMessage, Logger traceLog) {
/*  39 */       this.loggers = loggersMessage;
/*  40 */       this.traceLog = traceLog;
/*     */     }
/*     */     
/*     */     private void logWarn(Object message) {
/*  44 */       for (int i = 0; i < this.loggers.length; i++) {
/*  45 */         this.loggers[i].warn(message);
/*     */       }
/*     */     }
/*     */     
/*     */     private void logInfo(Object message) {
/*  50 */       for (int i = 0; i < this.loggers.length; i++) {
/*  51 */         this.loggers[i].info(message);
/*     */       }
/*     */     }
/*     */     
/*     */     public void onPropertyRequest(String key, String result, List stackTrace) {
/*  56 */       long traceID = this.traceID++;
/*  57 */       StringBuffer msg = new StringBuffer();
/*  58 */       msg.append("requested key: ").append(key).append(", returned value:").append((result == null) ? "NULL" : result);
/*  59 */       msg.append("  (trace: ").append(traceID).append(" )");
/*  60 */       if (result == null) {
/*  61 */         logWarn(msg);
/*     */       } else {
/*  63 */         logInfo(msg);
/*     */       } 
/*  65 */       StringBuffer tmp = new StringBuffer("trace " + traceID + " :");
/*  66 */       for (Iterator<StackTraceElement> iter = stackTrace.iterator(); iter.hasNext(); ) {
/*  67 */         StackTraceElement ste = iter.next();
/*  68 */         tmp.append("\n " + String.valueOf(ste));
/*     */       } 
/*  70 */       this.traceLog.info(tmp.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestTraceFacade(Configuration backend, Callback callback, Mode mode) {
/*  80 */     super(backend);
/*  81 */     this.callback = callback;
/*  82 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public String getProperty(final String key) {
/*  86 */     final String ret = super.getProperty(key);
/*  87 */     final Throwable t = new Throwable();
/*  88 */     Runnable r = new Runnable()
/*     */       {
/*     */         public void run() {
/*  91 */           RequestTraceFacade.this.callback.onPropertyRequest(key, ret, Arrays.asList(t.getStackTrace()));
/*     */         }
/*     */       };
/*     */     
/*  95 */     if (MODE_ASYNC == this.mode) {
/*  96 */       (new Thread(r)).start();
/*     */     } else {
/*  98 */       r.run();
/*     */     } 
/* 100 */     return ret;
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onPropertyRequest(String param1String1, String param1String2, List param1List);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\RequestTraceFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */