package com.eoos.gm.tis2web.lt.implementation.io.db;

import com.eoos.gm.tis2web.frame.export.common.datatype.DBVersionInformation;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTDataWork;
import com.eoos.gm.tis2web.lt.implementation.io.datamodel.LTLanguageContext;
import com.eoos.gm.tis2web.lt.implementation.io.domain.AWBlob;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ILTStore {
  Map loadLangMap();
  
  Set<String> getWinLanguages();
  
  Map loadSmcMap();
  
  Map loadSmcMcMap();
  
  Integer getW100000AW(int paramInt);
  
  LTDataWork getMainWork(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString);
  
  List getAddOnWorks(Integer paramInteger1, Integer paramInteger2, String paramString);
  
  boolean fillMainworkMultiText(Integer paramInteger, LTDataWork paramLTDataWork);
  
  boolean fillAddonworkMultiText(Integer paramInteger, LTDataWork paramLTDataWork);
  
  boolean fillMainworkTC(Integer paramInteger, LTDataWork paramLTDataWork);
  
  boolean fillMainworkSXAW(Integer paramInteger, LTDataWork paramLTDataWork, Map paramMap);
  
  boolean fillLangContextHourLabel(LTLanguageContext paramLTLanguageContext);
  
  boolean fillLangContext(LTLanguageContext paramLTLanguageContext);
  
  boolean loadOperatorCache(Integer paramInteger, Map paramMap1, Map paramMap2);
  
  boolean fillAddonworkSXAW(Integer paramInteger, List paramList, Map paramMap);
  
  AWBlob loadGraphic(int paramInt) throws Exception;
  
  AWBlob loadDocument(int paramInt, Integer paramInteger) throws Exception;
  
  DBVersionInformation getVersionInfo();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\implementation\io\db\ILTStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */