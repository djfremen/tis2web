/*     */ package com.eoos.log;
/*     */ 
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.io.StreamUtil;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Writer;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.WriterAppender;
/*     */ import org.apache.log4j.helpers.CountingQuietWriter;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ import org.apache.log4j.helpers.OptionConverter;
/*     */ import org.apache.log4j.helpers.QuietWriter;
/*     */ import org.apache.log4j.spi.LoggingEvent;
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
/*     */ public class RolloverAppender
/*     */   extends WriterAppender
/*     */ {
/*  38 */   private int rolloverIndex = 0;
/*  39 */   private long maxFileSize = 10485760L;
/*     */   
/*     */   private String directory;
/*     */   
/*     */   private long rolloverPeriod;
/*     */   private long tsNextRollover;
/*     */   private String namePattern;
/*  46 */   private long tsStart = System.currentTimeMillis();
/*     */   
/*     */   private ICounter counter;
/*     */   
/*  50 */   private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirectory(String directory) {
/*  57 */     this.directory = directory;
/*     */   }
/*     */   
/*     */   public void setNamePattern(String namePattern) {
/*  61 */     this.namePattern = namePattern;
/*     */   }
/*     */   
/*     */   public void setMaxFileSize(String size) {
/*  65 */     this.maxFileSize = OptionConverter.toFileSize(size, this.maxFileSize);
/*     */   }
/*     */   
/*     */   public void setRolloverPeriod(String period) {
/*     */     try {
/*  70 */       this.rolloverPeriod = Math.max(60000L, Util.parseDuration(period).getAsMillis());
/*  71 */     } catch (IllegalArgumentException e) {
/*  72 */       LogLog.error("unable to set rollover period to '" + period + "', using default: 1 hour ", e);
/*  73 */       this.rolloverPeriod = 3600000L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void subAppend(LoggingEvent event) {
/*  79 */     if (System.currentTimeMillis() > this.tsNextRollover) {
/*  80 */       rollover();
/*     */     }
/*  82 */     super.subAppend(event);
/*  83 */     if (this.counter.getCount().longValue() > this.maxFileSize) {
/*  84 */       rollover();
/*     */     }
/*     */   }
/*     */   
/*     */   public void activateOptions() {
/*  89 */     rollover();
/*     */   }
/*     */   
/*  92 */   private static final Pattern PATTERN_VARIABLE = Pattern.compile("\\[(.*?)\\]");
/*     */ 
/*     */   
/*     */   private void rollover() {
/*  96 */     StringBuffer tmp = new StringBuffer(this.namePattern);
/*  97 */     Util.replace(tmp, PATTERN_VARIABLE, new Util.ReplacementCallback()
/*     */         {
/*     */           public CharSequence getReplacement(CharSequence match, Util.ReplacementCallback.MatcherCallback matcherCallback) {
/* 100 */             CharSequence ret = match;
/* 101 */             String key = matcherCallback.getGroup(1);
/* 102 */             if ("time".equalsIgnoreCase(key)) {
/* 103 */               ret = RolloverAppender.dateFormat.format(Long.valueOf(RolloverAppender.this.tsStart));
/* 104 */             } else if ("index".equalsIgnoreCase(key)) {
/* 105 */               ret = String.valueOf(RolloverAppender.this.rolloverIndex);
/*     */             } 
/* 107 */             return ret;
/*     */           }
/*     */         });
/* 110 */     File file = new File(this.directory, tmp.toString());
/* 111 */     if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
/*     */       try {
/* 113 */         this.counter = (ICounter)new Counter();
/* 114 */         setWriter(createWriter((OutputStream)new OutputStreamByteCount(new FileOutputStream(file), this.counter)));
/* 115 */       } catch (FileNotFoundException e) {
/* 116 */         throw new RuntimeException(e);
/*     */       } 
/*     */     } else {
/* 119 */       LogLog.error("failed to create file: " + file + "");
/* 120 */       setWriter(createWriter(StreamUtil.NILOutputStream));
/*     */     } 
/*     */     
/* 123 */     this.rolloverIndex++;
/* 124 */     this.tsNextRollover = System.currentTimeMillis() + this.rolloverPeriod;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setQWForFiles(Writer writer) {
/* 129 */     this.qw = (QuietWriter)new CountingQuietWriter(writer, this.errorHandler);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\RolloverAppender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */