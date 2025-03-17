/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ 
/*    */ public class UncheckedInterruptedException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public UncheckedInterruptedException() {}
/*    */   
/*    */   public UncheckedInterruptedException(InterruptedException e) {
/* 12 */     super(e);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\UncheckedInterruptedException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */