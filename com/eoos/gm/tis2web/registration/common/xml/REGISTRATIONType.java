package com.eoos.gm.tis2web.registration.common.xml;

import java.util.List;

public interface REGISTRATIONType {
  String getSubscriberID();
  
  void setSubscriberID(String paramString);
  
  String getLicensedSessions();
  
  void setLicensedSessions(String paramString);
  
  SOFTWAREKEYType getSOFTWAREKEY();
  
  void setSOFTWAREKEY(SOFTWAREKEYType paramSOFTWAREKEYType);
  
  DEALERSHIPType getDEALERSHIP();
  
  void setDEALERSHIP(DEALERSHIPType paramDEALERSHIPType);
  
  String getVersion();
  
  void setVersion(String paramString);
  
  String getRequestID();
  
  void setRequestID(String paramString);
  
  List getSUBSCRIPTION();
  
  LICENSEKEYType getLICENSEKEY();
  
  void setLICENSEKEY(LICENSEKEYType paramLICENSEKEYType);
  
  String getRequestDate();
  
  void setRequestDate(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\common\xml\REGISTRATIONType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */