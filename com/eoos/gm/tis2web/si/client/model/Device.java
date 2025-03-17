/*    */ package com.eoos.gm.tis2web.si.client.model;
/*    */ 
/*    */ import com.eoos.gm.tis2web.si.client.ClientUtil;
/*    */ import com.eoos.gm.tis2web.si.client.device.IDevice;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public enum Device
/*    */ {
/* 10 */   TECH31, TECH32;
/*    */ 
/*    */   
/*    */   public static List<Baudrate> BAUDRATES;
/*    */ 
/*    */   
/*    */   public int getScreenCount() {
/* 17 */     return (this == TECH31) ? 15 : 10;
/*    */   }
/*    */   
/*    */   public void storeDeviceScreen(Port port, Baudrate rate, int screenNumber, byte[] data) throws Exception {
/* 21 */     IDevice delegate = com.eoos.gm.tis2web.si.client.device.Device.getDevice(this);
/*    */     try {
/* 23 */       delegate.initialize(port.toString() + "," + rate.toString());
/* 24 */       if (!delegate.uploadData(data, (short)screenNumber)) {
/* 25 */         throw new Exception("failed to upload data");
/*    */       }
/*    */     } finally {
/* 28 */       com.eoos.gm.tis2web.si.client.device.Device.discardDevice(delegate);
/*    */     } 
/*    */   }
/*    */   static {
/* 32 */     BAUDRATES = new LinkedList<Baudrate>();
/*    */     
/* 34 */     String[] rates = { "115200", "57600", "38400", "19200", "9600" };
/* 35 */     for (int i = 0; i < rates.length; i++) {
/* 36 */       BAUDRATES.add(ClientUtil.toBaudrate(rates[i]));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Baudrate> getBaudrates() {
/* 41 */     return BAUDRATES;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\model\Device.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */