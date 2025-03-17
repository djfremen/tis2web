/*     */ package com.eoos.gm.tis2web.sps.client.lt;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.client.ui.SPSClientController;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSEvent;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.lt.SPSLaborTimeConfiguration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSTime
/*     */ {
/*  15 */   private static final Logger log = Logger.getLogger(SPSClientController.class);
/*     */   
/*     */   private LTFormula formula;
/*     */   private int maxDownloadTime;
/*     */   private int maxProgrammingTime;
/*     */   private int maxType4Time;
/*     */   
/*     */   public SPSTime(String formula, String maxDownloadTime, String maxProgrammingTime, String maxType4Time) {
/*  23 */     this.formula = new LTFormula(formula);
/*  24 */     if (maxDownloadTime.endsWith("s")) {
/*  25 */       maxDownloadTime = maxDownloadTime.replace('s', ' ').trim();
/*     */     }
/*  27 */     this.maxDownloadTime = Integer.parseInt(maxDownloadTime);
/*  28 */     if (maxProgrammingTime.endsWith("s")) {
/*  29 */       maxProgrammingTime = maxProgrammingTime.replace('s', ' ').trim();
/*     */     }
/*  31 */     this.maxProgrammingTime = Integer.parseInt(maxProgrammingTime);
/*  32 */     if (maxType4Time.endsWith("s")) {
/*  33 */       maxType4Time = maxType4Time.replace('s', ' ').trim();
/*     */     }
/*  35 */     this.maxType4Time = Integer.parseInt(maxType4Time);
/*     */   }
/*     */   
/*     */   public SPSTime(String formula, int maxDownloadTime, int maxProgrammingTime, int maxType4Time) {
/*  39 */     this.formula = new LTFormula(formula);
/*  40 */     this.maxDownloadTime = maxDownloadTime;
/*  41 */     this.maxProgrammingTime = maxProgrammingTime;
/*  42 */     this.maxType4Time = maxType4Time;
/*     */   }
/*     */   
/*     */   public SPSTime(SPSLaborTimeConfiguration cfg) {
/*  46 */     this.formula = new LTFormula(cfg.getFormula());
/*  47 */     this.maxDownloadTime = cfg.getMaxDownloadTime();
/*  48 */     this.maxProgrammingTime = cfg.getMaxProgrammingTime();
/*  49 */     this.maxType4Time = cfg.getMaxType4Time();
/*     */   }
/*     */   
/*     */   protected int computeTime(int actual, int max) {
/*  53 */     if (actual == 0)
/*  54 */       return 0; 
/*  55 */     if (actual > max) {
/*  56 */       return max;
/*     */     }
/*  58 */     return actual;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int computeTotal(int actualDownloadTime, int actualProgrammingTime, int actualType4Time) {
/*  63 */     return computeTime(actualDownloadTime, this.maxDownloadTime) + computeTime(actualProgrammingTime, this.maxProgrammingTime) + computeTime(actualType4Time, this.maxType4Time);
/*     */   }
/*     */   
/*     */   public int computeTotal(SPSEvent event) {
/*  67 */     return computeTotal(event.getActualDownloadTime(), event.getActualProgrammingTime(), event.getActualType4Time());
/*     */   }
/*     */   
/*     */   public int computeTotal(List events) {
/*  71 */     int totalDownloadTime = 0;
/*  72 */     int totalTime = 0;
/*  73 */     Iterator<SPSEvent> it = events.iterator();
/*  74 */     while (it.hasNext()) {
/*  75 */       SPSEvent event = it.next();
/*  76 */       totalDownloadTime += event.getActualDownloadTime();
/*  77 */       totalTime += computeTime(event.getActualProgrammingTime(), this.maxProgrammingTime);
/*  78 */       totalTime += computeTime(event.getActualType4Time(), this.maxType4Time);
/*  79 */       log.info("sps event: " + event.getActualDownloadTime() + "/" + event.getActualProgrammingTime() + "/" + event.getActualType4Time());
/*     */     } 
/*  81 */     if (totalDownloadTime > events.size() * this.maxDownloadTime) {
/*  82 */       totalTime += events.size() * this.maxDownloadTime;
/*     */     } else {
/*  84 */       totalTime += totalDownloadTime;
/*     */     } 
/*  86 */     return totalTime;
/*     */   }
/*     */   
/*     */   public String computeLTFactor(int total) {
/*     */     try {
/*  91 */       return this.formula.calculate(total);
/*  92 */     } catch (Exception e) {
/*  93 */       return "n/a";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws Exception {
/*  98 */     String formula = "round( 10*(( (#{X} * 1.21) + 179 ) / 3600 )) / 10";
/*  99 */     SPSTime lt = new SPSTime(formula, "80s", "600", "120");
/* 100 */     SPSEvent event = SPSEvent.createSPSProgrammingEvent(11, 210);
/* 101 */     int total = lt.computeTotal(event);
/* 102 */     System.out.println(total + "s -> " + lt.computeLTFactor(total));
/* 103 */     event = SPSEvent.createSPSProgrammingEvent(66, 722);
/* 104 */     total = lt.computeTotal(event);
/* 105 */     System.out.println(total + "s -> " + lt.computeLTFactor(total));
/* 106 */     event = SPSEvent.createSPSType4Event(10, 20);
/* 107 */     total = lt.computeTotal(event);
/* 108 */     System.out.println(total + "s -> " + lt.computeLTFactor(total));
/* 109 */     event = SPSEvent.createSPSType4Event(10, 362);
/* 110 */     total = lt.computeTotal(event);
/* 111 */     System.out.println(total + "s -> " + lt.computeLTFactor(total));
/* 112 */     List<SPSEvent> events = new ArrayList();
/* 113 */     events.add(SPSEvent.createSPSProgrammingEvent(122, 944));
/* 114 */     events.add(SPSEvent.createSPSProgrammingEvent(44, 555));
/* 115 */     events.add(SPSEvent.createSPSType4Event(1, 142));
/* 116 */     total = lt.computeTotal(events);
/* 117 */     System.out.println(total + "s -> " + lt.computeLTFactor(total));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\lt\SPSTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */