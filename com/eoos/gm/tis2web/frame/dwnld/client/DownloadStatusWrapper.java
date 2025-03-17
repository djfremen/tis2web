/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadStatus;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DownloadStatusWrapper
/*    */   implements IDownloadStatus
/*    */ {
/*    */   private static final long VALIDITY = 900L;
/* 14 */   private static final Map instances = new HashMap<Object, Object>();
/*    */   
/*    */   private DownloadManager manager;
/*    */   
/*    */   private IDownloadPackage pkg;
/*    */   
/*    */   private IDownloadUnit unit;
/*    */   
/* 22 */   private IDownloadStatus status = null;
/*    */   
/* 24 */   private long timestamp = 0L;
/*    */   
/*    */   private DownloadStatusWrapper(DownloadManager manager, IDownloadPackage pkg, IDownloadUnit unit) {
/* 27 */     this.manager = manager;
/* 28 */     this.pkg = pkg;
/* 29 */     this.unit = unit;
/*    */   }
/*    */   
/*    */   static synchronized DownloadStatusWrapper getInstance(DownloadManager manager, IDownloadPackage pkg, IDownloadUnit unit) {
/* 33 */     Object key = CollectionUtil.toList(new Object[] { pkg, unit });
/* 34 */     DownloadStatusWrapper ret = (DownloadStatusWrapper)instances.get(key);
/* 35 */     if (ret == null) {
/* 36 */       ret = new DownloadStatusWrapper(manager, pkg, unit);
/* 37 */       instances.put(key, ret);
/*    */     } 
/* 39 */     return ret.update();
/*    */   }
/*    */   
/*    */   private synchronized DownloadStatusWrapper update() {
/* 43 */     if (this.status == null || System.currentTimeMillis() - this.timestamp > 900L) {
/* 44 */       if (this.unit != null) {
/* 45 */         this.status = this.manager.calcStatus(this.pkg, this.unit);
/*    */       } else {
/* 47 */         this.status = this.manager.calcStatus(this.pkg);
/*    */       } 
/* 49 */       this.timestamp = System.currentTimeMillis();
/*    */     } 
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized int getPercentage() {
/* 56 */     return this.status.getPercentage();
/*    */   }
/*    */   
/*    */   public synchronized long getRemainingTimeEstimate() {
/* 60 */     return this.status.getRemainingTimeEstimate();
/*    */   }
/*    */   
/*    */   public synchronized long getTransferredByteCount() {
/* 64 */     return this.status.getTransferredByteCount();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\DownloadStatusWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */