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
/*    */ public class LinuxStarter
/*    */   implements IStarter
/*    */ {
/* 19 */   private static Logger log = Logger.getLogger(LinuxStarter.class);
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
/* 47 */       log.info("Available COM ports: " + ports);
/* 48 */       return ports;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean startProcess(String cmdLine) {
/* 53 */     synchronized (LinuxStarter.class) {
/* 54 */       log.info("Start process");
/* 55 */       return false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean setEnvironment() {
/* 60 */     synchronized (LinuxStarter.class) {
/* 61 */       log.info("Set environment");
/* 62 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\client\starter\LinuxStarter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */