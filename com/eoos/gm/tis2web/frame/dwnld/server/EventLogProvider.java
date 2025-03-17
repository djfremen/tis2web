/*    */ package com.eoos.gm.tis2web.frame.dwnld.server;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.ConfigurationServiceProvider;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.propcfg.util.ConfigurationUtil;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class EventLogProvider
/*    */ {
/* 11 */   private static final Logger log = Logger.getLogger(EventLogProvider.class);
/*    */   
/* 13 */   private static EventLog eventLog = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized EventLog getEventLog() {
/* 20 */     if (eventLog == null) {
/* 21 */       log.debug("creating event log ...");
/* 22 */       if (ConfigurationUtil.getBoolean("frame.dwnld.event.log.enabled", (Configuration)ConfigurationServiceProvider.getService()).booleanValue()) {
/* 23 */         eventLog = EventLogDBAdapter.getInstance();
/*    */       } else {
/* 25 */         log.debug("...logging is disabled, creating dummy");
/* 26 */         eventLog = new EventLog()
/*    */           {
/*    */             public void logDwnldEvent(Collection sessionIDs, long tsStart, long tsEnd, Collection downloadUnits, Object status) {}
/*    */           };
/*    */       } 
/*    */     } 
/*    */     
/* 33 */     return eventLog;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\server\EventLogProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */