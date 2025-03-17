/*     */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*     */ 
/*     */ import com.eoos.datatype.IVersionNumber;
/*     */ import com.eoos.datatype.VersionNumber;
/*     */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IInstalledVersionLookup;
/*     */ import com.eoos.scsm.v2.util.I15dText;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DownloadUnit
/*     */   extends DownloadUnitBase
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private long identifier;
/*     */   private VersionNumber versionNumber;
/*     */   private long releaseDate;
/*     */   private Collection aclTags;
/*     */   private I15dText description;
/*     */   private Collection downloadServers;
/*     */   private Collection files;
/*  31 */   private final Object SYNC_SIZE = new Serializable()
/*     */     {
/*     */       private static final long serialVersionUID = 1L;
/*     */     };
/*  35 */   private long totalBytes = -1L;
/*     */   
/*  37 */   private long totalBytesTransfer = -1L;
/*     */   
/*     */   private RegistryVersionLookupContainer registryLookup;
/*     */   
/*     */   private Collection classifiers;
/*     */   
/*     */   public DownloadUnit(long identifier, VersionNumber versionNumber, long releaseDate, Collection aclTags, I15dText description, Collection downloadServers, Collection files, RegistryVersionLookupContainer registryLookup, Collection classifiers) {
/*  44 */     this.identifier = identifier;
/*  45 */     this.versionNumber = versionNumber;
/*  46 */     this.releaseDate = releaseDate;
/*  47 */     this.aclTags = aclTags;
/*  48 */     this.description = description;
/*  49 */     this.downloadServers = downloadServers;
/*  50 */     this.files = files;
/*  51 */     this.registryLookup = registryLookup;
/*  52 */     this.classifiers = classifiers;
/*     */   }
/*     */   
/*     */   public String getDescripition(Locale locale) {
/*  56 */     return this.description.getText(locale);
/*     */   }
/*     */   
/*     */   public Collection getFiles() {
/*  60 */     return this.files;
/*     */   }
/*     */   
/*     */   public long getIdentifier() {
/*  64 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public long getReleaseDate() {
/*  68 */     return this.releaseDate;
/*     */   }
/*     */   
/*     */   private void calcSizes() {
/*  72 */     long size = 0L;
/*  73 */     long sizeTransfer = 0L;
/*  74 */     for (Iterator<DownloadFile> iter = this.files.iterator(); iter.hasNext(); ) {
/*  75 */       DownloadFile file = iter.next();
/*  76 */       size += file.getSize();
/*  77 */       if (file.isCompressed()) {
/*  78 */         sizeTransfer += file.getCompressedSize(); continue;
/*     */       } 
/*  80 */       sizeTransfer += file.getSize();
/*     */     } 
/*     */     
/*  83 */     this.totalBytes = size;
/*  84 */     this.totalBytesTransfer = sizeTransfer;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getTotalBytes() {
/*  89 */     synchronized (this.SYNC_SIZE) {
/*  90 */       if (this.totalBytes == -1L) {
/*  91 */         calcSizes();
/*     */       }
/*  93 */       return this.totalBytes;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getTotalBytesTransfer() {
/*  98 */     synchronized (this.SYNC_SIZE) {
/*  99 */       if (this.totalBytesTransfer == -1L) {
/* 100 */         calcSizes();
/*     */       }
/* 102 */       return this.totalBytesTransfer;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IVersionNumber getVersionNumber() {
/* 108 */     return (IVersionNumber)this.versionNumber;
/*     */   }
/*     */   
/*     */   public Collection getACLTags() {
/* 112 */     return this.aclTags;
/*     */   }
/*     */   
/*     */   public Collection<DownloadServer> getDownloadServers() {
/* 116 */     return this.downloadServers;
/*     */   }
/*     */   
/*     */   public IInstalledVersionLookup getInstalledVersionLookup() {
/* 120 */     return this.registryLookup;
/*     */   }
/*     */   
/*     */   public Collection getClassfiers() {
/* 124 */     return this.classifiers;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\DownloadUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */