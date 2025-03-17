/*     */ package com.eoos.gm.tis2web.lt.implementation.io.datamodel;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*     */ import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.IVCFacade;
/*     */ import com.eoos.gm.tis2web.vc.v2.service.VCFacade;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LTVCContext
/*     */ {
/*     */   private String curVIN;
/*     */   private String salesMake;
/*     */   private String model;
/*     */   private String engine;
/*     */   private String transmission;
/*     */   private String year;
/*     */   private IConfiguration cfg;
/*     */   
/*     */   public LTVCContext() {
/*  26 */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*  30 */     this.curVIN = "";
/*  31 */     this.year = this.transmission = this.engine = this.salesMake = this.model = new String();
/*     */   }
/*     */ 
/*     */   
/*     */   private String toString(Object obj) {
/*  36 */     if (obj != null) {
/*  37 */       return obj.toString();
/*     */     }
/*  39 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(ClientContext context) {
/*  44 */     reset();
/*     */     
/*  46 */     IVCFacade facade = VCFacade.getInstance(context);
/*     */     
/*  48 */     this.curVIN = toString(facade.getCurrentVIN());
/*  49 */     this.salesMake = toString(facade.getCurrentSalesmake());
/*  50 */     this.model = toString(facade.getCurrentModel());
/*  51 */     this.engine = toString(facade.getCurrentEngine());
/*  52 */     this.year = toString(facade.getCurrentModelyear());
/*  53 */     this.transmission = toString(facade.getCurrentTransmission());
/*  54 */     this.cfg = facade.getCfg();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVIN() {
/*  64 */     return this.curVIN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/*  73 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getYear() {
/*  82 */     return this.year;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEngine() {
/*  91 */     return this.engine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSalesMake() {
/* 100 */     return this.salesMake;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTransmission() {
/* 109 */     return this.transmission;
/*     */   }
/*     */   
/*     */   public IConfiguration getCfg() {
/* 113 */     return this.cfg;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\datamodel\LTVCContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */