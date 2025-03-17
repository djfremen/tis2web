/*    */ package com.eoos.gm.tis2web.frame.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.declaration.service.StorageService;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectOutputStream;
/*    */ 
/*    */ 
/*    */ public class ObjectStoreFacadeImpl
/*    */   implements StorageService.ObjectStore
/*    */ {
/*    */   private StorageService dataStorage;
/*    */   
/*    */   ObjectStoreFacadeImpl(StorageService dataStorage) {
/* 16 */     this.dataStorage = dataStorage;
/*    */   }
/*    */   
/*    */   public void store(CharSequence identifier, Object object) throws Exception {
/* 20 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 21 */     ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 22 */     oos.writeObject(object);
/* 23 */     oos.close();
/* 24 */     this.dataStorage.store(identifier, baos.toByteArray());
/*    */   }
/*    */   
/*    */   public Object load(CharSequence identifier) throws Exception {
/* 28 */     Object result = null;
/* 29 */     byte[] data = this.dataStorage.load(identifier);
/* 30 */     if (data != null) {
/* 31 */       ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 32 */       ObjectInputStream ois = new ObjectInputStream(bais);
/* 33 */       result = ois.readObject();
/* 34 */       ois.close();
/*    */     } 
/* 36 */     return result;
/*    */   }
/*    */   
/*    */   public void delete(CharSequence identifier) throws Exception {
/* 40 */     this.dataStorage.delete(identifier);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\service\ObjectStoreFacadeImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */