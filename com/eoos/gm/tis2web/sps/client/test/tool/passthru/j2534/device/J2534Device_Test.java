/*    */ package com.eoos.gm.tis2web.sps.client.test.tool.passthru.j2534.device;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Device;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Error;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.J2534Version;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl.J2534DeviceImpl;
/*    */ import com.eoos.gm.tis2web.sps.client.tool.passthru.j2534.device.impl.J2534VersionImpl;
/*    */ 
/*    */ public class J2534Device_Test
/*    */ {
/*    */   public static void main(String[] args) {
/* 14 */     IStarter starter = Starter.getInstance();
/* 15 */     if (starter != null) {
/* 16 */       J2534Device dev = J2534DeviceImpl.getInstance();
/*    */ 
/*    */       
/* 19 */       if (dev.loadLibrary("02.02", "C:\\WINDOWS\\SYSTEM32\\CDPLS232.DLL") == true) {
/* 20 */         J2534Error err = null;
/*    */         
/* 22 */         System.out.println("Load library - OK");
/*    */         
/* 24 */         err = dev.passThruOpen();
/*    */         
/* 26 */         if (err.getErrorCode() == 0) {
/* 27 */           J2534VersionImpl j2534VersionImpl = new J2534VersionImpl();
/* 28 */           err = dev.passThruReadVersion((J2534Version)j2534VersionImpl);
/* 29 */           if (err.getErrorCode() == 0) {
/* 30 */             System.out.println("ReadVersion: OK\n");
/* 31 */             System.out.println("\n--- Version Info -----------------------------------------\n");
/* 32 */             System.out.println(j2534VersionImpl);
/* 33 */             System.out.println("\n----------------------------------------------------------\n");
/*    */           } else {
/* 35 */             System.out.println("ReadVersion: Failed - " + err.getErrorDescription());
/*    */           } 
/*    */         } else {
/* 38 */           System.out.println("Open: Failed - " + err.getErrorDescription());
/*    */         } 
/*    */         
/* 41 */         err = dev.passThruClose();
/*    */         
/* 43 */         if (err.getErrorCode() == 0) {
/* 44 */           System.out.println("Close: OK");
/*    */         } else {
/* 46 */           System.out.println("Close: Failed - " + err.getErrorDescription());
/*    */         } 
/*    */       } else {
/*    */         
/* 50 */         System.out.println("Load library - failed");
/*    */       } 
/*    */       
/* 53 */       dev.freeLibrary();
/*    */     } else {
/* 55 */       System.out.println("Invalid starter instance");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\tool\passthru\j2534\device\J2534Device_Test.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */