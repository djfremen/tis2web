/*     */ package com.eoos.gm.tis2web.frame.login.log;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Collection;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public interface LoginLog {
/*     */   void add(Entry paramEntry) throws Exception;
/*     */   
/*     */   public static interface SPI {
/*     */     void add(Collection param1Collection);
/*     */   }
/*     */   
/*     */   public static interface Query {
/*     */     Collection getEntries(BackendFilter param1BackendFilter, Filter param1Filter, int param1Int) throws Exception;
/*     */     
/*     */     public static class AbortionException extends Exception {
/*     */       private static final long serialVersionUID = 1L;
/*     */       
/*     */       public AbortionException(Collection processedEntries) {
/*  21 */         this.entries = processedEntries;
/*     */       }
/*     */       private Collection entries;
/*     */       public Collection getProcessedEntries() {
/*  25 */         return this.entries;
/*     */       }
/*     */     }
/*     */     
/*     */     public static interface BackendFilter {
/*     */       Long getTimestampMIN();
/*     */       
/*     */       Long getTimestampMAX();
/*     */       
/*     */       String getUsername();
/*     */       
/*     */       String getUsergroup();
/*     */     }
/*     */     
/*     */     public static abstract class EntryFilter
/*     */       implements Filter {
/*  41 */       private static final Logger log = Logger.getLogger(EntryFilter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean include(Object obj) {
/*  48 */         boolean retValue = false;
/*     */         try {
/*  50 */           retValue = include((LoginLog.Entry)obj);
/*  51 */         } catch (Exception e) {
/*  52 */           log.warn("unable to determine filter status for " + String.valueOf(obj) + " - exception: " + e + ", returning false");
/*  53 */           retValue = false;
/*     */         } 
/*  55 */         return retValue;
/*     */       }
/*     */       
/*     */       protected abstract boolean include(LoginLog.Entry param2Entry);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Deletion
/*     */     extends Query
/*     */   {
/*     */     void delete(Collection param1Collection) throws Exception;
/*     */   }
/*     */   
/*     */   public static interface Entry {
/*     */     public static abstract class Comparator
/*     */       implements java.util.Comparator {
/*     */       public int compare(Object o1, Object o2) {
/*  72 */         return compare((LoginLog.Entry)o1, (LoginLog.Entry)o2);
/*     */       }
/*     */       
/*     */       protected abstract int compare(LoginLog.Entry param2Entry1, LoginLog.Entry param2Entry2);
/*     */     }
/*     */     
/*  78 */     public static final Comparator COMPARATOR_DATE = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/*  80 */           return Util.compare(entry1.getTimestamp(), entry2.getTimestamp());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  85 */     public static final Comparator COMPARATOR_USERNAME = new Comparator()
/*     */       {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/*  88 */           return Util.compare(entry1.getUsername(), entry2.getUsername());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  93 */     public static final Comparator COMPARATOR_SRC_ADDRESS = new Comparator()
/*     */       {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/*  96 */           return Util.compare(entry1.getSourceAddress(), entry2.getSourceAddress());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 101 */     public static final Comparator COMPARATOR_SUCCESS = new Comparator()
/*     */       {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 104 */           return Util.compare(entry1.successfulLogin(), entry2.successfulLogin());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 109 */     public static final Comparator COMPARATOR_ORIGIN = new Comparator()
/*     */       {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 112 */           return Util.compare(entry1.getOrigin(), entry2.getOrigin());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 117 */     public static final Comparator COMPARATOR_USERGROUP = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 119 */           return Util.compare(entry1.getUserGroup(), entry2.getUserGroup());
/*     */         }
/*     */       };
/*     */     
/* 123 */     public static final Comparator COMPARATOR_DEALERCODE = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 125 */           return Util.compare(entry1.getDealerCode(), entry2.getDealerCode());
/*     */         }
/*     */       };
/*     */     
/* 129 */     public static final Comparator COMPARATOR_DIVISIONCODE = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 131 */           return Util.compare(entry1.getDivisionCode(), entry2.getDivisionCode());
/*     */         }
/*     */       };
/*     */     
/* 135 */     public static final Comparator COMPARATOR_ORGCOUNTRY = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 137 */           return Util.compare(entry1.getOriginalCountryCode(), entry2.getOriginalCountryCode());
/*     */         }
/*     */       };
/*     */     
/* 141 */     public static final Comparator COMPARATOR_MAPPEDCOUNTRY = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 143 */           return Util.compare(entry1.getMappedCountryCode(), entry2.getMappedCountryCode());
/*     */         }
/*     */       };
/*     */     
/* 147 */     public static final Comparator COMPARATOR_T2WGROUP = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 149 */           return Util.compare(entry1.getT2WGroup(), entry2.getT2WGroup());
/*     */         }
/*     */       };
/*     */     
/* 153 */     public static final Comparator COMPARATOR_FREEPARAM = new Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 155 */           return Util.compare(entry1.getFreeParameter(), entry2.getFreeParameter());
/*     */         }
/*     */       };
/*     */     
/*     */     long getTimestamp();
/*     */     
/*     */     String getUsername();
/*     */     
/*     */     String getSourceAddress();
/*     */     
/*     */     String getFreeParameter();
/*     */     
/*     */     String getOrigin();
/*     */     
/*     */     String getUserGroup();
/*     */     
/*     */     String getDealerCode();
/*     */     
/*     */     boolean successfulLogin();
/*     */     
/*     */     String getDivisionCode();
/*     */     
/*     */     String getOriginalCountryCode();
/*     */     
/*     */     String getMappedCountryCode();
/*     */     
/*     */     String getT2WGroup();
/*     */   }
/*     */   
/*     */   public static interface Entry2
/*     */     extends Entry {
/* 186 */     public static final LoginLog.Entry.Comparator COMPARATOR_SERVERNAME = new LoginLog.Entry.Comparator() {
/*     */         protected int compare(LoginLog.Entry entry1, LoginLog.Entry entry2) {
/* 188 */           return Util.compare(((LoginLog.Entry2)entry1).getServerName(), ((LoginLog.Entry2)entry2).getServerName());
/*     */         }
/*     */       };
/*     */     
/*     */     String getServerName();
/*     */     
/*     */     long getIdentifier();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\LoginLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */