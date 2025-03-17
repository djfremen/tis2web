/*    */ package com.eoos.scsm.v2.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IHCallLogger
/*    */   extends InvocationHandlerWrapper
/*    */ {
/*    */   private Logger log;
/*    */   
/*    */   public IHCallLogger(InvocationHandler backend, Logger log) {
/* 16 */     super(backend);
/* 17 */     this.log = log;
/*    */   }
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 21 */     this.log.info("invoking method:" + toString(method) + " on: " + toString(this.backend));
/* 22 */     if (args != null) {
/* 23 */       for (int i = 0; i < args.length; i++) {
/* 24 */         this.log.info("...param[" + i + "]: " + toString(args[i]));
/*    */       }
/*    */     }
/*    */     try {
/* 28 */       Object result = super.invoke(proxy, method, args);
/* 29 */       this.log.info("...result: " + toString(result));
/* 30 */       return result;
/* 31 */     } catch (Throwable t) {
/* 32 */       this.log.error("...throws: " + t.getClass().getName() + ": " + t.getMessage(), t);
/* 33 */       throw t;
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String toString(Object obj) {
/* 38 */     return String.valueOf(obj);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\scsm\v2\reflect\IHCallLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */