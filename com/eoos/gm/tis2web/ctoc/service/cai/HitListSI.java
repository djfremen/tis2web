/*    */ package com.eoos.gm.tis2web.ctoc.service.cai;
/*    */ 
/*    */ import com.eoos.gm.tis2web.frame.export.common.ClientContext;
/*    */ import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
/*    */ import com.eoos.gm.tis2web.fts.implementation.IFTS;
/*    */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*    */ import com.eoos.gm.tis2web.fts.service.cai.HitList;
/*    */ import com.eoos.gm.tis2web.si.v2.SIDataAdapterFacade;
/*    */ import com.eoos.gm.tis2web.vcr.service.cai.VCR;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class HitListSI
/*    */   extends HitList
/*    */ {
/* 16 */   protected static final Logger log = Logger.getLogger(HitListSI.class);
/*    */   
/*    */   private ClientContext context;
/*    */ 
/*    */   
/*    */   public HitListSI(ClientContext context) throws Exception {
/* 22 */     this.context = context;
/*    */   }
/*    */   
/*    */   protected String mangleSIT(CTOCNode sit) {
/* 26 */     return (sit == null) ? null : ("sit" + sit.getID());
/*    */   }
/*    */   
/*    */   public Collection query(LocaleInfo locale, VCR vcr, CTOCNode sit, String field, String query, int operator) throws IFTS.MaximumExceededException {
/* 30 */     Collection sios = getFTSServiceRetrieval().getFTSService().query(locale, vcr, mangleSIT(sit), field, query, operator);
/* 31 */     return sios;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FTSService.Retrieval getFTSServiceRetrieval() {
/* 36 */     return SIDataAdapterFacade.getInstance(this.context).getFTSService().createRetrievalImpl();
/*    */   }
/*    */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\HitListSI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */