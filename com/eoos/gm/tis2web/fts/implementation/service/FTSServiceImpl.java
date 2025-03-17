/*    */ package com.eoos.gm.tis2web.fts.implementation.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.fts.implementation.FTS;
/*    */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*    */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
/*    */ import com.eoos.propcfg.Configuration;
/*    */ import com.eoos.scsm.v2.util.Util;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FTSServiceImpl
/*    */   implements FTSService
/*    */ {
/*    */   private IFTS fts;
/*    */   
/*    */   public FTSServiceImpl(Configuration cfg, FTSService.Callback callback, ILVCAdapter.Retrieval lvcr) {
/*    */     try {
/* 25 */       this.fts = (IFTS)new FTS(cfg, callback, lvcr.getLVCAdapter().getVC());
/* 26 */     } catch (Exception e) {
/* 27 */       throw Util.toRuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Collection query(LocaleInfo locale, VCR vcr, String sit, String field, String query, int operator) throws IFTS.MaximumExceededException {
/* 32 */     return this.fts.query(locale, vcr, sit, field, query, operator);
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public FTSService.Retrieval createRetrievalImpl() {
/* 40 */     return (FTSService.Retrieval)new FTSService.Retrieval.RI(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\implementation\service\FTSServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */