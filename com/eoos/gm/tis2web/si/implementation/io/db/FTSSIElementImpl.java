/*     */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.fts.implementation.service.FTSSIO;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SI;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import com.eoos.scsm.v2.util.HashCalc;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ public class FTSSIElementImpl
/*     */   extends SIOElementImpl
/*     */   implements FTSSIElement, FTSSIO
/*     */ {
/*     */   protected SI.Retrieval siRetrieval;
/*     */   protected ILVCAdapter.Retrieval lvcRetrieval;
/*  19 */   protected Integer sioID = null;
/*     */   
/*  21 */   protected String lun = null;
/*     */   
/*     */   protected int order;
/*     */   
/*  25 */   protected String nonMarketConstraint = null;
/*     */   
/*  27 */   protected List sits = null;
/*     */   
/*  29 */   protected VCR vcr = null;
/*     */ 
/*     */   
/*     */   public FTSSIElementImpl(Integer sioId, SI.Retrieval siRetrieval, ILVCAdapter.Retrieval lvcRetrieval) {
/*  33 */     this.siRetrieval = siRetrieval;
/*  34 */     this.lvcRetrieval = lvcRetrieval;
/*  35 */     this.sioID = sioId;
/*     */   }
/*     */   
/*     */   public Integer getID() {
/*  39 */     return this.sioID;
/*     */   }
/*     */   
/*     */   public String getLabel(LocaleInfo locale) {
/*  43 */     return this.siRetrieval.getSI().getSubject(this.sioID, locale);
/*     */   }
/*     */   
/*     */   public void setLiteratureNumber(String lun) {
/*  47 */     this.lun = lun;
/*     */   }
/*     */   
/*     */   public String getLiteratureNumber() {
/*  51 */     return this.lun;
/*     */   }
/*     */   
/*     */   public void setNonMarketsConstraints(String nonMarketConstraint) {
/*  55 */     this.nonMarketConstraint = nonMarketConstraint;
/*     */   }
/*     */   
/*     */   public String getNonMarketsConstraints() {
/*  59 */     return this.nonMarketConstraint;
/*     */   }
/*     */   
/*     */   public void resolveVCR(String vcr) {
/*  63 */     this.vcr = this.lvcRetrieval.getLVCAdapter().makeVCR(vcr);
/*     */   }
/*     */   
/*     */   public void setVCR(VCR vcr) {
/*  67 */     this.vcr = vcr;
/*     */   }
/*     */   
/*     */   public void setOrder(int order) {
/*  71 */     this.order = order;
/*     */   }
/*     */   
/*     */   public int getOrder() {
/*  75 */     return this.order;
/*     */   }
/*     */   
/*     */   public void setSits(List sits) {
/*  79 */     this.sits = sits;
/*     */   }
/*     */   
/*     */   public List getSITIDs() {
/*  83 */     return this.sits;
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/*  87 */     return this.vcr;
/*     */   }
/*     */   
/*     */   public SIO lookupSIO() {
/*  91 */     return this.siRetrieval.getSI().lookupSIO(this.sioID.intValue());
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  95 */     if (this == obj)
/*  96 */       return true; 
/*  97 */     if (obj instanceof FTSSIElementImpl) {
/*  98 */       return this.sioID.equals(((FTSSIElementImpl)obj).sioID);
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 106 */     int ret = SIOElementImpl.class.hashCode();
/* 107 */     ret = HashCalc.addHashCode(ret, this.sioID);
/* 108 */     return ret;
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\FTSSIElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */