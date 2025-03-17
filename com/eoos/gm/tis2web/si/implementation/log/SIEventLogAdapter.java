/*    */ package com.eoos.gm.tis2web.si.implementation.log;
/*    */ 
/*    */ import com.eoos.util.DateConvert;
/*    */ import com.eoos.util.v2.Util;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SIEventLogAdapter
/*    */   implements SIEventLog.Entry
/*    */ {
/* 11 */   private String docInfo = null;
/*    */   
/* 13 */   private String vc = null;
/*    */   
/*    */   private String siModuleType;
/*    */   
/*    */   private String user;
/*    */   
/*    */   public SIEventLogAdapter(String docInfo, String siModuleType, String user, String vc) {
/* 20 */     this.docInfo = docInfo;
/* 21 */     this.siModuleType = siModuleType;
/* 22 */     this.vc = vc;
/* 23 */     this.user = user;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUsername() {
/*    */     try {
/* 29 */       return this.user;
/* 30 */     } catch (NullPointerException e) {
/* 31 */       return "";
/*    */     } 
/*    */   }
/*    */   
/*    */   public long getTimestamp() {
/* 36 */     return System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     Map<Object, Object> innerState = new HashMap<Object, Object>();
/* 41 */     innerState.put("username", getUsername());
/* 42 */     innerState.put("timestamp", DateConvert.toISOFormat(getTimestamp()));
/* 43 */     innerState.put("si information", getSIInformation());
/* 44 */     innerState.put("vc information", getVC());
/* 45 */     return Util.toString(this, innerState);
/*    */   }
/*    */   
/*    */   public String getSIInformation() {
/* 49 */     return this.siModuleType + "," + this.docInfo;
/*    */   }
/*    */   
/*    */   public long getTSDisplay() {
/* 53 */     return getTimestamp();
/*    */   }
/*    */   
/*    */   public String getUserID() {
/* 57 */     return this.user;
/*    */   }
/*    */   
/*    */   public String getVC() {
/* 61 */     return formatterVC(this.vc);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String formatterVC(String vc) {
/* 66 */     int count = 0;
/* 67 */     int maxOccur = 4;
/*    */     int i;
/* 69 */     for (i = -1; (i = vc.indexOf(",", i + 1)) != -1; count++);
/*    */ 
/*    */     
/* 72 */     for (i = 0; i < maxOccur - count; i++) {
/* 73 */       vc = vc + ",";
/*    */     }
/*    */     
/* 76 */     return vc;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\log\SIEventLogAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */