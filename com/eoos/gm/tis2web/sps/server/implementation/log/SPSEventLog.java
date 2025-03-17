/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*     */ 
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface SPSEventLog
/*     */ {
/*  16 */   public static final Adapter ADAPTER_NAO = Adapter.NAO;
/*     */   
/*  18 */   public static final Adapter ADAPTER_GME = Adapter.GME;
/*     */   
/*  20 */   public static final Adapter ADAPTER_GLOBAL = Adapter.GLOBAL;
/*     */   
/*     */   public static class Attribute {
/*  23 */     public static final java.util.Comparator COMPARATOR = new java.util.Comparator()
/*     */       {
/*     */         public int compare(Object o1, Object o2) {
/*     */           try {
/*  27 */             int retValue = ((SPSEventLog.Attribute)o1).name.compareTo(((SPSEventLog.Attribute)o2).name);
/*  28 */             if (retValue == 0) {
/*  29 */               retValue = ((SPSEventLog.Attribute)o1).value.compareTo(((SPSEventLog.Attribute)o2).value);
/*     */             }
/*  31 */             return retValue;
/*  32 */           } catch (Exception e) {
/*  33 */             return 0;
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     private String name;
/*     */     
/*     */     private String value;
/*     */     
/*     */     public Attribute(String name, String value) {
/*  44 */       this.name = trim(name);
/*  45 */       this.value = trim(value);
/*     */     }
/*     */     
/*     */     private static String trim(String string) {
/*  49 */       if (string == null) {
/*  50 */         return string;
/*     */       }
/*  52 */       return string.trim();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/*  57 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getValue() {
/*  61 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class VoltAttribute extends Attribute {
/*     */     public VoltAttribute(String name, String value) {
/*  67 */       super(name, value);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Entry {
/*  72 */     public static final Adapter ADAPTER_NAO = SPSEventLog.ADAPTER_NAO;
/*     */     
/*  74 */     public static final Adapter ADAPTER_GME = SPSEventLog.ADAPTER_GME;
/*     */     
/*  76 */     public static final Adapter ADAPTER_GLOBAL = SPSEventLog.ADAPTER_GLOBAL;
/*     */     
/*     */     public static abstract class Comparator implements java.util.Comparator {
/*     */       public int compare(Object o1, Object o2) {
/*  80 */         return compare((SPSEventLog.Entry)o1, (SPSEventLog.Entry)o2);
/*     */       }
/*     */       
/*     */       protected abstract int compare(SPSEventLog.Entry param2Entry1, SPSEventLog.Entry param2Entry2);
/*     */     }
/*     */     
/*  86 */     public static final Comparator COMPARATOR_EVENTNAME = new Comparator()
/*     */       {
/*     */         protected int compare(SPSEventLog.Entry entry1, SPSEventLog.Entry entry2) {
/*  89 */           return Util.compare(entry1.getEventName(), entry2.getEventName());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*  94 */     public static final Comparator COMPARATOR_ADAPTER = new Comparator()
/*     */       {
/*     */         protected int compare(SPSEventLog.Entry entry1, SPSEventLog.Entry entry2) {
/*  97 */           return Util.compare(entry1.getAdapter().toString(), entry2.getAdapter().toString());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     Adapter getAdapter();
/*     */ 
/*     */     
/*     */     String getEventName();
/*     */     
/*     */     List getEventAttributes();
/*     */   }
/*     */   
/*     */   public static final class MetaEntry
/*     */   {
/*     */     public SPSEventLog.Entry entry;
/*     */     public Collection flags;
/*     */     public Attachment[] attachments;
/*     */   }
/*     */   
/*     */   public static interface SPI
/*     */   {
/*     */     void add(Collection param1Collection) throws Exception;
/*     */     
/*     */     void ensureInit();
/*     */   }
/*     */   
/*     */   public static final class Flag
/*     */   {
/*     */     private Flag() {}
/*     */   }
/*     */   
/* 129 */   public static final Flag FLAG_NONE = new Flag();
/*     */   
/* 131 */   public static final Flag FLAG_ALL = new Flag();
/*     */   
/* 133 */   public static final Flag FLAG_ONSTAR = new Flag();
/*     */   
/* 135 */   public static final Flag FLAG_VOLT = new Flag();
/*     */   
/*     */   void add(Entry paramEntry, Collection paramCollection, Attachment[] paramArrayOfAttachment);
/*     */   
/*     */   void ensureInit();
/*     */   
/* 141 */   public static final List FLAG_DOMAIN = Arrays.asList(new Flag[] { FLAG_ALL, FLAG_ONSTAR, FLAG_VOLT });
/*     */   
/*     */   public static interface LoggedEntry extends Entry {
/* 144 */     public static final SPSEventLog.Entry.Comparator COMPARATOR_DATE = new SPSEventLog.Entry.Comparator()
/*     */       {
/*     */         protected int compare(SPSEventLog.Entry entry1, SPSEventLog.Entry entry2) {
/* 147 */           return (int)(((SPSEventLog.LoggedEntry)entry1).getTimestamp() - ((SPSEventLog.LoggedEntry)entry2).getTimestamp());
/*     */         }
/*     */       };
/*     */     
/*     */     long getTimestamp();
/*     */     
/*     */     String getServer();
/*     */     
/*     */     long getIdentifier();
/*     */     
/*     */     Set getAttachmentKeys();
/*     */   }
/*     */   
/*     */   public static interface Query
/*     */   {
/*     */     Collection getEntries(BackendFilter param1BackendFilter, int param1Int) throws Exception;
/*     */     
/*     */     Object getAttachedObject(SPSEventLog.Entry param1Entry, Attachment.Key param1Key) throws Exception;
/*     */     
/*     */     public static class AbortionException
/*     */       extends Exception {
/*     */       private static final long serialVersionUID = 1L;
/*     */       private Collection entries;
/*     */       
/*     */       public AbortionException(Collection processedEntries) {
/* 172 */         this.entries = processedEntries;
/*     */       }
/*     */       
/*     */       public Collection getProcessedEntries() {
/* 176 */         return this.entries;
/*     */       }
/*     */     }
/*     */     
/*     */     public static interface BackendFilter {
/*     */       public static final String WILDCARD_ANY = "%";
/*     */       public static final String WILDCARD_ONE = "_";
/*     */       
/*     */       Long getTimestampMIN();
/*     */       
/*     */       Long getTimestampMAX();
/*     */       
/*     */       Adapter getAdapter();
/*     */       
/*     */       String getNamePattern();
/*     */       
/*     */       Collection getFlags();
/*     */       
/*     */       AttributeFilter[] getAttributeFilters();
/*     */       
/*     */       public static interface AttributeFilter {
/*     */         String getNamePattern();
/*     */         
/*     */         String getValuePattern();
/*     */       }
/*     */     }
/*     */     
/*     */     public static abstract class EntryFilter
/*     */       implements Filter {
/* 205 */       private static final Logger log = Logger.getLogger(EntryFilter.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean include(Object obj) {
/* 212 */         boolean retValue = false;
/*     */         try {
/* 214 */           retValue = include((SPSEventLog.Entry)obj);
/* 215 */         } catch (Exception e) {
/* 216 */           log.warn("unable to determine filter status for " + String.valueOf(obj) + " - exception: " + e + ", returning false");
/* 217 */           retValue = false;
/*     */         } 
/* 219 */         return retValue;
/*     */       }
/*     */       
/*     */       protected abstract boolean include(SPSEventLog.Entry param2Entry);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Deletion extends Query {
/*     */     void delete(Collection param1Collection) throws Exception;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLog.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */