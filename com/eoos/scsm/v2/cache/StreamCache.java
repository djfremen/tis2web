/*    */ package com.eoos.scsm.v2.cache;
/*    */ 
/*    */ import com.eoos.scsm.v2.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface StreamCache
/*    */ {
/* 16 */   public static final StreamCache DUMMY = new StreamCache()
/*    */     {
/*    */       public void clear() {}
/*    */ 
/*    */       
/*    */       public OutputStream getStorageStream(Object key) throws IOException {
/* 22 */         return StreamUtil.NILOutputStream;
/*    */       }
/*    */       
/*    */       public InputStream lookup(Object key) throws IOException {
/* 26 */         return null;
/*    */       }
/*    */     };
/*    */   
/*    */   InputStream lookup(Object paramObject) throws IOException;
/*    */   
/*    */   OutputStream getStorageStream(Object paramObject) throws IOException;
/*    */   
/*    */   void clear();
/*    */   
/*    */   public static interface StreamSize {
/*    */     long getLength(Object param1Object) throws IOException;
/*    */   }
/*    */   
/*    */   public static interface KeyQuery {
/*    */     Set getKeys();
/*    */   }
/*    */   
/*    */   public static interface Size {
/*    */     int size();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\cache\StreamCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */