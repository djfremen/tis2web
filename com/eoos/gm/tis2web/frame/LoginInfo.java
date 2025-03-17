/*     */ package com.eoos.gm.tis2web.frame;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ApplicationContext;
/*     */ import com.eoos.gm.tis2web.frame.export.common.datatype.DealerCode;
/*     */ import com.eoos.scsm.v2.util.Util;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class LoginInfo
/*     */ {
/*     */   private boolean authorized = false;
/*     */   private String user;
/*     */   private String loginGroup;
/*     */   private Locale locale;
/*     */   private String country;
/*     */   private String divisions;
/*     */   private Map group2ManufMap;
/*     */   private String fromAddr;
/*     */   private String confParam;
/*     */   private String origin;
/*     */   private String portalId;
/*     */   private long requestAt;
/*     */   private DealerCode dealerCode;
/*     */   private String id;
/*     */   private boolean publicAccess = false;
/*     */   private boolean specialAccess = false;
/*  48 */   private String t2wGroup = null;
/*     */   
/*  50 */   private String sporefCountry = null;
/*     */   
/*  52 */   private String requestedModuleType = null;
/*     */   
/*  54 */   private String vc = null;
/*     */ 
/*     */   
/*     */   private long maxSessionDuration;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuthorized() {
/*  62 */     this.authorized = true;
/*     */   }
/*     */   
/*     */   public boolean isAuthorized() {
/*  66 */     return this.authorized;
/*     */   }
/*     */   
/*     */   public void setUser(String _user) {
/*  70 */     this.user = _user;
/*     */   }
/*     */   
/*     */   public String getUser() {
/*  74 */     return this.user;
/*     */   }
/*     */   
/*     */   public void setLoginGroup(String _group) {
/*  78 */     this.loginGroup = _group;
/*     */   }
/*     */   
/*     */   public String getLoginGroup() {
/*  82 */     return this.loginGroup;
/*     */   }
/*     */   
/*     */   public void setLocale(Locale _locale) {
/*  86 */     this.locale = _locale;
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  90 */     Locale result = this.locale;
/*  91 */     if (result == null) {
/*  92 */       this.locale = Locale.US;
/*     */     }
/*  94 */     return result;
/*     */   }
/*     */   
/*     */   public void setCountry(String _country) {
/*  98 */     this.country = _country;
/*     */   }
/*     */   
/*     */   public String getCountry() {
/* 102 */     return this.country;
/*     */   }
/*     */   
/*     */   public void setDivisions(String _divisions) {
/* 106 */     this.divisions = _divisions;
/*     */   }
/*     */   
/*     */   public String getDivisions() {
/* 110 */     return this.divisions;
/*     */   }
/*     */   
/*     */   public void setGroup2ManufMap(Map _map) {
/* 114 */     this.group2ManufMap = _map;
/*     */   }
/*     */   
/*     */   public Map getGroup2ManufMap() {
/* 118 */     return this.group2ManufMap;
/*     */   }
/*     */   
/*     */   public void setFromAddr(String _addr) {
/* 122 */     this.fromAddr = _addr;
/*     */   }
/*     */   
/*     */   public String getFromAddr() {
/* 126 */     return this.fromAddr;
/*     */   }
/*     */   
/*     */   public void setConfParam(String _param) {
/* 130 */     this.confParam = _param;
/*     */   }
/*     */   
/*     */   public String getConfParam() {
/* 134 */     return this.confParam;
/*     */   }
/*     */   
/*     */   public void setOrigin(String _origin) {
/* 138 */     this.origin = _origin;
/*     */   }
/*     */   
/*     */   public String getOrigin() {
/* 142 */     return this.origin;
/*     */   }
/*     */   
/*     */   public void setPortalId(String _portalId) {
/* 146 */     this.portalId = _portalId;
/*     */   }
/*     */   
/*     */   public String getPortalId() {
/* 150 */     return this.portalId;
/*     */   }
/*     */   
/*     */   public long getRequestAt() {
/* 154 */     return this.requestAt;
/*     */   }
/*     */   
/*     */   public void setDealerCode(DealerCode _dc) {
/* 158 */     this.dealerCode = _dc;
/*     */   }
/*     */   
/*     */   public DealerCode getDealerCode() {
/* 162 */     return this.dealerCode;
/*     */   }
/*     */   
/*     */   public String getDetails() {
/* 166 */     String result = "user: " + ((this.user == null) ? "" : this.user) + " group: " + ((this.loginGroup == null) ? "" : this.loginGroup) + " source IP: " + ((this.fromAddr == null) ? "" : this.fromAddr) + " configurable parameter: " + ((this.confParam == null) ? "" : this.confParam) + " origin: " + ((this.origin == null) ? "" : this.origin) + " date: " + (new Date(getRequestAt())).toString() + " dealer code: " + getDealerCode().getDealerCode();
/* 167 */     return result;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 171 */     return this.id.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 176 */     if (this == obj)
/* 177 */       return true; 
/* 178 */     if (obj instanceof LoginInfo) {
/* 179 */       LoginInfo other = (LoginInfo)obj;
/* 180 */       boolean ret = Util.equals(this.id, other.id);
/* 181 */       return ret;
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPublicAccess(boolean publicAccess) {
/* 188 */     this.publicAccess = publicAccess;
/*     */   }
/*     */   
/*     */   public boolean isPublicAccess() {
/* 192 */     return this.publicAccess;
/*     */   }
/*     */   
/*     */   public void setSpecialAccess(boolean specialAccess) {
/* 196 */     this.specialAccess = specialAccess;
/*     */   }
/*     */   
/*     */   public boolean isSpecialAccess() {
/* 200 */     return this.specialAccess;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 204 */     return "LoginInfo[" + getDetails() + "]";
/*     */   }
/*     */   
/*     */   public String getT2WGroup() {
/* 208 */     return this.t2wGroup;
/*     */   }
/*     */   
/*     */   public void setT2WGroup(String t2wGroup) {
/* 212 */     this.t2wGroup = t2wGroup;
/*     */   }
/*     */   
/*     */   public String getSPOREFCountry() {
/* 216 */     return this.sporefCountry;
/*     */   }
/*     */   
/*     */   public void setSPOREFCountry(String sporefCountry) {
/* 220 */     this.sporefCountry = sporefCountry;
/*     */   }
/*     */   
/* 223 */   public LoginInfo() { this.maxSessionDuration = -1L;
/*     */     this.id = ApplicationContext.getInstance().createID();
/*     */     this.requestAt = (new Date()).getTime(); } public void setMaximumSessionDuration(long sessionDuration) {
/* 226 */     this.maxSessionDuration = sessionDuration;
/*     */   }
/*     */   
/*     */   public long getMaximumSessionDuration() {
/* 230 */     return this.maxSessionDuration;
/*     */   }
/*     */   
/*     */   public String getRequestedModule() {
/* 234 */     return this.requestedModuleType;
/*     */   }
/*     */   
/*     */   public void setRequestedModule(String moduleType) {
/* 238 */     this.requestedModuleType = moduleType;
/*     */   }
/*     */   
/*     */   public void setRequestedVC(String vc) {
/* 242 */     this.vc = vc;
/*     */   }
/*     */   
/*     */   public String getRequestedVC() {
/* 246 */     return this.vc;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\LoginInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */