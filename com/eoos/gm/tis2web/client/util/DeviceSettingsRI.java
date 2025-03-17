/*    */ package com.eoos.gm.tis2web.client.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class DeviceSettingsRI
/*    */   implements DeviceSettings, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Object port;
/*    */   private Object baudrate;
/*    */   
/*    */   public DeviceSettingsRI(Object port, Object rate) {
/* 15 */     this.port = port;
/* 16 */     this.baudrate = rate;
/*    */   }
/*    */   
/*    */   public Object getBaudrate() {
/* 20 */     return this.baudrate;
/*    */   }
/*    */   
/*    */   public Object getPort() {
/* 24 */     return this.port;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 28 */     return String.valueOf(this.port) + "/" + String.valueOf(this.baudrate);
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 32 */     if (this == obj)
/* 33 */       return true; 
/* 34 */     if (obj instanceof DeviceSettingsRI) {
/* 35 */       DeviceSettingsRI other = (DeviceSettingsRI)obj;
/* 36 */       boolean ret = Util.equals(this.port, other.port);
/* 37 */       ret = (ret && Util.equals(this.baudrate, other.baudrate));
/* 38 */       return ret;
/*    */     } 
/* 40 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 45 */     int ret = DeviceSettingsRI.class.hashCode();
/* 46 */     ret = HashCalc.addHashCode(ret, this.port);
/* 47 */     ret = HashCalc.addHashCode(ret, this.baudrate);
/* 48 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\clien\\util\DeviceSettingsRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */