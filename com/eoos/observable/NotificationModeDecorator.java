/*    */ package com.eoos.observable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotificationModeDecorator
/*    */   implements Notification
/*    */ {
/*    */   private Notification notification;
/*    */   
/*    */   public NotificationModeDecorator(Notification notification) {
/* 12 */     this.notification = notification;
/*    */   }
/*    */   
/*    */   public void notify(Object observer) {
/* 16 */     notify(observer, IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY);
/*    */   }
/*    */   
/*    */   public void notify(final Object observer, IObservableSupport.Mode mode) {
/* 20 */     Runnable runnable = new Runnable() {
/*    */         public void run() {
/* 22 */           NotificationModeDecorator.this.notification.notify(observer);
/*    */         }
/*    */       };
/*    */     
/* 26 */     if (mode == IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY) {
/* 27 */       Thread t = new Thread(runnable);
/* 28 */       t.start();
/*    */     } else {
/* 30 */       runnable.run();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\observable\NotificationModeDecorator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */