/*    */ package com.eoos.gm.tis2web.rpo;
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
/*    */ public class CacheAdapterImpl
/*    */   implements CacheAdapter {
/*    */   private static final class CT_ClearCache implements ClusterTask {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private CT_ClearCache() {}
/*    */     
/*    */     public Object execute() {
/* 22 */       Object retValue = null;
/*    */       try {
/* 24 */         synchronized (DatabaseFacade.class) {
/* 25 */           DatabaseFacade.reset();
/*    */         } 
/* 27 */       } catch (Exception e) {
/* 28 */         retValue = e;
/*    */       } 
/* 30 */       return retValue;
/*    */     }
/*    */   }
/*    */   
/* 34 */   private static final Logger log = Logger.getLogger(CacheAdapterImpl.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 41 */     CT_ClearCache cT_ClearCache = new CT_ClearCache();
/*    */     
/* 43 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ClearCache, principal);
/* 44 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 45 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 46 */       URL url = iter.next();
/* 47 */       if (result.getResult(url) instanceof Exception) {
/* 48 */         log.warn("failed to reset cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 52 */     if (result.getLocalResult() instanceof Exception) {
/* 53 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 58 */     return ApplicationContext.getInstance().getLabel(locale, "cache.rpo.codes");
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */