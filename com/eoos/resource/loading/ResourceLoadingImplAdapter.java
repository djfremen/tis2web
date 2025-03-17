/*    */ package com.eoos.resource.loading;
/*    */ 
/*    */ import com.eoos.io.StreamUtil;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ResourceLoadingImplAdapter
/*    */   implements ResourceLoading
/*    */ {
/*    */   public Object loadResource(Object identifier) throws IOException {
/*    */     try {
/* 18 */       return StreamUtil.readFully(getResource(identifier));
/* 19 */     } catch (NullPointerException e) {
/* 20 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean existsResource(Object identifier) {
/* 26 */     boolean retValue = false;
/*    */     try {
/* 28 */       retValue = (getResource(identifier) != null);
/* 29 */     } catch (IOException e) {}
/*    */ 
/*    */     
/* 32 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\resource\loading\ResourceLoadingImplAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */