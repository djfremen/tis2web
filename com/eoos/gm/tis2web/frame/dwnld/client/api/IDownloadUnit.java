/*    */ package com.eoos.gm.tis2web.frame.dwnld.client.api;
/*    */ 
/*    */ import com.eoos.datatype.IVersionNumber;
/*    */ import com.eoos.gm.tis2web.frame.dwnld.common.DownloadServer;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.io.Serializable;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.Locale;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IDownloadUnit
/*    */   extends Serializable
/*    */ {
/* 21 */   public static final Comparator COMPARATOR_VERSION = new Comparator() {
/* 22 */       private final Logger log = Logger.getLogger(getClass());
/*    */       
/*    */       public int compare(Object o1, Object o2) {
/*    */         try {
/* 26 */           IVersionNumber v1 = ((IDownloadUnit)o1).getVersionNumber();
/* 27 */           IVersionNumber v2 = ((IDownloadUnit)o2).getVersionNumber();
/* 28 */           return Util.compare(v1, v2);
/* 29 */         } catch (Exception e) {
/* 30 */           this.log.error("unable to compare " + String.valueOf(o1) + " with " + String.valueOf(o2) + ", returning 0 - exception: " + e, e);
/* 31 */           return 0;
/*    */         } 
/*    */       }
/*    */     };
/*    */   
/*    */   long getIdentifier();
/*    */   
/*    */   IVersionNumber getVersionNumber();
/*    */   
/*    */   String getDescripition(Locale paramLocale);
/*    */   
/*    */   long getReleaseDate();
/*    */   
/*    */   Collection getFiles();
/*    */   
/*    */   long getTotalBytes();
/*    */   
/*    */   IInstalledVersionLookup getInstalledVersionLookup();
/*    */   
/*    */   Collection getClassfiers();
/*    */   
/*    */   Collection<DownloadServer> getDownloadServers();
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IDownloadUnit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */