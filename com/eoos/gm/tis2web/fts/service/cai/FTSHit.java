/*     */ package com.eoos.gm.tis2web.fts.service.cai;
/*     */ 
/*     */ public class FTSHit
/*     */   extends Hit
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*   7 */   protected String lu_property = null;
/*     */   
/*   9 */   protected String nmc_property = null;
/*     */   
/*  11 */   protected int vcr = -1;
/*     */   
/*  13 */   protected String vrcText = null;
/*     */   
/*  15 */   protected int order = 0;
/*     */   
/*  17 */   protected int type = -1;
/*     */   
/*  19 */   protected String sitsText = null;
/*     */   
/*  21 */   protected String mo = null;
/*     */   
/*  23 */   protected Integer labelId = Integer.valueOf(-1);
/*     */   
/*     */   public FTSHit(Integer sio) {
/*  26 */     super(sio);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setID(Integer sio) {
/*  31 */     this.sio = sio;
/*     */   }
/*     */   
/*     */   public void setMajorOperation(String mo) {
/*  35 */     this.mo = mo;
/*     */   }
/*     */   
/*     */   public String getMajorOperation() {
/*  39 */     return this.mo;
/*     */   }
/*     */   
/*     */   public void setLabelId(Integer labelId) {
/*  43 */     this.labelId = labelId;
/*     */   }
/*     */   
/*     */   public Integer getLabelId() {
/*  47 */     return this.labelId;
/*     */   }
/*     */   
/*     */   public void setLUProperty(String lu) {
/*  51 */     this.lu_property = lu;
/*     */   }
/*     */   
/*     */   public void setNMCProperty(String nmc) {
/*  55 */     this.nmc_property = nmc;
/*     */   }
/*     */   
/*     */   public void setVCR(int vcr) {
/*  59 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public void setVCRText(String vcrText) {
/*  63 */     this.vrcText = vcrText;
/*     */   }
/*     */   
/*     */   public void setSitsText(String sitsText) {
/*  67 */     this.sitsText = sitsText;
/*     */   }
/*     */   
/*     */   public String getSitsText() {
/*  71 */     return this.sitsText;
/*     */   }
/*     */   
/*     */   public void setOrder(int order) {
/*  75 */     this.order = order;
/*     */   }
/*     */   
/*     */   public void setType(int type) {
/*  79 */     this.type = type;
/*     */   }
/*     */   
/*     */   public String getLUProperty() {
/*  83 */     return this.lu_property;
/*     */   }
/*     */   
/*     */   public String getNMCProperty() {
/*  87 */     return this.nmc_property;
/*     */   }
/*     */   
/*     */   public int getVCR() {
/*  91 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public String getVCRText() {
/*  95 */     return this.vrcText;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  99 */     return this.order;
/*     */   }
/*     */   
/*     */   public int getType() {
/* 103 */     return this.type;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\service\cai\FTSHit.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */