/*   */ package com.eoos.gm.tis2web.fts.service.cai;
/*   */ 
/*   */ import com.eoos.gm.tis2web.fts.service.FTSService;
/*   */ import org.apache.log4j.Logger;
/*   */ 
/*   */ 
/*   */ public abstract class HitList
/*   */ {
/* 9 */   protected static final Logger log = Logger.getLogger(HitList.class);
/*   */   
/*   */   protected abstract FTSService.Retrieval getFTSServiceRetrieval();
/*   */ }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\service\cai\HitList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */