/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntryImpl
/*    */   implements SWDLMetricsLog.Entry2
/*    */ {
/*    */   private final long ts;
/*    */   final String version;
/*    */   final String device;
/*    */   final String application;
/*    */   private final String server;
/*    */   final String language;
/*    */   final String userID;
/*    */   final long identifier;
/*    */   
/*    */   public EntryImpl(long ts, String version, String device, String application, String server, String language, long identifier, String userID) {
/* 24 */     this.ts = ts;
/* 25 */     this.version = version;
/* 26 */     this.device = device;
/* 27 */     this.application = application;
/* 28 */     this.server = server;
/* 29 */     this.language = language;
/* 30 */     this.identifier = identifier;
/* 31 */     this.userID = userID;
/*    */   }
/*    */   
/*    */   public String getLanguage() {
/* 35 */     return this.language;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 39 */     return this.version;
/*    */   }
/*    */   
/*    */   public String getApplication() {
/* 43 */     return this.application;
/*    */   }
/*    */   
/*    */   public String getDevice() {
/* 47 */     return this.device;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 51 */     return this.ts;
/*    */   }
/*    */   
/*    */   public String getServerName() {
/* 55 */     return this.server;
/*    */   }
/*    */   
/*    */   public long getIdentifier() {
/* 59 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getUserID() {
/* 63 */     return this.userID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\EntryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */