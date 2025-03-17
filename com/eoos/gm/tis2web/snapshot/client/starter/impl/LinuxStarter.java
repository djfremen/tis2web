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
/*    */   public boolean isStartAllowed() {
/* 34 */     synchronized (StarterImpl.class) {
/* 35 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 40 */     synchronized (StarterImpl.class) {
/* 41 */       List<String> ports = new ArrayList<String>();
/* 42 */       for (int index = 0; index < 10; index++) {
/* 43 */         ports.add("COM" + index);
/*    */       }
/* 45 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 50 */     synchronized (StarterImpl.class) {
/* 51 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 56 */     synchronized (StarterImpl.class) {
/* 57 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTech2WinComPort() {
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\starter\impl\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */