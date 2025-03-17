/*     */ package com.eoos.gm.tis2web.frame.login.monitor;
/*     */ 
/*     */ import com.eoos.datatype.ExceptionWrapper;
/*     */ import com.eoos.datatype.SectionIndex;
/*     */ import com.eoos.filter.Filter;
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.FrameServiceProvider;
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.declaration.service.MailService;
/*     */ import com.eoos.propcfg.Configuration;
/*     */ import com.eoos.propcfg.SubConfigurationWrapper;
/*     */ import com.eoos.propcfg.TypeDecorator;
/*     */ import com.eoos.scsm.v2.collection.CollectionUtil;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import com.eoos.util.PeriodicTask;
/*     */ import com.eoos.util.StringUtilities;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoginMonitor
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(LoginMonitor.class);
/*     */   
/*  39 */   private static final DateFormat DATEFORMAT = DateFormat.getDateTimeInstance(1, 1);
/*     */   
/*  41 */   private static LoginMonitor instance = null;
/*     */   private static String EMAIL_SUBJECT;
/*     */   private static String EMAIL_BODY_TEMPLATE;
/*     */   
/*     */   public class MailServiceCallback
/*     */     implements MailService.Callback
/*     */   {
/*     */     private String user;
/*     */     private int count;
/*     */     private long startdate;
/*     */     private long enddate;
/*     */     
/*     */     public MailServiceCallback(String user, int count, long startdate, long enddate) {
/*  54 */       this.user = user;
/*  55 */       this.count = count;
/*  56 */       this.startdate = startdate;
/*  57 */       this.enddate = enddate;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSender() {
/*  62 */       return LoginMonitor.this.sender;
/*     */     }
/*     */     
/*     */     public Collection getRecipients() {
/*  66 */       return LoginMonitor.this.recipients;
/*     */     }
/*     */     
/*     */     public String getSubject() {
/*  70 */       return LoginMonitor.EMAIL_SUBJECT;
/*     */     }
/*     */     
/*     */     public String getText() {
/*  74 */       StringBuffer retValue = new StringBuffer(LoginMonitor.EMAIL_BODY_TEMPLATE);
/*     */       
/*  76 */       StringUtilities.replace(retValue, "{USER}", this.user);
/*  77 */       StringUtilities.replace(retValue, "{COUNT}", String.valueOf(this.count));
/*  78 */       StringUtilities.replace(retValue, "{STARTDATE}", LoginMonitor.DATEFORMAT.format(new Date(this.startdate)));
/*  79 */       StringUtilities.replace(retValue, "{ENDDATE}", LoginMonitor.DATEFORMAT.format(new Date(this.enddate)));
/*  80 */       return retValue.toString();
/*     */     }
/*     */     
/*     */     public DataSource[] getAttachments() {
/*  84 */       return null;
/*     */     }
/*     */     
/*     */     public Collection getReplyTo() {
/*  88 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  98 */       String template = new String(ApplicationContext.getInstance().loadResource("frame/loginmonitor/mailtemplate.txt"));
/*  99 */       SectionIndex index = StringUtilities.getSectionIndex(template, "<subject>", "</subject>", 0, false, false);
/* 100 */       EMAIL_SUBJECT = StringUtilities.replace(StringUtilities.getSectionContent(template, index), "\r\n", "");
/* 101 */       index = StringUtilities.getSectionIndex(template, "<body>", "</body>", 0, false, false);
/* 102 */       EMAIL_BODY_TEMPLATE = StringUtilities.getSectionContent(template, index);
/* 103 */     } catch (Exception e) {
/* 104 */       log.fatal("unable to init mail template - error:" + e, e);
/* 105 */       throw new ExceptionWrapper(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean sendMail = true;
/* 111 */   private Number keepRecordInterval = null;
/*     */   
/*     */   private boolean failedCheck = false;
/*     */   
/* 115 */   private Number failedMaxCount = null;
/*     */   
/* 117 */   private Number failedCheckInterval = null;
/*     */   
/* 119 */   private String sender = null;
/*     */   
/* 121 */   private Collection recipients = new HashSet();
/*     */   
/* 123 */   private List loginInfoList = Collections.synchronizedList(new LinkedList());
/*     */   private PeriodicTask ptCleanup;
/*     */   
/*     */   private class CleanupTask implements Runnable {
/*     */     public void run() {
/* 128 */       final long minTimestamp = System.currentTimeMillis() - LoginMonitor.this.keepRecordInterval.longValue();
/* 129 */       Filter filter = new Filter()
/*     */         {
/*     */           public boolean include(Object object) {
/* 132 */             boolean retValue = false;
/*     */             try {
/* 134 */               LoginMonitor.LoginInfoWrapper wrapper = (LoginMonitor.LoginInfoWrapper)object;
/* 135 */               LoginInfo info = wrapper.getLoginInfo();
/* 136 */               retValue = (info.getRequestAt() > minTimestamp);
/* 137 */             } catch (Exception e) {
/* 138 */               LoginMonitor.log.warn("unable to determine inclusion status, returning false - exception: " + e, e);
/*     */             } 
/* 140 */             return retValue;
/*     */           }
/*     */         };
/*     */       
/* 144 */       synchronized (LoginMonitor.this.loginInfoList) {
/* 145 */         CollectionUtil.filter(LoginMonitor.this.loginInfoList, filter);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private CleanupTask() {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected class LoginInfoWrapper
/*     */   {
/*     */     private final LoginInfo loginInfo;
/*     */     private volatile boolean processed = false;
/*     */     private final boolean successful;
/*     */     
/*     */     public LoginInfoWrapper(LoginInfo info, boolean successful) {
/* 161 */       this.loginInfo = info;
/* 162 */       this.successful = successful;
/*     */     }
/*     */     
/*     */     public LoginInfo getLoginInfo() {
/* 166 */       return this.loginInfo;
/*     */     }
/*     */     
/*     */     public boolean isProcessed() {
/* 170 */       return this.processed;
/*     */     }
/*     */     
/*     */     public void markProcessed() {
/* 174 */       this.processed = true;
/*     */     }
/*     */     
/*     */     public boolean wasSuccessful() {
/* 178 */       return this.successful;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 182 */       return 7 + this.loginInfo.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 187 */       if (this == obj)
/* 188 */         return true; 
/* 189 */       if (obj instanceof LoginInfoWrapper) {
/* 190 */         LoginInfoWrapper other = (LoginInfoWrapper)obj;
/* 191 */         boolean ret = Util.equals(this.loginInfo, other.loginInfo);
/* 192 */         return ret;
/*     */       } 
/* 194 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginMonitor() {
/* 202 */     SubConfigurationWrapper subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)ApplicationContext.getInstance(), "frame.login.monitor.");
/* 203 */     TypeDecorator _configuration = new TypeDecorator((Configuration)subConfigurationWrapper1);
/*     */ 
/*     */     
/* 206 */     Number keepRecordInterval = _configuration.getNumber("record.ttl");
/* 207 */     if (keepRecordInterval != null) {
/* 208 */       this.keepRecordInterval = Long.valueOf((keepRecordInterval.intValue() * 60 * 1000));
/*     */     } else {
/* 210 */       this.keepRecordInterval = Long.valueOf(600000L);
/*     */     } 
/*     */ 
/*     */     
/* 214 */     String sender = _configuration.getProperty("email.sender");
/* 215 */     if (sender != null && sender.length() > 0) {
/* 216 */       this.sender = sender;
/*     */     } else {
/* 218 */       log.warn("disabling mail notification, since sender property is not specified");
/* 219 */       this.sendMail = false;
/*     */     } 
/*     */ 
/*     */     
/* 223 */     SubConfigurationWrapper subConfigurationWrapper2 = new SubConfigurationWrapper((Configuration)_configuration, "email.recipient.");
/* 224 */     for (Iterator<String> iter = subConfigurationWrapper2.getKeys().iterator(); iter.hasNext(); ) {
/* 225 */       String recipient = subConfigurationWrapper2.getProperty(iter.next());
/* 226 */       if (recipient != null && recipient.length() > 0) {
/* 227 */         this.recipients.add(recipient);
/*     */       }
/*     */     } 
/* 230 */     if (this.recipients.size() == 0) {
/* 231 */       log.warn("disabling mail notification, since no recipient is specified");
/* 232 */       this.sendMail = false;
/*     */     } 
/*     */ 
/*     */     
/* 236 */     subConfigurationWrapper1 = new SubConfigurationWrapper((Configuration)subConfigurationWrapper1, "failed.requests.");
/* 237 */     _configuration = new TypeDecorator((Configuration)subConfigurationWrapper1);
/*     */ 
/*     */     
/* 240 */     Boolean failedCheck = _configuration.getBoolean("check");
/* 241 */     if (failedCheck != null) {
/* 242 */       this.failedCheck = failedCheck.booleanValue();
/*     */     } else {
/* 244 */       this.failedCheck = false;
/*     */     } 
/*     */ 
/*     */     
/* 248 */     Number failedCheckInterval = _configuration.getNumber("interval");
/* 249 */     if (failedCheckInterval != null) {
/* 250 */       this.failedCheckInterval = Long.valueOf((failedCheckInterval.intValue() * 60 * 1000));
/*     */     }
/* 252 */     else if (this.failedCheck) {
/* 253 */       log.warn("disabling failed check, because the interval property is not specified");
/* 254 */       this.failedCheck = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     Number failedMaxCount = _configuration.getNumber("max.count");
/* 260 */     if (failedMaxCount != null) {
/* 261 */       this.failedMaxCount = failedMaxCount;
/*     */     }
/* 263 */     else if (this.failedCheck) {
/* 264 */       log.warn("disabling failed requests check, because the max.count property is not specified");
/* 265 */       this.failedCheck = false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     this.ptCleanup = new PeriodicTask(new CleanupTask(), this.keepRecordInterval.longValue() + 1000L);
/* 272 */     this.ptCleanup.start();
/*     */     
/* 274 */     ApplicationContext.getInstance().addShutdownListener(new ApplicationContext.ShutdownListener()
/*     */         {
/*     */           public void onShutdown() {
/* 277 */             LoginMonitor.this.ptCleanup.stop();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static LoginMonitor getInstance() {
/* 284 */     synchronized (ApplicationContext.getInstance().getLockObject()) {
/* 285 */       if (instance == null) {
/* 286 */         instance = new LoginMonitor();
/*     */       }
/*     */       
/* 289 */       return instance;
/*     */     } 
/*     */   }
/*     */   
/*     */   private List getLoginInfoList(Filter filter) {
/* 294 */     synchronized (this.loginInfoList) {
/* 295 */       return (List)CollectionUtil.filterAndReturn(new ArrayList(this.loginInfoList), filter);
/*     */     } 
/*     */   }
/*     */   
/* 299 */   private static final Object SYNC_FAILED_CHECK = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performFailedCheck(final String user) {
/* 312 */     synchronized (SYNC_FAILED_CHECK) {
/* 313 */       long currentTimestamp = System.currentTimeMillis();
/* 314 */       final long minTimestamp = currentTimestamp - this.failedCheckInterval.longValue();
/*     */       
/* 316 */       Filter filter = new Filter()
/*     */         {
/*     */           public boolean include(Object object) {
/* 319 */             boolean retValue = true;
/*     */             try {
/* 321 */               LoginMonitor.LoginInfoWrapper wrapper = (LoginMonitor.LoginInfoWrapper)object;
/* 322 */               LoginInfo info = wrapper.getLoginInfo();
/* 323 */               retValue = (retValue && info.getUser().equalsIgnoreCase(user));
/* 324 */               retValue = (retValue && info.getRequestAt() >= minTimestamp);
/* 325 */               retValue = (retValue && !info.isAuthorized());
/* 326 */               retValue = (retValue && !wrapper.isProcessed());
/* 327 */             } catch (Exception e) {
/* 328 */               retValue = false;
/*     */             } 
/* 330 */             return retValue;
/*     */           }
/*     */         };
/*     */       
/* 334 */       List list = getLoginInfoList(filter);
/* 335 */       int failedAttempts = list.size();
/* 336 */       if (failedAttempts >= this.failedMaxCount.intValue())
/*     */       {
/* 338 */         if (this.sendMail) {
/*     */ 
/*     */           
/* 341 */           long actualMin = System.currentTimeMillis();
/* 342 */           long actualMax = 0L;
/* 343 */           for (Iterator<LoginInfoWrapper> iter = list.iterator(); iter.hasNext(); ) {
/* 344 */             LoginInfoWrapper wrapper = iter.next();
/* 345 */             LoginInfo info = wrapper.getLoginInfo();
/* 346 */             actualMin = Math.min(actualMin, info.getRequestAt());
/* 347 */             actualMax = Math.max(actualMax, info.getRequestAt());
/* 348 */             wrapper.markProcessed();
/*     */           } 
/*     */           try {
/* 351 */             MailService service = (MailService)FrameServiceProvider.getInstance().getService(MailService.class);
/* 352 */             service.send(new MailServiceCallback(user, failedAttempts, actualMin, actualMax));
/*     */           }
/* 354 */           catch (Exception e) {
/* 355 */             log.warn("unable to send warning email - error:" + e);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onAddition(LoginInfoWrapper wrapper) {
/* 364 */     LoginInfo loginInfo = wrapper.getLoginInfo();
/*     */     
/* 366 */     if (!wrapper.wasSuccessful() && this.failedCheck) {
/* 367 */       performFailedCheck(loginInfo.getUser());
/*     */     }
/*     */   }
/*     */   
/*     */   public void add(LoginInfo loginInfo, boolean successful) {
/* 372 */     if (successful || !this.failedCheck) {
/*     */       return;
/*     */     }
/*     */     try {
/* 376 */       LoginInfoWrapper wrapper = new LoginInfoWrapper(loginInfo, successful);
/* 377 */       if (!this.loginInfoList.contains(wrapper)) {
/* 378 */         this.loginInfoList.add(wrapper);
/* 379 */         onAddition(wrapper);
/*     */       } 
/* 381 */     } catch (Exception e) {
/* 382 */       log.warn("unable to update login monitor - error:" + e + " , ignoring");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\monitor\LoginMonitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */