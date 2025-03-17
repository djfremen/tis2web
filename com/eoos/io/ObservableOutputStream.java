/*    */ package com.eoos.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class ObservableOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   private OutputStreamCountWrapper backend;
/* 22 */   private Set observers = Collections.synchronizedSet(new HashSet());
/*    */   
/* 24 */   private int notificationStep = 1;
/*    */   
/*    */   public ObservableOutputStream(OutputStream os) {
/* 27 */     this(os, 1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObservableOutputStream(OutputStream os, int notificationStep) {
/* 34 */     if (!(os instanceof OutputStreamCountWrapper)) {
/* 35 */       this.backend = new OutputStreamCountWrapper(os);
/*    */     } else {
/* 37 */       this.backend = (OutputStreamCountWrapper)os;
/*    */     } 
/* 39 */     this.notificationStep = notificationStep;
/*    */   }
/*    */   
/*    */   public void addObserver(Observer observer) {
/* 43 */     this.observers.add(observer);
/*    */   }
/*    */   
/*    */   public void removeObserver(Observer observer) {
/* 47 */     this.observers.remove(observer);
/*    */   }
/*    */   
/*    */   private void notifyObservers(long currentCount) {
/* 51 */     synchronized (this.observers) {
/* 52 */       for (Iterator<Observer> iter = this.observers.iterator(); iter.hasNext(); ) {
/* 53 */         Observer observer = iter.next();
/* 54 */         if (currentCount != -1L) {
/* 55 */           observer.onWrote(currentCount); continue;
/*    */         } 
/* 57 */         observer.onEOF();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 64 */     this.backend.write(b);
/* 65 */     long count = this.backend.getCount().longValue();
/* 66 */     if (count % this.notificationStep == 0L) {
/* 67 */       notifyObservers(count);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 73 */     if (len == 0)
/*    */       return; 
/* 75 */     long oldCount = this.backend.getCount().longValue();
/* 76 */     this.backend.write(b, off, len);
/* 77 */     long newCount = oldCount + len;
/* 78 */     if (len >= this.notificationStep || oldCount / this.notificationStep < newCount / this.notificationStep) {
/* 79 */       notifyObservers(newCount);
/*    */     }
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 84 */     super.close();
/* 85 */     this.backend.close();
/* 86 */     notifyObservers(-1L);
/*    */   }
/*    */   
/*    */   public static interface Observer {
/*    */     void onWrote(long param1Long);
/*    */     
/*    */     void onEOF();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\io\ObservableOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */