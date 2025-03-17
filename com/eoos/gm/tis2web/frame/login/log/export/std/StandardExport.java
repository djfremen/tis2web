/*     */ package com.eoos.gm.tis2web.frame.login.log.export.std;
/*     */ 
/*     */ import com.eoos.collection.v2.CollectionUtil;
/*     */ import com.eoos.datatype.Time;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.FrameService;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLog;
/*     */ import com.eoos.gm.tis2web.frame.login.log.LoginLogFacade;
/*     */ import com.eoos.gm.tis2web.frame.login.log.export.AutomaticExport;
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
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class StandardExport
/*     */   implements AutomaticExport
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(StandardExport.class);
/*     */   
/*     */   private static final String LOG_TEMPLATE = "{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}";
/*     */   
/*  45 */   private static final DateFormat LOG_DATEFORMAT = DateFormat.getDateInstance(2);
/*     */   
/*  47 */   private static final DateFormat LOG_TIMEFORMAT = DateFormat.getTimeInstance(2);
/*     */   
/*     */   private int exportLimit;
/*     */   
/*     */   private String emailSender;
/*     */   
/*     */   private String emailSubject;
/*     */   
/*  55 */   private List emailRecipients = new LinkedList();
/*     */   private TimedExecution timedExecution;
/*     */   private static final String STORAGE_KEY_LAST_EXPORT_TS = "login.log.std.export.lts";
/*     */   
/*     */   public StandardExport(Configuration configuration) {
/*  60 */     log.debug("initializing " + this);
/*  61 */     Number exportLimit = ConfigurationUtil.getNumber("limit", configuration);
/*  62 */     if (exportLimit == null) {
/*  63 */       this.exportLimit = 35000;
/*     */     } else {
/*  65 */       this.exportLimit = exportLimit.intValue();
/*     */     } 
/*  67 */     log.debug("...set export limit: " + this.exportLimit);
/*     */     
/*  69 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper(configuration, "mail.");
/*  70 */     this.emailSubject = subConfigurationWrapper1.getProperty("subject");
/*  71 */     log.debug("...set email subject: " + this.emailSubject);
/*  72 */     this.emailSender = subConfigurationWrapper1.getProperty("sender");
/*  73 */     log.debug("...set email sender: " + this.emailSender);
/*  74 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "recipient.");
/*  75 */     for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/*  76 */       String key = iter.next();
/*  77 */       String recipient = subConfigurationWrapper2.getProperty(key);
/*  78 */       this.emailRecipients.add(recipient);
/*     */     } 
/*  80 */     log.debug("...set email recipients: " + this.emailRecipients);
/*     */     
/*  82 */     List<Time> executionTimes = new LinkedList();
/*  83 */     SubConfigurationWrapper subConfigurationWrapper3 = new SubConfigurationWrapper(configuration, "exec.time.");
/*  84 */     for (Iterator<String> iterator1 = subConfigurationWrapper3.getKeys().iterator(); iterator1.hasNext(); ) {
/*  85 */       String key = iterator1.next();
/*  86 */       String time = subConfigurationWrapper3.getProperty(key);
/*     */       try {
/*  88 */         Time t = Time.parse(time);
/*  89 */         executionTimes.add(t);
/*  90 */       } catch (Exception e) {
/*  91 */         log.warn("unable to read execution time with key:" + key + " - exception:" + e + ", skipping");
/*     */       } 
/*     */     } 
/*  94 */     CollectionUtil.unify(executionTimes);
/*  95 */     Collections.sort(executionTimes);
/*  96 */     log.debug("...execution times: " + executionTimes);
/*  97 */     this.timedExecution = new TimedExecution(new TimedExecution.Operation()
/*     */         {
/*     */           public boolean execute(Time executionTime) {
/* 100 */             StandardExport.this.export(executionTime);
/* 101 */             return true;
/*     */           }
/*     */         },  executionTimes);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 108 */     this.timedExecution.start();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private long getLastExportTimestamp() throws Exception {
/* 114 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 115 */     return ((Long)frameService.getPersistentObject("login.log.std.export.lts")).longValue();
/*     */   }
/*     */   
/*     */   private void setLastExportTimestamp(long timestamp) throws Exception {
/* 119 */     FrameService frameService = (FrameService)FrameServiceProvider.getInstance().getService(FrameService.class);
/* 120 */     frameService.storePersistentObject("login.log.std.export.lts", Long.valueOf(timestamp));
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void export(Time executionTime) {
/* 125 */     log.debug("running " + Util.getClassName(getClass()));
/*     */     try {
/* 127 */       log.debug("....determining last export date...");
/* 128 */       Date lastExportDate = null;
/*     */       try {
/* 130 */         lastExportDate = new Date(getLastExportTimestamp());
/* 131 */         log.debug("....last export date : " + DateFormat.getDateTimeInstance(3, 3).format(lastExportDate));
/* 132 */       } catch (Exception e) {
/* 133 */         log.debug("...could not determine last export date, disabling MIN filter");
/*     */       } 
/*     */       
/* 136 */       Calendar c = Calendar.getInstance();
/* 137 */       c.set(11, executionTime.getHour());
/* 138 */       c.set(12, executionTime.getMinute());
/* 139 */       c.set(13, 0);
/* 140 */       final Date currentDate = c.getTime();
/*     */       
/* 142 */       final Long tsMin = (lastExportDate != null) ? Long.valueOf(lastExportDate.getTime()) : null;
/* 143 */       final Long tsMax = Long.valueOf(currentDate.getTime());
/*     */       
/* 145 */       LoginLog.Query.BackendFilter backendFilter = new LoginLog.Query.BackendFilter()
/*     */         {
/*     */           public Long getTimestampMAX() {
/* 148 */             return tsMax;
/*     */           }
/*     */           
/*     */           public Long getTimestampMIN() {
/* 152 */             return tsMin;
/*     */           }
/*     */           
/*     */           public String getUsergroup() {
/* 156 */             return null;
/*     */           }
/*     */           
/*     */           public String getUsername() {
/* 160 */             return null;
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 165 */       log.debug("....querying login log for entries ");
/* 166 */       Collection entries = LoginLogFacade.getInstance().getEntries(backendFilter, null, this.exportLimit);
/* 167 */       log.debug("....retrieved " + entries.size() + " entries");
/*     */       
/* 169 */       if (!Util.isNullOrEmpty(entries)) {
/* 170 */         MailService.Callback callback = null;
/* 171 */         log.debug("....creating export file");
/* 172 */         File exportFile = File.createTempFile("jtf-", null);
/*     */         try {
/* 174 */           FileOutputStream fos = new FileOutputStream(exportFile);
/* 175 */           OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
/* 176 */           PrintWriter pw = new PrintWriter(osw);
/*     */ 
/*     */           
/*     */           try {
/* 180 */             StringBuffer header = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}");
/* 181 */             StringUtilities.replace(header, "{USER}", "NAME");
/* 182 */             StringUtilities.replace(header, "{STATUS}", "STATUS");
/* 183 */             StringUtilities.replace(header, "{DATE}", "DATE");
/* 184 */             StringUtilities.replace(header, "{TIME}", "TIME");
/* 185 */             StringUtilities.replace(header, "{IP}", "SOURCE ADDR");
/* 186 */             String freeParam = ApplicationContext.getInstance().getProperty("frame.scout.nao.login.logparam");
/* 187 */             if (freeParam == null) {
/* 188 */               freeParam = "";
/*     */             }
/* 190 */             StringUtilities.replace(header, "{FREEPARAM}", freeParam);
/* 191 */             StringUtilities.replace(header, "{ORIGIN}", "ORIGIN");
/* 192 */             StringUtilities.replace(header, "{GROUPNAME}", "GROUP");
/* 193 */             StringUtilities.replace(header, "{GROUPCODE}", "CODE");
/* 194 */             StringUtilities.replace(header, "{DIVISION}", "DIVISION CODE");
/* 195 */             StringUtilities.replace(header, "{ORG_COUNTRY}", "COUNTRY (SPOREF)");
/* 196 */             StringUtilities.replace(header, "{MAPPED_COUNTRY}", "COUNTRY");
/* 197 */             StringUtilities.replace(header, "{T2W_GROUP}", "INTERNAL GROUP");
/*     */             
/* 199 */             pw.println(header);
/* 200 */             for (Iterator<LoginLog.Entry2> iter = entries.iterator(); iter.hasNext(); ) {
/* 201 */               LoginLog.Entry2 entry = iter.next();
/* 202 */               StringBuffer logentry = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}\t{DIVISION}\t{ORG_COUNTRY}\t{MAPPED_COUNTRY}\t{T2W_GROUP}");
/* 203 */               StringUtilities.replace(logentry, "{USER}", adjust(entry.getUsername()));
/* 204 */               StringUtilities.replace(logentry, "{STATUS}", entry.successfulLogin() ? "allow" : "deny");
/* 205 */               StringUtilities.replace(logentry, "{DATE}", LOG_DATEFORMAT.format(new Date(entry.getTimestamp())));
/* 206 */               StringUtilities.replace(logentry, "{TIME}", LOG_TIMEFORMAT.format(new Date(entry.getTimestamp())));
/* 207 */               StringUtilities.replace(logentry, "{IP}", adjust(entry.getSourceAddress()));
/* 208 */               StringUtilities.replace(logentry, "{FREEPARAM}", adjust(entry.getFreeParameter()));
/* 209 */               StringUtilities.replace(logentry, "{ORIGIN}", adjust(entry.getOrigin()));
/* 210 */               StringUtilities.replace(logentry, "{GROUPNAME}", adjust(entry.getUserGroup()));
/* 211 */               StringUtilities.replace(logentry, "{GROUPCODE}", adjust(entry.getDealerCode()));
/* 212 */               StringUtilities.replace(logentry, "{DIVISION}", adjust(entry.getDivisionCode()));
/* 213 */               StringUtilities.replace(logentry, "{ORG_COUNTRY}", adjust(entry.getOriginalCountryCode()));
/* 214 */               StringUtilities.replace(logentry, "{MAPPED_COUNTRY}", adjust(entry.getMappedCountryCode()));
/* 215 */               StringUtilities.replace(logentry, "{T2W_GROUP}", adjust(entry.getT2WGroup()));
/* 216 */               pw.println(logentry);
/*     */             } 
/*     */           } finally {
/* 219 */             pw.close();
/*     */           } 
/*     */           
/* 222 */           final DataSource ds = new FileDataSource(exportFile) {
/*     */               public String getName() {
/* 224 */                 return "loginlog-export-" + DateConvert.toISOFormat(currentDate) + ".csv";
/*     */               }
/*     */               
/*     */               public String getContentType() {
/* 228 */                 return "text/csv";
/*     */               }
/*     */               
/*     */               public OutputStream getOutputStream() throws IOException {
/* 232 */                 throw new UnsupportedOperationException();
/*     */               }
/*     */             };
/*     */           
/* 236 */           callback = new MailService.Callback()
/*     */             {
/*     */               public DataSource[] getAttachments() {
/* 239 */                 return new DataSource[] { this.val$ds };
/*     */               }
/*     */               
/*     */               public String getText() {
/* 243 */                 return "SEE ATTACHED FILE";
/*     */               }
/*     */               
/*     */               public String getSubject() {
/* 247 */                 return StandardExport.this.emailSubject;
/*     */               }
/*     */               
/*     */               public Collection getRecipients() {
/* 251 */                 return StandardExport.this.emailRecipients;
/*     */               }
/*     */               
/*     */               public String getSender() {
/* 255 */                 return StandardExport.this.emailSender;
/*     */               }
/*     */               
/*     */               public Collection getReplyTo() {
/* 259 */                 return null;
/*     */               }
/*     */             };
/*     */ 
/*     */           
/* 264 */           log.debug(".... sending mail");
/* 265 */           MailService mailService = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 266 */           mailService.send(callback);
/*     */         } finally {
/* 268 */           if (!exportFile.delete()) {
/* 269 */             log.warn("unable to delete temporary file: " + exportFile + ", scheduling deleting on JVM exit");
/* 270 */             exportFile.deleteOnExit();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 275 */       setLastExportTimestamp(currentDate.getTime());
/* 276 */       log.debug("...successfully executed export");
/*     */     }
/* 278 */     catch (Exception e) {
/* 279 */       log.error("unable to execute standard export - exception:" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String adjust(String string) {
/* 285 */     if (string == null) {
/* 286 */       return "-";
/*     */     }
/* 288 */     return String.valueOf(string);
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\export\std\StandardExport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */