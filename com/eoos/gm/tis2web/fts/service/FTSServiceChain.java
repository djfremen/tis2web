/*    */ package com.eoos.gm.tis2web.fts.service;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class FTSServiceChain
/*    */   implements FTSService
/*    */ {
/*    */   private Collection delegates;
/*    */   
/*    */   public FTSServiceChain(Collection delegates) {
/* 17 */     this.delegates = delegates;
/*    */   }
/*    */   
/*    */   private static FTSService toFTSService(Object object) {
/* 21 */     return ((FTSService.Retrieval)object).getFTSService();
/*    */   }
/*    */   
/*    */   public Collection query(LocaleInfo locale, VCR vcr, String sit, String field, String query, int operator) throws IFTS.MaximumExceededException {
/* 25 */     Set ret = new LinkedHashSet();
/* 26 */     for (Iterator iter = this.delegates.iterator(); iter.hasNext(); ) {
/* 27 */       Collection sios = toFTSService(iter.next()).query(locale, vcr, sit, field, query, operator);
/* 28 */       if (sios == null)
/*    */         continue; 
/* 30 */       ret.addAll(sios);
/*    */     } 
/* 32 */     return ret;
/*    */   }
/*    */   
/*    */   public Object getIdentifier() {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public FTSService.Retrieval createRetrievalImpl() {
/* 40 */     return new FTSService.Retrieval.RI(this);
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\service\FTSServiceChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */