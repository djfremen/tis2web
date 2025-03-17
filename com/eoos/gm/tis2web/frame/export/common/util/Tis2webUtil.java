/*    */ package com.eoos.gm.tis2web.frame.export.common.util;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.html.base.ClientContextBase;
/*    */ import com.eoos.scsm.v2.cache.Cache;
/*    */ import com.eoos.scsm.v2.cache.HotspotCache2;
/*    */ import com.eoos.scsm.v2.cache.SoftCache;
/*    */ import com.eoos.scsm.v2.reflect.IHExecutionTimeStatistics;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.lang.reflect.Method;
/*    */ import java.math.BigInteger;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tis2webUtil
/*    */ {
/* 19 */   private static final Logger log = Logger.getLogger(Tis2webUtil.class);
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
/*    */   public static Object hookWithExecutionTimeStatistics(final Object object, final ClientContext context) {
/* 33 */     return IHExecutionTimeStatistics.createProxy(object, new IHExecutionTimeStatistics.Callback()
/*    */         {
/*    */           public void executionStatistics(Method m, long averageResponseTime, BigInteger calls) {
/* 36 */             Tis2webUtil.log.debug("average execution time for method: " + Util.getClassName(m.getDeclaringClass()) + "." + m.getName() + " (" + Util.toString(object) + "): " + averageResponseTime + " ms (total calls: " + calls + ")");
/*    */           }
/*    */           
/*    */           public void setTrigger(final IHExecutionTimeStatistics.Callback.Trigger trigger) {
/* 40 */             if (context != null) {
/* 41 */               context.addLogoutListener(new ClientContextBase.LogoutListener()
/*    */                   {
/*    */                     public void onLogout() {
/* 44 */                       trigger.calcStatistics();
/*    */                     }
/*    */                   });
/*    */             } else {
/*    */               
/* 49 */               ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*    */                   {
/*    */                     public void onShutdown() {
/* 52 */                       trigger.calcStatistics();
/*    */                     }
/*    */                   });
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Object hookWithExecutionTimeStatistics(Object object) {
/* 69 */     return hookWithExecutionTimeStatistics(object, null);
/*    */   }
/*    */   
/*    */   public static String getPortalID(ClientContext context) {
/* 73 */     String ret = context.getSessionID();
/* 74 */     ret = ret.substring(0, ret.indexOf('.'));
/* 75 */     return ret;
/*    */   }
/*    */   
/*    */   public static Cache createStdCache() {
/* 79 */     return (Cache)new SoftCache();
/*    */   }
/*    */   
/*    */   public static Cache createHotspotCache(long maxIdle) {
/* 83 */     return (Cache)new HotspotCache2(maxIdle);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\Tis2webUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */