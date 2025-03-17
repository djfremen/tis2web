/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.COP;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Part;
/*    */ 
/*    */ 
/*    */ public abstract class SPSCOP
/*    */   implements COP
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected SPSPart part;
/*    */   protected int mode;
/*    */   
/*    */   protected SPSCOP(SPSPart part, int mode) {
/* 15 */     this.part = part;
/* 16 */     this.mode = mode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 20 */     return this.part.toString();
/*    */   }
/*    */   
/*    */   public Part getPart() {
/* 24 */     return this.part;
/*    */   }
/*    */   
/*    */   public int getMode() {
/* 28 */     return this.mode;
/*    */   }
/*    */   
/*    */   public void setMode(int mode) {
/* 32 */     this.mode = mode;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSCOP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */