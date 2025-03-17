/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class IHExplodingCallLogger
/*    */   implements InvocationHandler
/*    */ {
/*    */   private InvocationHandler delegate;
/*    */   private Logger log;
/*    */   
/*    */   public IHExplodingCallLogger(Object object, Logger log) {
/* 16 */     InvocationHandler handler0 = new IHBackend(object);
/* 17 */     InvocationHandler handler1 = new IHCallLogger(handler0, log);
/* 18 */     this.delegate = handler1;
/* 19 */     this.log = log;
/*    */   }
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 23 */     Object ret = this.delegate.invoke(proxy, method, args);
/* 24 */     if (ret != null && method.getReturnType().isInterface()) {
/* 25 */       Class[] interfazes = (Class[])ReflectionUtil.getAllInterfaces(ret.getClass()).toArray((Object[])new Class[0]);
/* 26 */       if (interfazes != null && interfazes.length > 0) {
/* 27 */         ret = Proxy.newProxyInstance(ret.getClass().getClassLoader(), interfazes, new IHExplodingCallLogger(ret, this.log));
/*    */       }
/*    */     } 
/* 30 */     return ret;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\IHExplodingCallLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */