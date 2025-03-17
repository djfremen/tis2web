/*    */ package com.eoos.gm.tis2web.sps.common.gtwo.implementation;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Result;
/*    */ import com.eoos.gm.tis2web.sps.common.gtwo.export.Status;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResultImpl
/*    */   implements Result, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 15 */   private Status status = null;
/* 16 */   private Object object = null;
/*    */   
/*    */   public ResultImpl(Status status, Object object) {
/* 19 */     this.status = status;
/* 20 */     this.object = object;
/*    */   }
/*    */   
/*    */   public ResultImpl(Object object) {
/* 24 */     if (object instanceof Throwable) {
/* 25 */       this.status = Status.EXCEPTION;
/* 26 */     } else if (object instanceof com.eoos.gm.tis2web.sps.common.gtwo.export.CustomException) {
/* 27 */       this.status = Result.EXCEPTION_CUSTOM;
/*    */     } else {
/* 29 */       this.status = Status.OK;
/*    */     } 
/* 31 */     this.object = object;
/*    */   }
/*    */   
/*    */   public Status getStatus() {
/* 35 */     return this.status;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 39 */     return this.object;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\implementation\ResultImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */