/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class StreamCache_Memory
/*    */   implements StreamCache {
/* 11 */   private HotspotCache store = new HotspotCache(300000L);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 18 */     this.store.clear();
/*    */   }
/*    */   
/*    */   public OutputStream getStorageStream(final Object key) throws IOException {
/* 22 */     return new ByteArrayOutputStream()
/*    */       {
/*    */         public void close() throws IOException
/*    */         {
/* 26 */           super.close();
/* 27 */           StreamCache_Memory.this.store.store(key, toByteArray());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public InputStream lookup(Object key) throws IOException {
/* 34 */     byte[] data = (byte[])this.store.lookup(key);
/* 35 */     if (data != null) {
/* 36 */       return new ByteArrayInputStream(data);
/*    */     }
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public void remove(Object key) {
/* 42 */     this.store.remove(key);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\StreamCache_Memory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */