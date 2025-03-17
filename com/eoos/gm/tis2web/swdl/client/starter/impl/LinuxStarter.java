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
/*    */   implements IStarter
/*    */ {
/*    */   public boolean isStartAllowed() {
/* 36 */     synchronized (LinuxStarter.class) {
/* 37 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 42 */     synchronized (LinuxStarter.class) {
/* 43 */       List<String> ports = new ArrayList<String>();
/* 44 */       for (int index = 0; index < 10; index++) {
/* 45 */         ports.add("COM" + index);
/*    */       }
/* 47 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 52 */     synchronized (LinuxStarter.class) {
/* 53 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 58 */     synchronized (LinuxStarter.class) {
/* 59 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 64 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\client\starter\impl\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */