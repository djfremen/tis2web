/*    */ package com.eoos.gm.tis2web.frame.scout.gme;
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
/*    */ public class GmeCh21CacheAdapterImpl
/*    */   implements CacheAdapter, CacheAdapter.Size, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 19 */   private static final Logger log = Logger.getLogger(GmeCh21CacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 22 */     return ApplicationContext.getInstance().getLabel(locale, "scout.gme.gmech21scout.groupmapcache");
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 27 */     ClusterTask clusterTask = new ClusterTask() {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 31 */           Object retValue = null;
/*    */           try {
/* 33 */             GmeCh21CacheAdapterImpl.log.info("Performing GmeCh21Scout GroupMap reset ...");
/* 34 */             GmeCh21Scout.resetGroupMapData();
/* 35 */             GmeCh21CacheAdapterImpl.log.info("GmeCh21Scout GroupMap reset completed.");
/* 36 */           } catch (Exception e) {
/* 37 */             retValue = e;
/*    */           } 
/* 39 */           return retValue;
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 44 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 45 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 46 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 47 */       URL url = iter.next();
/* 48 */       if (result.getResult(url) instanceof Exception) {
/* 49 */         log.warn("failed to clear groupmap cache (GmeCh21Scout) for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 53 */     if (result.getLocalResult() instanceof Exception) {
/* 54 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 60 */     synchronized (GmeCh21Scout.SYNC_GROUPMAPDATA) {
/* 61 */       if (GmeCh21Scout.groupMapData != null) {
/* 62 */         return GmeCh21Scout.groupMapData.size();
/*    */       }
/* 64 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeCh21CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */