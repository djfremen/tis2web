/*    */ package com.eoos.gm.tis2web.sas.client.system;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SASClientContextProvider
/*    */ {
/*  9 */   private static SASClientContextProvider instance = null;
/*    */   
/* 11 */   private SASClientContext context = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized SASClientContextProvider getInstance() {
/* 18 */     if (instance == null) {
/* 19 */       instance = new SASClientContextProvider();
/*    */     }
/* 21 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized SASClientContext createContext(File homeDir) {
/* 25 */     this.context = new SASClientContextImpl(homeDir);
/* 26 */     return this.context;
/*    */   }
/*    */   
/*    */   public synchronized SASClientContext getContext() {
/* 30 */     if (this.context == null) {
/* 31 */       throw new IllegalStateException("context is not created yet ");
/*    */     }
/* 33 */     return this.context;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\SASClientContextProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */