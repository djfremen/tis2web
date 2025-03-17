/*    */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.gme;
/*    */ 
/*    */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class SPSPart
/*    */   implements SPSPart {
/*    */   private static final long serialVersionUID = 1L;
/*    */   protected int id;
/*    */   protected String description;
/*    */   
/*    */   public SPSPart(int id, String description) {
/* 14 */     this.id = id;
/* 15 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getPartNumber() {
/* 19 */     return Integer.toString(this.id);
/*    */   }
/*    */   
/*    */   public String getLabel() {
/* 23 */     return null;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 27 */     return this.description;
/*    */   }
/*    */   
/*    */   public int getID() {
/* 31 */     return this.id;
/*    */   }
/*    */   
/*    */   public List getCOP() {
/* 35 */     return null;
/*    */   }
/*    */   
/*    */   public List getBulletins() {
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public String getCVN() {
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 47 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getDescription(Locale locale) {
/* 51 */     return getDescription();
/*    */   }
/*    */   
/*    */   public String getShortDescription(Locale locale) {
/* 55 */     return getDescription(locale);
/*    */   }
/*    */   
/*    */   public List getCalibrationParts() {
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public String getCalibrationVerificationNumber(String partNumber) {
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\gme\SPSPart.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */