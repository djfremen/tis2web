/*    */ package com.eoos.gm.tis2web.swdl.common.domain.device;
/*    */ 
/*    */ import java.io.ObjectStreamException;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Device
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   public static final Device TECH1 = new Device("Tech 1", new BaudRate[] { new BaudRate("19200"), new BaudRate("9600") });
/*    */   
/* 19 */   public static final Device TECH2 = new Device("Tech 2", new BaudRate[] { new BaudRate("115200"), new BaudRate("57600"), new BaudRate("38400"), new BaudRate("19200"), new BaudRate("9600") });
/*    */   
/* 21 */   public static final Device[] DOMAIN = new Device[] { TECH1, TECH2 };
/*    */   
/* 23 */   private String description = null;
/*    */   
/* 25 */   private BaudRate[] baudRates = null;
/*    */ 
/*    */   
/*    */   private Device(String description, BaudRate[] baudRates) {
/* 29 */     this.description = description;
/* 30 */     this.baudRates = baudRates;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 34 */     return this.description;
/*    */   }
/*    */   
/*    */   public BaudRate[] getBaudRates() {
/* 38 */     return this.baudRates;
/*    */   }
/*    */   
/*    */   public static Device getTech(String description) {
/* 42 */     for (int i = 0; i < DOMAIN.length; i++) {
/* 43 */       Device device = DOMAIN[i];
/* 44 */       if (device.description.equalsIgnoreCase(description)) {
/* 45 */         return device;
/*    */       }
/*    */     } 
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     return getDescription();
/*    */   }
/*    */   
/*    */   private Object readResolve() throws ObjectStreamException {
/* 56 */     return getTech(this.description);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\device\Device.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */