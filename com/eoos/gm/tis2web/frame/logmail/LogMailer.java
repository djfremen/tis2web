/*     */ package com.eoos.gm.tis2web.frame.logmail;
/*     */ 
/*     */ import com.eoos.file.FileUtil;
/*     */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.io.StreamUtil;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.scsm.v2.filter.Filter;
/*     */ import com.eoos.scsm.v2.filter.LinkedFilter;
/*     */ import com.eoos.scsm.v2.io.OutputStreamByteCount;
/*     */ import com.eoos.scsm.v2.util.Counter;
/*     */ import com.eoos.scsm.v2.util.ICounter;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TimerTask;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogMailer
/*     */ {
/*     */   public static class SizeExceededException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  52 */     private int index = 0;
/*     */     
/*     */     public SizeExceededException(int index) {
/*  55 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/*  59 */       return this.index;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  64 */   private static final Logger log = Logger.getLogger(LogMailer.class);
/*     */   
/*  66 */   private Date startDate = null;
/*     */   
/*  68 */   private String sender = null;
/*     */   
/*  70 */   private List recipients = new LinkedList();
/*     */   
/*  72 */   private String subject = null;
/*     */   
/*  74 */   private File directory = null;
/*     */   
/*     */   private boolean basicInitialized = false;
/*     */   
/*     */   private boolean fullyInitialized = false;
/*     */   
/*  80 */   private TimerTask trigger = null;
/*     */   
/*  82 */   private Filter inclusionFilter = null;
/*     */   
/*  84 */   private Filter exclusionFilter = null;
/*     */   
/*  86 */   private long maxAge = 259200000L;
/*     */   
/*  88 */   private long maxSize = 20000000L;
/*     */   
/*     */   private long delay;
/*     */   
/*     */   private LogMailer() {
/*  93 */     log.debug("initializing LogMailer ...");
/*     */     try {
/*  95 */       SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.log.mailer.");
/*     */ 
/*     */       
/*  98 */       String dir = subConfigurationWrapper1.getProperty("dir");
/*  99 */       this.directory = new File(dir);
/* 100 */       if (!this.directory.isDirectory()) {
/* 101 */         throw new IllegalArgumentException("dir configuration property does not reference a directory");
/*     */       }
/* 103 */       log.debug("...directory :" + this.directory.getAbsolutePath());
/*     */ 
/*     */       
/* 106 */       this.sender = subConfigurationWrapper1.getProperty("email.sender");
/* 107 */       this.sender = (this.sender == null) ? "system@tis2web" : this.sender;
/* 108 */       log.debug("... email sender:" + this.sender);
/*     */ 
/*     */       
/* 111 */       this.subject = subConfigurationWrapper1.getProperty("email.subject");
/* 112 */       this.subject = (this.subject == null) ? "tis2web logfiles" : this.subject;
/* 113 */       log.debug("... email subject:" + this.subject);
/*     */       
/*     */       try {
/* 116 */         this.maxAge = (new TypeDecorator((Configuration)subConfigurationWrapper1)).getNumber("max.age").longValue() * 24L * 60L * 60L * 1000L;
/* 117 */       } catch (Exception e) {
/* 118 */         log.warn("...unable to read parameter 'max.age', using default");
/*     */       } 
/* 120 */       log.debug("...max age is set to " + this.maxAge + "ms (" + Util.formatDuration(this.maxAge, "${days} days, ${hours} hours") + ")");
/*     */       
/*     */       try {
/* 123 */         this.maxSize = (new TypeDecorator((Configuration)subConfigurationWrapper1)).getNumber("max.size").longValue() * 1024L * 1024L;
/* 124 */       } catch (Exception e) {
/* 125 */         log.warn("...unable to read parameter 'max.size', using default");
/*     */       } 
/* 127 */       log.debug("...max size is set to " + this.maxSize + " bytes (" + Util.formatBytesSI(this.maxSize, "${mega} MB") + ")");
/*     */       
/* 129 */       this.inclusionFilter = Filter.EXCLUDE_ALL;
/*     */       
/* 131 */       SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "include.pattern.");
/* 132 */       for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/* 133 */         String _pattern = subConfigurationWrapper2.getProperty(iter.next());
/* 134 */         log.debug("...adding inclusion pattern: " + _pattern);
/* 135 */         final Pattern pattern = Pattern.compile(_pattern, 2);
/*     */         
/* 137 */         this.inclusionFilter = (Filter)new LinkedFilter(new Filter[] { this.inclusionFilter, new Filter()
/*     */               {
/*     */                 public boolean include(Object obj) {
/* 140 */                   return pattern.matcher((String)obj).matches();
/*     */                 }
/*     */               },   }, LinkedFilter.OR);
/*     */       } 
/* 144 */       if (this.inclusionFilter == Filter.EXCLUDE_ALL) {
/* 145 */         log.debug("setting inclusion filter to 'include all' since no inclusion filter is set");
/* 146 */         this.inclusionFilter = Filter.INCLUDE_ALL;
/*     */       } 
/*     */       
/* 149 */       SubConfigurationWrapper subConfigurationWrapper3 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "exclude.pattern.");
/*     */ 
/*     */       
/* 152 */       this.exclusionFilter = Filter.EXCLUDE_ALL;
/* 153 */       for (Iterator<String> iterator1 = subConfigurationWrapper3.getKeys().iterator(); iterator1.hasNext(); ) {
/* 154 */         String _pattern = subConfigurationWrapper3.getProperty(iterator1.next());
/* 155 */         log.debug("...adding exclusion pattern: " + _pattern);
/* 156 */         final Pattern pattern = Pattern.compile(_pattern, 2);
/*     */         
/* 158 */         this.exclusionFilter = (Filter)new LinkedFilter(new Filter[] { this.exclusionFilter, new Filter()
/*     */               {
/*     */                 public boolean include(Object obj) {
/* 161 */                   return pattern.matcher((String)obj).matches();
/*     */                 }
/*     */               },   }, LinkedFilter.OR);
/*     */       } 
/*     */       
/* 166 */       this.basicInitialized = true;
/*     */       
/* 168 */       String _enable = subConfigurationWrapper1.getProperty("enable");
/* 169 */       boolean enable = false;
/*     */       try {
/* 171 */         enable = Boolean.valueOf(_enable).booleanValue();
/* 172 */       } catch (Exception e) {}
/*     */       
/* 174 */       if (enable) {
/* 175 */         log.debug("... automatic mailing enabled");
/*     */         try {
/* 177 */           SubConfigurationWrapper subConfigurationWrapper = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "email.recipient.");
/* 178 */           for (Iterator<String> iterator = subConfigurationWrapper.getKeys().iterator(); iterator.hasNext(); ) {
/* 179 */             String recipient = subConfigurationWrapper.getProperty(iterator.next());
/* 180 */             this.recipients.add(recipient);
/*     */           } 
/* 182 */           log.debug("... email recipients:" + this.recipients);
/*     */ 
/*     */           
/* 185 */           String time = subConfigurationWrapper1.getProperty("time").trim();
/* 186 */           int index = time.indexOf(":");
/* 187 */           int hours = Integer.parseInt(time.substring(0, index));
/* 188 */           int minutes = Integer.parseInt(time.substring(index + 1, time.length()));
/* 189 */           Calendar c = Calendar.getInstance();
/* 190 */           c.set(11, hours);
/* 191 */           c.set(12, minutes);
/*     */           
/* 193 */           if (c.getTimeInMillis() < System.currentTimeMillis()) {
/* 194 */             c.set(5, c.get(5) + 1);
/*     */           }
/*     */           
/* 197 */           this.startDate = c.getTime();
/* 198 */           log.debug("... start date set to:" + this.startDate);
/*     */           
/* 200 */           String _delay = subConfigurationWrapper1.getProperty("delay");
/* 201 */           int delayhours = 24;
/*     */           try {
/* 203 */             delayhours = Integer.parseInt(_delay);
/* 204 */           } catch (Exception e) {
/* 205 */             log.warn("unable to parse, using default - exception: " + e, e);
/*     */           } 
/*     */           
/* 208 */           this.delay = (delayhours * 60 * 60 * 1000);
/* 209 */           log.debug("... delay:" + this.delay + " ms (" + delayhours + " hours)");
/*     */           
/* 211 */           this.fullyInitialized = true;
/*     */         }
/* 213 */         catch (Exception e) {
/* 214 */           log.error("... unable to initialize for automatic mailing - exception:" + e + " , disabling automcatic mailing");
/*     */         } 
/*     */       } else {
/* 217 */         log.debug("... automatic mailing is disabled");
/*     */       } 
/* 219 */     } catch (Exception e) {
/* 220 */       log.warn("...unable to init log mailer, disabling it - error:" + e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized LogMailer getInstance() {
/* 227 */     LogMailer instance = (LogMailer)ApplicationContext.getInstance().getObject(LogMailer.class);
/* 228 */     if (instance == null) {
/* 229 */       instance = new LogMailer();
/* 230 */       ApplicationContext.getInstance().storeObject(LogMailer.class, instance);
/*     */     } 
/* 232 */     return instance;
/*     */   }
/*     */   
/*     */   public void start() {
/* 236 */     if (this.fullyInitialized && this.trigger == null) {
/*     */       
/* 238 */       this.trigger = Util.createTimerTask(new Runnable()
/*     */           {
/*     */             public void run() {
/* 241 */               LogMailer.log.debug("starting log mailer thread with low priority...");
/*     */               try {
/* 243 */                 Thread t = new Thread()
/*     */                   {
/*     */                     public void run() {
/*     */                       try {
/* 247 */                         LogMailer.this.sendLogs(null);
/* 248 */                       } catch (Throwable t) {
/* 249 */                         LogMailer.log.error("unable to send log files - error:" + t, t);
/*     */                       } 
/*     */                     }
/*     */                   };
/* 253 */                 t.setPriority(1);
/* 254 */                 t.start();
/*     */               }
/* 256 */               catch (Throwable t) {}
/*     */             }
/*     */           });
/*     */ 
/*     */       
/* 261 */       Util.getTimer().scheduleAtFixedRate(this.trigger, this.startDate, this.delay);
/* 262 */       log.debug("started automatic log mailing");
/*     */     } else {
/* 264 */       log.debug("ignored call to start (already started or not correctly initialized)");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stop() {
/* 269 */     if (this.trigger != null) {
/* 270 */       this.trigger.cancel();
/* 271 */       this.trigger = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean include(File file) {
/* 284 */     long age = System.currentTimeMillis() - file.lastModified();
/* 285 */     if (age > this.maxAge) {
/* 286 */       log.debug("...file is not included (too old): " + file);
/* 287 */       return false;
/*     */     } 
/*     */     
/* 290 */     String filename = Util.relativize(file, this.directory).getPath();
/* 291 */     log.debug("....filename for pattern matching: " + filename);
/* 292 */     boolean ret = (this.inclusionFilter.include(filename) && !this.exclusionFilter.include(filename));
/* 293 */     if (log.isDebugEnabled()) {
/* 294 */       log.debug("....file is " + (ret ? "included" : "excluded"));
/*     */     }
/* 296 */     return ret;
/*     */   }
/*     */   
/*     */   private LinkedList collectFiles(File dir) {
/* 300 */     if (!dir.isDirectory()) {
/* 301 */       return new LinkedList();
/*     */     }
/* 303 */     LinkedList<File> ret = new LinkedList();
/* 304 */     File[] files = dir.listFiles(new FileFilter()
/*     */         {
/*     */           public boolean accept(File file) {
/* 307 */             if (file.isDirectory()) {
/* 308 */               return true;
/*     */             }
/* 310 */             return LogMailer.this.include(file);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 315 */     if (files != null) {
/* 316 */       for (int i = 0; i < files.length; i++) {
/* 317 */         if (files[i].isDirectory()) {
/* 318 */           ret.addAll(collectFiles(files[i]));
/*     */         } else {
/* 320 */           ret.add(files[i]);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 324 */       log.warn("unable to list directory content for dir: " + String.valueOf(dir) + " (listFiles() returned null), IGNORING DIRECTORY!!!");
/*     */     } 
/* 326 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean maxSizeExeeded(long size) {
/* 331 */     if (this.maxSize != -1L) {
/* 332 */       return (size > this.maxSize);
/*     */     }
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createZIP(List<File> files, OutputStream os) throws SizeExceededException, IOException {
/* 339 */     Counter counter = new Counter();
/* 340 */     OutputStreamByteCount osbc = new OutputStreamByteCount(os, (ICounter)counter);
/* 341 */     ZipOutputStream zos = new ZipOutputStream((OutputStream)osbc);
/*     */     try {
/* 343 */       zos.setLevel(9);
/* 344 */       for (int i = 0; i < files.size(); i++) {
/* 345 */         File file = files.get(i);
/* 346 */         FileInputStream fis = new FileInputStream(file);
/*     */         try {
/* 348 */           String entryName = FileUtil.relativize(this.directory, file).getPath();
/* 349 */           ZipEntry entry = new ZipEntry(entryName);
/* 350 */           zos.putNextEntry(entry);
/* 351 */           StreamUtil.transfer(fis, zos);
/* 352 */           log.info("added file:" + file.getAbsolutePath());
/* 353 */           zos.flush();
/*     */         } finally {
/* 355 */           fis.close();
/*     */         } 
/*     */         
/* 358 */         if (maxSizeExeeded(counter.getCount().longValue())) {
/* 359 */           throw new SizeExceededException(i);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 363 */       StreamUtil.close(zos, log);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendLogs(final String recipient) throws Throwable {
/* 369 */     log.info("start sending log files ....");
/*     */     
/* 371 */     if ((recipient != null && !this.basicInitialized) || (recipient == null && !this.fullyInitialized)) {
/* 372 */       log.debug("...incorrectly configured, ignoring call");
/*     */       
/*     */       return;
/*     */     } 
/* 376 */     log.debug("...constructing mail attachment");
/* 377 */     List<?> remainingFiles = collectFiles(this.directory);
/* 378 */     if (remainingFiles == null || remainingFiles.size() == 0) {
/* 379 */       throw new Exception("no log files available (check exclusion filters)");
/*     */     }
/*     */     
/* 382 */     Collections.sort(remainingFiles, Util.reverseComparator(FileUtil.COMPARATOR_DATE));
/*     */     
/* 384 */     int iteration = 0;
/* 385 */     while (remainingFiles.size() > 0) {
/* 386 */       List<?> files = new LinkedList(remainingFiles);
/*     */       
/* 388 */       final File attachment = Util.createTmpFile();
/* 389 */       boolean done = false;
/* 390 */       while (!done && files.size() > 0) {
/*     */         try {
/* 392 */           FileOutputStream fos = new FileOutputStream(attachment);
/*     */           try {
/* 394 */             createZIP(files, fos);
/* 395 */             done = true;
/*     */           } finally {
/* 397 */             StreamUtil.close(fos, log);
/*     */           } 
/* 399 */         } catch (SizeExceededException e) {
/* 400 */           if (e.getIndex() > 0) {
/* 401 */             files = files.subList(0, e.getIndex());
/* 402 */             log.warn("...size exeeded - retrying"); continue;
/*     */           } 
/* 404 */           log.warn("...size exeeded (single file), throwing exception");
/* 405 */           throw e;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 411 */       log.debug("...creating mail message");
/*     */       
/* 413 */       String host = ApplicationContext.getInstance().getHostName();
/* 414 */       if (host == null) {
/* 415 */         host = ApplicationContext.getInstance().getIPAddr();
/* 416 */         host = (host == null) ? "unknown" : host;
/*     */       } 
/* 418 */       host = StringUtilities.replace(host, ".", "_");
/*     */       
/* 420 */       final String _name = ConfigurationServiceProvider.getService().getProperty("frame.log.mailer.archive.name");
/* 421 */       log.debug("...archive name: " + String.valueOf(_name));
/*     */       
/* 423 */       final DataSource ds = new DataSource()
/*     */         {
/*     */           public String getContentType() {
/* 426 */             return "application/octet-stream";
/*     */           }
/*     */           
/*     */           public InputStream getInputStream() throws IOException {
/* 430 */             return new FileInputStream(attachment);
/*     */           }
/*     */           
/*     */           public String getName() {
/* 434 */             return _name;
/*     */           }
/*     */           
/*     */           public OutputStream getOutputStream() throws IOException {
/* 438 */             throw new IOException("read only");
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/* 444 */       final String subject = this.subject + " (part " + iteration++ + ")";
/* 445 */       MailService.Callback callback = new MailService.Callback()
/*     */         {
/*     */           public String getSender() {
/* 448 */             return LogMailer.this.sender;
/*     */           }
/*     */           
/*     */           public Collection getRecipients() {
/* 452 */             Collection<String> retValue = null;
/* 453 */             if (recipient == null) {
/* 454 */               retValue = LogMailer.this.recipients;
/*     */             } else {
/* 456 */               retValue = new LinkedList();
/* 457 */               retValue.add(recipient);
/*     */             } 
/* 459 */             return retValue;
/*     */           }
/*     */           
/*     */           public String getSubject() {
/* 463 */             return subject;
/*     */           }
/*     */           
/*     */           public String getText() {
/* 467 */             return "log files attached";
/*     */           }
/*     */           
/*     */           public DataSource[] getAttachments() {
/* 471 */             return new DataSource[] { this.val$ds };
/*     */           }
/*     */           
/*     */           public Collection getReplyTo() {
/* 475 */             return null;
/*     */           }
/*     */         };
/*     */       
/* 479 */       log.info("... sending mail");
/* 480 */       MailService ms = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 481 */       ms.send(callback);
/* 482 */       remainingFiles.removeAll(files);
/*     */     } 
/* 484 */     log.debug("....finished");
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\logmail\LogMailer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */