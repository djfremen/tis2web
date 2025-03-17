/*    */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadObserverAdapter
/*    */   implements IDownloadService.DownloadObserver
/*    */ {
/* 15 */   public static final DownloadObserverAdapter DUMMY = new DownloadObserverAdapter();
/*    */   
/*    */   public void onError(IDownloadPackage pkg, Exception e) {}
/*    */   
/*    */   public void onFinished(IDownloadPackage pkg) {}
/*    */   
/*    */   public void onFinished(IDownloadPackage pkg, IDownloadUnit unit) {}
/*    */   
/*    */   public void onFinished(IDownloadPackage pkg, IDownloadUnit unit, IDownloadFile file) {}
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\DownloadObserverAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */