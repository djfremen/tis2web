/*    */ package com.eoos.resource.loading;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ 
/*    */ public class ResourceLoadingChain
/*    */   extends ResourceLoadingImplAdapter
/*    */ {
/*    */   private ResourceLoading[] chain;
/*    */   
/*    */   public ResourceLoadingChain(ResourceLoading[] resourceLoaders) {
/* 15 */     this.chain = resourceLoaders;
/*    */   }
/*    */   
/*    */   public InputStream getResource(Object identifier) throws IOException {
/* 19 */     InputStream is = null;
/* 20 */     for (int i = 0; i < this.chain.length && is == null; i++) {
/*    */       try {
/* 22 */         is = this.chain[i].getResource(identifier);
/* 23 */       } catch (IOException e) {}
/*    */     } 
/*    */ 
/*    */     
/* 27 */     return is;
/*    */   }
/*    */   
/*    */   public Collection searchResources(Object searchPattern) {
/* 31 */     Collection ret = new HashSet();
/* 32 */     for (int i = 0; i < this.chain.length; i++) {
/* 33 */       Collection tmp = this.chain[i].searchResources(searchPattern);
/* 34 */       if (!Util.isNullOrEmpty(tmp)) {
/* 35 */         ret.addAll(tmp);
/*    */       }
/*    */     } 
/* 38 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\ResourceLoadingChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */