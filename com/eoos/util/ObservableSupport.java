/*    */ package com.eoos.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObservableSupport
/*    */ {
/* 21 */   protected List observers = new LinkedList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized boolean addObserver(Object observer) {
/* 28 */     if (!this.observers.contains(observer)) {
/* 29 */       return this.observers.add(observer);
/*    */     }
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized boolean removeObserver(Object observer) {
/* 36 */     return this.observers.remove(observer);
/*    */   }
/*    */   
/*    */   public synchronized void notify(Notification notification) {
/* 40 */     Iterator iter = this.observers.iterator();
/* 41 */     while (iter.hasNext())
/* 42 */       notification.notify(iter.next()); 
/*    */   }
/*    */   
/*    */   public static interface Notification {
/*    */     void notify(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\ObservableSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */