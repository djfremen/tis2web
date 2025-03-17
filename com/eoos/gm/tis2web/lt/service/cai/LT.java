/*    */ package com.eoos.gm.tis2web.lt.service.cai;public interface LT extends CTOCFactory { public static final String WILDCARD = "*"; SITOCElement getSITOCElement(int paramInt);
/*    */   Integer getCheckListID(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
/*    */   List getCheckListModelYears(String paramString1, String paramString2, String paramString3, String paramString4);
/*    */   List getCheckListEngines(String paramString1, String paramString2, String paramString3, String paramString4);
/*    */   List getCheckListAttributes(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
/*    */   Serviceplan getServiceplan(Locale paramLocale, Integer paramInteger, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
/*    */   String getServiceTypeXSL(Locale paramLocale, String paramString) throws Exception;
/*    */   Collection getVersionInfo();
/*    */   String findCacheTextElement(Integer paramInteger, String paramString);
/*    */   String findOperatorCacheElement(Integer paramInteger, char paramChar);
/*    */   String formatAW(Locale paramLocale, boolean paramBoolean1, Integer paramInteger1, String paramString, Integer paramInteger2, Integer paramInteger3, W100000Handler paramW100000Handler, boolean paramBoolean2);
/*    */   void formatAWs(Locale paramLocale, boolean paramBoolean, Integer paramInteger, LTDataWork paramLTDataWork, W100000Handler paramW100000Handler);
/*    */   List getAddOnWorks(Integer paramInteger1, Integer paramInteger2, String paramString);
/*    */   String getAWUnit(boolean paramBoolean, Integer paramInteger);
/*    */   AWBlob getDocument(int paramInt, Integer paramInteger);
/*    */   AWBlob getGraphic(int paramInt);
/*    */   LTLanguageContext getLanguageContext(Integer paramInteger);
/*    */   Integer getLc(LocaleInfo paramLocaleInfo);
/*    */   LTDataWork getMainWork(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString);
/*    */   Integer getMc(Integer paramInteger, String paramString);
/*    */   Integer getSmc(String paramString);
/*    */   Integer getW100000AW(Integer paramInteger);
/*    */   SITOCElement make(CTOCType paramCTOCType, int paramInt1, int paramInt2, long paramLong, VCR paramVCR);
/*    */   void register(CTOC paramCTOC);
/*    */   void removeNotValidSXAWData(LTDataWork paramLTDataWork, AWKInterpreter paramAWKInterpreter);
/*    */   Set<String> getSupportedWinLangs();
/*    */   Set<String> getWinLanguagesTable();
/*    */   public static interface Retrieval { LT getLT();
/*    */     public static final class RI implements Retrieval { public RI(LT lt) {
/* 30 */         this.lt = lt;
/*    */       }
/*    */       private LT lt;
/*    */       public LT getLT() {
/* 34 */         return this.lt;
/*    */       } }
/*    */      }
/*    */    }


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\service\cai\LT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */