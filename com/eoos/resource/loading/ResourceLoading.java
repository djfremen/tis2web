/*    */ package com.eoos.resource.loading;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ResourceLoading
/*    */ {
/* 15 */   public static final ResourceLoading DUMMY = new ResourceLoading()
/*    */     {
/*    */       public Collection searchResources(Object searchPattern) {
/* 18 */         return Collections.EMPTY_SET;
/*    */       }
/*    */       
/*    */       public Object loadResource(Object identifier) throws IOException {
/* 22 */         return null;
/*    */       }
/*    */       
/*    */       public InputStream getResource(Object identifier) throws IOException {
/* 26 */         return null;
/*    */       }
/*    */       
/*    */       public boolean existsResource(Object identifier) {
/* 30 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   Object loadResource(Object paramObject) throws IOException;
/*    */   
/*    */   InputStream getResource(Object paramObject) throws IOException;
/*    */   
/*    */   boolean existsResource(Object paramObject);
/*    */   
/*    */   Collection searchResources(Object paramObject);
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\ResourceLoading.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */