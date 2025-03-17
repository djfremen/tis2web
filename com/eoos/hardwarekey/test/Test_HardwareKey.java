/*    */ package com.eoos.hardwarekey.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
/*    */ import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
/*    */ import com.eoos.hardwarekey.common.HardwareKey;
/*    */ import com.eoos.hardwarekey.impl.HardwareKeyImpl;
/*    */ 
/*    */ 
/*    */ public class Test_HardwareKey
/*    */ {
/*    */   public static void main(String[] args) {
/* 12 */     System.out.println("Start tests");
/*    */     
/* 14 */     HardwareKey hwk = HardwareKeyImpl.getInstance();
/*    */     
/* 16 */     System.out.println("1. Test Start *************************************************************");
/*    */     try {
/* 18 */       String hardwareKey = hwk.getHardwareKey();
/* 19 */       System.out.println("Hardware key: " + hardwareKey);
/* 20 */     } catch (SystemDriverNotInstalledException e) {
/* 21 */       System.out.println("System driver not installed exception: " + e.getMessage());
/* 22 */     } catch (UnavailableHardwareKeyException e) {
/* 23 */       System.out.println("Unavailable hardware key exception: " + e.getMessage());
/* 24 */     } catch (Exception e) {
/* 25 */       System.out.println("Unexpected exception: " + e.getMessage());
/*    */     } 
/* 27 */     System.out.println("1. Test End ***************************************************************\n");
/*    */     
/* 29 */     System.out.println("2. Test Start *************************************************************");
/*    */     try {
/* 31 */       boolean ret = hwk.isMigratedHardwareKey();
/* 32 */       System.out.println("Is migrated hardware key: " + ret);
/* 33 */     } catch (SystemDriverNotInstalledException e) {
/* 34 */       System.out.println("System driver not installed exception: " + e.getMessage());
/* 35 */     } catch (UnavailableHardwareKeyException e) {
/* 36 */       System.out.println("Unavailable hardware key exception: " + e.getMessage());
/* 37 */     } catch (Exception e) {
/* 38 */       System.out.println("Unexpected exception: " + e.getMessage());
/*    */     } 
/* 40 */     System.out.println("2. Test End ***************************************************************\n");
/*    */     
/* 42 */     System.out.println("3. Test Start *************************************************************");
/*    */     try {
/* 44 */       boolean ret = hwk.migrateHardwareKey();
/* 45 */       System.out.println("Migrate hardware key returned: " + ret);
/* 46 */     } catch (SystemDriverNotInstalledException e) {
/* 47 */       System.out.println("System driver not installed exception: " + e.getMessage());
/* 48 */     } catch (UnavailableHardwareKeyException e) {
/* 49 */       System.out.println("Unavailable hardware key exception: " + e.getMessage());
/* 50 */     } catch (Exception e) {
/* 51 */       System.out.println("Unexpected exception: " + e.getMessage());
/*    */     } 
/* 53 */     System.out.println("3. Test End ***************************************************************\n");
/*    */     
/* 55 */     System.out.println("4. Test Start *************************************************************");
/*    */     try {
/* 57 */       boolean ret = hwk.isMigratedHardwareKey();
/* 58 */       System.out.println("Is migrated hardware key: " + ret);
/* 59 */     } catch (SystemDriverNotInstalledException e) {
/* 60 */       System.out.println("System driver not installed exception: " + e.getMessage());
/* 61 */     } catch (UnavailableHardwareKeyException e) {
/* 62 */       System.out.println("Unavailable hardware key exception: " + e.getMessage());
/* 63 */     } catch (Exception e) {
/* 64 */       System.out.println("Unexpected exception: " + e.getMessage());
/*    */     } 
/* 66 */     System.out.println("4. Test End ***************************************************************\n");
/*    */     
/* 68 */     System.out.println("End tests");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\hardwarekey\test\Test_HardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */