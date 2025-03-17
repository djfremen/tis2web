/*     */ package com.eoos.gm.tis2web.sas.client.ui.util;
/*     */ 
/*     */ import javax.swing.SwingUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SwingWorker
/*     */ {
/*     */   private Object value;
/*     */   private ThreadVar threadVar;
/*     */   
/*     */   private static class ThreadVar
/*     */   {
/*     */     private Thread thread;
/*     */     
/*     */     ThreadVar(Thread t) {
/*  24 */       this.thread = t;
/*     */     }
/*     */     
/*     */     synchronized Thread get() {
/*  28 */       return this.thread;
/*     */     }
/*     */     
/*     */     synchronized void clear() {
/*  32 */       this.thread = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized Object getValue() {
/*  43 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void setValue(Object x) {
/*  50 */     this.value = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finished() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interrupt() {
/*  70 */     Thread t = this.threadVar.get();
/*  71 */     if (t != null) {
/*  72 */       t.interrupt();
/*     */     }
/*  74 */     this.threadVar.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get() {
/*     */     while (true) {
/*  86 */       Thread t = this.threadVar.get();
/*  87 */       if (t == null) {
/*  88 */         return getValue();
/*     */       }
/*     */       try {
/*  91 */         t.join();
/*  92 */       } catch (InterruptedException e) {
/*  93 */         Thread.currentThread().interrupt();
/*  94 */         return null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SwingWorker() {
/* 104 */     final Runnable doFinished = new Runnable() {
/*     */         public void run() {
/* 106 */           SwingWorker.this.finished();
/*     */         }
/*     */       };
/*     */     
/* 110 */     Runnable doConstruct = new Runnable() {
/*     */         public void run() {
/*     */           try {
/* 113 */             SwingWorker.this.setValue(SwingWorker.this.construct());
/*     */           } finally {
/* 115 */             SwingWorker.this.threadVar.clear();
/*     */           } 
/*     */           
/* 118 */           SwingUtilities.invokeLater(doFinished);
/*     */         }
/*     */       };
/*     */     
/* 122 */     Thread t = new Thread(doConstruct);
/* 123 */     this.threadVar = new ThreadVar(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/* 130 */     Thread t = this.threadVar.get();
/* 131 */     if (t != null)
/* 132 */       t.start(); 
/*     */   }
/*     */   
/*     */   public abstract Object construct();
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\clien\\u\\util\SwingWorker.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */