/*    */ package com.eoos.gm.tis2web.frame.login.log;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.StringUtilities;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Collection;
/*    */ import java.util.Date;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class LoginLog_Log4j
/*    */   implements LoginLog.SPI
/*    */ {
/* 17 */   private static final Logger log = Logger.getLogger(LoginLog_Log4j.class);
/*    */   
/*    */   private final Logger loginLog;
/*    */   
/*    */   private static final String LOG_TEMPLATE = "{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}";
/*    */   
/* 23 */   private static final DateFormat LOG_DATEFORMAT = DateFormat.getDateInstance(2);
/*    */   
/* 25 */   private static final DateFormat LOG_TIMEFORMAT = DateFormat.getTimeInstance(2);
/*    */   
/*    */   public LoginLog_Log4j(Configuration configuration) throws Exception {
/* 28 */     String loggerName = configuration.getProperty("logger");
/* 29 */     if (loggerName == null || loggerName.length() == 0) {
/* 30 */       loggerName = "login";
/*    */     }
/* 32 */     this.loginLog = Logger.getLogger(loggerName);
/* 33 */     StringBuffer entry = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}");
/* 34 */     StringUtilities.replace(entry, "{USER}", "NAME");
/* 35 */     StringUtilities.replace(entry, "{STATUS}", "STATUS");
/* 36 */     StringUtilities.replace(entry, "{DATE}", "DATE");
/* 37 */     StringUtilities.replace(entry, "{TIME}", "TIME");
/* 38 */     StringUtilities.replace(entry, "{IP}", "SOURCE ADDR");
/* 39 */     String freeParam = ApplicationContext.getInstance().getProperty("frame.scout.nao.login.logparam");
/* 40 */     if (freeParam == null) {
/* 41 */       freeParam = "";
/*    */     }
/* 43 */     StringUtilities.replace(entry, "{FREEPARAM}", freeParam);
/* 44 */     StringUtilities.replace(entry, "{ORIGIN}", "ORIGIN");
/* 45 */     StringUtilities.replace(entry, "{GROUPNAME}", "GROUP");
/* 46 */     StringUtilities.replace(entry, "{GROUPCODE}", "CODE");
/*    */     
/* 48 */     this.loginLog.info(entry.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(LoginLog.Entry entry) throws Exception {
/* 53 */     StringBuffer logentry = new StringBuffer("{USER}\t{STATUS}\t{DATE}\t{TIME}\t{IP}\t{FREEPARAM}\t{ORIGIN}\t{GROUPNAME}\t{GROUPCODE}");
/* 54 */     StringUtilities.replace(logentry, "{USER}", entry.getUsername());
/* 55 */     StringUtilities.replace(logentry, "{STATUS}", entry.successfulLogin() ? "allow" : "deny");
/* 56 */     synchronized (LOG_DATEFORMAT) {
/* 57 */       StringUtilities.replace(logentry, "{DATE}", LOG_DATEFORMAT.format(new Date(entry.getTimestamp())));
/* 58 */       StringUtilities.replace(logentry, "{TIME}", LOG_TIMEFORMAT.format(new Date(entry.getTimestamp())));
/*    */     } 
/* 60 */     StringUtilities.replace(logentry, "{IP}", entry.getSourceAddress());
/* 61 */     StringUtilities.replace(logentry, "{FREEPARAM}", entry.getFreeParameter());
/* 62 */     StringUtilities.replace(logentry, "{ORIGIN}", entry.getOrigin());
/* 63 */     StringUtilities.replace(logentry, "{GROUPNAME}", entry.getUserGroup());
/* 64 */     StringUtilities.replace(logentry, "{GROUPCODE}", entry.getDealerCode());
/*    */     
/* 66 */     this.loginLog.info(logentry.toString());
/*    */   }
/*    */   
/*    */   public void add(Collection entries) {
/* 70 */     if (!Util.isNullOrEmpty(entries))
/* 71 */       for (Iterator<LoginLog.Entry> iter = entries.iterator(); iter.hasNext();) {
/*    */         try {
/* 73 */           add(iter.next());
/* 74 */         } catch (Exception e) {
/* 75 */           log.error("unable to add entry - exception: " + e, e);
/*    */         } 
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\LoginLog_Log4j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */