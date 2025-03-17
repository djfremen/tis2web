/*    */ package com.eoos.file;
/*    */ 
/*    */ import com.eoos.util.PeriodicTask;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModificationObserver
/*    */ {
/*    */   private PeriodicTask pt;
/*    */   
/*    */   public static ModificationObserver start(final File subject, long interval, final Callback callback) {
/* 19 */     ModificationObserver instance = new ModificationObserver();
/* 20 */     instance.pt = new PeriodicTask(new Runnable()
/*    */         {
/* 22 */           private long lastTS = subject.lastModified();
/*    */           
/*    */           public void run() {
/* 25 */             long newTS = subject.lastModified();
/* 26 */             if (newTS != this.lastTS) {
/* 27 */               callback.onModified();
/* 28 */               this.lastTS = newTS;
/*    */             } 
/*    */           }
/*    */         },  interval);
/*    */     
/* 33 */     instance.pt.start();
/* 34 */     return instance;
/*    */   }
/*    */   
/*    */   public void stop() {
/* 38 */     if (this.pt != null) {
/* 39 */       this.pt.stop();
/* 40 */       this.pt = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void onModified();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\file\ModificationObserver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */