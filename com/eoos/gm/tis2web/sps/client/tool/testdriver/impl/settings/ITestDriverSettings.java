package com.eoos.gm.tis2web.sps.client.tool.testdriver.impl.settings;

import java.util.Map;

public interface ITestDriverSettings {
  public static final short NO_JOB = 0;
  
  public static final short GET_ECU = 1;
  
  public static final short REPROGRAM_ECU = 2;
  
  void reset();
  
  int getStatusLineNo();
  
  void setStatusLineNo(int paramInt);
  
  boolean IsLastVIT1();
  
  void setIsLastVIT1(boolean paramBoolean);
  
  String getVIT1FileName();
  
  void setVIT1FileName(String paramString);
  
  void setCurrDir(String paramString);
  
  String getCurrDir();
  
  String getOutDir();
  
  String getVITID();
  
  void setVITID(String paramString);
  
  String getStatus();
  
  void setStatus(String paramString);
  
  String getWriteModules();
  
  void setWriteModules(String paramString);
  
  String getReadHandling();
  
  void setReadHandling(String paramString);
  
  String getRenameVIT1();
  
  void setRenameVIT1(String paramString);
  
  int getModulesNo();
  
  void setModulesNo(int paramInt);
  
  String getVIT2FileName();
  
  String getLogFileName();
  
  boolean isTXT();
  
  String getSetting4Key(String paramString);
  
  boolean getBoolSetting4Key(String paramString);
  
  void setReadFromDir();
  
  boolean readFromDir();
  
  boolean canWriteReadStatus(short paramShort);
  
  boolean canRenameVIT1File();
  
  Map getBlobID2Size();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\testdriver\impl\settings\ITestDriverSettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */