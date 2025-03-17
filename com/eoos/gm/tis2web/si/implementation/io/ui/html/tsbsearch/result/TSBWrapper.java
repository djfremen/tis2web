/*    */ package com.eoos.gm.tis2web.si.implementation.io.ui.html.tsbsearch.result;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.implementation.io.datamodel.domain.tsb.TSBAdapter;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSB;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOTSBImpl;
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TSBWrapper
/*    */ {
/*    */   protected SIOTSB tsb;
/* 24 */   protected String model = null;
/*    */   
/* 26 */   protected String engine = null;
/*    */   
/*    */   public TSBWrapper(String model, String engine, SIOTSB tsb) {
/* 29 */     this.tsb = tsb;
/* 30 */     this.engine = engine;
/* 31 */     this.model = model;
/*    */   }
/*    */   
/*    */   public TSBWrapper(SIOTSBImpl tsb) {
/* 35 */     this(null, null, (SIOTSB)tsb);
/*    */   }
/*    */   
/*    */   public String getEngine() {
/* 39 */     if (this.engine != null) {
/* 40 */       return this.engine;
/*    */     }
/* 42 */     return this.tsb.getEngine();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModel() {
/* 47 */     if (this.model != null) {
/* 48 */       return this.model;
/*    */     }
/* 50 */     return this.tsb.getModel();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAllModelString() {
/* 55 */     return this.tsb.getModel();
/*    */   }
/*    */   
/*    */   public Date getPublicationDate() {
/* 59 */     return this.tsb.getPublicationDate();
/*    */   }
/*    */   
/*    */   public String getRemedyNumber() {
/* 63 */     return this.tsb.getRemedyNumber();
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 67 */     return this.tsb.getID();
/*    */   }
/*    */   
/*    */   public boolean hasModelRestriction() {
/* 71 */     return this.tsb.hasModelRestriction();
/*    */   }
/*    */   
/*    */   public boolean hasEngineRestriction() {
/* 75 */     return this.tsb.hasEngineRestriction();
/*    */   }
/*    */   
/*    */   public SIOTSB getTSB() {
/* 79 */     return this.tsb;
/*    */   }
/*    */   
/*    */   public String getSubject(LocaleInfo li) {
/* 83 */     return this.tsb.getSubject(li);
/*    */   }
/*    */   
/*    */   public String getTroubleCode() {
/* 87 */     String retValue = TSBAdapter.getTroubleCode(this.tsb);
/* 88 */     return (retValue != null) ? retValue : "";
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\i\\ui\html\tsbsearch\result\TSBWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */