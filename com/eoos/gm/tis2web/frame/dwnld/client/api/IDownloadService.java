/*     */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*     */ 
/*     */ import com.eoos.gm.tis2web.common.HostInfo;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface IDownloadService
/*     */ {
/*  92 */   public static final DwnldFilter FILTER_KDR_DATA_DELIVERABLE = (DwnldFilter)ClassificationFilter.KDR_DATA_DELIVERABLE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public static final DwnldFilter FILTER_KDR_DATA_DELIVERABLE_DESCRIPTOR = (DwnldFilter)ClassificationFilter.KDR_DATA_DELIVERABLE_DESCRIPTOR;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public static final DwnldFilter FILTER_KDR_CORE = (DwnldFilter)ClassificationFilter.KDR_CORE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final DwnldFilter FILTER_JRE = (DwnldFilter)ClassificationFilter.JRE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final DwnldFilter FILTER_MDI = (DwnldFilter)ClassificationFilter.MDI;
/*     */   
/*     */   DwnldFilter createVersionFilter(String paramString);
/*     */   
/*     */   Collection getDownloadUnits(Collection paramCollection);
/*     */   
/*     */   IDownloadPackage createPackage(Collection paramCollection, File paramFile) throws IOException;
/*     */   
/*     */   IDownloadUnit getNewestDownloadUnit(Collection paramCollection);
/*     */   
/*     */   void dropPackage(IDownloadPackage paramIDownloadPackage, boolean paramBoolean);
/*     */   
/*     */   IDownloadPackage getPackage(Identifier paramIdentifier);
/*     */   
/*     */   Collection getPackages();
/*     */   
/*     */   void resumeDownloads(DownloadObserver paramDownloadObserver);
/*     */   
/*     */   IDownloadStatus getStatus(IDownloadPackage paramIDownloadPackage);
/*     */   
/*     */   IDownloadStatus getStatus(IDownloadPackage paramIDownloadPackage, IDownloadUnit paramIDownloadUnit);
/*     */   
/*     */   IGDSFacade getGDSFacade();
/*     */   
/*     */   void startOrResumeDwnld(IDownloadPackage paramIDownloadPackage, DownloadObserver paramDownloadObserver);
/*     */   
/*     */   void cancelDownload(IDownloadPackage paramIDownloadPackage);
/*     */   
/*     */   void download(IDownloadUnit paramIDownloadUnit, IDownloadFile paramIDownloadFile, OutputStream paramOutputStream) throws Exception;
/*     */   
/*     */   void downloadPackage(IDownloadPackage paramIDownloadPackage) throws Exception;
/*     */   
/*     */   void waitForCompletion(IDownloadPackage paramIDownloadPackage);
/*     */   
/*     */   void cancelAll();
/*     */   
/*     */   public static interface Callback {
/*     */     Authentication getProxyAuthentication(HostInfo param1HostInfo);
/*     */   }
/*     */   
/*     */   public static interface DownloadObserver {
/*     */     void onFinished(IDownloadPackage param1IDownloadPackage);
/*     */     
/*     */     void onFinished(IDownloadPackage param1IDownloadPackage, IDownloadUnit param1IDownloadUnit);
/*     */     
/*     */     void onFinished(IDownloadPackage param1IDownloadPackage, IDownloadUnit param1IDownloadUnit, IDownloadFile param1IDownloadFile);
/*     */     
/*     */     void onError(IDownloadPackage param1IDownloadPackage, Exception param1Exception);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IDownloadService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */