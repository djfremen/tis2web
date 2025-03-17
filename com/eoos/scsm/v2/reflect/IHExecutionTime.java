/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import com.eoos.util.v2.StopWatch;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IHExecutionTime
/*    */   extends InvocationHandlerWrapper
/*    */ {
/*    */   private Callback callback;
/*    */   
/*    */   public static class CallbackAdapter
/*    */     implements Callback
/*    */   {
/*    */     private Logger log;
/*    */     
/*    */     public CallbackAdapter(Logger log) {
/* 25 */       this.log = log;
/*    */     }
/*    */     
/*    */     public void onSuccessfulExecution(Method m, Object[] args, Object result, long time) {
/* 29 */       this.log.info("exec time for method: " + m.getName() + " - " + time + " ms");
/*    */     }
/*    */     
/*    */     public void onFailedExecution(Method m, Object[] args, Throwable t, long time) {
/* 33 */       this.log.info("exec time for method: " + m.getName() + " - " + time + " ms (exception thrown)");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IHExecutionTime(InvocationHandler backend, Callback callback) {
/* 42 */     super(backend);
/* 43 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 47 */     StopWatch sw = StopWatch.getInstance().start();
/*    */     try {
/* 49 */       Object ret = this.backend.invoke(proxy, method, args);
/* 50 */       this.callback.onSuccessfulExecution(method, args, ret, sw.stop());
/* 51 */       return ret;
/* 52 */     } catch (Throwable t) {
/* 53 */       this.callback.onFailedExecution(method, args, t, sw.stop());
/* 54 */       throw t;
/*    */     } finally {
/* 56 */       StopWatch.freeInstance(sw);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String toString(Object obj) {
/* 61 */     return String.valueOf(obj);
/*    */   }
/*    */   
/*    */   public static Object createProxy(Object proxiedObject, Callback callback) {
/* 65 */     if (proxiedObject != null) {
/* 66 */       Class[] interfazes = (Class[])ReflectionUtil.getAllInterfaces(proxiedObject.getClass()).toArray((Object[])new Class[0]);
/* 67 */       if (interfazes != null && interfazes.length > 0) {
/* 68 */         return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfazes, new IHExecutionTime(new IHBackend(proxiedObject), callback));
/*    */       }
/* 70 */       throw new IllegalArgumentException("class: " + proxiedObject.getClass() + " does not implement any interface");
/*    */     } 
/*    */     
/* 73 */     throw new NullPointerException();
/*    */   }
/*    */   
/*    */   public static interface Callback {
/*    */     void onSuccessfulExecution(Method param1Method, Object[] param1ArrayOfObject, Object param1Object, long param1Long);
/*    */     
/*    */     void onFailedExecution(Method param1Method, Object[] param1ArrayOfObject, Throwable param1Throwable, long param1Long);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\IHExecutionTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */