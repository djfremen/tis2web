/*    */ package com.eoos.scsm.v2.multiton.v4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IMultitonSupport
/*    */ {
/*    */   public static final class ArgumentType
/*    */   {
/*    */     private ArgumentType() {}
/*    */   }
/*    */   
/*    */   public static final class Decorator
/*    */   {
/*    */     public static boolean existsInstance(Object key, IMultitonSupport msupport) {
/* 15 */       return (msupport.getInstance(key, false) != null);
/*    */     }
/*    */   }
/*    */   
/* 19 */   public static final ArgumentType KEY = new ArgumentType();
/*    */   
/* 21 */   public static final ArgumentType INSTANCE = new ArgumentType();
/*    */   
/*    */   Object getInstance(Object paramObject, boolean paramBoolean);
/*    */   
/*    */   void removeInstance(Object paramObject, ArgumentType paramArgumentType);
/*    */   
/*    */   void visitInstances(Visitor paramVisitor);
/*    */   
/*    */   public static interface Visitor {
/*    */     void onVisit(Object param1Object);
/*    */   }
/*    */   
/*    */   public static interface CreationCallback {
/*    */     Object createInstance(Object param1Object);
/*    */   }
/*    */   
/*    */   public static interface CreationCallbackExt extends CreationCallback {
/*    */     Object createStorageReplacement(Object param1Object);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\multiton\v4\IMultitonSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */