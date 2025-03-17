/*    */ package com.eoos.gm.tis2web.swdl.client.msg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SDNotificationServer
/*    */   extends NotificationServer
/*    */ {
/*    */   public static synchronized SDNotificationServer getInstance() {
/* 10 */     if (instance == null) {
/* 11 */       instance = new SDNotificationServer();
/*    */     }
/* 13 */     return instance;
/*    */   }
/*    */   
/*    */   public static void destroy() {
/* 17 */     instance = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   private static SDNotificationServer instance = null;
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\msg\SDNotificationServer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */