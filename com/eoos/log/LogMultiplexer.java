/*    */ package com.eoos.log;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogMultiplexer
/*    */   implements ILogger
/*    */ {
/* 14 */   private ILogger dynProxy = (ILogger)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { ILogger.class }, new InvocationHandler()
/*    */       {
/*    */         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 17 */           for (int i = 0; i < logs.length; i++) {
/* 18 */             method.invoke(logs[i], args);
/*    */           }
/* 20 */           return null;
/*    */         }
/*    */       });
/*    */ 
/*    */   
/*    */   public static ILogger create(Logger[] logs) {
/* 26 */     ILogger[] _logs = new ILogger[logs.length];
/* 27 */     for (int i = 0; i < logs.length; i++) {
/* 28 */       _logs[i] = new Adapter(logs[i]);
/*    */     }
/* 30 */     return new LogMultiplexer(_logs);
/*    */   }
/*    */   public LogMultiplexer(final ILogger[] logs) {}
/*    */   public void debug(Object message) {
/* 34 */     this.dynProxy.debug(message);
/*    */   }
/*    */   
/*    */   public void debug(Object message, Throwable t) {
/* 38 */     this.dynProxy.debug(message, t);
/*    */   }
/*    */   
/*    */   public void info(Object message) {
/* 42 */     this.dynProxy.info(message);
/*    */   }
/*    */   
/*    */   public void info(Object message, Throwable t) {
/* 46 */     this.dynProxy.info(message, t);
/*    */   }
/*    */   
/*    */   public void warn(Object message) {
/* 50 */     this.dynProxy.warn(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void warn(Object message, Throwable t) {
/* 55 */     this.dynProxy.warn(message, t);
/*    */   }
/*    */   
/*    */   public void error(Object message) {
/* 59 */     this.dynProxy.error(message);
/*    */   }
/*    */ 
/*    */   
/*    */   public void error(Object message, Throwable t) {
/* 64 */     this.dynProxy.error(message, t);
/*    */   }
/*    */   
/*    */   public void fatal(Object message) {
/* 68 */     this.dynProxy.fatal(message);
/*    */   }
/*    */   
/*    */   public void fatal(Object message, Throwable t) {
/* 72 */     this.dynProxy.fatal(message, t);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\log\LogMultiplexer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */