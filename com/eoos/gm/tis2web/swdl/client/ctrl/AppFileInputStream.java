/*    */ package com.eoos.gm.tis2web.swdl.client.ctrl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.client.msg.Notification;
/*    */ import com.eoos.gm.tis2web.swdl.client.msg.SDEvent;
/*    */ import com.eoos.gm.tis2web.swdl.client.msg.SDNotificationServer;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AppFileInputStream
/*    */ {
/*    */   protected InputStream is;
/* 20 */   private int length = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public AppFileInputStream(InputStream is, int length) {
/* 25 */     this.is = is;
/* 26 */     this.length = length;
/*    */   }
/*    */   
/*    */   public byte[] read() throws Exception {
/* 30 */     int bytesTotalRead = 0;
/*    */     
/* 32 */     if (this.length <= 0 || this.is == null) {
/* 33 */       throw new IOException();
/*    */     }
/*    */     
/* 36 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 37 */     byte[] buffer = new byte[Math.max(10240, this.length)];
/* 38 */     int count = 0;
/* 39 */     while ((count = this.is.read(buffer)) != -1) {
/* 40 */       baos.write(buffer, 0, count);
/* 41 */       bytesTotalRead += count;
/* 42 */       SDNotificationServer.getInstance().sendNotification((Notification)new SDEvent(27L, Integer.valueOf(bytesTotalRead)));
/*    */     } 
/* 44 */     return baos.toByteArray();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\ctrl\AppFileInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */