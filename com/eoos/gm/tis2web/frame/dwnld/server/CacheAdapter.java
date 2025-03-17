/*    */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.CacheAdapter;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class CacheAdapter
/*    */   implements CacheAdapter {
/*    */   private static final class MyClusterTask implements ClusterTask {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public Object execute() {
/* 20 */       Object retValue = null;
/*    */       try {
/* 22 */         DatabaseAdapter.reset();
/* 23 */       } catch (Exception e) {
/* 24 */         retValue = e;
/*    */       } 
/* 26 */       return retValue;
/*    */     }
/*    */     
/*    */     private MyClusterTask() {} }
/* 30 */   private static final Logger log = Logger.getLogger(CacheAdapter.class);
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 33 */     MyClusterTask myClusterTask = new MyClusterTask();
/*    */     
/* 35 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)myClusterTask, principal);
/* 36 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 37 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 38 */       URL url = iter.next();
/* 39 */       if (result.getResult(url) instanceof Exception) {
/* 40 */         log.warn("failed to clear dwnld cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 44 */     if (result.getLocalResult() instanceof Exception) {
/* 45 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 51 */     return ApplicationContext.getInstance().getLabel(locale, "cache.description.dwnld.service.cache");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\CacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */