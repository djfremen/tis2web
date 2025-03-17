/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ScoutFactory
/*    */ {
/* 10 */   private static ScoutFactory instance = null;
/*    */   private Map scoutMap;
/* 12 */   private static final Logger log = Logger.getLogger(ScoutFactory.class);
/* 13 */   private final Object SYNC_OBJ = new Object();
/*    */   
/*    */   private ScoutFactory() {
/* 16 */     this.scoutMap = new HashMap<Object, Object>();
/*    */   }
/*    */   
/*    */   public static synchronized ScoutFactory getInstance() {
/* 20 */     if (instance == null) {
/* 21 */       instance = new ScoutFactory();
/*    */     }
/* 23 */     return instance;
/*    */   }
/*    */   
/*    */   public IScout getScout(String scoutName) {
/* 27 */     IScout result = null;
/* 28 */     if (scoutName != null) {
/* 29 */       synchronized (this.SYNC_OBJ) {
/* 30 */         if (this.scoutMap.get(scoutName) == null) {
/*    */           try {
/* 32 */             Class<?> c = Class.forName(scoutName);
/* 33 */             this.scoutMap.put(scoutName, c.newInstance());
/* 34 */             log.info("scout loaded: " + scoutName);
/* 35 */           } catch (Exception e) {
/* 36 */             log.error("cannot get scout interface implementation:" + e, e);
/*    */           } 
/*    */         }
/*    */       } 
/* 40 */       result = (IScout)this.scoutMap.get(scoutName);
/*    */     } 
/* 42 */     return result;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 46 */     synchronized (this.SYNC_OBJ) {
/* 47 */       this.scoutMap = new HashMap<Object, Object>();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\ScoutFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */