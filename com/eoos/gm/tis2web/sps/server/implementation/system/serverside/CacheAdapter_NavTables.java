/*    */ package com.eoos.gm.tis2web.sps.server.implementation.system.serverside;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.CacheAdapter;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ import java.net.URL;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheAdapter_NavTables
/*    */   implements CacheAdapter, CacheAdapter.Size, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 21 */   private static final Logger log = Logger.getLogger(CacheAdapter_NavTables.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 28 */     return ApplicationContext.getInstance().getLabel(locale, "sps.navtable.cache");
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 33 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 38 */           Object retValue = null;
/*    */           try {
/* 40 */             synchronized (NavigationTableDataStore.class) {
/* 41 */               NavigationTableDataStore.getInstance().reset();
/*    */             } 
/* 43 */           } catch (Exception e) {
/* 44 */             retValue = e;
/*    */           } 
/* 46 */           return retValue;
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 51 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 52 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 53 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 54 */       URL url = iter.next();
/* 55 */       if (result.getResult(url) instanceof Exception) {
/* 56 */         log.warn("failed to clear navigationtable  cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 60 */     if (result.getLocalResult() instanceof Exception) {
/* 61 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 66 */     return NavigationTableDataStore.getInstance().getCacheSize();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\CacheAdapter_NavTables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */