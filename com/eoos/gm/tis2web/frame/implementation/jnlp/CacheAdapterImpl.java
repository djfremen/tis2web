/*    */ package com.eoos.gm.tis2web.frame.implementation.jnlp;
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
/*    */ public class CacheAdapterImpl
/*    */   implements CacheAdapter, CacheAdapter.Size, Serializable {
/*    */   private static final long serialVersionUID = 1L;
/* 18 */   private static final Logger log = Logger.getLogger(CacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 21 */     return ApplicationContext.getInstance().getLabel(locale, "jnlp.resource.catalog");
/*    */   }
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 25 */     log.info("creating cluster task for jnlp cache reset");
/* 26 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 31 */           CacheAdapterImpl.log.info("clearing jnlp cache");
/* 32 */           Object retValue = null;
/*    */           try {
/* 34 */             synchronized (ResourceCatalog.class) {
/* 35 */               if ((ResourceCatalog.getInstance()).resources != null) {
/* 36 */                 CacheAdapterImpl.log.info("...reloading resource catalog");
/* 37 */                 ResourceCatalog.getInstance().reload();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */               
/*    */               }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */             
/*    */             }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           }
/* 65 */           catch (Exception e) {
/* 66 */             retValue = e;
/*    */           } 
/* 68 */           return retValue;
/*    */         }
/*    */       };
/*    */     
/* 72 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/*    */     
/* 74 */     log.info("...executing task");
/* 75 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 76 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 77 */       URL url = iter.next();
/* 78 */       if (result.getResult(url) instanceof Exception) {
/* 79 */         log.warn("...failed to clear jnlp cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 83 */     if (result.getLocalResult() instanceof Exception) {
/* 84 */       log.debug("...local invocation raised exception, rethrowing");
/* 85 */       throw (Exception)result.getLocalResult();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 90 */     if ((ResourceCatalog.getInstance()).resources != null) {
/* 91 */       synchronized ((ResourceCatalog.getInstance()).resources) {
/* 92 */         return (ResourceCatalog.getInstance()).resources.size();
/*    */       } 
/*    */     }
/* 95 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\implementation\jnlp\CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */