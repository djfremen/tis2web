/*    */ package com.eoos.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObservableInputStream
/*    */   extends InputStream
/*    */ {
/*    */   private InputStreamCountWrapper backend;
/* 22 */   private Set observers = Collections.synchronizedSet(new HashSet());
/*    */   
/* 24 */   private int notificationStep = 1;
/*    */   
/*    */   public ObservableInputStream(InputStream is) {
/* 27 */     this(is, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ObservableInputStream(InputStream is, int notificationStep) {
/* 33 */     if (!(is instanceof InputStreamCountWrapper)) {
/* 34 */       this.backend = new InputStreamCountWrapper(is);
/*    */     } else {
/* 36 */       this.backend = (InputStreamCountWrapper)is;
/*    */     } 
/* 38 */     this.notificationStep = notificationStep;
/*    */   }
/*    */   
/*    */   public void addObserver(Observer observer) {
/* 42 */     this.observers.add(observer);
/*    */   }
/*    */   
/*    */   public void removeObserver(Observer observer) {
/* 46 */     this.observers.remove(observer);
/*    */   }
/*    */   
/*    */   private void notifyObservers(long currentCount) {
/* 50 */     synchronized (this.observers) {
/* 51 */       for (Iterator<Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/* 52 */         Observer observer = iter.next();
/* 53 */         if (currentCount != -1L) {
/* 54 */           observer.onRead(currentCount); continue;
/*    */         } 
/* 56 */         observer.onEOF();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 63 */     int retValue = this.backend.read();
/* 64 */     long count = this.backend.getCount().longValue();
/* 65 */     if (count % this.notificationStep == 0L || retValue == -1) {
/* 66 */       notifyObservers(count);
/*    */     }
/* 68 */     if (retValue == -1) {
/* 69 */       notifyObservers(-1L);
/*    */     }
/* 71 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int off, int len) throws IOException {
/* 76 */     long oldCount = this.backend.getCount().longValue();
/* 77 */     int ret = this.backend.read(b, off, len);
/* 78 */     if (ret == -1) {
/* 79 */       notifyObservers(-1L);
/*    */     } else {
/* 81 */       long newCount = oldCount + ret;
/* 82 */       if (ret >= this.notificationStep || oldCount / this.notificationStep < newCount / this.notificationStep) {
/* 83 */         notifyObservers(newCount);
/*    */       }
/*    */     } 
/* 86 */     return ret;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 90 */     super.close();
/* 91 */     this.backend.close();
/* 92 */     notifyObservers(-1L);
/*    */   }
/*    */   
/*    */   public static interface Observer {
/*    */     void onRead(long param1Long);
/*    */     
/*    */     void onEOF();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\ObservableInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */