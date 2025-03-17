/*    */ package com.eoos.gm.tis2web.sps.client.test.starter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Installer;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ public class Starter_InstallationTest
/*    */ {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 13 */       IStarter starter = Starter.getInstance();
/*    */       
/* 15 */       if (starter != null) {
/*    */         
/* 17 */         String installerType = "MSI";
/*    */         
/* 19 */         if (installerType.compareToIgnoreCase("MSI") == 0) {
/* 20 */           File path = new File("c:\\temp\\j2534\\installation\\tech2");
/*    */           
/* 22 */           Integer retCode = starter.install(path, new Installer() {
/*    */                 private static final long serialVersionUID = 1L;
/*    */                 
/*    */                 public Installer.Type getType() {
/* 26 */                   return Installer.TYPE_MSI;
/*    */                 }
/*    */                 
/*    */                 public byte[] getData() {
/* 30 */                   return null;
/*    */                 }
/*    */                 
/*    */                 public String getFilename() {
/* 34 */                   return "Tech2 SAE J2534 DLL.msi";
/*    */                 }
/*    */                 
/*    */                 public String getCmdLineParams() {
/* 38 */                   return "/qb";
/*    */                 }
/*    */                 
/*    */                 public byte[] getChecksum() {
/* 42 */                   return null;
/*    */                 }
/*    */                 
/*    */                 public Installer.InstallStatus getInstallStatus(Integer returnCode) {
/* 46 */                   return null;
/*    */                 }
/*    */               });
/*    */           
/* 50 */           System.out.println("Installation return code: " + retCode);
/*    */         } else {
/*    */           
/* 53 */           File path = new File("c:\\temp\\j2534\\installation\\cardaq");
/*    */           
/* 55 */           Integer retCode = starter.install(path, new Installer() {
/*    */                 private static final long serialVersionUID = 1L;
/*    */                 
/*    */                 public Installer.Type getType() {
/* 59 */                   return Installer.TYPE_EXE;
/*    */                 }
/*    */                 
/*    */                 public byte[] getData() {
/* 63 */                   return null;
/*    */                 }
/*    */                 
/*    */                 public String getFilename() {
/* 67 */                   return "CarDAQ_Plus_Installer_v1.7.6.exe";
/*    */                 }
/*    */                 
/*    */                 public String getCmdLineParams() {
/* 71 */                   return null;
/*    */                 }
/*    */                 
/*    */                 public byte[] getChecksum() {
/* 75 */                   return null;
/*    */                 }
/*    */                 
/*    */                 public Installer.InstallStatus getInstallStatus(Integer returnCode) {
/* 79 */                   return null;
/*    */                 }
/*    */               });
/*    */           
/* 83 */           System.out.println("Installation return code: " + retCode);
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 88 */         System.out.println("Reference to object is NULL");
/*    */       }
/*    */     
/* 91 */     } catch (Exception e) {
/* 92 */       System.out.println("Exception in \"main\" function" + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\starter\Starter_InstallationTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */