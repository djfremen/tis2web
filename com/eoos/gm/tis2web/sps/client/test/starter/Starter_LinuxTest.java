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
/*    */ public class Starter_LinuxTest
/*    */ {
/*    */   public static void main(String[] args) {
/*    */     try {
/* 16 */       IStarter start = Starter.getInstance();
/*    */       
/* 18 */       if (start != null) {
/*    */ 
/*    */         
/* 21 */         if (start.isStartAllowed() == true) {
/* 22 */           System.out.println("Start is allowed - OK");
/*    */         } else {
/* 24 */           System.out.println("SPS client is already running. Start is not allowed");
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 29 */         List commPorts = start.getAvailableCommPorts();
/*    */         
/* 31 */         System.out.println("\n\n***** Available communication ports: *****\n");
/* 32 */         if (commPorts != null) {
/* 33 */           Iterator<String> it = commPorts.iterator();
/* 34 */           while (it.hasNext()) {
/* 35 */             String port = it.next();
/* 36 */             System.out.println("Port: " + port);
/*    */           } 
/*    */         } else {
/* 39 */           System.out.println("Communication port list is NULL");
/*    */         } 
/* 41 */         System.out.println("\n******************************************");
/*    */       } else {
/*    */         
/* 44 */         System.out.println("Reference to object is NULL");
/*    */       }
/*    */     
/* 47 */     } catch (Exception e) {
/* 48 */       System.out.println("Exception in \"main\" function" + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\starter\Starter_LinuxTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */