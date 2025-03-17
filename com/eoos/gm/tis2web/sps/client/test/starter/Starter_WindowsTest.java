/*    */ package com.eoos.gm.tis2web.sps.client.test.starter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.starter.IStarter;
/*    */ import com.eoos.gm.tis2web.sps.client.starter.impl.Starter;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Starter_WindowsTest
/*    */ {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 16 */       IStarter start = Starter.getInstance();
/*    */       
/* 18 */       if (start != null) {
/*    */ 
/*    */         
/* 21 */         System.out.println("\n\n***** Ask for start allowance: *****\n");
/*    */         
/* 23 */         if (start.isStartAllowed() == true) {
/* 24 */           System.out.println("Start is allowed - OK");
/*    */         } else {
/* 26 */           System.out.println("SPS client is already running. Start is not allowed");
/*    */         } 
/*    */         
/* 29 */         System.out.println("\n******************************************");
/*    */ 
/*    */ 
/*    */         
/* 33 */         List commPorts = start.getAvailableCommPorts();
/*    */         
/* 35 */         System.out.println("\n\n***** Available communication ports: *****\n");
/* 36 */         if (commPorts != null) {
/* 37 */           Iterator<String> it = commPorts.iterator();
/* 38 */           while (it.hasNext()) {
/* 39 */             String port = it.next();
/* 40 */             System.out.println("Port: " + port);
/*    */           } 
/*    */         } else {
/* 43 */           System.out.println("Communication port list is NULL");
/*    */         } 
/* 45 */         System.out.println("\n******************************************");
/*    */ 
/*    */ 
/*    */         
/* 49 */         System.out.println("\n\n***** Start a process: *****\n");
/*    */         
/* 51 */         if (start.startProcess("I:\\Cosids\\j2534_sw\\T2_J2534_Config.exe") == true) {
/* 52 */           System.out.println("Process successfully started");
/*    */         } else {
/* 54 */           System.out.println("Couldn't start process");
/*    */         } 
/* 56 */         System.out.println("\n******************************************");
/*    */       } else {
/*    */         
/* 59 */         System.out.println("Reference to object is NULL");
/*    */       }
/*    */     
/* 62 */     } catch (Exception e) {
/* 63 */       System.out.println("Exception in \"main\" function" + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\starter\Starter_WindowsTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */