/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvocationHandlerWrapper
/*    */   implements InvocationHandler
/*    */ {
/* 11 */   protected InvocationHandler backend = null;
/*    */   
/*    */   public InvocationHandlerWrapper(InvocationHandler backend) {
/* 14 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 18 */     return this.backend.invoke(proxy, method, args);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\InvocationHandlerWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */