/*    */ package com.eoos.gm.tis2web.acl.implementation.service;
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
/*    */ public class CacheAdapterImpl implements CacheAdapter, CacheAdapter.Size {
/*    */   private static final class CT_ACLCacheReset implements ClusterTask, Serializable {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private CT_ACLCacheReset() {}
/*    */     
/*    */     public Object execute() {
/* 22 */       Object retValue = null;
/*    */       try {
/* 24 */         synchronized (ACLServiceImpl.cache) {
/* 25 */           if (ACLServiceImpl.cache != null) {
/* 26 */             ACLServiceImpl.cache.clear();
/*    */           }
/*    */         } 
/* 29 */       } catch (Exception e) {
/* 30 */         retValue = e;
/*    */       } 
/* 32 */       return retValue;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static final Logger log = Logger.getLogger(CacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 42 */     return ApplicationContext.getInstance().getLabel(locale, "acl.cache");
/*    */   }
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 46 */     CT_ACLCacheReset cT_ACLCacheReset = new CT_ACLCacheReset();
/*    */     
/* 48 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)cT_ACLCacheReset, principal);
/* 49 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 50 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 51 */       URL url = iter.next();
/* 52 */       if (result.getResult(url) instanceof Exception) {
/* 53 */         log.warn("failed to clear acl cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     if (result.getLocalResult() instanceof Exception) {
/* 58 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 64 */     synchronized (ACLServiceImpl.cache) {
/* 65 */       return ACLServiceImpl.cache.size();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\acl\implementation\service\CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */