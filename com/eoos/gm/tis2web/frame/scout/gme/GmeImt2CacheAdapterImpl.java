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
/*    */ public class GmeImt2CacheAdapterImpl implements CacheAdapter, CacheAdapter.Size, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private static final class CT_ClearCache implements ClusterTask {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public Object execute() {
/* 22 */       Object retValue = null;
/*    */       try {
/* 24 */         GmeImt2CacheAdapterImpl.log.info("Performing GmeImt2Scout GroupMap reset ...");
/* 25 */         GmeImt2Scout.resetGroupMapData();
/* 26 */         GmeImt2CacheAdapterImpl.log.info("GmeImt2Scout GroupMap reset completed.");
/* 27 */       } catch (Exception e) {
/* 28 */         retValue = e;
/*    */       } 
/* 30 */       return retValue;
/*    */     }
/*    */     
/*    */     private CT_ClearCache() {}
/*    */   }
/* 35 */   private static final Logger log = Logger.getLogger(GmeImt2CacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 38 */     return ApplicationContext.getInstance().getLabel(locale, "scout.gme.gmeimt2scout.groupmapcache");
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 43 */     CT_ClearCache cT_ClearCache = new CT_ClearCache();
/*    */     
/* 45 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ClearCache, principal);
/* 46 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 47 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 48 */       URL url = iter.next();
/* 49 */       if (result.getResult(url) instanceof Exception) {
/* 50 */         log.warn("failed to clear groupmap cache (GmeImt2Scout) for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 54 */     if (result.getLocalResult() instanceof Exception) {
/* 55 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 61 */     synchronized (GmeImt2Scout.SYNC_GROUPMAPDATA) {
/* 62 */       if (GmeImt2Scout.groupMapData != null) {
/* 63 */         return GmeImt2Scout.groupMapData.size();
/*    */       }
/* 65 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\gme\GmeImt2CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */