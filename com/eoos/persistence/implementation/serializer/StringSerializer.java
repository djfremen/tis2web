/*    */ package com.eoos.persistence.implementation.serializer;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.persistence.util.Serializer;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringSerializer
/*    */   implements Serializer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 17 */   private static StringSerializer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized StringSerializer getInstance() {
/* 23 */     if (instance == null) {
/* 24 */       instance = new StringSerializer();
/*    */     }
/* 26 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] serialize(Object object) {
/*    */     try {
/* 31 */       return ((String)object).getBytes("utf-8");
/* 32 */     } catch (UnsupportedEncodingException e) {
/* 33 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object deserialize(byte[] data) {
/*    */     try {
/* 39 */       return new String(data, "utf-8");
/* 40 */     } catch (UnsupportedEncodingException e) {
/* 41 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\serializer\StringSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */