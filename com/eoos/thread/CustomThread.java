/*     */ package com.eoos.thread;
/*     */ 
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
/*     */ public class CustomThread
/*     */   extends Thread
/*     */ {
/*  16 */   private ProgressObserver progressObserver = null;
/*     */ 
/*     */   
/*     */   private volatile boolean aborted = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomThread() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomThread(String name) {
/*  28 */     super(name);
/*     */   }
/*     */   
/*     */   public void setProgress(ProgressInfo progressInfo) {
/*  32 */     if (this.progressObserver != null && 
/*  33 */       !this.progressObserver.onProcessing(progressInfo)) {
/*  34 */       abort();
/*  35 */       throw new AbortionException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObserver(ProgressObserver observer) {
/*  47 */     this.progressObserver = observer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abort() {
/*  55 */     Logger log = getLogger();
/*  56 */     log.debug("aborting " + this);
/*  57 */     log.debug("...setting abortion flag");
/*  58 */     this.aborted = true;
/*  59 */     log.debug("...creating and starting interrupting thread");
/*  60 */     Thread t = new Thread(getName() + "-INTERRUPTER") {
/*     */         public void run() {
/*  62 */           Logger log = CustomThread.this.getLogger();
/*  63 */           log.debug("...interrupting " + CustomThread.this);
/*     */           try {
/*  65 */             int count = 0;
/*     */             do {
/*  67 */               log.debug("....calling interrupt()");
/*  68 */               CustomThread.this.interrupt();
/*  69 */               log.debug("....waiting for " + CustomThread.this + " to finish()");
/*  70 */               CustomThread.this.join(1000L);
/*  71 */               count++;
/*  72 */             } while (CustomThread.this.isAlive() && count < 100);
/*  73 */             if (CustomThread.this.isAlive()) {
/*  74 */               log.warn("...unable to interrupt " + CustomThread.this + " (does not react), giving up");
/*     */             }
/*     */           }
/*  77 */           catch (Exception e) {
/*  78 */             log.error("...unable to interrupt " + CustomThread.this + " - exception: " + e, e);
/*     */           } 
/*     */         }
/*     */       };
/*  82 */     t.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAborted() {
/*  92 */     return this.aborted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void assertNotAborted() {
/* 102 */     if (isAborted()) {
/* 103 */       throw new AbortionException();
/*     */     }
/*     */   }
/*     */   
/*     */   public Logger getLogger() {
/* 108 */     return Logger.getLogger(getClass().getName() + "." + getName());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\thread\CustomThread.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */