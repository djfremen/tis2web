/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Collection;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public interface SWDLMetricsLog {
/*     */   void add(Entry paramEntry) throws Exception;
/*     */   
/*     */   public static interface Query {
/*     */     Collection getEntries(BackendFilter param1BackendFilter, Filter param1Filter, int param1Int) throws Exception;
/*     */     
/*     */     public static class AbortionException extends Exception {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public AbortionException(Collection processedEntries) {
/*  18 */         this.entries = processedEntries;
/*     */       }
/*     */       private Collection entries;
/*     */       public Collection getProcessedEntries() {
/*  22 */         return this.entries;
/*     */       }
/*     */     }
/*     */     
/*     */     public static interface BackendFilter {
/*     */       Long getTimestampMIN();
/*     */       
/*     */       Long getTimestampMAX();
/*     */     }
/*     */     
/*     */     public static abstract class EntryFilter
/*     */       implements Filter {
/*  34 */       private static final Logger log = Logger.getLogger(EntryFilter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean include(Object obj) {
/*  41 */         boolean retValue = false;
/*     */         try {
/*  43 */           retValue = include((SWDLMetricsLog.Entry)obj);
/*  44 */         } catch (Exception e) {
/*  45 */           log.warn("unable to determine filter status for " + String.valueOf(obj) + " - exception: " + e + ", returning false");
/*  46 */           retValue = false;
/*     */         } 
/*  48 */         return retValue;
/*     */       }
/*     */       
/*     */       protected abstract boolean include(SWDLMetricsLog.Entry param2Entry);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Deletion
/*     */     extends Query {
/*     */     void delete(Collection param1Collection) throws Exception;
/*     */   }
/*     */   
/*     */   public static interface Entry {
/*     */     public static abstract class Comparator
/*     */       implements java.util.Comparator {
/*     */       public int compare(Object o1, Object o2) {
/*  64 */         return compare((SWDLMetricsLog.Entry)o1, (SWDLMetricsLog.Entry)o2);
/*     */       }
/*     */       
/*     */       protected abstract int compare(SWDLMetricsLog.Entry param2Entry1, SWDLMetricsLog.Entry param2Entry2);
/*     */     }
/*     */     
/*  70 */     public static final Comparator COMPARATOR_DATE = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/*  72 */           return Util.compare(entry1.getTimestamp(), entry2.getTimestamp());
/*     */         }
/*     */       };
/*     */     
/*  76 */     public static final Comparator COMPARATOR_DEVICE = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/*  78 */           return Util.compare(entry1.getDevice(), entry2.getDevice());
/*     */         }
/*     */       };
/*     */     
/*  82 */     public static final Comparator COMPARATOR_APPLICATION = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/*  84 */           return Util.compare(entry1.getApplication(), entry2.getApplication());
/*     */         }
/*     */       };
/*     */     
/*  88 */     public static final Comparator COMPARATOR_VERSION = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/*  90 */           return Util.compare(entry1.getVersion(), entry2.getVersion());
/*     */         }
/*     */       };
/*     */     
/*  94 */     public static final Comparator COMPARATOR_LANGUAGE = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/*  96 */           return Util.compare(entry1.getLanguage(), entry2.getLanguage());
/*     */         }
/*     */       };
/*     */     
/* 100 */     public static final Comparator COMPARATOR_USERID = new Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/* 102 */           return Util.compare(entry1.getUserID(), entry2.getUserID());
/*     */         }
/*     */       };
/*     */     
/*     */     long getTimestamp();
/*     */     
/*     */     String getDevice();
/*     */     
/*     */     String getApplication();
/*     */     
/*     */     String getVersion();
/*     */     
/*     */     String getLanguage();
/*     */     
/*     */     String getUserID();
/*     */   }
/*     */   
/*     */   public static interface Entry2 extends Entry {
/* 120 */     public static final SWDLMetricsLog.Entry.Comparator COMPARATOR_SERVERNAME = new SWDLMetricsLog.Entry.Comparator() {
/*     */         protected int compare(SWDLMetricsLog.Entry entry1, SWDLMetricsLog.Entry entry2) {
/* 122 */           return Util.compare(((SWDLMetricsLog.Entry2)entry1).getServerName(), ((SWDLMetricsLog.Entry2)entry2).getServerName());
/*     */         }
/*     */       };
/*     */     
/*     */     String getServerName();
/*     */     
/*     */     long getIdentifier();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\SWDLMetricsLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */