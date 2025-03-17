/*     */ package com.eoos.thread;
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
/*     */ public class AsynchronousExecution
/*     */ {
/*     */   public static class IneffectiveAbortionException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */   }
/*  20 */   private final Object SYNC_RESULT = new Object();
/*     */   
/*  22 */   private Object result = null;
/*     */   
/*     */   private boolean thrown = false;
/*     */   
/*  26 */   private final Object SYNC_THREAD = new Object();
/*     */   
/*  28 */   private CustomThread thread = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setResult(Object result, boolean thrown) {
/*  35 */     synchronized (this.SYNC_RESULT) {
/*  36 */       this.result = result;
/*  37 */       this.thrown = thrown;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AsynchronousExecution start(final Operation operation, final Object[] input) {
/*  42 */     final AsynchronousExecution ae = new AsynchronousExecution();
/*  43 */     ae.thread = new CustomThread(Thread.currentThread().getName() + "-AsynExec") {
/*     */         public void run() {
/*     */           try {
/*  46 */             ae.setResult(operation.execute(input), false);
/*  47 */           } catch (Exception e) {
/*  48 */             ae.setResult(e, true);
/*     */           } 
/*     */         }
/*     */       };
/*  52 */     ae.thread.start();
/*  53 */     return ae;
/*     */   }
/*     */ 
/*     */   
/*     */   public void abort() {
/*  58 */     synchronized (this.SYNC_THREAD) {
/*  59 */       this.thread.abort();
/*     */     } 
/*     */   } public static interface Operation {
/*     */     Object execute(Object[] param1ArrayOfObject) throws Exception; }
/*     */   public Object getResult(long maximalWait, boolean abort) throws Exception, IneffectiveAbortionException {
/*  64 */     synchronized (this.SYNC_THREAD) {
/*  65 */       this.thread.join(maximalWait);
/*  66 */       if (abort && this.thread.isAlive()) {
/*  67 */         this.thread.abort();
/*  68 */         this.thread.join(1000L);
/*  69 */         if (this.thread.isAlive()) {
/*  70 */           throw new IneffectiveAbortionException();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     synchronized (this.SYNC_RESULT) {
/*  76 */       if (this.result instanceof Exception && this.thrown) {
/*  77 */         throw (Exception)this.result;
/*     */       }
/*  79 */       return this.result;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getResult(long maximalWait) throws Exception {
/*  85 */     return getResult(maximalWait, false);
/*     */   }
/*     */   
/*     */   public Object getResult() throws Exception {
/*  89 */     return getResult(0L, false);
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/*  93 */     synchronized (this.SYNC_THREAD) {
/*  94 */       return !this.thread.isAlive();
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void setProgressObserver(ProgressObserver observer) {
/*  99 */     if (this.thread != null)
/* 100 */       synchronized (this.SYNC_THREAD) {
/* 101 */         this.thread.setObserver(observer);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\thread\AsynchronousExecution.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */