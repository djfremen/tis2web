/*    */ package com.eoos.gm.tis2web.frame.dls.server;
/*    */ 
/*    */ public class LeaseManager {
/*  4 */   private static LeaseManager instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized LeaseManager getInstance() {
/* 11 */     if (instance == null) {
/* 12 */       instance = new LeaseManager();
/*    */     }
/* 14 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\LeaseManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */