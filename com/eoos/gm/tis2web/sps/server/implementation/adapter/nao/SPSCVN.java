/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSCVN
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final String NOT_AVAILABLE = "n/a";
/*    */   protected String part;
/*    */   protected String cvnDB;
/*    */   protected String cvnVIT1;
/*    */   
/*    */   public String getCVNDB() {
/* 19 */     return this.cvnDB;
/*    */   }
/*    */   
/*    */   public String getCVNECU() {
/* 23 */     return this.cvnVIT1;
/*    */   }
/*    */   
/*    */   public String getPart() {
/* 27 */     return this.part;
/*    */   }
/*    */   
/*    */   public SPSCVN(String part, String cvnDB, String cvnVIT1) {
/* 31 */     this.part = part;
/* 32 */     this.cvnDB = (cvnDB == null) ? "n/a" : cvnDB;
/* 33 */     this.cvnVIT1 = (cvnVIT1 == null) ? "n/a" : cvnVIT1;
/*    */   }
/*    */   
/*    */   public static boolean match(String dbcvn, String vit1cvn) {
/*    */     try {
/* 38 */       long cvnA = Long.parseLong(dbcvn, 16);
/* 39 */       long cvnB = Long.parseLong(vit1cvn);
/* 40 */       return (cvnA == cvnB);
/* 41 */     } catch (Exception e) {
/*    */       
/* 43 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSCVN.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */