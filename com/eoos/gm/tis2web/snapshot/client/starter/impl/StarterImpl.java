/*    */ package com.eoos.gm.tis2web.snapshot.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.snapshot.client.starter.Starter;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StarterImpl
/*    */   implements Starter
/*    */ {
/* 10 */   private static Starter instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Starter getInstance() {
/* 16 */     synchronized (StarterImpl.class) {
/* 17 */       if (instance == null) {
/*    */         try {
/* 19 */           String osSystem = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
/* 20 */           if (osSystem.indexOf("WINDOWS") != -1) {
/* 21 */             instance = new WindowsStarter();
/*    */           } else {
/* 23 */             instance = new LinuxStarter();
/*    */           } 
/* 25 */         } catch (Throwable e) {
/* 26 */           if (e instanceof UnsatisfiedLinkError) {
/* 27 */             System.out.println("Can't load SnapshotBridge library. " + e);
/*    */           }
/* 29 */           if (e instanceof SecurityException) {
/* 30 */             System.out.println("Can't load SnapshotBridge library. " + e);
/*    */           }
/*    */         } 
/*    */       }
/* 34 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 39 */     synchronized (StarterImpl.class) {
/* 40 */       return instance.isStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 45 */     synchronized (StarterImpl.class) {
/* 46 */       return instance.getAvailableCommPorts();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 51 */     synchronized (StarterImpl.class) {
/* 52 */       return instance.startProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 57 */     synchronized (StarterImpl.class) {
/* 58 */       return instance.setEnvironment();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 63 */     synchronized (StarterImpl.class) {
/* 64 */       return instance.getTech2WinComPort();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\starter\impl\StarterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */