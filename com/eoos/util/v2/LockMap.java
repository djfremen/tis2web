/*    */ package com.eoos.util.v2;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LockMap
/*    */ {
/* 18 */   private static final Logger log = Logger.getLogger(LockMap.class);
/*    */   
/* 20 */   private final Object SYNC = new Object();
/*    */   
/* 22 */   private Reference refMap = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Map getMap() {
/* 28 */     Map<?, ?> retValue = (this.refMap != null) ? this.refMap.get() : null;
/* 29 */     if (retValue == null) {
/* 30 */       retValue = Collections.synchronizedMap(new WeakHashMap<Object, Object>()
/*    */           {
/*    */             public String toString() {
/* 33 */               return Util.toString(this, this);
/*    */             }
/*    */           });
/* 36 */       this.refMap = new WeakReference<Map<?, ?>>(retValue);
/*    */     } 
/*    */     
/* 39 */     return retValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getLockObject(Object keyObject) {
/* 44 */     log.debug("retrieving sync object for " + String.valueOf(keyObject) + "...");
/* 45 */     Integer _keyObject = keyHash(keyObject);
/* 46 */     synchronized (this.SYNC) {
/* 47 */       Map<Integer, Object> map = getMap();
/* 48 */       Object retValue = map.get(_keyObject);
/* 49 */       if (retValue == null) {
/* 50 */         log.debug("...creating new sync object ...");
/* 51 */         retValue = new Object() {
/*    */           
/*    */           };
/* 54 */         log.debug("...registering sync object " + String.valueOf(retValue));
/* 55 */         map.put(_keyObject, retValue);
/*    */       } 
/* 57 */       log.debug("... returning sync object " + String.valueOf(retValue));
/* 58 */       return retValue;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private Integer keyHash(Object keyObject) {
/* 64 */     return Integer.valueOf((keyObject == null) ? 0 : keyObject.hashCode());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoo\\util\v2\LockMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */