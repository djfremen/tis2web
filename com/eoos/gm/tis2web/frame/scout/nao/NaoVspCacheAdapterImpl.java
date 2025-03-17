/*    */ package com.eoos.gm.tis2web.frame.scout.nao;
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
/*    */ public class NaoVspCacheAdapterImpl
/*    */   implements CacheAdapter, CacheAdapter.Size, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   private static final class CT_ClearCache implements ClusterTask {
/*    */     public Object execute() {
/* 21 */       Object retValue = null;
/*    */       try {
/* 23 */         NaoVspCacheAdapterImpl.log.info("Performing NaoVspScout GroupMap reset ...");
/* 24 */         NaoVspScout.resetGroupMapData();
/* 25 */         NaoVspCacheAdapterImpl.log.info("NaoVspScout GroupMap reset completed.");
/* 26 */       } catch (Exception e) {
/* 27 */         retValue = e;
/*    */       } 
/* 29 */       return retValue;
/*    */     }
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private CT_ClearCache() {} }
/*    */   
/* 35 */   private static final Logger log = Logger.getLogger(NaoVspCacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 38 */     return ApplicationContext.getInstance().getLabel(locale, "scout.nao.naovspscout.groupmapcache");
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
/* 50 */         log.warn("failed to clear groupmap cache (NaoVspScout) for cluster server :" + url);
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
/* 61 */     synchronized (NaoVspScout.SYNC_GROUPMAPDATA) {
/* 62 */       if (NaoVspScout.groupMapData != null) {
/* 63 */         return NaoVspScout.groupMapData.size();
/*    */       }
/* 65 */       return 0;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\nao\NaoVspCacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */