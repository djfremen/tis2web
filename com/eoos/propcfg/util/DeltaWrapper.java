/*    */ package com.eoos.propcfg.util;
/*    */ 
/*    */ public class DeltaWrapper extends ConfigurationWrapperBase {
/*    */   private Mode mode;
/*    */   private String key;
/*    */   private String value;
/*    */   
/*    */   private enum Mode {
/*  9 */     REMOVE, SET;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private DeltaWrapper(Configuration backend, Mode mode, String key, String value) {
/* 18 */     super(backend);
/* 19 */     this.mode = mode;
/* 20 */     this.key = key;
/* 21 */     this.value = value;
/*    */   }
/*    */   
/*    */   public static DeltaWrapper createRemovalWrapper(Configuration cfg, String key) {
/* 25 */     return new DeltaWrapper(cfg, Mode.REMOVE, key, null);
/*    */   }
/*    */   
/*    */   public static DeltaWrapper createModificationWrapper(Configuration cfg, String key, String value) {
/* 29 */     return new DeltaWrapper(cfg, Mode.SET, key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set getKeys() {
/* 34 */     Set<String> ret = super.getKeys();
/* 35 */     if (this.mode == Mode.REMOVE) {
/* 36 */       ret.remove(this.key);
/*    */     } else {
/* 38 */       ret.add(this.key);
/*    */     } 
/* 40 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getProperty(String key) {
/* 45 */     String ret = null;
/* 46 */     if (this.key.equals(key)) {
/* 47 */       if (this.mode == Mode.SET) {
/* 48 */         ret = this.value;
/*    */       }
/*    */     } else {
/* 51 */       ret = super.getProperty(key);
/*    */     } 
/* 53 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove(String key) {
/* 58 */     if (this.key.equals(key)) {
/* 59 */       if (this.mode == Mode.SET) {
/* 60 */         this.mode = Mode.REMOVE;
/*    */       }
/*    */     } else {
/* 63 */       super.remove(key);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProperty(String key, String value) {
/* 69 */     if (this.key.equals(key)) {
/* 70 */       if (this.mode == Mode.SET) {
/* 71 */         this.value = value;
/*    */       } else {
/* 73 */         this.mode = Mode.SET;
/* 74 */         this.value = value;
/*    */       } 
/*    */     } else {
/* 77 */       super.setProperty(key, value);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcf\\util\DeltaWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */