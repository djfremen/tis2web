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
/*    */ public class LinuxStarter
/*    */   implements Starter
/*    */ {
/*    */   private native String nativeGetLibraryLocation();
/*    */   
/*    */   public boolean isStartAllowed() {
/* 40 */     synchronized (LinuxStarter.class) {
/* 41 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 46 */     synchronized (LinuxStarter.class) {
/* 47 */       List<String> ports = new ArrayList<String>();
/* 48 */       for (int index = 0; index < 10; index++) {
/* 49 */         ports.add("COM" + index);
/*    */       }
/* 51 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 56 */     synchronized (LinuxStarter.class) {
/* 57 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 62 */     synchronized (LinuxStarter.class) {
/* 63 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getLibraryLocation() {
/* 68 */     synchronized (LinuxStarter.class) {
/* 69 */       return nativeGetLibraryLocation();
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\starter\impl\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */