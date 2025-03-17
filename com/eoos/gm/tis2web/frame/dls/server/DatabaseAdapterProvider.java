/*    */ package com.eoos.gm.tis2web.frame.dls.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.Tis2webUtil;
/*    */ import com.eoos.scsm.v2.reflect.IHExecutionTime;
/*    */ import com.eoos.scsm.v2.reflect.ReflectionUtil;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DatabaseAdapterProvider
/*    */ {
/* 16 */   private static IDatabase instance = null;
/*    */   
/* 18 */   private static List cachedMethodNames = Arrays.asList(new String[] { "getUserGroup", "getSessionID" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized IDatabase getDatabaseAdapter() {
/* 25 */     if (instance == null) {
/* 26 */       instance = DatabaseAdapter.getInstance();
/*    */       
/* 28 */       instance = (IDatabase)ReflectionUtil.createCachingProxy(instance, Tis2webUtil.createStdCache(), (ReflectionUtil.CachingProxyCallback)new ReflectionUtil.CachingProxyCallbackAdapter()
/*    */           {
/*    */             public boolean enableCaching(Method m) {
/* 31 */               return DatabaseAdapterProvider.cachedMethodNames.contains(m.getName());
/*    */             }
/*    */             
/*    */             public Object createKey(Method m, Object[] args) {
/* 35 */               return ReflectionUtil.CachingProxyCallback.STD.createKey(m, args);
/*    */             }
/*    */           });
/*    */       
/* 39 */       if (ApplicationContext.getInstance().developMode()) {
/* 40 */         instance = (IDatabase)IHExecutionTime.createProxy(instance, (IHExecutionTime.Callback)new IHExecutionTime.CallbackAdapter(Logger.getLogger("performance." + DatabaseAdapter.class.getName())));
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\DatabaseAdapterProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */