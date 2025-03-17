/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.util;
/*     */ 
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.HashCalc;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class SWDLMetricsLogUtil {
/*     */   private static final String LOG_TEMPLATE = "{DATE}\t{TIME}\t{DEVICE}\t{APPLICATION}\t{VERSION}\t{LANGUAGE}\t{USER}";
/*     */   
/*     */   private static class Wrapper
/*     */     implements SWDLMetricsLog.Entry {
/*     */     private SWDLMetricsLog.Entry backend;
/*     */     
/*     */     private Wrapper(SWDLMetricsLog.Entry backend) {
/*  27 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     public String toString() {
/*  31 */       StringBuffer tmp = new StringBuffer("{TECH}, {APP}, {VERSION}, {LANGUAGE}");
/*  32 */       StringUtilities.replace(tmp, "{TECH}", String.valueOf(getDevice()));
/*  33 */       StringUtilities.replace(tmp, "{APP}", String.valueOf(getApplication()));
/*  34 */       StringUtilities.replace(tmp, "{VERSION}", String.valueOf(getVersion()));
/*  35 */       StringUtilities.replace(tmp, "{LANGUAGE}", String.valueOf(getLanguage()));
/*  36 */       return tmp.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/*  40 */       int retValue = Wrapper.class.hashCode();
/*  41 */       retValue = HashCalc.addHashCode(retValue, getApplication());
/*  42 */       retValue = HashCalc.addHashCode(retValue, getVersion());
/*  43 */       retValue = HashCalc.addHashCode(retValue, getDevice());
/*  44 */       retValue = HashCalc.addHashCode(retValue, getLanguage());
/*  45 */       return retValue;
/*     */     }
/*     */     
/*     */     public long getTimestamp() {
/*  49 */       return this.backend.getTimestamp();
/*     */     }
/*     */     
/*     */     public String getDevice() {
/*  53 */       return this.backend.getDevice();
/*     */     }
/*     */     
/*     */     public String getApplication() {
/*  57 */       return this.backend.getApplication();
/*     */     }
/*     */     
/*     */     public String getVersion() {
/*  61 */       return this.backend.getVersion();
/*     */     }
/*     */     
/*     */     public String getLanguage() {
/*  65 */       return this.backend.getLanguage();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  70 */       if (this == obj)
/*  71 */         return true; 
/*  72 */       if (obj instanceof Wrapper) {
/*  73 */         Wrapper other = (Wrapper)obj;
/*  74 */         boolean ret = Util.equals(getDevice(), other.getDevice());
/*  75 */         ret = (ret && Util.equals(getApplication(), other.getApplication()));
/*  76 */         ret = (ret && Util.equals(getVersion(), other.getVersion()));
/*  77 */         ret = (ret && Util.equals(getLanguage(), other.getLanguage()));
/*     */         
/*  79 */         return ret;
/*     */       } 
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUserID() {
/*  86 */       return this.backend.getUserID();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  92 */   private static final DateFormat LOG_DATEFORMAT = DateFormat.getDateInstance(2);
/*     */   
/*  94 */   private static final DateFormat LOG_TIMEFORMAT = DateFormat.getTimeInstance(2);
/*     */   public static byte[] createStatistic(Collection entries) {
/*     */     OutputStreamWriter osw;
/*  97 */     if (entries == null || entries.size() == 0) {
/*  98 */       return null;
/*     */     }
/*     */     
/* 101 */     HashMap<Object, Object> entryToCounter = new HashMap<Object, Object>();
/* 102 */     long minimalTS = -1L;
/* 103 */     long maximalTS = -1L;
/* 104 */     for (Iterator<SWDLMetricsLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 105 */       Wrapper entry = new Wrapper(iter.next());
/* 106 */       if (minimalTS == -1L) {
/* 107 */         minimalTS = entry.getTimestamp();
/* 108 */         maximalTS = minimalTS;
/*     */       } else {
/* 110 */         minimalTS = Math.min(minimalTS, entry.getTimestamp());
/* 111 */         maximalTS = Math.max(maximalTS, entry.getTimestamp());
/*     */       } 
/* 113 */       Integer counter = (Integer)entryToCounter.get(entry);
/* 114 */       if (counter == null) {
/* 115 */         counter = Integer.valueOf(1);
/*     */       } else {
/* 117 */         counter = Integer.valueOf(counter.intValue() + 1);
/*     */       } 
/* 119 */       entryToCounter.put(entry, counter);
/*     */     } 
/* 121 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(entries.size() * 500);
/*     */     
/*     */     try {
/* 124 */       osw = new OutputStreamWriter(baos, "UTF-8");
/* 125 */     } catch (UnsupportedEncodingException e) {
/* 126 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 129 */     PrintWriter pw = new PrintWriter(osw);
/* 130 */     String from = DateConvert.toDateString(minimalTS, DateFormat.getInstance());
/* 131 */     String until = DateConvert.toDateString(maximalTS, DateFormat.getInstance());
/* 132 */     pw.println("**** SWDL metrics (from: " + from + ", until: " + until + ") ****** ");
/* 133 */     Iterator<Map.Entry> iterator = entryToCounter.entrySet().iterator();
/* 134 */     while (iterator.hasNext()) {
/* 135 */       Map.Entry mapEntry = iterator.next();
/* 136 */       SWDLMetricsLog.Entry entry = (SWDLMetricsLog.Entry)mapEntry.getKey();
/* 137 */       pw.println(String.valueOf(entry) + ": " + String.valueOf(mapEntry.getValue()));
/*     */     } 
/* 139 */     pw.close();
/*     */     
/* 141 */     return baos.toByteArray();
/*     */   }
/*     */   
/*     */   public static byte[] createExport(Collection entries) {
/*     */     OutputStreamWriter osw;
/* 146 */     if (entries == null || entries.size() == 0) {
/* 147 */       return null;
/*     */     }
/*     */     
/* 150 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(entries.size() * 500);
/*     */     
/*     */     try {
/* 153 */       osw = new OutputStreamWriter(baos, "UTF-8");
/* 154 */     } catch (UnsupportedEncodingException e) {
/* 155 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 158 */     PrintWriter pw = new PrintWriter(osw);
/*     */ 
/*     */     
/* 161 */     StringBuffer header = new StringBuffer("{DATE}\t{TIME}\t{DEVICE}\t{APPLICATION}\t{VERSION}\t{LANGUAGE}\t{USER}");
/* 162 */     StringUtilities.replace(header, "{DATE}", "DATE");
/* 163 */     StringUtilities.replace(header, "{TIME}", "TIME");
/* 164 */     StringUtilities.replace(header, "{DEVICE}", "DEVICE");
/* 165 */     StringUtilities.replace(header, "{APPLICATION}", "APPLICATION");
/* 166 */     StringUtilities.replace(header, "{VERSION}", "VERSION");
/* 167 */     StringUtilities.replace(header, "{LANGUAGE}", "LANGUAGE");
/* 168 */     StringUtilities.replace(header, "{USER}", "USER");
/* 169 */     pw.println(header);
/*     */     
/* 171 */     synchronized (LOG_DATEFORMAT) {
/* 172 */       for (Iterator<SWDLMetricsLog.Entry2> iter = entries.iterator(); iter.hasNext(); ) {
/* 173 */         SWDLMetricsLog.Entry2 entry = iter.next();
/* 174 */         StringBuffer tmp = new StringBuffer("{DATE}\t{TIME}\t{DEVICE}\t{APPLICATION}\t{VERSION}\t{LANGUAGE}\t{USER}");
/* 175 */         StringUtilities.replace(tmp, "{DATE}", LOG_DATEFORMAT.format(new Date(entry.getTimestamp())));
/* 176 */         StringUtilities.replace(tmp, "{TIME}", LOG_TIMEFORMAT.format(new Date(entry.getTimestamp())));
/* 177 */         StringUtilities.replace(tmp, "{DEVICE}", entry.getDevice());
/* 178 */         StringUtilities.replace(tmp, "{APPLICATION}", entry.getApplication());
/* 179 */         StringUtilities.replace(tmp, "{VERSION}", entry.getVersion());
/* 180 */         StringUtilities.replace(tmp, "{LANGUAGE}", entry.getLanguage());
/* 181 */         StringUtilities.replace(tmp, "{USER}", entry.getUserID());
/* 182 */         pw.println(tmp);
/*     */       } 
/*     */     } 
/* 185 */     pw.close();
/*     */     
/* 187 */     return baos.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metric\\util\SWDLMetricsLogUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */