/*    */ package com.eoos.gm.tis2web.si.implementation.io.db;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIO;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOLU;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOLUElement;
/*    */ import com.eoos.gm.tis2web.si.service.cai.SIOType;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ 
/*    */ public class SIOLUElementImpl extends SIOElementImpl implements SIOLU, SIOLUElement {
/*    */   private SIOElementImpl.Callback callback;
/*    */   
/*    */   public SIOLUElementImpl(Integer id, int order, VCR vcr, SIOElementImpl.Callback callback, ILVCAdapter.Retrieval lvcr) {
/* 17 */     super(id, order, SIOType.SI.ord(), vcr, callback, lvcr);
/* 18 */     this.callback = callback;
/*    */   }
/*    */   
/*    */   public SIOBlob getDocument(LocaleInfo locale) {
/* 22 */     return this.callback.getDocument((SIO)this, locale);
/*    */   }
/*    */   
/*    */   public boolean isQualified(LocaleInfo locale, String country, VCR vcr) {
/* 26 */     if (!super.isQualified(locale, country, vcr)) {
/* 27 */       return false;
/*    */     }
/* 29 */     String nonMarkets = getNonMarketsConstraints();
/* 30 */     if (!Util.isNullOrEmpty(nonMarkets)) {
/* 31 */       return (nonMarkets.indexOf(country) >= 0);
/*    */     }
/*    */     
/* 34 */     return (getSubject(locale) != null);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\SIOLUElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */