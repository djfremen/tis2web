/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IHBackend
/*    */   implements InvocationHandler
/*    */ {
/*    */   protected Object delegate;
/*    */   
/*    */   public IHBackend(Object delegate) {
/* 15 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*    */     try {
/* 20 */       return method.invoke(this.delegate, args);
/* 21 */     } catch (InvocationTargetException e) {
/* 22 */       throw e.getCause();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getDelegate() {
/* 31 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 35 */     return this.delegate.toString();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\IHBackend.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */