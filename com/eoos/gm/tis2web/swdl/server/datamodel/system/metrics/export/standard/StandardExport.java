/*     */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.export.standard;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.Time;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLog;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.SWDLMetricsLogFacade;
/*     */ import com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics.export.AutomaticExport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.HashCalc;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.TimedExecution;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardExport
/*     */   implements AutomaticExport
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(StandardExport.class);
/*     */   
/*     */   private String emailSender;
/*     */   
/*     */   private String emailSubject;
/*     */   
/*  49 */   private List emailRecipients = new LinkedList();
/*     */   private TimedExecution timedExecution;
/*     */   private static final String STORAGE_KEY_LAST_EXPORT_TS = "swdl.std.export.lts";
/*     */   
/*     */   public StandardExport(Configuration configuration) {
/*  54 */     log.debug("initializing " + this);
/*  55 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "mail.");
/*  56 */     this.emailSubject = subConfigurationWrapper1.getProperty("subject");
/*  57 */     log.debug("...set email subject: " + this.emailSubject);
/*  58 */     this.emailSender = subConfigurationWrapper1.getProperty("sender");
/*  59 */     log.debug("...set email sender: " + this.emailSender);
/*  60 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "recipient.");
/*  61 */     for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/*  62 */       String key = iter.next();
/*  63 */       String recipient = subConfigurationWrapper2.getProperty(key);
/*  64 */       this.emailRecipients.add(recipient);
/*     */     } 
/*  66 */     log.debug("...set email recipients: " + this.emailRecipients);
/*     */     
/*  68 */     List<Time> executionTimes = new LinkedList();
/*  69 */     SubConfigurationWrapper subConfigurationWrapper3 = new SubConfigurationWrapper(configuration, "exec.time.");
/*  70 */     for (Iterator<String> iterator1 = subConfigurationWrapper3.getKeys().iterator(); iterator1.hasNext(); ) {
/*  71 */       String key = iterator1.next();
/*  72 */       String time = subConfigurationWrapper3.getProperty(key);
/*     */       try {
/*  74 */         Time t = Time.parse(time);
/*  75 */         executionTimes.add(t);
/*  76 */       } catch (Exception e) {
/*  77 */         log.warn("unable to read execution time with key:" + key + " - exception:" + e + ", skipping");
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     CollectionUtil.unify(executionTimes);
/*  82 */     Collections.sort(executionTimes);
/*  83 */     log.debug("...execution times: " + executionTimes);
/*  84 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation() {
/*     */           public boolean execute(Time executionTime) {
/*  86 */             StandardExport.this.export(executionTime);
/*  87 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */   
/*     */   public void start() {
/*  93 */     this.timedExecution.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLastExportTimestamp() throws Exception {
/*  99 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 100 */     return ((Long)frameService.getPersistentObject("swdl.std.export.lts")).longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportTimestamp(long timestamp) throws Exception {
/* 104 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 105 */     frameService.storePersistentObject("swdl.std.export.lts", Long.valueOf(timestamp));
/*     */   }
/*     */   
/*     */   private static class MyEntry
/*     */     implements SWDLMetricsLog.Entry
/*     */   {
/*     */     private SWDLMetricsLog.Entry backend;
/*     */     
/*     */     private MyEntry(SWDLMetricsLog.Entry backend) {
/* 114 */       this.backend = backend;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 118 */       StringBuffer tmp = new StringBuffer("{TECH}, {APP}, {VERSION}, {LANGUAGE}");
/* 119 */       StringUtilities.replace(tmp, "{TECH}", String.valueOf(getDevice()));
/* 120 */       StringUtilities.replace(tmp, "{APP}", String.valueOf(getApplication()));
/* 121 */       StringUtilities.replace(tmp, "{VERSION}", String.valueOf(getVersion()));
/* 122 */       StringUtilities.replace(tmp, "{LANGUAGE}", String.valueOf(getLanguage()));
/* 123 */       return tmp.toString();
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 127 */       int retValue = MyEntry.class.hashCode();
/* 128 */       retValue = HashCalc.addHashCode(retValue, getApplication());
/* 129 */       retValue = HashCalc.addHashCode(retValue, getVersion());
/* 130 */       retValue = HashCalc.addHashCode(retValue, getDevice());
/* 131 */       retValue = HashCalc.addHashCode(retValue, getLanguage());
/* 132 */       return retValue;
/*     */     }
/*     */     
/*     */     public long getTimestamp() {
/* 136 */       return this.backend.getTimestamp();
/*     */     }
/*     */     
/*     */     public String getDevice() {
/* 140 */       return this.backend.getDevice();
/*     */     }
/*     */     
/*     */     public String getApplication() {
/* 144 */       return this.backend.getApplication();
/*     */     }
/*     */     
/*     */     public String getVersion() {
/* 148 */       return this.backend.getVersion();
/*     */     }
/*     */     
/*     */     public String getLanguage() {
/* 152 */       return this.backend.getLanguage();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 157 */       if (this == obj)
/* 158 */         return true; 
/* 159 */       if (obj instanceof MyEntry) {
/* 160 */         MyEntry other = (MyEntry)obj;
/* 161 */         boolean ret = Util.equals(getDevice(), other.getDevice());
/* 162 */         ret = (ret && Util.equals(getApplication(), other.getApplication()));
/* 163 */         ret = (ret && Util.equals(getVersion(), other.getVersion()));
/* 164 */         ret = (ret && Util.equals(getLanguage(), other.getLanguage()));
/* 165 */         return ret;
/*     */       } 
/* 167 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getUserID() {
/* 172 */       return this.backend.getUserID();
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/* 177 */     log.debug("running swdl standard export ...");
/*     */     try {
/* 179 */       log.debug("....determining last export date...");
/* 180 */       Date lastExportDate = null;
/*     */       try {
/* 182 */         lastExportDate = new Date(getLastExportTimestamp());
/* 183 */         log.debug("....last export date : " + DateFormat.getDateTimeInstance(3, 3).format(lastExportDate));
/* 184 */       } catch (Exception e) {
/* 185 */         log.debug("...could not determine last export date, disabling MIN filter");
/*     */       } 
/*     */       
/* 188 */       Calendar c = Calendar.getInstance();
/* 189 */       c.set(11, executionTime.getHour());
/* 190 */       c.set(12, executionTime.getMinute());
/* 191 */       c.set(13, 0);
/* 192 */       final Date currentDate = c.getTime();
/*     */       
/* 194 */       final Long tsMin = (lastExportDate != null) ? Long.valueOf(lastExportDate.getTime()) : null;
/* 195 */       final Long tsMax = Long.valueOf(currentDate.getTime());
/*     */       
/* 197 */       SWDLMetricsLog.Query.BackendFilter backendFilter = new SWDLMetricsLog.Query.BackendFilter()
/*     */         {
/*     */           public Long getTimestampMAX() {
/* 200 */             return tsMax;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 204 */             return tsMin;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 209 */       log.debug("....querying swdl metrics log for entries ");
/* 210 */       Collection entries = SWDLMetricsLogFacade.getInstance().getEntries(backendFilter, null, -1);
/* 211 */       log.debug("....retrieved " + entries.size() + " entries");
/*     */       
/* 213 */       MailService.Callback callback = null;
/* 214 */       log.debug("....creating statistic");
/* 215 */       HashMap<Object, Object> entryToCounter = new HashMap<Object, Object>();
/* 216 */       long minimalTS = System.currentTimeMillis();
/* 217 */       for (Iterator<SWDLMetricsLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 218 */         MyEntry entry = new MyEntry(iter.next());
/* 219 */         minimalTS = Math.min(minimalTS, entry.getTimestamp());
/* 220 */         Integer counter = (Integer)entryToCounter.get(entry);
/* 221 */         if (counter == null) {
/* 222 */           counter = Integer.valueOf(1);
/*     */         } else {
/* 224 */           counter = Integer.valueOf(counter.intValue() + 1);
/*     */         } 
/* 226 */         entryToCounter.put(entry, counter);
/*     */       } 
/* 228 */       log.debug("....creating export file");
/* 229 */       ByteArrayOutputStream baos = new ByteArrayOutputStream(entries.size() * 500);
/* 230 */       OutputStreamWriter osw = new OutputStreamWriter(baos, "UTF-8");
/* 231 */       PrintWriter pw = new PrintWriter(osw);
/* 232 */       String from = DateConvert.toDateString((tsMin != null) ? tsMin.longValue() : minimalTS, DateFormat.getInstance());
/* 233 */       String until = DateConvert.toDateString(tsMax.longValue(), DateFormat.getInstance());
/* 234 */       pw.println("**** SWDL metrics (from: " + from + ", until: " + until + ") ****** ");
/* 235 */       Iterator<Map.Entry> iterator = entryToCounter.entrySet().iterator();
/* 236 */       while (iterator.hasNext()) {
/* 237 */         Map.Entry mapEntry = iterator.next();
/* 238 */         SWDLMetricsLog.Entry entry = (SWDLMetricsLog.Entry)mapEntry.getKey();
/* 239 */         pw.println(String.valueOf(entry) + ": " + String.valueOf(mapEntry.getValue()));
/*     */       } 
/* 241 */       pw.close();
/*     */       
/* 243 */       final byte[] data = baos.toByteArray();
/*     */       
/* 245 */       final DataSource ds = new DataSource()
/*     */         {
/*     */           public String getName() {
/* 248 */             return "swdl-export_" + DateConvert.toISOFormat(currentDate) + ".csv";
/*     */           }
/*     */           
/*     */           public String getContentType() {
/* 252 */             return "text/csv";
/*     */           }
/*     */           
/*     */           public OutputStream getOutputStream() throws IOException {
/* 256 */             throw new UnsupportedOperationException();
/*     */           }
/*     */           
/*     */           public InputStream getInputStream() throws IOException {
/* 260 */             return new ByteArrayInputStream(data);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 265 */       callback = new MailService.Callback()
/*     */         {
/*     */           public DataSource[] getAttachments() {
/* 268 */             return new DataSource[] { this.val$ds };
/*     */           }
/*     */           
/*     */           public String getText() {
/* 272 */             return "SEE ATTACHED FILE";
/*     */           }
/*     */           
/*     */           public String getSubject() {
/* 276 */             return StandardExport.this.emailSubject;
/*     */           }
/*     */           
/*     */           public Collection getRecipients() {
/* 280 */             return StandardExport.this.emailRecipients;
/*     */           }
/*     */           
/*     */           public String getSender() {
/* 284 */             return StandardExport.this.emailSender;
/*     */           }
/*     */           
/*     */           public Collection getReplyTo() {
/* 288 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 293 */       log.debug(".... sending mail");
/* 294 */       MailService mailService = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 295 */       mailService.send(callback);
/* 296 */       log.debug("...successfully executed export, storing execution time ");
/* 297 */       setLastExportTimestamp(currentDate.getTime());
/*     */     }
/* 299 */     catch (Exception e) {
/* 300 */       log.error("unable to execute swdl standard export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\export\standard\StandardExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */