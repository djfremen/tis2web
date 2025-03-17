package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import java.io.Serializable;
import java.util.Set;

public interface RequestMethodData extends Serializable {
  int getRequestMethodID();
  
  int getDeviceID();
  
  int getProtocol();
  
  int getPINLink();
  
  int getReadVIN();
  
  int getVINReadType();
  
  String getVINAddresses();
  
  int getReadParts();
  
  int getPartReadType();
  
  String getPartAddresses();
  
  String getPartFormat();
  
  String getPartLength();
  
  String getPartStartByte();
  
  int getReadHWID();
  
  int getHWIDReadType();
  
  String getHWIDAddresses();
  
  int getHWIDFormat();
  
  int getHWIDLength();
  
  int getHWIDStartByte();
  
  int getReadSecuritySeed();
  
  int getSpecialReqType();
  
  int getReadCVN();
  
  Set getAttributes();
  
  Object getValue(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\RequestMethodData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */