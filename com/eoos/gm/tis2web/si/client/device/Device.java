/*    */ package com.eoos.gm.tis2web.si.client.device;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class Device
/*    */   implements IDevice
/*    */ {
/* 19 */   private static Logger log = Logger.getLogger(Device.class);
/*    */   
/*    */   private static final String LIBRARY = "sitoolbridge";
/*    */   
/*    */   private static final String DEVICE_TECH31 = "tech31";
/*    */   
/*    */   private static final String DEVICE_TECH32 = "tech32";
/*    */   
/* 27 */   public static Map<String, Device> devices = new HashMap<String, Device>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   private String name = null; private native String nativeGetDevice(String paramString);
/*    */   private native boolean nativeDiscardDevice(String paramString);
/*    */   public static IDevice getDevice(com.eoos.gm.tis2web.si.client.model.Device dev) {
/* 40 */     synchronized (Device.class) {
/* 41 */       Device device = devices.get(deviceNameFromEnum(dev));
/* 42 */       if (device == null) {
/*    */         try {
/* 44 */           System.loadLibrary("sitoolbridge");
/* 45 */           device = new Device();
/* 46 */           device.name = device.nativeGetDevice(deviceNameFromEnum(dev));
/* 47 */           if (device.name != null && device.name.length() > 0) {
/* 48 */             devices.put(device.name, device);
/*    */           } else {
/* 50 */             log.error("Could not get device");
/*    */           } 
/* 52 */         } catch (Exception e) {
/* 53 */           log.error("Could not load library" + e);
/*    */         } 
/*    */       }
/* 56 */       return device;
/*    */     } 
/*    */   } private native String nativeInitialize(String paramString1, String paramString2);
/*    */   private native boolean nativeUploadData(String paramString, byte[] paramArrayOfbyte, short paramShort);
/*    */   public static void discardDevice(IDevice device) {
/* 61 */     synchronized (Device.class) {
/* 62 */       Device dev = (Device)device;
/* 63 */       if (!dev.nativeDiscardDevice(dev.name)) {
/* 64 */         log.error("Could not discard device");
/*    */       }
/* 66 */       devices.remove(dev.name);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String initialize(String initParams) {
/* 71 */     synchronized (Device.class) {
/* 72 */       return nativeInitialize(this.name, initParams);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean uploadData(byte[] data, short screen) {
/* 77 */     synchronized (Device.class) {
/* 78 */       return nativeUploadData(this.name, data, screen);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String deviceNameFromEnum(com.eoos.gm.tis2web.si.client.model.Device device) {
/* 83 */     if (device == com.eoos.gm.tis2web.si.client.model.Device.TECH31) {
/* 84 */       return "tech31";
/*    */     }
/* 86 */     if (device == com.eoos.gm.tis2web.si.client.model.Device.TECH32) {
/* 87 */       return "tech32";
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\device\Device.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */