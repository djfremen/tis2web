/*    */ package com.eoos.persistence.util;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SerializationUtil
/*    */ {
/*    */   public static byte[] serializeObject(Serializable object) {
/* 20 */     byte[] retValue = null;
/*    */     try {
/* 22 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 23 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 24 */       oos.writeObject(object);
/* 25 */       oos.close();
/* 26 */       retValue = baos.toByteArray();
/* 27 */     } catch (Exception e) {
/* 28 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 30 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public static Object deserializeObject(byte[] data) {
/* 35 */     Object retValue = null;
/*    */     try {
/* 37 */       ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 38 */       ObjectInputStream ois = new ObjectInputStream(bais);
/* 39 */       retValue = ois.readObject();
/* 40 */       ois.close();
/* 41 */     } catch (Exception e) {
/* 42 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 44 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistenc\\util\SerializationUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */