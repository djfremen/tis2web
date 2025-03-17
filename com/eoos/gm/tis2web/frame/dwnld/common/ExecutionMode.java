/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.scsm.v2.multiton.v4.SerializableEnumBase;
/*    */ 
/*    */ public class ExecutionMode
/*    */   extends SerializableEnumBase {
/*    */   private static final long serialVersionUID = 1L;
/*  8 */   public static final ExecutionMode SYNC = new ExecutionMode();
/*    */   
/* 10 */   public static final ExecutionMode ASYNC = new ExecutionMode();
/*    */   
/* 12 */   private static final Object[] domain = new Object[] { SYNC, ASYNC };
/*    */   
/*    */   protected Object[] getInstanceArray() {
/* 15 */     return domain;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\ExecutionMode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */