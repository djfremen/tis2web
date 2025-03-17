/*    */ package com.eoos.gm.tis2web.si.client.starter;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
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
/* 21 */   private static Logger log = Logger.getLogger(WindowsStarter.class);
/*    */   
/*    */   private native boolean nativeIsStartAllowed();
/*    */   
/*    */   private native String[] nativeGetAvailableCommPorts();
/*    */   
/*    */   private native boolean nativeStartProcess(String paramString);
/*    */   
/*    */   private native boolean nativeSetEnvironment();
/*    */   
/*    */   public WindowsStarter() throws SecurityException, UnsatisfiedLinkError {
/* 32 */     System.loadLibrary("StarterBridge");
/*    */   }
/*    */   
/*    */   public boolean isStartAllowed() {
/* 36 */     synchronized (WindowsStarter.class) {
/* 37 */       boolean ret = nativeIsStartAllowed();
/* 38 */       if (ret == true) {
/* 39 */         log.info("Client start allowed");
/*    */       } else {
/* 41 */         log.info("Client start not allowed. A client instance running already");
/*    */       } 
/* 43 */       return ret;
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
/* 57 */       log.info("Available COM ports: " + ports);
/* 58 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 63 */     synchronized (WindowsStarter.class) {
/* 64 */       log.info("Start process");
/* 65 */       return nativeStartProcess(cmdLine);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 70 */     synchronized (WindowsStarter.class) {
/* 71 */       log.info("Set environment");
/* 72 */       return nativeSetEnvironment();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\starter\WindowsStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */