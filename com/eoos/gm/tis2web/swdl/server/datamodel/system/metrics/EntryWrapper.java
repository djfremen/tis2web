/*    */ package com.eoos.gm.tis2web.swdl.server.datamodel.system.metrics;
/*    */ 
/*    */ 
/*    */ public class EntryWrapper
/*    */   implements SWDLMetricsLog.Entry
/*    */ {
/*    */   private SWDLMetricsLog.Entry backend;
/*    */   
/*    */   public EntryWrapper(SWDLMetricsLog.Entry backend) {
/* 10 */     this.backend = backend;
/*    */   }
/*    */   
/*    */   private String adjust(String string) {
/* 14 */     return (string == null) ? "" : string;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 18 */     return this.backend.getTimestamp();
/*    */   }
/*    */   
/*    */   public String getDevice() {
/* 22 */     return adjust(this.backend.getDevice());
/*    */   }
/*    */   
/*    */   public String getApplication() {
/* 26 */     return adjust(this.backend.getApplication());
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 30 */     return adjust(this.backend.getVersion());
/*    */   }
/*    */   
/*    */   public String getLanguage() {
/* 34 */     return adjust(this.backend.getLanguage());
/*    */   }
/*    */   
/*    */   public String getUserID() {
/* 38 */     return adjust(this.backend.getUserID());
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\swdl\server\datamodel\system\metrics\EntryWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */