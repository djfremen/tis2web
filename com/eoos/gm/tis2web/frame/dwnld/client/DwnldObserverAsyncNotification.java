/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ 
/*    */ public class DwnldObserverAsyncNotification
/*    */   implements IDownloadService.DownloadObserver {
/*    */   private IDownloadService.DownloadObserver delegate;
/*    */   
/*    */   private DwnldObserverAsyncNotification(IDownloadService.DownloadObserver observer) {
/* 13 */     this.delegate = observer;
/*    */   }
/*    */   
/*    */   public static IDownloadService.DownloadObserver create(IDownloadService.DownloadObserver observer) {
/* 17 */     if (observer != null && !(observer instanceof DwnldObserverAsyncNotification)) {
/* 18 */       return new DwnldObserverAsyncNotification(observer);
/*    */     }
/* 20 */     return observer;
/*    */   }
/*    */ 
/*    */   
/*    */   private void asyncExecute(final Runnable runnable) {
/* 25 */     (new Thread("async notification") {
/*    */         public void run() {
/* 27 */           runnable.run();
/*    */         }
/*    */       }).start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onError(final IDownloadPackage pkg, final Exception e) {
/* 34 */     asyncExecute(new Runnable()
/*    */         {
/*    */           public void run() {
/* 37 */             DwnldObserverAsyncNotification.this.delegate.onError(pkg, e);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onFinished(final IDownloadPackage pkg) {
/* 44 */     asyncExecute(new Runnable()
/*    */         {
/*    */           public void run() {
/* 47 */             DwnldObserverAsyncNotification.this.delegate.onFinished(pkg);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onFinished(final IDownloadPackage pkg, final IDownloadUnit unit) {
/* 54 */     asyncExecute(new Runnable()
/*    */         {
/*    */           public void run() {
/* 57 */             DwnldObserverAsyncNotification.this.delegate.onFinished(pkg, unit);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onFinished(final IDownloadPackage pkg, final IDownloadUnit unit, final IDownloadFile file) {
/* 64 */     asyncExecute(new Runnable()
/*    */         {
/*    */           public void run() {
/* 67 */             DwnldObserverAsyncNotification.this.delegate.onFinished(pkg, unit, file);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\DwnldObserverAsyncNotification.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */