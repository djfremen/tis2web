package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import java.io.Serializable;
import java.util.List;

public interface ProgrammingData extends Serializable {
  String getVIN();
  
  List getCalibrationFiles();
  
  Integer getDeviceID();
  
  String getVMECUHN();
  
  String getSSECUSVN();
  
  List getOptionBytes();
  
  String getOdometer();
  
  String getKeycode();
  
  List getVIT1TransferAttributes();
  
  String getRepairShopCode();
  
  void setRepairShopCode(String paramString);
  
  boolean skipSameCalibration();
  
  boolean isSecurityCodeRequired();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\ProgrammingData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */