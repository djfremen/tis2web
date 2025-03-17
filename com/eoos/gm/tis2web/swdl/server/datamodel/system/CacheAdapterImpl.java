/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClusterTaskExecution;
/*    */ import com.eoos.gm.tis2web.frame.export.common.util.CacheAdapter;
/*    */ import com.eoos.gm.tis2web.frame.servlet.ClusterTask;
/*    */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter;
/*    */ import com.eoos.gm.tis2web.swdl.server.db.DatabaseAdapter_CacheImpl;
/*    */ import com.eoos.util.Task;
/*    */ import java.io.Serializable;
/*    */ import java.net.URL;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheAdapterImpl
/*    */   implements CacheAdapter, CacheAdapter.Size, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 25 */   private static final Logger log = Logger.getLogger(CacheAdapterImpl.class);
/*    */   
/*    */   public String getCacheDescription(Locale locale) {
/* 28 */     return ApplicationContext.getInstance().getLabel(locale, "swdl.filecache");
/*    */   }
/*    */   
/*    */   public void clear(ClientContext principal) throws Exception {
/* 32 */     ClusterTask clusterTask = new ClusterTask()
/*    */       {
/*    */         private static final long serialVersionUID = 1L;
/*    */         
/*    */         public Object execute() {
/* 37 */           synchronized (CacheAdapterImpl.class) {
/* 38 */             Object retValue = null;
/*    */             try {
/* 40 */               Collection adapterMetaSet = (DeviceRegistry.getInstance().getState()).deviceToDBAdapters.values();
/* 41 */               synchronized (adapterMetaSet) {
/* 42 */                 for (Iterator<Set> iterMetaSet = adapterMetaSet.iterator(); iterMetaSet.hasNext(); ) {
/* 43 */                   Set adapterSet = iterMetaSet.next();
/* 44 */                   synchronized (adapterSet) {
/* 45 */                     for (Iterator<DatabaseAdapter> iterAdapterSet = adapterSet.iterator(); iterAdapterSet.hasNext(); ) {
/* 46 */                       DatabaseAdapter adapter = iterAdapterSet.next();
/* 47 */                       if (adapter instanceof DatabaseAdapter_CacheImpl) {
/* 48 */                         ((DatabaseAdapter_CacheImpl)adapter).clearCache();
/*    */                       }
/*    */                     } 
/*    */                   } 
/*    */                 } 
/*    */               } 
/* 54 */             } catch (Exception e) {
/* 55 */               retValue = e;
/*    */             } 
/* 57 */             return retValue;
/*    */           } 
/*    */         }
/*    */       };
/*    */ 
/*    */     
/* 63 */     ClusterTaskExecution clusterTaskExecution = new ClusterTaskExecution((Task)clusterTask, principal);
/* 64 */     ClusterTaskExecution.Result result = clusterTaskExecution.execute();
/* 65 */     for (Iterator<URL> iter = result.getClusterURLs().iterator(); iter.hasNext(); ) {
/* 66 */       URL url = iter.next();
/* 67 */       if (result.getResult(url) instanceof Exception) {
/* 68 */         log.warn("failed to clear swdl cache for cluster server :" + url);
/*    */       }
/*    */     } 
/*    */     
/* 72 */     if (result.getLocalResult() instanceof Exception) {
/* 73 */       throw (Exception)result.getLocalResult();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSize() {
/* 79 */     int retValue = 0;
/* 80 */     Collection adapterMetaSet = (DeviceRegistry.getInstance().getState()).deviceToDBAdapters.values();
/* 81 */     synchronized (adapterMetaSet) {
/* 82 */       for (Iterator<Set> iterMetaSet = adapterMetaSet.iterator(); iterMetaSet.hasNext(); ) {
/* 83 */         Set adapterSet = iterMetaSet.next();
/* 84 */         synchronized (adapterSet) {
/* 85 */           for (Iterator<DatabaseAdapter> iterAdapterSet = adapterSet.iterator(); iterAdapterSet.hasNext(); ) {
/* 86 */             DatabaseAdapter adapter = iterAdapterSet.next();
/* 87 */             if (adapter instanceof DatabaseAdapter_CacheImpl) {
/* 88 */               retValue += ((DatabaseAdapter_CacheImpl)adapter).cacheSize();
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 94 */     return retValue;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\CacheAdapterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */