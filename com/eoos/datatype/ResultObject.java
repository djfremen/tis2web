/*    */ package com.eoos.datatype;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResultObject
/*    */ {
/*    */   public static final int SUCCESS = 0;
/*    */   public Object object;
/*    */   public int code;
/*    */   
/*    */   public ResultObject(int code, Object object) {
/* 15 */     this.code = code;
/* 16 */     this.object = object;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 20 */     return this.code;
/*    */   }
/*    */   
/*    */   public Object getObject() {
/* 24 */     return this.object;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\ResultObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */