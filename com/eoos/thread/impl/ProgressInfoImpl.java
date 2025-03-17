/*    */ package com.eoos.thread.impl;
/*    */ 
/*    */ import com.eoos.thread.ProgressInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressInfoImpl
/*    */   implements ProgressInfo
/*    */ {
/* 10 */   private Integer total = null;
/*    */   
/* 12 */   private Integer processed = null;
/*    */ 
/*    */   
/*    */   protected String message;
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getTotalTicks() {
/* 20 */     return this.total;
/*    */   }
/*    */   
/*    */   public void setTotalTicks(int total) {
/* 24 */     this.total = Integer.valueOf(total);
/*    */   }
/*    */   
/*    */   public Integer getProcessedTicks() {
/* 28 */     return this.processed;
/*    */   }
/*    */   
/*    */   public void setProcessedTicks(int processed) {
/* 32 */     this.processed = Integer.valueOf(processed);
/*    */   }
/*    */   
/*    */   public void incProcessedTicks() {
/* 36 */     if (getProcessedTicks() != null) {
/* 37 */       setProcessedTicks(getProcessedTicks().intValue() + 1);
/*    */     } else {
/* 39 */       setProcessedTicks(1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setFinished() {
/* 44 */     if (getTotalTicks() != null) {
/* 45 */       setProcessedTicks(getTotalTicks().intValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void setMessage(String message) {
/* 51 */     this.message = message;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 55 */     return this.message;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\thread\impl\ProgressInfoImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */