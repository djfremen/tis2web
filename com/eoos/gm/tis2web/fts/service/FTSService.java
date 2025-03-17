/*    */ package com.eoos.gm.tis2web.fts.service;
/*    */ public interface FTSService extends Service { public static final int OPERATOR_OR = 0; public static final int OPERATOR_AND = 1; public static final int EXPERT = 2; public static final String SUBJECT = "subject"; public static final String CONTENT = "content";
/*    */   public static final String SIT = "sit";
/*    */   public static final String LTSIT = "SIT0LT";
/*    */   
/*    */   Retrieval createRetrievalImpl();
/*    */   
/*    */   Collection query(LocaleInfo paramLocaleInfo, VCR paramVCR, String paramString1, String paramString2, String paramString3, int paramInt) throws IFTS.MaximumExceededException;
/*    */   
/*    */   public static interface Callback { Integer getSioId(String param1String);
/*    */     
/*    */     List loadSIOs(List param1List);
/*    */     
/*    */     SITOCElement lookupSIO(Integer param1Integer);
/*    */     
/*    */     Object createElement(String param1String); }
/*    */   
/*    */   public static interface Retrieval { FTSService getFTSService();
/*    */     
/*    */     public static final class RI implements Retrieval { public RI(FTSService fts) {
/* 21 */         this.fts = fts;
/*    */       }
/*    */       private FTSService fts;
/*    */       public FTSService getFTSService() {
/* 25 */         return this.fts;
/*    */       } }
/*    */      }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\fts\service\FTSService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */