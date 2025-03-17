/*    */ package com.eoos.gm.tis2web.sps.client.test.vci;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*    */ import com.eoos.gm.tis2web.sps.client.vci.VCICalculation;
/*    */ import com.eoos.gm.tis2web.sps.client.vci.impl.VCICalculationImpl;
/*    */ 
/*    */ public class Test_VCI
/*    */ {
/*    */   public static void main(String[] args) {
/* 11 */     IStarter starter = Starter.getInstance();
/* 12 */     if (starter != null) {
/* 13 */       if (starter.isStartAllowed() == true) {
/* 14 */         VCICalculation vci = VCICalculationImpl.getInstance();
/* 15 */         if (vci != null) {
/* 16 */           Integer calculatedVCI = vci.calculateVCI("C:\\Temp\\vci\\NAO-ArchivesAndPartFiles\\1160242342343243.zip");
/* 17 */           System.out.println("Calculated VCI code: " + calculatedVCI.toString());
/*    */         } else {
/* 19 */           System.out.println("Could not get VCI calculation instance");
/*    */         } 
/*    */       } 
/*    */     } else {
/* 23 */       System.out.println("Invalid starter instance");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\vci\Test_VCI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */