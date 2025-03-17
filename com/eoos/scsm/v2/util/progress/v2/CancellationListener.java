/*    */ package com.eoos.scsm.v2.util.progress.v2;
/*    */ 
/*    */ public interface CancellationListener {
/*    */   void onCancel();
/*    */   
/*    */   public static final class Interrupter implements CancellationListener {
/*    */     public Interrupter(Thread toInterrupt) {
/*  8 */       this.t = toInterrupt;
/*    */     }
/*    */     private Thread t;
/*    */     public void onCancel() {
/* 12 */       this.t.interrupt();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\progress\v2\CancellationListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */