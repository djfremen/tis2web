/*    */ package com.eoos.gm.tis2web.frame.login.log;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntryImpl
/*    */   implements LoginLog.Entry2
/*    */ {
/*    */   private long ts;
/*    */   private boolean successful;
/*    */   private String sourceAddress;
/*    */   private String loginParams;
/*    */   private String origin;
/*    */   private String userGroup;
/*    */   private String userName;
/*    */   private String dealerCode;
/*    */   private String serverName;
/*    */   private long id;
/*    */   private String divisionCode;
/*    */   private String mappedCountryCode;
/*    */   private String orgCountryCode;
/*    */   private String t2wGroup;
/*    */   
/*    */   public EntryImpl(long ts, String name, boolean successful, String sourceAddress, String params, String origin, String userGroup, String dealerCode, String divisionCode, String orgCountryCode, String mappedCountryCode, String t2wGroup, String serverName, long id) {
/* 24 */     this.ts = ts;
/* 25 */     this.userName = name;
/* 26 */     this.successful = successful;
/* 27 */     this.sourceAddress = sourceAddress;
/* 28 */     this.loginParams = params;
/* 29 */     this.origin = origin;
/* 30 */     this.userGroup = userGroup;
/* 31 */     this.dealerCode = dealerCode;
/* 32 */     this.serverName = serverName;
/* 33 */     this.divisionCode = divisionCode;
/* 34 */     this.orgCountryCode = orgCountryCode;
/* 35 */     this.mappedCountryCode = mappedCountryCode;
/* 36 */     this.t2wGroup = t2wGroup;
/* 37 */     this.id = id;
/*    */   }
/*    */   
/*    */   public EntryImpl(long ts, String name, boolean successful, String address, String params, String origin, String group, String code, String divisionCode, String orgCountryCode, String mappedCountryCode, String t2wGroup) {
/* 41 */     this(ts, name, successful, address, params, origin, group, code, divisionCode, orgCountryCode, mappedCountryCode, t2wGroup, null, -1L);
/*    */   }
/*    */   
/*    */   public String getServerName() {
/* 45 */     return this.serverName;
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 49 */     return this.ts;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 53 */     return this.userName;
/*    */   }
/*    */   
/*    */   public String getSourceAddress() {
/* 57 */     return this.sourceAddress;
/*    */   }
/*    */   
/*    */   public String getFreeParameter() {
/* 61 */     return this.loginParams;
/*    */   }
/*    */   
/*    */   public String getOrigin() {
/* 65 */     return this.origin;
/*    */   }
/*    */   
/*    */   public String getUserGroup() {
/* 69 */     return this.userGroup;
/*    */   }
/*    */   
/*    */   public String getDealerCode() {
/* 73 */     return this.dealerCode;
/*    */   }
/*    */   
/*    */   public boolean successfulLogin() {
/* 77 */     return this.successful;
/*    */   }
/*    */   
/*    */   public long getIdentifier() {
/* 81 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getDivisionCode() {
/* 85 */     return this.divisionCode;
/*    */   }
/*    */   
/*    */   public String getMappedCountryCode() {
/* 89 */     return this.mappedCountryCode;
/*    */   }
/*    */   
/*    */   public String getOriginalCountryCode() {
/* 93 */     return this.orgCountryCode;
/*    */   }
/*    */   
/*    */   public String getT2WGroup() {
/* 97 */     return this.t2wGroup;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\EntryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */