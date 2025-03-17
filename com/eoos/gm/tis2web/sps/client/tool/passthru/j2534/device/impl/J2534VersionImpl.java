/*    */ package com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Version;
/*    */ 
/*    */ public class J2534VersionImpl implements J2534Version {
/*  6 */   String firmwareVersion = null;
/*  7 */   String dllVersion = null;
/*  8 */   String apiVersion = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFirmwareVersion(String firmwareVersion) {
/* 14 */     this.firmwareVersion = firmwareVersion;
/*    */   }
/*    */   
/*    */   public String getFirmwareVersion() {
/* 18 */     return this.firmwareVersion;
/*    */   }
/*    */   
/*    */   public void setDllVersion(String dllVersion) {
/* 22 */     this.dllVersion = dllVersion;
/*    */   }
/*    */   
/*    */   public String getDllVersion() {
/* 26 */     return this.dllVersion;
/*    */   }
/*    */   
/*    */   public void setApiVersion(String apiVersion) {
/* 30 */     this.apiVersion = apiVersion;
/*    */   }
/*    */   
/*    */   public String getApiVersion() {
/* 34 */     return this.apiVersion;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 38 */     StringBuffer buffer = new StringBuffer();
/* 39 */     buffer.append("Firmware Version: " + this.firmwareVersion + "\n");
/* 40 */     buffer.append("DLL Version: " + this.dllVersion + "\n");
/* 41 */     buffer.append("API Version: " + this.apiVersion);
/* 42 */     return buffer.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\passthru\j2534\device\impl\J2534VersionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */