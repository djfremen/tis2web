/*    */ package com.eoos.persistence.implementation.objectstore;
/*    */ 
/*    */ import com.eoos.datatype.ExceptionWrapper;
/*    */ import com.eoos.persistence.FullFeaturedObjectStore;
/*    */ import com.eoos.persistence.implementation.serializer.DefaultSerializer;
/*    */ import com.eoos.persistence.util.Serializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SerializationWrapper
/*    */   extends WrapperBase
/*    */ {
/*    */   private Serializer serializer;
/*    */   
/*    */   public SerializationWrapper(FullFeaturedObjectStore objectStore, Serializer serializer) {
/* 19 */     super(objectStore);
/* 20 */     this.serializer = (serializer != null) ? serializer : (Serializer)DefaultSerializer.getInstance();
/*    */   }
/*    */   
/*    */   protected final Serializer getSerializer() {
/* 24 */     return this.serializer;
/*    */   }
/*    */   
/*    */   public void store(Object identifier, Object data) {
/*    */     try {
/* 29 */       getWrappedStore().store(identifier, getSerializer().serialize(data));
/* 30 */     } catch (Exception e) {
/* 31 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Object load(Object identifier) {
/*    */     try {
/* 37 */       return getSerializer().deserialize((byte[])getWrappedStore().load(identifier));
/* 38 */     } catch (Exception e) {
/* 39 */       throw new ExceptionWrapper(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\implementation\objectstore\SerializationWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */