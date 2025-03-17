/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import com.eoos.math.AverageCalculator;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IHExecutionTimeStatistics
/*    */   implements InvocationHandler
/*    */ {
/*    */   private Callback callback;
/*    */   private IHExecutionTime delegate;
/* 31 */   private Map methodToAverage = Collections.synchronizedMap(new LinkedHashMap<Object, Object>());
/*    */   
/*    */   public IHExecutionTimeStatistics(InvocationHandler backend, Callback callback) {
/* 34 */     this.callback = callback;
/*    */     
/* 36 */     this.delegate = new IHExecutionTime(backend, new IHExecutionTime.Callback()
/*    */         {
/*    */           public synchronized void onSuccessfulExecution(Method m, Object[] args, Object result, long time) {
/* 39 */             AverageCalculator calculator = (AverageCalculator)IHExecutionTimeStatistics.this.methodToAverage.get(m);
/* 40 */             if (calculator == null) {
/* 41 */               calculator = new AverageCalculator(0);
/* 42 */               IHExecutionTimeStatistics.this.methodToAverage.put(m, calculator);
/*    */             } 
/* 44 */             calculator.add(BigDecimal.valueOf(time));
/*    */           }
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void onFailedExecution(Method m, Object[] args, Throwable t, long time) {}
/*    */         });
/* 52 */     this.callback.setTrigger(new Callback.Trigger()
/*    */         {
/*    */           public void calcStatistics() {
/* 55 */             synchronized (IHExecutionTimeStatistics.this.methodToAverage) {
/* 56 */               for (Iterator<Map.Entry> iter = IHExecutionTimeStatistics.this.methodToAverage.entrySet().iterator(); iter.hasNext(); ) {
/* 57 */                 Map.Entry entry = iter.next();
/* 58 */                 Method m = (Method)entry.getKey();
/* 59 */                 AverageCalculator calc = (AverageCalculator)entry.getValue();
/* 60 */                 IHExecutionTimeStatistics.this.callback.executionStatistics(m, calc.getCurrentAverage().longValue(), calc.getValueCount());
/*    */               } 
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 71 */     return this.delegate.invoke(proxy, method, args);
/*    */   } public static interface Callback {
/*    */     void setTrigger(Trigger param1Trigger); void executionStatistics(Method param1Method, long param1Long, BigInteger param1BigInteger); public static interface Trigger {
/*    */       void calcStatistics(); } } public static Object createProxy(Object proxiedObject, Callback callback) {
/* 75 */     if (proxiedObject != null) {
/* 76 */       Class[] interfazes = (Class[])ReflectionUtil.getAllInterfaces(proxiedObject.getClass()).toArray((Object[])new Class[0]);
/* 77 */       if (interfazes != null && interfazes.length > 0) {
/* 78 */         return Proxy.newProxyInstance(proxiedObject.getClass().getClassLoader(), interfazes, new IHExecutionTimeStatistics(new IHBackend(proxiedObject), callback));
/*    */       }
/* 80 */       throw new IllegalArgumentException("class: " + proxiedObject.getClass() + " does not implement any interface");
/*    */     } 
/*    */     
/* 83 */     throw new NullPointerException();
/*    */   }
/*    */   
/*    */   public static interface Trigger {
/*    */     void calcStatistics();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\IHExecutionTimeStatistics.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */