/*    */ package com.eoos.gm.tis2web.snapshot.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.snapshot.client.starter.Starter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WindowsStarter
/*    */   implements Starter
/*    */ {
/*    */   private static final String LIBRARY = "StarterBridge";
/*    */   
/*    */   private native boolean nativeIsStartAllowed();
/*    */   
/*    */   private native String[] nativeGetAvailableCommPorts();
/*    */   
/*    */   private native String nativeGetTech2WinComPort();
/*    */   
/*    */   private native boolean nativeStartProcess(String paramString);
/*    */   
/*    */   private native boolean nativeSetEnvironment();
/*    */   
/*    */   public WindowsStarter() throws SecurityException, UnsatisfiedLinkError {
/* 35 */     System.loadLibrary("StarterBridge");
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 39 */     synchronized (StarterImpl.class) {
/* 40 */       return nativeIsStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 45 */     synchronized (StarterImpl.class) {
/* 46 */       List<String> ports = null;
/* 47 */       String[] portsArray = nativeGetAvailableCommPorts();
/* 48 */       if (portsArray != null) {
/* 49 */         ports = new ArrayList<String>();
/* 50 */         for (int index = 0; index < portsArray.length; index++) {
/* 51 */           ports.add(portsArray[index]);
/*    */         }
/*    */       } 
/* 54 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 59 */     synchronized (StarterImpl.class) {
/* 60 */       return nativeStartProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 65 */     synchronized (StarterImpl.class) {
/* 66 */       return nativeSetEnvironment();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 71 */     synchronized (StarterImpl.class) {
/* 72 */       return nativeGetTech2WinComPort();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\starter\impl\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */