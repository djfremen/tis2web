/*    */ package com.eoos.gm.tis2web.frame.scout;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.LoginInfo;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import com.eoos.security.execution.delimiter.ExecutionDelimiter;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ICOPScout
/*    */   extends BaseScout
/*    */ {
/*    */   private static final String T2W_GROUP = "icopclient";
/*    */   private static final String DEFAULT_MANUFACTURER = "ALL";
/*    */   
/*    */   public LoginInfo getLoginInfo(Map params) {
/* 22 */     LoginInfo ret = new LoginInfo();
/* 23 */     ret.setUser(retrieveUser(params));
/* 24 */     ret.setLocale(getLocale(params));
/* 25 */     ret.setCountry(getCountry(params));
/* 26 */     ret.setRequestedVC((String)params.get("vehicle"));
/* 27 */     if (checkToken(getSecToken(params))) {
/* 28 */       ret.setAuthorized();
/*    */     }
/* 30 */     ret.setRequestedModule("lt");
/* 31 */     ret.setT2WGroup("icopclient");
/* 32 */     ret.setGroup2ManufMap(getGroup2ManufMap());
/*    */     
/* 34 */     return ret;
/*    */   }
/*    */   
/*    */   private Locale getLocale(Map params) {
/* 38 */     String _locale = (String)params.get("locale");
/* 39 */     if (!Util.isNullOrEmpty(_locale)) {
/* 40 */       return Util.parseLocale(_locale);
/*    */     }
/* 42 */     return Locale.ENGLISH;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String getCountry(Map params) {
/* 48 */     return (String)params.get("country");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getScoutClassName() {
/* 53 */     return getClass().getName();
/*    */   }
/*    */   
/*    */   private CharSequence getSecToken(Map params) {
/* 57 */     return (String)params.get("token");
/*    */   }
/*    */   
/*    */   private Map getGroup2ManufMap() {
/* 61 */     Map<String, Set<String>> result = new HashMap<String, Set<String>>();
/* 62 */     HashSet<String> set = new HashSet<String>();
/* 63 */     set.add("ALL");
/* 64 */     result.put("icopclient", set);
/* 65 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CharSequence createToken(byte validity) {
/* 75 */     return ExecutionDelimiter.createToken("icop.scout", new Date(), ExecutionDelimiter.RESOLUTION_MINUTES, validity);
/*    */   }
/*    */   
/*    */   public static boolean checkToken(CharSequence token) {
/* 79 */     return (token != null) ? ExecutionDelimiter.check("icop.scout", token) : false;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\ICOPScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */