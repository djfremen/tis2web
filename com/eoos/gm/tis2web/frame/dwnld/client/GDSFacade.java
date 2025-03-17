/*     */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DownloadServiceUtil;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.DwnldFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDeliverableDescriptor;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadService;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IGDSFacade;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.ClassificationFilter;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadFile;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadUnit;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.common.IDownloadServer;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class GDSFacade
/*     */   implements IGDSFacade {
/*  28 */   private static final Logger log = Logger.getLogger(GDSFacade.class);
/*     */   
/*     */   private DownloadManager downloadManager;
/*     */   
/*     */   public GDSFacade(DownloadManager downloadManager) {
/*  33 */     this.downloadManager = downloadManager;
/*     */   }
/*     */   
/*     */   public IDownloadPackage createPackage(IDeliverableDescriptor descriptor, File destDir) throws IOException {
/*  37 */     return createPackage(Collections.singleton(descriptor), destDir);
/*     */   }
/*     */   
/*     */   public IDownloadPackage createPackage(Collection descriptors, File destDir) throws IOException {
/*     */     try {
/*  42 */       Collection<DownloadUnit> originalUnits = new HashSet();
/*  43 */       for (Iterator<DeliverableDescriptor> iter = descriptors.iterator(); iter.hasNext();) {
/*  44 */         originalUnits.add(((DeliverableDescriptor)iter.next()).getDownloadUnit());
/*     */       }
/*  46 */       Collection relatedUnits = this.downloadManager.getRelatedDownloadUnits(originalUnits);
/*  47 */       return this.downloadManager.createPackage(relatedUnits, destDir);
/*  48 */     } catch (IOException e) {
/*  49 */       log.error("unable to create package for descriptor(s): " + String.valueOf(descriptors) + ", rethrowing exception: " + e, e);
/*  50 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection getAvailableCoreVersions() {
/*  55 */     return this.downloadManager.getDownloadUnits(Collections.singleton(IDownloadService.FILTER_KDR_CORE));
/*     */   }
/*     */   
/*     */   public Collection getAvailableJREVersions() {
/*  59 */     return this.downloadManager.getDownloadUnits(DownloadServiceUtil.toGDSFilter(IDownloadService.FILTER_JRE));
/*     */   }
/*     */   
/*     */   public Collection getAvailableMDIVersions() {
/*  63 */     return this.downloadManager.getDownloadUnits(DownloadServiceUtil.toGDSFilter(IDownloadService.FILTER_MDI));
/*     */   }
/*     */   
/*     */   public Collection getDeliverableDescriptors() {
/*  67 */     log.debug("retrieving list of data deliverable descriptors...");
/*  68 */     Set<DeliverableDescriptor> ret = new LinkedHashSet();
/*     */     try {
/*  70 */       log.debug("...requesting meta data");
/*  71 */       Collection<DwnldFilter> filter = Collections.singleton(IDownloadService.FILTER_KDR_DATA_DELIVERABLE_DESCRIPTOR);
/*  72 */       Collection tmp = this.downloadManager.getDownloadUnits(filter);
/*  73 */       log.debug("...retrieving  " + tmp.size() + " descriptors");
/*  74 */       for (Iterator<DownloadUnit> iter = tmp.iterator(); iter.hasNext(); ) {
/*  75 */         DownloadUnit unit = iter.next();
/*  76 */         DownloadFile file = unit.getFiles().iterator().next();
/*  77 */         byte[] data = null;
/*  78 */         log.debug("...retrieving data for descriptor: " + unit);
/*  79 */         List downloadServers = this.downloadManager.getDownloadServers(unit);
/*     */         
/*  81 */         for (Iterator<IDownloadServer> iterURLS = downloadServers.iterator(); iterURLS.hasNext() && data == null; ) {
/*  82 */           IDownloadServer server = iterURLS.next();
/*     */           try {
/*  84 */             ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*     */             try {
/*  86 */               log.debug("...downloading from server: " + server);
/*  87 */               this.downloadManager.downloadFile(file, server, baos);
/*     */             } finally {
/*  89 */               baos.close();
/*     */             } 
/*  91 */             data = baos.toByteArray();
/*  92 */           } catch (Exception e) {
/*  93 */             log.warn("failed to download data from url: " + server + " - exception: " + e, e);
/*  94 */             if (!iterURLS.hasNext()) {
/*  95 */               throw e;
/*     */             }
/*  97 */             log.debug("...trying next server");
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 102 */         ret.add(new DeliverableDescriptor(unit, data));
/*     */       } 
/* 104 */       return ret;
/* 105 */     } catch (Exception e) {
/* 106 */       log.error("unable to retrieve data deliverable descriptors, returning empty list - exception:" + e, e);
/* 107 */       return Collections.EMPTY_SET;
/*     */     } 
/*     */   }
/*     */   
/*     */   public IDownloadUnit getNewestCoreVersion() {
/* 112 */     return this.downloadManager.getNewestDownloadUnit(Collections.singleton(ClassificationFilter.KDR_CORE));
/*     */   }
/*     */   
/*     */   public IDownloadUnit getNewestJREVersion() {
/* 116 */     return this.downloadManager.getNewestDownloadUnit(DownloadServiceUtil.toGDSFilter((DwnldFilter)ClassificationFilter.JRE));
/*     */   }
/*     */   
/*     */   public IDownloadUnit getNewestMDIVersion() {
/* 120 */     return this.downloadManager.getNewestDownloadUnit(DownloadServiceUtil.toGDSFilter((DwnldFilter)ClassificationFilter.MDI));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\GDSFacade.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */