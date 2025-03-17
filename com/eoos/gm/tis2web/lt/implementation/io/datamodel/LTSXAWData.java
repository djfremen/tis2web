/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTSXAWData
/*     */   implements Cloneable
/*     */ {
/*  10 */   private String sx = null;
/*     */ 
/*     */   
/*  13 */   private String aw = null;
/*     */ 
/*     */   
/*  16 */   private String sxnr = null;
/*     */ 
/*     */   
/*     */   private boolean change_flag = false;
/*     */ 
/*     */   
/*  22 */   private String awFormatted = null;
/*     */ 
/*     */   
/*  25 */   private int internalID = -1;
/*     */   
/*  27 */   private LTAWKSchluessel key = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LTAWKSchluessel getAWKSchluessel() {
/*  35 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAWKSchluessel(LTAWKSchluessel sx) {
/*  45 */     this.key = sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSx() {
/*  54 */     return this.sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSx(String sx) {
/*  64 */     this.sx = sx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAw() {
/*  73 */     return this.aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAw(String aw) {
/*  83 */     this.aw = aw;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSxnr() {
/*  92 */     return this.sxnr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSxnr(String sxnr) {
/* 102 */     this.sxnr = sxnr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChange_flag() {
/* 111 */     return this.change_flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChange_flag(boolean change_flag) {
/* 121 */     this.change_flag = change_flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAwFormatted() {
/* 130 */     return this.awFormatted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAwFormatted(String awFormatted) {
/* 140 */     this.awFormatted = awFormatted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInternalID() {
/* 149 */     return this.internalID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInternalID(int internalID) {
/* 159 */     this.internalID = internalID;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 163 */     LTSXAWData oC = new LTSXAWData();
/* 164 */     oC.key = this.key;
/* 165 */     oC.sx = this.sx;
/* 166 */     oC.aw = this.aw;
/* 167 */     oC.sxnr = this.sxnr;
/* 168 */     oC.change_flag = this.change_flag;
/* 169 */     oC.awFormatted = this.awFormatted;
/* 170 */     oC.internalID = this.internalID;
/* 171 */     return oC;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTSXAWData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */