/*     */ package com.eoos.gm.tis2web.lt.implementation.io.db;
/*     */ 
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
/*     */ import com.eoos.gm.tis2web.ctoc.service.cai.SITOCProperty;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LGSIT;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*     */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfoProvider;
/*     */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*     */ import com.eoos.gm.tis2web.fts.service.cai.FTSHit;
/*     */ import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SILabel;
/*     */ import com.eoos.gm.tis2web.si.service.cai.SITOCElementProxy;
/*     */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*     */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public class LTTOCElementProxyImpl
/*     */   extends SIOLTElement
/*     */   implements SITOCElementProxy
/*     */ {
/*     */   protected LTCache cacheLT;
/*     */   protected LTDataWork mo;
/*  25 */   protected String label = null;
/*     */   
/*  27 */   protected FTSHit proxy = null;
/*     */   
/*  29 */   protected CTOC ctoc = null;
/*     */   
/*     */   public LTTOCElementProxyImpl(FTSHit proxy, CTOC ctoc, ILVCAdapter adapter, FTSService.Retrieval ftsRetrieval) {
/*  32 */     super(proxy.getSIO(), proxy.getOrder(), proxy.getLabelId().intValue(), (VCR)null, adapter);
/*  33 */     this.proxy = proxy;
/*  34 */     this.ctoc = ctoc;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOrder() {
/*  39 */     return this.proxy.getOrder();
/*     */   }
/*     */   
/*     */   public VCR getVCR() {
/*  43 */     boolean isVRCText = false;
/*  44 */     if (isVRCText && 
/*  45 */       this.proxy.getVCRText() != null) {
/*  46 */       return this.lvcAdapter.makeVCR(this.proxy.getVCRText());
/*     */     }
/*  48 */     return this.vcr;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLabel(String label, LocaleInfo locale) {
/*  53 */     this.label = label;
/*     */   }
/*     */   
/*     */   public String getSubject(LocaleInfo locale) {
/*  57 */     if (this.label == null) {
/*  58 */       List<Integer> flc = locale.getLocaleFLC(LGSIT.LT);
/*     */       
/*  60 */       if (flc == null) {
/*  61 */         return null;
/*     */       }
/*  63 */       for (int i = 0; i < flc.size(); i++) {
/*     */         
/*  65 */         LocaleInfo li = LocaleInfoProvider.getInstance().getLocale(flc.get(i));
/*  66 */         this.label = getLabel(li);
/*  67 */         if (this.label != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*  72 */     return this.label;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getPropertySet() {
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasProperty(SITOCProperty property) {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getProperty(SITOCProperty property) {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public String getMajorOperationNumber() {
/*  91 */     return this.proxy.getMajorOperation();
/*     */   }
/*     */   
/*     */   public Integer getID() {
/*  95 */     return this.proxy.getSIO();
/*     */   }
/*     */   
/*     */   public Integer getVCRId() {
/*  99 */     return Integer.valueOf(this.proxy.getVCR());
/*     */   }
/*     */ 
/*     */   
/*     */   public List getSITs() {
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLabelCached(LocaleInfo locale) {
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public SILabel getSubject() {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\LTTOCElementProxyImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */