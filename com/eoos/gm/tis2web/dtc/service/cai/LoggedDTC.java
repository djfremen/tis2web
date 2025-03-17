/*    */ package com.eoos.gm.tis2web.dtc.service.cai;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoggedDTC
/*    */   implements DTC.Logged, DTC, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3L;
/*    */   private long id;
/*    */   private long timestamp;
/*    */   private String bacCode;
/*    */   private String countryCode;
/*    */   private byte[] content;
/*    */   private String applicationID;
/*    */   private String portalID;
/*    */   
/*    */   public LoggedDTC(long id, long timestamp, String bacCode, String countryCode, byte[] content, String applicationID, String portalID) {
/* 26 */     this.id = id;
/* 27 */     this.timestamp = timestamp;
/* 28 */     this.bacCode = bacCode;
/* 29 */     this.content = content;
/* 30 */     this.countryCode = countryCode;
/* 31 */     this.applicationID = applicationID;
/* 32 */     this.portalID = portalID;
/*    */   }
/*    */   
/*    */   public long getID() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 40 */     return this.timestamp;
/*    */   }
/*    */   
/*    */   public byte[] getContent() {
/* 44 */     return this.content;
/*    */   }
/*    */   
/*    */   public String getBACCode() {
/* 48 */     return this.bacCode;
/*    */   }
/*    */   
/*    */   public String getCountryCode() {
/* 52 */     return this.countryCode;
/*    */   }
/*    */   
/*    */   public String getApplicationID() {
/* 56 */     return this.applicationID;
/*    */   }
/*    */   
/*    */   public String getPortalID() {
/* 60 */     return this.portalID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\cai\LoggedDTC.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */