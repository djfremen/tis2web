/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ public class Adapter {
/*  4 */   public static final Adapter NAO = new Adapter("nao");
/*    */   
/*  6 */   public static final Adapter GME = new Adapter("gme");
/*    */   
/*  8 */   public static final Adapter GLOBAL = new Adapter("global");
/*    */   
/*    */   private String display;
/*    */   
/*    */   private Adapter(String display) {
/* 13 */     this.display = display;
/*    */   }
/*    */   
/*    */   public static Adapter getInstance(String string) {
/* 17 */     if ("gme".equalsIgnoreCase(string))
/* 18 */       return GME; 
/* 19 */     if ("nao".equalsIgnoreCase(string))
/* 20 */       return NAO; 
/* 21 */     if ("global".equalsIgnoreCase(string)) {
/* 22 */       return GLOBAL;
/*    */     }
/* 24 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return this.display;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\Adapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */