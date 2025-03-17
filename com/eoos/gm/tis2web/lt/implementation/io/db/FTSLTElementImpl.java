/*    */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.ctoc.service.LTCTOCService;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.lt.service.cai.FTSLTElement;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ 
/*    */ public class FTSLTElementImpl
/*    */   extends SIOLTElement
/*    */   implements FTSLTElement
/*    */ {
/*    */   protected ILVCAdapter.Retrieval lvcRetrieval;
/*    */   protected LTCTOCService ltctocService;
/* 15 */   protected Integer id = null;
/*    */   
/* 17 */   protected String lun = null;
/*    */   
/* 19 */   protected String moNr = null;
/*    */   
/* 21 */   protected Integer labelId = null;
/*    */   
/* 23 */   String paintStage = null;
/*    */   
/*    */   public FTSLTElementImpl(Integer id, LTCTOCService ltctocService, ILVCAdapter.Retrieval adapter) {
/* 26 */     super(Integer.valueOf(0), 0, 0, (VCR)null, (ILVCAdapter)null);
/* 27 */     this.id = id;
/* 28 */     this.ltctocService = ltctocService;
/* 29 */     this.lvcRetrieval = adapter;
/*    */   }
/*    */   
/*    */   public Integer getID() {
/* 33 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setMajorOperationNumber(String moNr) {
/* 37 */     this.moNr = moNr;
/*    */   }
/*    */   
/*    */   public String getMajorOperationNumber() {
/* 41 */     return this.moNr;
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolveVCR(String vcr) {
/* 46 */     this.vcr = this.lvcRetrieval.getLVCAdapter().makeVCR(vcr);
/*    */   }
/*    */   
/*    */   public void setVCR(VCR vcr) {
/* 50 */     this.vcr = vcr;
/*    */   }
/*    */   
/*    */   public void setPaintStage(String paintStage) {
/* 54 */     this.paintStage = paintStage;
/*    */   }
/*    */   
/*    */   public String getPaintingStage() {
/* 58 */     return this.paintStage;
/*    */   }
/*    */   
/*    */   public void setLabelID(Integer labelId) {
/* 62 */     this.labelId = labelId;
/*    */   }
/*    */   
/*    */   public String getDisplay(LocaleInfo locale) {
/* 66 */     String ps = getPaintingStage();
/* 67 */     if (ps == null) {
/* 68 */       return getMajorOperationNumber() + " " + getSubject(locale);
/*    */     }
/* 70 */     return getMajorOperationNumber() + " " + getSubject(locale) + " - " + ps;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSubject(LocaleInfo locale) {
/* 75 */     return this.ltctocService.getCTOC().getCTOCStore().getLabel(locale, this.labelId);
/*    */   }
/*    */   
/*    */   public Integer getLabelID() {
/* 79 */     return this.labelId;
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\FTSLTElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */