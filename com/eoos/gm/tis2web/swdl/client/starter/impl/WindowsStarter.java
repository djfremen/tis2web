/*    */ package com.eoos.gm.tis2web.swdl.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.swdl.client.starter.IStarter;
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
/*    */ public class WindowsStarter
/*    */   implements IStarter
/*    */ {
/*    */   private static final String LIBRARY = "StarterBridge";
/*    */   
/*    */   private native boolean nativeIsStartAllowed();
/*    */   
/*    */   private native String[] nativeGetAvailableCommPorts();
/*    */   
/*    */   private native boolean nativeStartProcess(String paramString);
/*    */   
/*    */   private native boolean nativeSetEnvironment();
/*    */   
/*    */   private native String nativeGetTech2WinComPort();
/*    */   
/*    */   public WindowsStarter() throws SecurityException, UnsatisfiedLinkError {
/* 34 */     System.loadLibrary("StarterBridge");
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 38 */     synchronized (WindowsStarter.class) {
/* 39 */       return nativeIsStartAllowed();
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 44 */     synchronized (WindowsStarter.class) {
/* 45 */       List<String> ports = null;
/* 46 */       String[] portsArray = nativeGetAvailableCommPorts();
/* 47 */       if (portsArray != null) {
/* 48 */         ports = new ArrayList<String>();
/* 49 */         for (int index = 0; index < portsArray.length; index++) {
/* 50 */           ports.add(portsArray[index]);
/*    */         }
/*    */       } 
/* 53 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 58 */     synchronized (WindowsStarter.class) {
/* 59 */       return nativeStartProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 64 */     synchronized (WindowsStarter.class) {
/* 65 */       return nativeSetEnvironment();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 70 */     synchronized (WindowsStarter.class) {
/* 71 */       return nativeGetTech2WinComPort();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\starter\impl\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */