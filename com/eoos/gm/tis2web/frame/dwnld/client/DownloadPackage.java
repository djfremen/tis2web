/*    */ package com.eoos.gm.tis2web.frame.dwnld.client;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadPackage;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.IDownloadUnit;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.client.api.Identifier;
/*    */ import com.eoos.scsm.v2.util.HashCalc;
/*    */ import java.io.File;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DownloadPackage
/*    */   implements IDownloadPackage, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final Identifier identifier;
/*    */   private final Collection units;
/*    */   private final File destDir;
/* 23 */   private final Object SYNC_SIZE = new Serializable()
/*    */     {
/*    */       private static final long serialVersionUID = 1L;
/*    */     };
/*    */   
/* 28 */   private long totalBytes = -1L;
/*    */   
/*    */   public DownloadPackage(Identifier identifier, Collection units, File destDir) {
/* 31 */     this.identifier = identifier;
/* 32 */     this.units = units;
/* 33 */     this.destDir = destDir;
/*    */   }
/*    */   
/*    */   public Identifier getIdentifier() {
/* 37 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public long getTotalBytes() {
/* 41 */     synchronized (this.SYNC_SIZE) {
/* 42 */       if (this.totalBytes == -1L) {
/* 43 */         long size = 0L;
/* 44 */         for (Iterator<IDownloadUnit> iter = this.units.iterator(); iter.hasNext(); ) {
/* 45 */           IDownloadUnit unit = iter.next();
/* 46 */           size += unit.getTotalBytes();
/*    */         } 
/* 48 */         this.totalBytes = size;
/*    */       } 
/* 50 */       return this.totalBytes;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection getUnits() {
/* 55 */     return this.units;
/*    */   }
/*    */   
/*    */   public File getDestinationDir() {
/* 59 */     return this.destDir;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj) {
/* 63 */     if (this == obj)
/* 64 */       return true; 
/* 65 */     if (obj instanceof DownloadPackage) {
/* 66 */       return this.identifier.equals(((DownloadPackage)obj).identifier);
/*    */     }
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     int ret = DownloadPackage.class.hashCode();
/* 74 */     ret = HashCalc.addHashCode(ret, this.identifier);
/* 75 */     return ret;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 79 */     return "DownloadPackage[" + this.identifier.toString() + "]";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\DownloadPackage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */