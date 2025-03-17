/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log.export.global;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.Time;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.Adapter;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLog;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.SPSEventLogFacade;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.log.export.AutomaticExport;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.util.ConfigurationUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import com.eoos.util.v2.TimedExecution;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class GlobalExport
/*     */   implements AutomaticExport
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(GlobalExport.class);
/*     */   
/*     */   private String emailSender;
/*     */   
/*     */   private String emailSubject;
/*     */   
/*  48 */   private List emailRecipients = new LinkedList();
/*     */   
/*     */   private TimedExecution timedExecution;
/*     */   private int exportLimit;
/*     */   private static final String STORAGE_KEY_LAST_EXPORT_TS = "sps.global.export.lts";
/*     */   
/*     */   public GlobalExport(Configuration configuration) {
/*  55 */     log.debug("initializing " + this);
/*  56 */     Number exportLimit = ConfigurationUtil.getNumber("limit", configuration);
/*  57 */     if (exportLimit == null) {
/*  58 */       this.exportLimit = 30000;
/*     */     } else {
/*  60 */       this.exportLimit = exportLimit.intValue();
/*     */     } 
/*  62 */     log.debug("...set export limit: " + this.exportLimit);
/*     */     
/*  64 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "mail.");
/*  65 */     this.emailSubject = subConfigurationWrapper1.getProperty("subject");
/*  66 */     log.debug("...set email subject: " + this.emailSubject);
/*  67 */     this.emailSender = subConfigurationWrapper1.getProperty("sender");
/*  68 */     log.debug("...set email sender: " + this.emailSender);
/*  69 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "recipient.");
/*  70 */     for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/*  71 */       String key = iter.next();
/*  72 */       String recipient = subConfigurationWrapper2.getProperty(key);
/*  73 */       this.emailRecipients.add(recipient);
/*     */     } 
/*  75 */     log.debug("...set email recipients: " + this.emailRecipients);
/*     */     
/*  77 */     List<Time> executionTimes = new LinkedList();
/*  78 */     SubConfigurationWrapper subConfigurationWrapper3 = new SubConfigurationWrapper(configuration, "exec.time.");
/*  79 */     for (Iterator<String> iterator1 = subConfigurationWrapper3.getKeys().iterator(); iterator1.hasNext(); ) {
/*  80 */       String key = iterator1.next();
/*  81 */       String time = subConfigurationWrapper3.getProperty(key);
/*     */       try {
/*  83 */         Time t = Time.parse(time);
/*  84 */         executionTimes.add(t);
/*  85 */       } catch (Exception e) {
/*  86 */         log.warn("unable to read execution time with key:" + key + " - exception:" + e + ", skipping");
/*     */       } 
/*     */     } 
/*  89 */     CollectionUtil.unify(executionTimes);
/*  90 */     Collections.sort(executionTimes);
/*  91 */     log.debug("...execution times: " + executionTimes);
/*  92 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation()
/*     */         {
/*     */           public boolean execute(Time executionTime) {
/*  95 */             GlobalExport.this.export(executionTime);
/*  96 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 103 */     this.timedExecution.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLastExportTimestamp() throws Exception {
/* 109 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 110 */     return ((Long)frameService.getPersistentObject("sps.global.export.lts")).longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportTimestamp(long timestamp) throws Exception {
/* 114 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 115 */     frameService.storePersistentObject("sps.global.export.lts", Long.valueOf(timestamp));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/* 120 */     log.debug("running " + Util.getClassName(getClass()));
/*     */     try {
/* 122 */       log.debug("....determining last export date...");
/* 123 */       Date lastExportDate = null;
/*     */       try {
/* 125 */         lastExportDate = new Date(getLastExportTimestamp());
/* 126 */         log.debug("....last export date : " + DateFormat.getDateTimeInstance(3, 3).format(lastExportDate));
/* 127 */       } catch (Exception e) {
/* 128 */         log.debug("...could not determine last export date, disabling MIN filter");
/*     */       } 
/*     */       
/* 131 */       Calendar c = Calendar.getInstance();
/* 132 */       c.set(11, executionTime.getHour());
/* 133 */       c.set(12, executionTime.getMinute());
/* 134 */       c.set(13, 0);
/* 135 */       final Date currentDate = c.getTime();
/*     */       
/* 137 */       final Long tsMin = (lastExportDate != null) ? Long.valueOf(lastExportDate.getTime()) : null;
/* 138 */       final Long tsMax = Long.valueOf(currentDate.getTime());
/*     */       
/* 140 */       SPSEventLog.Query.BackendFilter backendFilter = new SPSEventLog.Query.BackendFilter()
/*     */         {
/*     */           public Adapter getAdapter() {
/* 143 */             return null;
/*     */           }
/*     */           
/*     */           public Long getTimestampMAX() {
/* 147 */             return tsMax;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 151 */             return tsMin;
/*     */           }
/*     */           
/*     */           public String getNamePattern() {
/* 155 */             return null;
/*     */           }
/*     */           
/*     */           public Collection getFlags() {
/* 159 */             return null;
/*     */           }
/*     */           
/*     */           public SPSEventLog.Query.BackendFilter.AttributeFilter[] getAttributeFilters() {
/* 163 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 168 */       log.debug("....querying event log for entries ");
/* 169 */       Collection entries = SPSEventLogFacade.getInstance().getEntries(backendFilter, this.exportLimit);
/* 170 */       log.debug("....retrieved " + entries.size() + " entries");
/* 171 */       if (!Util.isNullOrEmpty(entries)) {
/* 172 */         MailService.Callback callback = null;
/* 173 */         log.debug("....creating export file");
/* 174 */         DateFormat.getDateTimeInstance(3, 3, Locale.US);
/*     */         
/* 176 */         File exportFile = File.createTempFile("jts", null);
/*     */         try {
/* 178 */           FileOutputStream fos = new FileOutputStream(exportFile);
/* 179 */           OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
/* 180 */           PrintWriter pw = new PrintWriter(osw);
/*     */           try {
/* 182 */             for (Iterator<SPSEventLog.Entry> iter = entries.iterator(); iter.hasNext(); ) {
/* 183 */               SPSEventLog.Entry entry = iter.next();
/* 184 */               print(pw, entry);
/* 185 */               pw.println();
/*     */             } 
/*     */           } finally {
/* 188 */             pw.close();
/*     */           } 
/*     */           
/* 191 */           final DataSource ds = new FileDataSource(exportFile)
/*     */             {
/*     */               public String getName() {
/* 194 */                 return "sps-export-" + DateConvert.toISOFormat(currentDate) + ".csv";
/*     */               }
/*     */               
/*     */               public String getContentType() {
/* 198 */                 return "text/csv";
/*     */               }
/*     */               
/*     */               public OutputStream getOutputStream() throws IOException {
/* 202 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 207 */           callback = new MailService.Callback()
/*     */             {
/*     */               public DataSource[] getAttachments() {
/* 210 */                 return new DataSource[] { this.val$ds };
/*     */               }
/*     */               
/*     */               public String getText() {
/* 214 */                 return "SEE ATTACHED FILE";
/*     */               }
/*     */               
/*     */               public String getSubject() {
/* 218 */                 return GlobalExport.this.emailSubject;
/*     */               }
/*     */               
/*     */               public Collection getRecipients() {
/* 222 */                 return GlobalExport.this.emailRecipients;
/*     */               }
/*     */               
/*     */               public String getSender() {
/* 226 */                 return GlobalExport.this.emailSender;
/*     */               }
/*     */               
/*     */               public Collection getReplyTo() {
/* 230 */                 return null;
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 235 */           log.debug(".... sending mail");
/* 236 */           MailService mailService = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 237 */           mailService.send(callback);
/* 238 */           log.debug("...successfully executed export");
/* 239 */           setLastExportTimestamp(currentDate.getTime());
/*     */         } finally {
/* 241 */           if (!exportFile.delete() && exportFile.exists()) {
/* 242 */             exportFile.deleteOnExit();
/*     */           }
/*     */         } 
/*     */       } 
/* 246 */     } catch (Exception e) {
/* 247 */       log.error("unable to execute global export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void print(PrintWriter pw, SPSEventLog.Entry entry) {
/* 253 */     StringBuffer tmp = new StringBuffer("<{NAME} {ATTRIBUTES} />");
/* 254 */     StringUtilities.replace(tmp, "{NAME}", entry.getEventName());
/* 255 */     for (Iterator<SPSEventLog.Attribute> iter = entry.getEventAttributes().iterator(); iter.hasNext(); ) {
/* 256 */       SPSEventLog.Attribute attribute = iter.next();
/* 257 */       StringBuffer attr = new StringBuffer();
/* 258 */       attr.append(attribute.getName());
/* 259 */       attr.append("=");
/* 260 */       attr.append("\"" + attribute.getValue() + "\"");
/* 261 */       StringUtilities.replace(tmp, "{ATTRIBUTES}", attr.toString() + " {ATTRIBUTES}");
/*     */     } 
/* 263 */     StringUtilities.replace(tmp, " {ATTRIBUTES}", "");
/*     */     
/* 265 */     pw.println(tmp.toString());
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\export\global\GlobalExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */