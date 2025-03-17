/*    */ package com.eoos.gm.tis2web.sps.client.starter.impl;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Installer;
/*    */ import java.io.File;
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
/*    */ public class LinuxStarter
/*    */   implements IStarter
/*    */ {
/*    */   public boolean isStartAllowed() {
/* 39 */     synchronized (LinuxStarter.class) {
/* 40 */       return true;
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<String> getAvailableCommPorts() {
/* 45 */     synchronized (LinuxStarter.class) {
/* 46 */       List<String> ports = new ArrayList<String>();
/* 47 */       for (int index = 0; index < 10; index++) {
/* 48 */         ports.add("COM" + index);
/*    */       }
/* 50 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getTech2WinComPort() {
/* 55 */     synchronized (LinuxStarter.class) {
/* 56 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 61 */     synchronized (LinuxStarter.class) {
/* 62 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 67 */     synchronized (LinuxStarter.class) {
/* 68 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Integer install(File path, Installer installer) {
/* 73 */     synchronized (LinuxStarter.class) {
/* 74 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\starter\impl\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */