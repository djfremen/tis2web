/*    */ package com.eoos.persistence.implementation.serializer;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.persistence.util.Serializer;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultSerializer
/*    */   implements Serializer
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   private static DefaultSerializer instance = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized DefaultSerializer getInstance() {
/* 26 */     if (instance == null) {
/* 27 */       instance = new DefaultSerializer();
/*    */     }
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public byte[] serialize(Object object) {
/* 33 */     byte[] result = null;
/*    */     try {
/* 35 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 36 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 37 */       oos.writeObject(object);
/* 38 */       oos.close();
/* 39 */       result = baos.toByteArray();
/* 40 */     } catch (Exception e) {
/* 41 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 43 */     return result;
/*    */   }
/*    */   
/*    */   public Object deserialize(byte[] data) {
/* 47 */     Object result = null;
/*    */     try {
/* 49 */       ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 50 */       ObjectInputStream ois = new ObjectInputStream(bais);
/* 51 */       result = ois.readObject();
/* 52 */       ois.close();
/* 53 */     } catch (Exception e) {
/* 54 */       throw new ExceptionWrapper(e);
/*    */     } 
/* 56 */     return result;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\serializer\DefaultSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */