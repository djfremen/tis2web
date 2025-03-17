/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*    */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*    */ import com.eoos.gm.tis2web.fts.service.cai.HitList;
/*    */ import com.eoos.gm.tis2web.lt.v2.LTDataAdapterFacade;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class HitListLT
/*    */   extends HitList
/*    */ {
/* 17 */   protected static final Logger log = Logger.getLogger(HitListLT.class);
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public HitListLT(ClientContext context) throws Exception {
/* 23 */     this.context = context;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection query(LocaleInfo locale, VCR vcr, String query, int operator) throws IFTS.MaximumExceededException {
/* 28 */     Collection result = getFTSServiceRetrieval().getFTSService().query(locale, null, "SIT0LT", "subject", query, operator);
/* 29 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FTSService.Retrieval getFTSServiceRetrieval() {
/* 34 */     return LTDataAdapterFacade.getInstance(this.context).getFTSService().createRetrievalImpl();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\HitListLT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */