/*    */ package com.eoos.gm.tis2web.tech2view.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.tech2view.client.starter.Starter;
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
/* 31 */     System.loadLibrary("StarterBridge");
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 35 */     synchronized (WindowsStarter.class) {
/* 36 */       return nativeIsStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 41 */     synchronized (WindowsStarter.class) {
/* 42 */       List<String> ports = null;
/* 43 */       String[] portsArray = nativeGetAvailableCommPorts();
/* 44 */       if (portsArray != null) {
/* 45 */         ports = new ArrayList<String>();
/* 46 */         for (int index = 0; index < portsArray.length; index++) {
/* 47 */           ports.add(portsArray[index]);
/*    */         }
/*    */       } 
/* 50 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 55 */     synchronized (WindowsStarter.class) {
/* 56 */       return nativeGetTech2WinComPort();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 61 */     synchronized (WindowsStarter.class) {
/* 62 */       return nativeStartProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 67 */     synchronized (WindowsStarter.class) {
/* 68 */       return nativeSetEnvironment();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\starter\impl\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */