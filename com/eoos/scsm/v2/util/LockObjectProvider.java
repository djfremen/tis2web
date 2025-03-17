/*    */ package com.eoos.scsm.v2.util;
/*    */ 
/*    */ import com.eoos.scsm.v2.multiton.v4.IMultitonSupport;
/*    */ import com.eoos.scsm.v2.multiton.v4.WeakMultitonSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LockObjectProvider
/*    */ {
/*    */   private WeakMultitonSupport multitonSupport;
/*    */   private Mode mode;
/*    */   
/*    */   private static final class LockObject
/*    */   {
/*    */     private LockObject(Object key) {}
/*    */   }
/*    */   
/*    */   public enum Mode
/*    */   {
/* 22 */     STATE, IDENTITY;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LockObjectProvider(Mode mode) {
/* 30 */     this.multitonSupport = new WeakMultitonSupport(new IMultitonSupport.CreationCallback()
/*    */         {
/*    */           public Object createInstance(Object key) {
/* 33 */             return new LockObjectProvider.LockObject(key);
/*    */           }
/*    */         });
/* 36 */     this.mode = mode;
/*    */   }
/*    */   
/*    */   public LockObjectProvider() {
/* 40 */     this(Mode.STATE);
/*    */   }
/*    */   
/*    */   public Object getLockObject(Object key) {
/* 44 */     Integer actualKey = Integer.valueOf((this.mode == Mode.STATE) ? key.hashCode() : System.identityHashCode(key));
/* 45 */     return this.multitonSupport.getInstance(actualKey, true);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v\\util\LockObjectProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */