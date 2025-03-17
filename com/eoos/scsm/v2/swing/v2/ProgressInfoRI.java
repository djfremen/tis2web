/*    */ package com.eoos.scsm.v2.swing.v2;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.progress.v2.ProgressInfo;
/*    */ 
/*    */ public class ProgressInfoRI
/*    */   implements ProgressInfo {
/*  7 */   private String message = "";
/*    */   
/*  9 */   private int maxProgress = -1;
/*    */   
/* 11 */   private int progress = -1;
/*    */ 
/*    */   
/*    */   public ProgressInfoRI() {}
/*    */ 
/*    */   
/*    */   public ProgressInfoRI(String message) {
/* 18 */     this(message, -1, -1);
/*    */   }
/*    */   
/*    */   public ProgressInfoRI(String message, int progress, int maxProgress) {
/* 22 */     this.message = message;
/* 23 */     this.progress = progress;
/* 24 */     this.maxProgress = maxProgress;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 28 */     return this.message;
/*    */   }
/*    */   
/*    */   public ProgressInfoRI setMessage(String message) {
/* 32 */     this.message = message;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public int getMaxProgress() {
/* 37 */     return this.maxProgress;
/*    */   }
/*    */   
/*    */   public ProgressInfoRI setMaxProgress(int maxProgress) {
/* 41 */     this.maxProgress = maxProgress;
/* 42 */     return this;
/*    */   }
/*    */   
/*    */   public int getProgress() {
/* 46 */     return this.progress;
/*    */   }
/*    */   
/*    */   public ProgressInfoRI setProgress(int progress) {
/* 50 */     this.progress = progress;
/* 51 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\swing\v2\ProgressInfoRI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */