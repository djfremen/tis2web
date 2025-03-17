/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.global;
/*     */ 
/*     */ import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
/*     */ import com.eoos.gm.tis2web.sps.server.implementation.adapter.common.SPSPart;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SPSHardware
/*     */   implements SPSPart, DisplayableValue, Comparable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   public static final int USER_SELECTION = 0;
/*     */   public static final int VIT1_HARDWARE = 1;
/*     */   public static final int HARDWARE_INDEX = 2;
/*     */   protected int partno;
/*     */   protected int origin;
/*     */   
/*     */   public SPSHardware(int partno) {
/*  23 */     this.partno = partno;
/*  24 */     this.origin = 0;
/*     */   }
/*     */   
/*     */   public SPSHardware(Integer partno) {
/*  28 */     this.partno = partno.intValue();
/*  29 */     this.origin = 2;
/*     */   }
/*     */   
/*     */   public SPSHardware(String partno) {
/*  33 */     this.partno = Integer.parseInt(partno);
/*  34 */     this.origin = 1;
/*     */   }
/*     */   
/*     */   public boolean isVIT1Hardware() {
/*  38 */     return (this.origin == 1);
/*     */   }
/*     */   
/*     */   public List getCOP() {
/*  42 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getCVN() {
/*  46 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/*  50 */     return getDescription();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  54 */     return getDescription();
/*     */   }
/*     */   
/*     */   public int getID() {
/*  58 */     return this.partno;
/*     */   }
/*     */   
/*     */   public String getPartNumber() {
/*  62 */     return Integer.toString(this.partno);
/*     */   }
/*     */   
/*     */   public String getLabel() {
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  70 */     return Integer.toString(this.partno);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  74 */     return this.partno;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/*  78 */     return (object != null && object instanceof SPSHardware && ((SPSHardware)object).partno == this.partno);
/*     */   }
/*     */   
/*     */   public List getBulletins() {
/*  82 */     return null;
/*     */   }
/*     */   
/*     */   public int compareTo(Object o) {
/*  86 */     if (o instanceof SPSHardware) {
/*  87 */       return this.partno - ((SPSHardware)o).getID();
/*     */     }
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription(Locale locale) {
/*  94 */     return getDenotation(locale);
/*     */   }
/*     */   
/*     */   public String getShortDescription(Locale locale) {
/*  98 */     return getDescription(locale);
/*     */   }
/*     */   
/*     */   public List getCalibrationParts() {
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public String getCalibrationVerificationNumber(String partNumber) {
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\global\SPSHardware.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */