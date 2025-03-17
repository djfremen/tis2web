/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.PairValue;
/*    */ import com.eoos.gm.tis2web.sps.service.cai.Value;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class PairValueImpl
/*    */   implements PairValue, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected String key;
/*    */   protected Value value;
/*    */   
/*    */   public PairValueImpl(String key, Value value) {
/* 15 */     this.key = key;
/* 16 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getID() {
/* 20 */     return this.key;
/*    */   }
/*    */   
/*    */   public Value getValue() {
/* 24 */     return this.value;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 28 */     return "<" + this.key + ":" + this.value + ">";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\PairValueImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */