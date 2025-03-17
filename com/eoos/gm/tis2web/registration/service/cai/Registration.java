package com.eoos.gm.tis2web.registration.service.cai;

import java.util.Date;
import java.util.List;

public interface Registration {
  void setRequestID(String paramString);
  
  String getRequestID();
  
  void setOrganization(SalesOrganization paramSalesOrganization);
  
  SalesOrganization getOrganization();
  
  void setRequestStatus(RequestStatus paramRequestStatus);
  
  RequestStatus getRequestStatus();
  
  void setRequestType(RequestType paramRequestType);
  
  RequestType getRequestType();
  
  void setRequestDate(Date paramDate);
  
  Date getRequestDate();
  
  void setDealership(Dealership paramDealership);
  
  Dealership getDealership();
  
  void setSubscriberID(String paramString);
  
  String getSubscriberID();
  
  void setSessionCount(Integer paramInteger);
  
  Integer getSessionCount();
  
  void setSoftwareKey(String paramString);
  
  String getSoftwareKey();
  
  void setLicenseKey(String paramString);
  
  String getLicenseKey();
  
  void setAuthorizationID(String paramString);
  
  String getAuthorizationID();
  
  void setAuthorizationList(List paramList);
  
  List getAuthorizationList();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\Registration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */