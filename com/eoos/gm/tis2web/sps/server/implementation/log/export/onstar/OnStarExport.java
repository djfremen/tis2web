/*     */ package com.eoos.gm.tis2web.sps.server.implementation.log.export.onstar;
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
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class OnStarExport
/*     */   implements AutomaticExport
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(OnStarExport.class);
/*     */   
/*     */   private String emailSender;
/*     */   
/*     */   private String emailSubject;
/*     */   
/*  49 */   private List emailRecipients = new LinkedList();
/*     */   
/*     */   private TimedExecution timedExecution;
/*     */   private int exportLimit;
/*     */   private static final String STORAGE_KEY_LAST_EXPORT_TS = "sps.onstar.export.lts";
/*     */   
/*     */   public OnStarExport(Configuration configuration) {
/*  56 */     log.debug("initializing " + this);
/*  57 */     Number exportLimit = ConfigurationUtil.getNumber("limit", configuration);
/*  58 */     if (exportLimit == null) {
/*  59 */       this.exportLimit = 30000;
/*     */     } else {
/*  61 */       this.exportLimit = exportLimit.intValue();
/*     */     } 
/*  63 */     log.debug("...set export limit: " + this.exportLimit);
/*     */     
/*  65 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "mail.");
/*  66 */     this.emailSubject = subConfigurationWrapper1.getProperty("subject");
/*  67 */     log.debug("...set email subject: " + this.emailSubject);
/*  68 */     this.emailSender = subConfigurationWrapper1.getProperty("sender");
/*  69 */     log.debug("...set email sender: " + this.emailSender);
/*  70 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "recipient.");
/*  71 */     for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/*  72 */       String key = iter.next();
/*  73 */       String recipient = subConfigurationWrapper2.getProperty(key);
/*  74 */       this.emailRecipients.add(recipient);
/*     */     } 
/*  76 */     log.debug("...set email recipients: " + this.emailRecipients);
/*     */     
/*  78 */     List<Time> executionTimes = new LinkedList();
/*  79 */     SubConfigurationWrapper subConfigurationWrapper3 = new SubConfigurationWrapper(configuration, "exec.time.");
/*  80 */     for (Iterator<String> iterator1 = subConfigurationWrapper3.getKeys().iterator(); iterator1.hasNext(); ) {
/*  81 */       String key = iterator1.next();
/*  82 */       String time = subConfigurationWrapper3.getProperty(key);
/*     */       try {
/*  84 */         Time t = Time.parse(time);
/*  85 */         executionTimes.add(t);
/*  86 */       } catch (Exception e) {
/*  87 */         log.warn("unable to read execution time with key:" + key + " - exception:" + e + ", skipping");
/*     */       } 
/*     */     } 
/*  90 */     CollectionUtil.unify(executionTimes);
/*  91 */     Collections.sort(executionTimes);
/*  92 */     log.debug("...execution times: " + executionTimes);
/*  93 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation()
/*     */         {
/*     */           public boolean execute(Time executionTime) {
/*  96 */             OnStarExport.this.export(executionTime);
/*  97 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 104 */     this.timedExecution.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLastExportTimestamp() throws Exception {
/* 111 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 112 */     return ((Long)frameService.getPersistentObject("sps.onstar.export.lts")).longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportTimestamp(long timestamp) throws Exception {
/* 116 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 117 */     frameService.storePersistentObject("sps.onstar.export.lts", Long.valueOf(timestamp));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/* 122 */     log.info("running onstar export ...");
/*     */     try {
/* 124 */       log.debug("....determining last export date...");
/* 125 */       Date lastExportDate = null;
/*     */       try {
/* 127 */         lastExportDate = new Date(getLastExportTimestamp());
/* 128 */         log.debug("....last export date : " + DateFormat.getDateTimeInstance(3, 3).format(lastExportDate));
/* 129 */       } catch (Exception e) {
/* 130 */         log.debug("...could not determine last export date, disabling MIN filter");
/*     */       } 
/*     */       
/* 133 */       Calendar c = Calendar.getInstance();
/* 134 */       c.set(11, executionTime.getHour());
/* 135 */       c.set(12, executionTime.getMinute());
/* 136 */       c.set(13, 0);
/* 137 */       final Date currentDate = c.getTime();
/*     */       
/* 139 */       final Long tsMin = (lastExportDate != null) ? Long.valueOf(lastExportDate.getTime()) : null;
/* 140 */       final Long tsMax = Long.valueOf(currentDate.getTime());
/*     */       
/* 142 */       SPSEventLog.Query.BackendFilter backendFilter = new SPSEventLog.Query.BackendFilter()
/*     */         {
/* 144 */           private Collection flags = Arrays.asList(new SPSEventLog.Flag[] { SPSEventLog.FLAG_ONSTAR });
/*     */           
/*     */           public Adapter getAdapter() {
/* 147 */             return Adapter.NAO;
/*     */           }
/*     */           
/*     */           public Long getTimestampMAX() {
/* 151 */             return tsMax;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 155 */             return tsMin;
/*     */           }
/*     */           
/*     */           public String getNamePattern() {
/* 159 */             return "reprogram";
/*     */           }
/*     */           
/*     */           public Collection getFlags() {
/* 163 */             return this.flags;
/*     */           }
/*     */           
/*     */           public SPSEventLog.Query.BackendFilter.AttributeFilter[] getAttributeFilters() {
/* 167 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 172 */       log.debug("....querying event log for entries ");
/* 173 */       Collection entries = SPSEventLogFacade.getInstance().getEntries(backendFilter, this.exportLimit);
/* 174 */       log.debug("....retrieved " + entries.size() + " entries");
/* 175 */       if (!Util.isNullOrEmpty(entries)) {
/* 176 */         MailService.Callback callback = null;
/* 177 */         log.debug("....creating export file");
/* 178 */         DateFormat df = new SimpleDateFormat("MM'/'dd'/'yyyy HH':'mm");
/*     */         
/* 180 */         File exportFile = File.createTempFile("jtf", null);
/*     */         try {
/* 182 */           FileOutputStream fos = new FileOutputStream(exportFile);
/* 183 */           OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
/* 184 */           PrintWriter pw = new PrintWriter(osw);
/*     */           try {
/* 186 */             pw.println("VIN,DATE,STID,ESN,MIN,MDN");
/* 187 */             for (Iterator<SPSEventLog.LoggedEntry> iter = entries.iterator(); iter.hasNext(); ) {
/* 188 */               SPSEventLog.LoggedEntry entry = iter.next();
/* 189 */               StringBuffer tmp = new StringBuffer("{VIN},{TS},{STiD},{ESN},{MIN},{MDN}");
/* 190 */               StringUtilities.replace(tmp, "{TS}", df.format(new Date(entry.getTimestamp())));
/* 191 */               OnStarEntryDecorator element = new OnStarEntryDecorator((SPSEventLog.Entry)entry);
/* 192 */               StringUtilities.replace(tmp, "{VIN}", element.getVIN());
/* 193 */               StringUtilities.replace(tmp, "{STiD}", element.getSTiD());
/* 194 */               StringUtilities.replace(tmp, "{ESN}", element.getESN());
/* 195 */               StringUtilities.replace(tmp, "{MIN}", element.getMIN());
/* 196 */               StringUtilities.replace(tmp, "{MDN}", element.getMDN());
/* 197 */               pw.println(tmp);
/*     */             } 
/*     */           } finally {
/* 200 */             pw.close();
/*     */           } 
/*     */           
/* 203 */           final DataSource ds = new FileDataSource(exportFile)
/*     */             {
/*     */               public String getName() {
/* 206 */                 return "sps-export-" + DateConvert.toISOFormat(currentDate) + ".csv";
/*     */               }
/*     */               
/*     */               public String getContentType() {
/* 210 */                 return "text/csv";
/*     */               }
/*     */               
/*     */               public OutputStream getOutputStream() throws IOException {
/* 214 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 219 */           callback = new MailService.Callback()
/*     */             {
/*     */               public DataSource[] getAttachments() {
/* 222 */                 return new DataSource[] { this.val$ds };
/*     */               }
/*     */               
/*     */               public String getText() {
/* 226 */                 return "SEE ATTACHED FILE";
/*     */               }
/*     */               
/*     */               public String getSubject() {
/* 230 */                 return OnStarExport.this.emailSubject;
/*     */               }
/*     */               
/*     */               public Collection getRecipients() {
/* 234 */                 return OnStarExport.this.emailRecipients;
/*     */               }
/*     */               
/*     */               public String getSender() {
/* 238 */                 return OnStarExport.this.emailSender;
/*     */               }
/*     */               
/*     */               public Collection getReplyTo() {
/* 242 */                 return null;
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 247 */           log.debug(".... sending mail");
/* 248 */           MailService mailService = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 249 */           mailService.send(callback);
/*     */         } finally {
/* 251 */           if (!exportFile.delete() && exportFile.exists()) {
/* 252 */             exportFile.deleteOnExit();
/*     */           }
/*     */         } 
/*     */       } 
/* 256 */       setLastExportTimestamp(currentDate.getTime());
/* 257 */       log.info("...finished onstar export");
/*     */     }
/* 259 */     catch (Exception e) {
/* 260 */       log.error("unable to execute onstar export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 266 */     DateFormat df = new SimpleDateFormat("MM'/'dd'/'yyyy HH':'mm");
/* 267 */     System.out.println(df.format(new Date()));
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\export\onstar\OnStarExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */