/*     */ package com.eoos.gm.tis2web.frame.login.log;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*     */ import com.eoos.util.DateConvert;
/*     */ import com.eoos.util.v2.Util;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LoginInfoAdapter
/*     */   implements LoginLog.Entry
/*     */ {
/*     */   private final HttpServletRequest request;
/*     */   private final LoginInfo info;
/*     */   private boolean successful = false;
/*     */   
/*     */   public LoginInfoAdapter(LoginInfo info, HttpServletRequest request, boolean successful) {
/*  21 */     this.request = request;
/*  22 */     this.info = info;
/*  23 */     this.successful = successful;
/*     */   }
/*     */   
/*     */   public boolean successfulLogin() {
/*  27 */     return this.successful;
/*     */   }
/*     */   
/*     */   public String getDealerCode() {
/*     */     try {
/*  32 */       return this.info.getDealerCode().getDealerCode();
/*  33 */     } catch (NullPointerException e) {
/*  34 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getUserGroup() {
/*     */     try {
/*  40 */       return this.info.getLoginGroup();
/*  41 */     } catch (NullPointerException e) {
/*  42 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getOrigin() {
/*     */     try {
/*  48 */       return this.info.getOrigin();
/*  49 */     } catch (NullPointerException e) {
/*  50 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getFreeParameter() {
/*     */     try {
/*  56 */       return this.info.getConfParam();
/*  57 */     } catch (NullPointerException e) {
/*  58 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSourceAddress() {
/*     */     try {
/*  64 */       if (this.info != null) {
/*  65 */         return this.info.getFromAddr();
/*     */       }
/*  67 */       return this.request.getRemoteAddr();
/*     */     }
/*  69 */     catch (NullPointerException e) {
/*  70 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUsername() {
/*     */     try {
/*  77 */       return this.info.getUser();
/*  78 */     } catch (NullPointerException e) {
/*  79 */       return "";
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/*  84 */     return (this.info != null) ? this.info.getRequestAt() : System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  88 */     Map<Object, Object> innerState = new HashMap<Object, Object>();
/*  89 */     innerState.put("username", getUsername());
/*  90 */     innerState.put("successful", String.valueOf(successfulLogin()));
/*  91 */     innerState.put("timestamp", DateConvert.toISOFormat(getTimestamp()));
/*  92 */     return Util.toString(this, innerState);
/*     */   }
/*     */   
/*     */   public String getDivisionCode() {
/*  96 */     return (this.info != null) ? this.info.getDivisions() : "";
/*     */   }
/*     */   
/*     */   public String getMappedCountryCode() {
/* 100 */     return (this.info != null) ? this.info.getCountry() : "";
/*     */   }
/*     */   
/*     */   public String getOriginalCountryCode() {
/* 104 */     return (this.info != null) ? this.info.getSPOREFCountry() : "";
/*     */   }
/*     */   
/*     */   public String getT2WGroup() {
/* 108 */     return (this.info != null) ? this.info.getT2WGroup() : "";
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\login\log\LoginInfoAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */