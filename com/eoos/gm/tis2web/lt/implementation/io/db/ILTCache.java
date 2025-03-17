package com.eoos.gm.tis2web.lt.implementation.io.db;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
import com.eoos.gm.tis2web.ctoc.service.cai.CTOCType;
import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.W100000Handler;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.awkinterpreter.AWKInterpreter;
import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface ILTCache {
  String findCacheTextElement(Integer paramInteger, String paramString);
  
  String findOperatorCacheElement(Integer paramInteger, char paramChar);
  
  String formatAW(Locale paramLocale, boolean paramBoolean1, Integer paramInteger1, String paramString, Integer paramInteger2, Integer paramInteger3, W100000Handler paramW100000Handler, boolean paramBoolean2);
  
  void formatAWs(Locale paramLocale, boolean paramBoolean, Integer paramInteger, LTDataWork paramLTDataWork, W100000Handler paramW100000Handler);
  
  List getAddOnWorks(Integer paramInteger1, Integer paramInteger2, String paramString);
  
  String getAWUnit(boolean paramBoolean, Integer paramInteger);
  
  AWBlob getDocument(int paramInt, Integer paramInteger);
  
  AWBlob getGraphic(int paramInt);
  
  LTLanguageContext getLanguageContext(Integer paramInteger);
  
  Integer getLc(LocaleInfo paramLocaleInfo);
  
  LTDataWork getMainWork(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString);
  
  Integer getMc(Integer paramInteger, String paramString);
  
  SITOCElement getSITOCElement(int paramInt);
  
  Integer getSmc(String paramString);
  
  DBVersionInformation getVersionInfo();
  
  Integer getW100000AW(Integer paramInteger);
  
  SITOCElement make(CTOCType paramCTOCType, int paramInt1, int paramInt2, long paramLong, VCR paramVCR);
  
  void register(CTOC paramCTOC);
  
  void removeNotValidSXAWData(LTDataWork paramLTDataWork, AWKInterpreter paramAWKInterpreter);
  
  Set<String> getSupportedWinLangs();
  
  Set<String> getWinLanguagesTable();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\ILTCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */