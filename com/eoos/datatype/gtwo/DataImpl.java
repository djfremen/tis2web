/*    */ package com.eoos.datatype.gtwo;
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
/*    */ public class DataImpl
/*    */   implements Data
/*    */ {
/*    */   protected Object data;
/*    */   
/*    */   public DataImpl(Object data) {
/* 18 */     this.data = data;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Data.MetaData getMetaData() {
/* 25 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getData() {
/* 32 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\gtwo\DataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */