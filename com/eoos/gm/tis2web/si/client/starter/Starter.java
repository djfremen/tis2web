/*    */ package com.eoos.gm.tis2web.si.client.starter;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class Starter
/*    */   implements IStarter
/*    */ {
/* 10 */   private static IStarter instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static IStarter getInstance() {
/* 16 */     synchronized (Starter.class) {
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
/* 27 */             System.out.println("Could not load StarterBridge library. " + e);
/*    */           }
/* 29 */           if (e instanceof SecurityException) {
/* 30 */             System.out.println("Could not load StarterBridge library. " + e);
/*    */           }
/* 32 */           throw Util.toRuntimeException(e);
/*    */         } 
/*    */       }
/* 35 */       return instance;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 40 */     synchronized (Starter.class) {
/* 41 */       return instance.isStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 46 */     synchronized (Starter.class) {
/* 47 */       return instance.getAvailableCommPorts();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 52 */     synchronized (Starter.class) {
/* 53 */       return instance.startProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 58 */     synchronized (Starter.class) {
/* 59 */       return instance.setEnvironment();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\starter\Starter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */