/*    */ package com.eoos.gm.tis2web.sas.server.implementation.tool.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridge;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.ToolBridgeImpl;
/*    */ import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SSAData;
/*    */ 
/*    */ 
/*    */ public class ToolTest
/*    */ {
/*    */   public static void main(String[] args) {
/* 12 */     ToolBridge tool = ToolBridgeImpl.getInstance("Tech2");
/*    */     
/* 14 */     String commIFace = tool.initialize("COM4,115200");
/* 15 */     sleep(2000);
/*    */     
/* 17 */     if (commIFace != null) {
/*    */ 
/*    */ 
/*    */       
/* 21 */       SSAData ssaData = tool.getSSAData();
/*    */       
/* 23 */       sleep(2000);
/*    */       
/* 25 */       if (ssaData != null) {
/* 26 */         ssaData.setStatus(new Byte((byte)0));
/* 27 */         ssaData.setHardwareKey(HardwareKey.getInstance("P000000009", "XXXXXXXXXXXXXXXXXXXX"));
/* 28 */         ssaData.setVersion(Integer.valueOf(4847));
/* 29 */         ssaData.setFreeShots(Integer.valueOf(16));
/* 30 */         tool.setSSAData(ssaData, null);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   static void sleep(int sleep) {
/*    */     try {
/* 37 */       Thread.sleep(2500L);
/* 38 */     } catch (InterruptedException e) {
/* 39 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\test\ToolTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */