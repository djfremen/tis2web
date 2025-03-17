/*    */ package com.eoos.gm.tis2web.sas.client.system.starter;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sas.client.system.starter.impl.LinuxStarter;
/*    */ import com.eoos.gm.tis2web.sas.client.system.starter.impl.WindowsStarter;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StarterProvider
/*    */ {
/* 12 */   private static final Logger log = Logger.getLogger(StarterProvider.class);
/*    */   
/* 14 */   private static StarterProvider instance = null;
/*    */   
/* 16 */   private Starter starter = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized StarterProvider getInstance() {
/* 22 */     if (instance == null) {
/* 23 */       instance = new StarterProvider();
/*    */     }
/* 25 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized Starter getStarter() {
/* 29 */     if (this.starter == null) {
/*    */       try {
/* 31 */         String osSystem = System.getProperty("os.name").toUpperCase(Locale.ENGLISH);
/* 32 */         if (osSystem.indexOf("WINDOWS") != -1) {
/* 33 */           this.starter = (Starter)new WindowsStarter();
/*    */         } else {
/* 35 */           this.starter = (Starter)new LinuxStarter();
/*    */         } 
/* 37 */       } catch (Throwable e) {
/* 38 */         if (e instanceof UnsatisfiedLinkError) {
/* 39 */           log.error("Could not load StarterBridge library. " + e);
/*    */         }
/* 41 */         if (e instanceof SecurityException) {
/* 42 */           log.error("Could not load StarterBridge library. " + e);
/*    */         }
/*    */       } 
/*    */     }
/* 46 */     return this.starter;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\starter\StarterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */