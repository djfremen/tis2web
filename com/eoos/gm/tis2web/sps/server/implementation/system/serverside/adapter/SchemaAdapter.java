package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingData;
import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingDataUnit;
import com.eoos.gm.tis2web.sps.server.implementation.dbinfo.DatabaseInfo;
import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;
import com.eoos.gm.tis2web.sps.service.cai.RequestException;

public interface SchemaAdapter {
  Boolean reprogram(ProgrammingData paramProgrammingData, AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
  
  ProgrammingData getProgrammingData(AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
  
  Controller getController(AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
  
  byte[] getData(ProgrammingDataUnit paramProgrammingDataUnit, AttributeValueMap paramAttributeValueMap) throws RequestException, Exception;
  
  String getBulletin(String paramString1, String paramString2);
  
  String getHTML(String paramString1, String paramString2);
  
  byte[] getImage(String paramString);
  
  Object getVersionInfo();
  
  DatabaseInfo getDatabaseInfo(AttributeValueMap paramAttributeValueMap, Attribute paramAttribute) throws Exception;
  
  void reset() throws Exception;
  
  void init() throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\SchemaAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */