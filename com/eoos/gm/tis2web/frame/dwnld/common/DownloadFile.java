/*    */ package com.eoos.gm.tis2web.frame.dwnld.common;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadFile;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ import java.util.Locale;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadFile
/*    */   implements IDownloadFile, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected long identifier;
/*    */   protected String name;
/*    */   protected long size;
/*    */   protected byte[] checksum;
/*    */   private String path;
/*    */   private String compression;
/*    */   private long compressedSize;
/*    */   
/*    */   public DownloadFile(long identifier, String name, long size, byte[] checksum, String path, String compression, long compressedSize) {
/* 29 */     this.identifier = identifier;
/* 30 */     this.name = name;
/* 31 */     this.size = size;
/* 32 */     this.checksum = checksum;
/* 33 */     this.path = path;
/* 34 */     this.compression = compression;
/* 35 */     this.compressedSize = compressedSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getIdentifier() {
/* 40 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 44 */     return this.name;
/*    */   }
/*    */   
/*    */   public long getSize() {
/* 48 */     return this.size;
/*    */   }
/*    */   
/*    */   public byte[] getChecksum() {
/* 52 */     return this.checksum;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 56 */     return this.path;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 60 */     if (this == obj)
/* 61 */       return true; 
/* 62 */     if (obj instanceof DownloadFile) {
/* 63 */       return (this.identifier == ((DownloadFile)obj).identifier);
/*    */     }
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 70 */     int ret = DownloadFile.class.hashCode();
/* 71 */     ret = HashCalc.addHashCode(ret, this.identifier);
/* 72 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 76 */     return String.valueOf(getName()) + "[file id: " + this.identifier + "]";
/*    */   }
/*    */   
/*    */   public boolean isCompressed() {
/* 80 */     return !Util.isNullOrEmpty(this.compression);
/*    */   }
/*    */   
/*    */   public boolean gzipCompressed() {
/* 84 */     return (isCompressed() && this.compression.toLowerCase(Locale.ENGLISH).indexOf("gzip") != -1);
/*    */   }
/*    */   
/*    */   public boolean isZIPArchive() {
/* 88 */     return (isCompressed() && this.compression.toLowerCase(Locale.ENGLISH).equals("zip"));
/*    */   }
/*    */   
/*    */   public long getCompressedSize() {
/* 92 */     return this.compressedSize;
/*    */   }
/*    */   
/*    */   public boolean isDirectory() {
/* 96 */     return (Util.isNullOrEmpty(this.name) || this.name.equals("."));
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\common\DownloadFile.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */