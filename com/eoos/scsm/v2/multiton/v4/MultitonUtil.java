/*    */ package com.eoos.scsm.v2.multiton.v4;
/*    */ 
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultitonUtil
/*    */ {
/*    */   public static void dumpInstancesToLog(IMultitonSupport multitonSupport, final Logger log) {
/* 13 */     log.info("multiton instances ...");
/*    */     
/* 15 */     multitonSupport.visitInstances(new IMultitonSupport.Visitor() {
/* 16 */           private int count = 0;
/*    */           
/*    */           public void onVisit(Object instance) {
/* 19 */             log.info(++this.count + ": " + Util.toString(instance));
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public static int getInstanceCount(IMultitonSupport multitonSupport) {
/* 25 */     final int[] result = new int[1];
/* 26 */     result[0] = 0;
/* 27 */     multitonSupport.visitInstances(new IMultitonSupport.Visitor()
/*    */         {
/*    */           public void onVisit(Object instance) {
/* 30 */             result[0] = result[0] + 1;
/*    */           }
/*    */         });
/* 33 */     return result[0];
/*    */   }
/*    */   
/*    */   public static IMultitonSupport.CreationCallbackExt createCheckWrapper(final IMultitonSupport.CreationCallbackExt creationCallback) {
/* 37 */     return new IMultitonSupport.CreationCallbackExt()
/*    */       {
/*    */         public Object createInstance(Object key) {
/* 40 */           return creationCallback.createInstance(key);
/*    */         }
/*    */         
/*    */         public Object createStorageReplacement(Object key) {
/* 44 */           Object ret = creationCallback.createStorageReplacement(key);
/* 45 */           if (!Util.equals(ret, key)) {
/* 46 */             throw new IllegalStateException("broken contract: the key replacement: " + String.valueOf(ret) + " is not equal to the key: " + String.valueOf(key));
/*    */           }
/* 48 */           return ret;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\MultitonUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */