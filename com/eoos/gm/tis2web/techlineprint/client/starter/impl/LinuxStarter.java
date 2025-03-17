/*    */ package com.eoos.gm.tis2web.techlineprint.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.techlineprint.client.starter.Starter;
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
/*    */ public class LinuxStarter
/*    */   implements Starter
/*    */ {
/*    */   public boolean isStartAllowed() {
/* 29 */     synchronized (LinuxStarter.class) {
/* 30 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 35 */     synchronized (LinuxStarter.class) {
/* 36 */       List<String> ports = new ArrayList<String>();
/* 37 */       for (int index = 0; index < 10; index++) {
/* 38 */         ports.add("COM" + index);
/*    */       }
/* 40 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 45 */     return null;
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 49 */     synchronized (LinuxStarter.class) {
/* 50 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 55 */     synchronized (LinuxStarter.class) {
/* 56 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\starter\impl\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */