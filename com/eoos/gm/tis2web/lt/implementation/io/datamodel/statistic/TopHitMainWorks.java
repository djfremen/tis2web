/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel.statistic;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.lt.service.cai.SIOLT;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ 
/*     */ public class TopHitMainWorks
/*     */ {
/*  28 */   private static final Logger mainWorkHitLog = Logger.getLogger("lt.mainwork.hits");
/*     */   
/*  30 */   private static TopHitMainWorks instance = null;
/*     */   
/*  32 */   private Map mainWorkHits = new ConcurrentHashMap<Object, Object>();
/*     */   
/*  34 */   private PeriodicTask ptHitEvaluation = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private TopHitMainWorks() {
/*  39 */     if (this.ptHitEvaluation == null) {
/*  40 */       long interval = 0L;
/*  41 */       String intervalProperty = ApplicationContext.getInstance().getProperty("component.lt.mainwork.hit.evaluation.interval");
/*     */       try {
/*  43 */         interval = Long.parseLong(intervalProperty) * 60L * 1000L;
/*  44 */       } catch (NumberFormatException e) {
/*  45 */         interval = 86400000L;
/*     */       } 
/*     */       
/*  48 */       Runnable r = new Runnable() {
/*     */           public void run() {
/*  50 */             synchronized (TopHitMainWorks.this.mainWorkHits) {
/*  51 */               List<?> entries = new ArrayList(TopHitMainWorks.this.mainWorkHits.entrySet());
/*  52 */               Comparator<?> c = new Comparator() {
/*     */                   public int compare(Object obj1, Object obj2) {
/*     */                     try {
/*  55 */                       Map.Entry e1 = (Map.Entry)obj1;
/*  56 */                       Map.Entry e2 = (Map.Entry)obj2;
/*  57 */                       return ((Long)e1.getValue()).compareTo((Long)e2.getValue()) * -1;
/*  58 */                     } catch (Exception e) {
/*  59 */                       return 0;
/*     */                     } 
/*     */                   }
/*     */                 };
/*     */               
/*  64 */               Collections.sort(entries, c);
/*  65 */               if (entries.size() > 0) {
/*  66 */                 TopHitMainWorks.mainWorkHitLog.info("LT Main Work Hits ----------------");
/*  67 */                 String template = new String("rank: {RANK}  main work: {DOCID}  hits: {HITS}");
/*     */                 
/*  69 */                 for (int i = 0; i < Math.min(50, entries.size()); i++) {
/*  70 */                   Map.Entry entry = (Map.Entry)entries.get(i);
/*  71 */                   SIOLT doc = (SIOLT)entry.getKey();
/*  72 */                   Long hits = (Long)entry.getValue();
/*  73 */                   StringBuffer tmp = new StringBuffer(template);
/*  74 */                   StringUtilities.replace(tmp, "{RANK}", String.valueOf(i + 1));
/*  75 */                   StringUtilities.replace(tmp, "{HITS}", String.valueOf(hits));
/*     */                   
/*  77 */                   String docid = doc.getMajorOperationNumber();
/*     */                   
/*  79 */                   StringUtilities.replace(tmp, "{DOCID}", docid);
/*  80 */                   TopHitMainWorks.mainWorkHitLog.info(tmp);
/*     */                 } 
/*     */               } 
/*  83 */               TopHitMainWorks.this.mainWorkHits.clear();
/*     */             } 
/*     */           }
/*     */         };
/*     */       
/*  88 */       this.ptHitEvaluation = new PeriodicTask(r, interval);
/*  89 */       this.ptHitEvaluation.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized TopHitMainWorks getInstance() {
/*  95 */     if (instance == null) {
/*  96 */       instance = new TopHitMainWorks();
/*     */     }
/*  98 */     return instance;
/*     */   }
/*     */   
/*     */   public void hit(SIOLT sioLT) {
/* 102 */     synchronized (this.mainWorkHits) {
/* 103 */       Long count = (Long)this.mainWorkHits.get(sioLT);
/* 104 */       this.mainWorkHits.put(sioLT, Long.valueOf((count != null) ? (count.longValue() + 1L) : 1L));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\statistic\TopHitMainWorks.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */