/*    */ package com.eoos.gm.tis2web.sas.client.system.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.system.starter.Starter;
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
/*    */   private native String nativeGetLibraryLocation();
/*    */   
/*    */   public WindowsStarter() throws SecurityException, UnsatisfiedLinkError {
/* 38 */     System.loadLibrary("StarterBridge");
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 42 */     synchronized (WindowsStarter.class) {
/* 43 */       return nativeIsStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 48 */     synchronized (WindowsStarter.class) {
/* 49 */       List<String> ports = null;
/* 50 */       String[] portsArray = nativeGetAvailableCommPorts();
/* 51 */       if (portsArray != null) {
/* 52 */         ports = new ArrayList<String>();
/* 53 */         for (int index = 0; index < portsArray.length; index++) {
/* 54 */           ports.add(portsArray[index]);
/*    */         }
/*    */       } 
/* 57 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 62 */     synchronized (WindowsStarter.class) {
/* 63 */       return nativeGetTech2WinComPort();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 68 */     synchronized (WindowsStarter.class) {
/* 69 */       return nativeStartProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 74 */     synchronized (WindowsStarter.class) {
/* 75 */       return nativeSetEnvironment();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLibraryLocation() {
/* 80 */     synchronized (WindowsStarter.class) {
/* 81 */       return nativeGetLibraryLocation();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\starter\impl\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */