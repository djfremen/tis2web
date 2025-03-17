/*    */ package com.eoos.gm.tis2web.admin.info.ui;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContextProvider;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CTSessionCount {
/*    */   public static interface Result {
/*    */     int getSessionCount();
/*    */     
/*    */     int getPublicCount();
/*    */   }
/*    */   
/* 20 */   private static final Logger log = Logger.getLogger(CTSessionCount.class);
/*    */   
/*    */   private static final class CT_CountSessions
/*    */     implements ClusterTask
/*    */   {
/*    */     public Object execute() {
/* 26 */       Object ret = null;
/*    */       try {
/* 28 */         ret = new int[] { ClientContextProvider.getInstance().currentSessionCount(), ClientContextProvider.getInstance().currentPublicAccessCount(null) };
/* 29 */       } catch (Exception e) {
/* 30 */         ret = e;
/*    */       } 
/* 32 */       return ret;
/*    */     }
/*    */ 
/*    */     
/*    */     private static final long serialVersionUID = 1L;
/*    */ 
/*    */     
/*    */     private CT_CountSessions() {}
/*    */   }
/*    */   
/*    */   public static Result execute(ClientContext principal) throws Exception {
/* 43 */     CT_CountSessions cT_CountSessions = new CT_CountSessions();
/*    */     
/* 45 */     int sessionCount = 0;
/* 46 */     int publicCount = 0;
/* 47 */     final ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_CountSessions, principal);
/* 48 */     Future<ClusterTaskExecution.Result> future = Util.executeAsynchronous(new Callable<ClusterTaskExecution.Result>()
/*    */         {
/*    */           public ClusterTaskExecution.Result call() throws Exception {
/* 51 */             return clusterTaskExecution.execute();
/*    */           }
/*    */         });
/*    */ 
/*    */     
/*    */     try {
/* 57 */       ClusterTaskExecution.Result result = future.get(20L, TimeUnit.SECONDS);
/*    */       
/* 59 */       for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 60 */         URL url = iter.next();
/* 61 */         if (result.getResult(url) instanceof Exception) {
/* 62 */           log.warn("failed to retrieve session count for cluster server :" + url); continue;
/*    */         } 
/* 64 */         int[] tmp = (int[])result.getResult(url);
/* 65 */         sessionCount += tmp[0];
/* 66 */         publicCount += tmp[1];
/*    */       } 
/*    */ 
/*    */       
/* 70 */       final int a = sessionCount;
/* 71 */       final int b = publicCount;
/* 72 */       return new Result()
/*    */         {
/*    */           public int getSessionCount() {
/* 75 */             return a;
/*    */           }
/*    */           
/*    */           public int getPublicCount() {
/* 79 */             return b;
/*    */           }
/*    */         };
/* 82 */     } catch (TimeoutException e) {
/* 83 */       future.cancel(true);
/* 84 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\admin\inf\\ui\CTSessionCount.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */