/*    */ package com.eoos.html.gtwo.util;
/*    */ 
/*    */ import com.eoos.datatype.gtwo.MIMEDataImpl;
/*    */ import com.eoos.html.gtwo.ResponseData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResponseDataImpl
/*    */   extends MIMEDataImpl
/*    */   implements ResponseData, ResponseData.MetaData
/*    */ {
/*    */   protected int cacheType;
/*    */   
/*    */   public ResponseDataImpl(int cacheType, String mime, Object data) {
/* 17 */     super(mime, data);
/* 18 */     this.cacheType = cacheType;
/*    */   }
/*    */   
/*    */   public ResponseDataImpl(String mime, Object data) {
/* 22 */     this(0, mime, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCacheType() {
/* 29 */     return this.cacheType;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtw\\util\ResponseDataImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */