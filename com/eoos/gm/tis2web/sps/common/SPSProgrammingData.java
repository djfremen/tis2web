package com.eoos.gm.tis2web.sps.common;

import java.util.List;

public interface SPSProgrammingData {
  List getBlobs();
  
  void setBlobs(List paramList);
  
  String getPostProgMsg();
  
  void setPostProgMsg(String paramString);
  
  String getVMECUHN();
  
  void setVMECUHN(String paramString);
  
  String getSSECUSVN();
  
  void setSSECUSVN(String paramString);
  
  String getEcuadr();
  
  void setEcuadr(String paramString);
  
  String getOdometer();
  
  void setOdometer(String paramString);
  
  String getKeycode();
  
  void setKeycode(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\SPSProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */