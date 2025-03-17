/*    */ package com.eoos.gm.tis2web.swdl.common.domain;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerError
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   public static final int UNKNOWN = 0;
/*    */   public static final int INACTIVE_SESSION = 1;
/*    */   public static final int UNPACK_COMMAND = 2;
/*    */   public static final int CHECK_SESSION = 3;
/*    */   public static final int PACK_RESPONSE = 4;
/*    */   public static final int OBJECT_NULL = 5;
/*    */   public static final int SERVER_REINIT = 6;
/* 29 */   private int error = 0;
/*    */ 
/*    */   
/*    */   public ServerError(int error) {
/* 33 */     this.error = error;
/*    */   }
/*    */   
/*    */   public int getError() {
/* 37 */     return this.error;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\common\domain\ServerError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */