/*     */ package com.eoos.gm.tis2web.sps.common.implementation;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.export.SPSBlob;
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.DownloadServer;
/*     */ import com.eoos.util.HashCalc;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSBlobImpl
/*     */   implements SPSBlob, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private String name;
/*     */   private Integer identifier;
/*     */   private Integer size;
/*     */   private byte[] checksum;
/*     */   private byte[] data;
/*     */   private DownloadServer downloadSite;
/*     */   private String downloadID;
/*     */   
/*     */   public SPSBlobImpl(String name, Integer identifier, Integer size, byte[] checksum, DownloadServer downloadSite, String downloadID) {
/*  24 */     this.name = name;
/*  25 */     this.identifier = identifier;
/*  26 */     this.size = size;
/*  27 */     this.checksum = checksum;
/*  28 */     this.downloadSite = downloadSite;
/*  29 */     this.downloadID = downloadID;
/*     */   }
/*     */   
/*     */   public void setModuleID(Integer identifier) {
/*  33 */     this.identifier = identifier;
/*     */   }
/*     */   
/*     */   public String getBlobName() {
/*  37 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setBlobID(Integer blobID) {
/*  41 */     this.identifier = blobID;
/*     */   }
/*     */   
/*     */   public Integer getBlobID() {
/*  45 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public byte[] getCheckSum() {
/*  49 */     return this.checksum;
/*     */   }
/*     */   
/*     */   public void setData(byte[] data) {
/*  53 */     this.data = data;
/*     */   }
/*     */   
/*     */   public byte[] getData() {
/*  57 */     return this.data;
/*     */   }
/*     */   
/*     */   public Integer getBlobSize() {
/*  61 */     if (this.data != null) {
/*  62 */       return Integer.valueOf(this.data.length);
/*     */     }
/*  64 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlobSize(Integer size) {
/*  69 */     this.size = size;
/*     */   }
/*     */   
/*     */   private Object _getIdentifier() {
/*  73 */     return String.valueOf(this.name) + "." + String.valueOf(this.identifier);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  77 */     boolean retValue = false;
/*  78 */     if (this == obj)
/*  79 */       return true; 
/*  80 */     if (obj instanceof SPSBlobImpl) {
/*  81 */       SPSBlobImpl spsBI = (SPSBlobImpl)obj;
/*  82 */       retValue = _getIdentifier().equals(spsBI._getIdentifier());
/*     */     } 
/*  84 */     return retValue;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  88 */     int retValue = SPSBlobImpl.class.hashCode();
/*  89 */     retValue = HashCalc.addHashCode(retValue, _getIdentifier());
/*  90 */     return retValue;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  94 */     return getClass().getName() + "@" + String.valueOf(getBlobID());
/*     */   }
/*     */   
/*     */   public DownloadServer getDownloadSite() {
/*  98 */     return this.downloadSite;
/*     */   }
/*     */   
/*     */   public String getDownloadID() {
/* 102 */     return (this.downloadID != null) ? this.downloadID : this.name;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\implementation\SPSBlobImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */