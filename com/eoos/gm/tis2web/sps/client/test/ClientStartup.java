/*    */ package com.eoos.gm.tis2web.sps.client.test;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContext;
/*    */ import com.eoos.gm.tis2web.sps.client.common.ClientAppContextProvider;
/*    */ import com.eoos.gm.tis2web.sps.client.system.classloader.ClientClassLoader;
/*    */ import com.eoos.gm.tis2web.sps.client.ui.SPSClientController;
/*    */ 
/*    */ public class ClientStartup {
/*    */   public static void main(String[] args) throws Throwable {
/* 10 */     System.setProperty("sps-client-mode", "local");
/* 11 */     System.setProperty("session.id", "sps-local");
/*    */     
/* 13 */     System.setProperty("com.eoos.gm.tis2web.sps.client", "true");
/* 14 */     System.setProperty("warranty.code.list", "20");
/* 15 */     System.setProperty("language.id", "en_US");
/* 16 */     System.setProperty("brands", "GME,NAO,SAT");
/*    */     
/* 18 */     ClientAppContext appContext = ClientAppContextProvider.getClientAppContext();
/* 19 */     appContext.init();
/* 20 */     ClientClassLoader clientClassLoader = new ClientClassLoader(ClientStartup.class.getClassLoader());
/*    */     try {
/* 22 */       SPSClientController client = (SPSClientController)clientClassLoader.loadClass(SPSClientController.class.getName()).newInstance();
/* 23 */       client.start();
/* 24 */     } catch (Exception e) {
/* 25 */       System.out.println("ClientStartup execution error: " + e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\test\ClientStartup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */