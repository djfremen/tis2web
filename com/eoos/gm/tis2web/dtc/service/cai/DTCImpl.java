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
/*    */ public class DTCImpl
/*    */   implements DTC, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3L;
/*    */   private byte[] content;
/*    */   private String bacCode;
/*    */   private String countryCode;
/*    */   private String applicationID;
/*    */   private String portalID;
/*    */   
/*    */   public DTCImpl(byte[] content, String bacCode, String countryCode, String applicationID, String portalID) {
/* 23 */     this.content = content;
/* 24 */     this.bacCode = bacCode;
/* 25 */     this.countryCode = countryCode;
/* 26 */     this.applicationID = applicationID;
/* 27 */     this.portalID = portalID;
/*    */   }
/*    */   
/*    */   public byte[] getContent() {
/* 31 */     return this.content;
/*    */   }
/*    */   
/*    */   public String getBACCode() {
/* 35 */     return this.bacCode;
/*    */   }
/*    */   
/*    */   public LoggedDTC toLoggedDTC(long id, long timestamp) {
/* 39 */     return new LoggedDTC(id, timestamp, this.bacCode, this.countryCode, this.content, this.applicationID, this.portalID);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 43 */     return "DTCImpl[bac:" + String.valueOf(this.bacCode) + "]";
/*    */   }
/*    */   
/*    */   public String getCountryCode() {
/* 47 */     return this.countryCode;
/*    */   }
/*    */   
/*    */   public String getApplicationID() {
/* 51 */     return this.applicationID;
/*    */   }
/*    */   
/*    */   public String getPortalID() {
/* 55 */     return this.portalID;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\service\cai\DTCImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */