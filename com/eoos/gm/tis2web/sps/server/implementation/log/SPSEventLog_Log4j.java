/*    */ package com.eoos.gm.tis2web.sps.server.implementation.log;
/*    */ 
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.util.v2.StringUtilities;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPSEventLog_Log4j
/*    */   implements SPSEventLog.SPI
/*    */ {
/* 15 */   private static final Logger myLog = Logger.getLogger(SPSEventLog_Log4j.class);
/*    */   
/*    */   private final Logger logFileGME;
/*    */   
/*    */   private final Logger logFileNAO;
/*    */   
/*    */   private final Logger logFileGlobal;
/*    */   
/*    */   public SPSEventLog_Log4j(Configuration configuration) throws Exception {
/* 24 */     String loggerNameNAO = configuration.getProperty("logger.nao");
/* 25 */     if (loggerNameNAO == null || loggerNameNAO.length() == 0) {
/* 26 */       loggerNameNAO = "nao-events.log";
/*    */     }
/* 28 */     this.logFileNAO = Logger.getLogger(loggerNameNAO);
/*    */     
/* 30 */     String loggerNameGlobal = configuration.getProperty("logger.global");
/* 31 */     if (loggerNameGlobal == null || loggerNameGlobal.length() == 0) {
/* 32 */       loggerNameGlobal = "global-events.log";
/*    */     }
/* 34 */     this.logFileGlobal = Logger.getLogger(loggerNameGlobal);
/*    */     
/* 36 */     String loggerNameGME = configuration.getProperty("logger.gme");
/* 37 */     if (loggerNameGME == null || loggerNameGME.length() == 0) {
/* 38 */       loggerNameGME = "gme-events.log";
/*    */     }
/* 40 */     this.logFileGME = Logger.getLogger(loggerNameGME);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void ensureInit() {}
/*    */ 
/*    */   
/*    */   public void add(SPSEventLog.Entry entry, Collection flags, Attachment[] attachments) throws Exception {
/* 49 */     if (attachments != null && attachments.length > 0) {
/* 50 */       myLog.warn("ignoring attachment (unable to log binary data)!!");
/*    */     }
/* 52 */     Logger log = null;
/* 53 */     if (SPSEventLog.Entry.ADAPTER_NAO == entry.getAdapter()) {
/* 54 */       log = this.logFileNAO;
/* 55 */     } else if (SPSEventLog.Entry.ADAPTER_GLOBAL == entry.getAdapter()) {
/* 56 */       log = this.logFileGlobal;
/*    */     } else {
/* 58 */       log = this.logFileGME;
/*    */     } 
/*    */     
/* 61 */     StringBuffer tmp = new StringBuffer("<{NAME} {ATTRIBUTES} />");
/* 62 */     StringUtilities.replace(tmp, "{NAME}", entry.getEventName());
/* 63 */     for (Iterator<SPSEventLog.Attribute> iter = entry.getEventAttributes().iterator(); iter.hasNext(); ) {
/* 64 */       SPSEventLog.Attribute attribute = iter.next();
/*    */       
/* 66 */       StringBuffer attr = new StringBuffer();
/* 67 */       attr.append(attribute.getName());
/* 68 */       attr.append("=");
/* 69 */       attr.append(attribute.getValue());
/* 70 */       StringUtilities.replace(tmp, "{ATTRIBUTES}", attr.toString() + " {ATTRIBUTES}");
/*    */     } 
/*    */     
/* 73 */     StringUtilities.replace(tmp, " {ATTRIBUTES}", "");
/* 74 */     log.info(tmp.toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(Collection metaEntries) throws Exception {
/* 79 */     if (!Util.isNullOrEmpty(metaEntries))
/* 80 */       for (Iterator<SPSEventLog.MetaEntry> iter = metaEntries.iterator(); iter.hasNext(); ) {
/* 81 */         SPSEventLog.MetaEntry mEntry = iter.next();
/* 82 */         add(mEntry.entry, mEntry.flags, mEntry.attachments);
/*    */       }  
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\log\SPSEventLog_Log4j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */