/*     */ package com.eoos.gm.tis2web.sps.server.implementation.adapter.nao;
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
/*     */   protected String partno;
/*     */   protected int origin;
/*     */   
/*     */   public SPSHardware(int origin, String partno) {
/*  28 */     this.partno = partno;
/*  29 */     this.origin = origin;
/*     */   }
/*     */   
/*     */   public boolean isVIT1Hardware() {
/*  33 */     return (this.origin == 1);
/*     */   }
/*     */   
/*     */   public List getCOP() {
/*  37 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getCVN() {
/*  41 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public String getDenotation(Locale locale) {
/*  45 */     return getDescription();
/*     */   }
/*     */   
/*     */   public String toString() {
/*  49 */     return getDescription();
/*     */   }
/*     */   
/*     */   public int getID() {
/*  53 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getPartNumber() {
/*  57 */     return this.partno;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  65 */     return this.partno;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  69 */     return this.partno.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/*  73 */     return (object != null && object instanceof SPSHardware && ((SPSHardware)object).partno.equals(this.partno));
/*     */   }
/*     */   
/*     */   public List getBulletins() {
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   public int compareTo(Object o) {
/*  81 */     if (o instanceof SPSHardware) {
/*  82 */       return this.partno.compareTo(((SPSHardware)o).getPartNumber());
/*     */     }
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription(Locale locale) {
/*  89 */     return getDenotation(locale);
/*     */   }
/*     */   
/*     */   public String getShortDescription(Locale locale) {
/*  93 */     return getDescription(locale);
/*     */   }
/*     */   
/*     */   public List getCalibrationParts() {
/*  97 */     return null;
/*     */   }
/*     */   
/*     */   public String getCalibrationVerificationNumber(String partNumber) {
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\nao\SPSHardware.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */