/*    */ package com.eoos.gm.tis2web.fts.service.cai;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Hit implements Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected Integer sio;
/*  9 */   protected FTSHit ftsHit = null;
/*    */   protected List sits;
/*    */   
/*    */   public Integer getSIO() {
/* 13 */     return this.sio;
/*    */   }
/*    */   
/*    */   public List getSITs() {
/* 17 */     return this.sits;
/*    */   }
/*    */   
/*    */   public void setSITs(List sits) {
/* 21 */     this.sits = sits;
/*    */   }
/*    */   
/*    */   public Hit(Integer sio) {
/* 25 */     this.sio = sio;
/*    */   }
/*    */   
/*    */   public Hit(FTSHit ftsHit) {
/* 29 */     this.ftsHit = ftsHit;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\service\cai\Hit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */