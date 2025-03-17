/*    */ package com.eoos.gm.tis2web.swdl.client.msg;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.TreeMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class NotificationServer
/*    */ {
/* 11 */   private Logger log = Logger.getLogger(NotificationServer.class);
/*    */   private TreeMap notificationHandlers;
/*    */   
/*    */   public NotificationServer() {
/* 15 */     this.notificationHandlers = new TreeMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public void register(long type, NotificationHandler handler) {
/* 19 */     this.log.debug("NotificationServer.register type=" + type + " handler=" + handler);
/* 20 */     LinkedList<NotificationHandler> list = getOrCreateListForType(type);
/* 21 */     list.add(handler);
/*    */   }
/*    */   
/*    */   public void unregister(long type, NotificationHandler handler) {
/* 25 */     this.log.debug("NotificationServer.unregister type=" + type + " handler=" + handler);
/* 26 */     Long localType = Long.valueOf(type);
/* 27 */     LinkedList list = (LinkedList)this.notificationHandlers.get(localType);
/* 28 */     if (list != null) {
/* 29 */       Iterator<NotificationHandler> it = list.iterator();
/* 30 */       while (it.hasNext()) {
/* 31 */         if (it.next() == handler) {
/* 32 */           it.remove();
/*    */         }
/*    */       } 
/* 35 */       if (list.size() == 0) {
/* 36 */         unregister(handler);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unregister(NotificationHandler handler) {
/* 42 */     this.notificationHandlers.remove(handler);
/*    */   }
/*    */   
/*    */   public void sendNotification(Notification msg) {
/* 46 */     this.log.debug("NotificationServer.sendNotification IN: msg=" + msg.getType());
/* 47 */     Long type = Long.valueOf(msg.getType());
/* 48 */     LinkedList list = (LinkedList)this.notificationHandlers.get(type);
/* 49 */     if (list != null && list.size() > 0) {
/* 50 */       Iterator<NotificationHandler> it = list.iterator();
/* 51 */       while (it.hasNext()) {
/* 52 */         ((NotificationHandler)it.next()).handleNotification(msg);
/*    */       }
/*    */     } 
/* 55 */     this.log.debug("NotificationServer.sendNotification OUT: msg=" + msg.getType());
/*    */   }
/*    */   
/*    */   public void cleanup() {
/* 59 */     this.notificationHandlers.clear();
/*    */   }
/*    */   
/*    */   private LinkedList getOrCreateListForType(long type) {
/* 63 */     Long theType = Long.valueOf(type);
/* 64 */     LinkedList list = (LinkedList)this.notificationHandlers.get(theType);
/* 65 */     if (list == null) {
/* 66 */       list = new LinkedList();
/* 67 */       this.notificationHandlers.put(theType, list);
/*    */     } 
/* 69 */     return list;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\msg\NotificationServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */