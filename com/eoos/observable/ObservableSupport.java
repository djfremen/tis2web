/*    */ package com.eoos.observable;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ObservableSupport
/*    */   implements IObservableSupport
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(ObservableSupport.class);
/*    */   
/* 18 */   private Set observers = Collections.synchronizedSet(new LinkedHashSet());
/*    */   
/*    */   private ThreadLocal reentranceCheck;
/*    */ 
/*    */   
/*    */   public void addObserver(final Object observer) {
/* 24 */     if (this.reentranceCheck.get() != null) {
/* 25 */       log.warn("detected reentrance, starting new thread (async) for modification, stack trace:", new Throwable());
/* 26 */       Util.executeAsynchronous(new Runnable()
/*    */           {
/*    */             public void run() {
/* 29 */               ObservableSupport.this.addObserver(observer);
/*    */             }
/*    */           });
/*    */     } else {
/* 33 */       this.observers.add(observer);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeObserver(final Object observer) {
/* 38 */     if (this.reentranceCheck.get() != null) {
/* 39 */       log.warn("detected reentrance, starting new thread (async) for modification, stack trace:", new Throwable());
/* 40 */       Util.executeAsynchronous(new Runnable()
/*    */           {
/*    */             public void run() {
/* 43 */               ObservableSupport.this.removeObserver(observer);
/*    */             }
/*    */           });
/*    */     } else {
/* 47 */       this.observers.remove(observer);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void notifyObservers(Notification notification) {
/* 52 */     notifyObservers(notification, IObservableSupport.Mode.SYNCHRONOUS_NOTIFY);
/*    */   }
/*    */   public ObservableSupport() {
/* 55 */     this.reentranceCheck = new ThreadLocal();
/*    */   }
/*    */   public void notifyObservers(final Notification notification, IObservableSupport.Mode notificationMode) {
/* 58 */     Runnable runnable = new Runnable()
/*    */       {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         public void run()
/*    */         {
/* 66 */           synchronized (ObservableSupport.this.observers) {
/* 67 */             ObservableSupport.this.reentranceCheck.set(Boolean.TRUE);
/* 68 */             for (Iterator iter = ObservableSupport.this.observers.iterator(); iter.hasNext(); ) {
/* 69 */               Object observer = iter.next();
/*    */               try {
/* 71 */                 notification.notify(observer);
/* 72 */               } catch (Throwable t) {
/* 73 */                 ObservableSupport.log.warn("unable to notify observer: " + String.valueOf(observer) + " , exception: " + String.valueOf(t) + " - ignoring");
/*    */               } 
/*    */             } 
/* 76 */             ObservableSupport.this.reentranceCheck.set(null);
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 81 */     if (notificationMode == IObservableSupport.Mode.ASYNCHRONOUS_NOTIFY) {
/* 82 */       Util.executeAsynchronous(runnable);
/*    */     } else {
/* 84 */       runnable.run();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\observable\ObservableSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */