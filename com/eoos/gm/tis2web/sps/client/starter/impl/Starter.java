/*    */ package com.eoos.gm.tis2web.sps.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Installer;
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class Starter
/*    */   implements IStarter
/*    */ {
/* 12 */   private static IStarter instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IStarter getInstance() {
/* 18 */     synchronized (Starter.class) {
/* 19 */       if (instance == null) {
/*    */         try {
/* 21 */           String osSystem = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
/* 22 */           if (osSystem.indexOf("WINDOWS") != -1) {
/* 23 */             instance = new WindowsStarter();
/*    */           } else {
/* 25 */             instance = new LinuxStarter();
/*    */           } 
/* 27 */         } catch (Throwable e) {
/* 28 */           if (e instanceof UnsatisfiedLinkError) {
/* 29 */             System.out.println("Can't load StarterBridge library. " + e);
/*    */           }
/* 31 */           if (e instanceof SecurityException) {
/* 32 */             System.out.println("Can't load StarterBridge library. " + e);
/*    */           }
/*    */         } 
/*    */       }
/* 36 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 41 */     synchronized (Starter.class) {
/* 42 */       return instance.isStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 47 */     synchronized (Starter.class) {
/* 48 */       return instance.getAvailableCommPorts();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 53 */     synchronized (WindowsStarter.class) {
/* 54 */       return instance.getTech2WinComPort();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 59 */     synchronized (Starter.class) {
/* 60 */       return instance.startProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 65 */     synchronized (Starter.class) {
/* 66 */       return instance.setEnvironment();
/*    */     } 
/*    */   }
/*    */   
/*    */   public Integer install(File path, Installer installer) {
/* 71 */     synchronized (Starter.class) {
/* 72 */       return instance.install(path, installer);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\starter\impl\Starter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */