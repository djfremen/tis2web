/*    */ package com.eoos.gm.tis2web.sps.client.tool.navigation.impl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RIMParams
/*    */ {
/* 10 */   Integer deviceID = null;
/* 11 */   Integer protocol = null;
/* 12 */   Integer commParam = null;
/*    */   
/*    */   public RIMParams(int deviceID, int protocol, int commParam) {
/* 15 */     this.deviceID = Integer.valueOf(deviceID);
/* 16 */     this.protocol = Integer.valueOf(protocol);
/* 17 */     this.commParam = Integer.valueOf(commParam);
/*    */   }
/*    */   
/*    */   public Integer getDeviceID() {
/* 21 */     return this.deviceID;
/*    */   }
/*    */   
/*    */   public Integer getProtocol() {
/* 25 */     return this.protocol;
/*    */   }
/*    */   
/*    */   public Integer getCommParam() {
/* 29 */     return this.commParam;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\navigation\impl\RIMParams.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */