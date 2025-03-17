/*    */ package com.eoos.datatype.gtwo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MIMEDataImpl
/*    */   extends DataImpl
/*    */   implements MIMEData, MIMEData.MetaData
/*    */ {
/*    */   protected String mime;
/*    */   
/*    */   public MIMEDataImpl(String mime, Object data) {
/* 17 */     super(data);
/* 18 */     this.mime = mime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMIME() {
/* 25 */     return this.mime;
/*    */   }
/*    */   
/*    */   public Data.MetaData getMetaData() {
/* 29 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\datatype\gtwo\MIMEDataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */