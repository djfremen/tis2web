package com.eoos.gm.tis2web.registration.service.cai;

import com.eoos.gm.tis2web.frame.export.common.util.DatabaseLink;
import com.eoos.propcfg.Configuration;
import java.util.List;

public interface Registry {
  void init(Configuration paramConfiguration, DatabaseLink paramDatabaseLink);
  
  List getAuthorizations() throws Exception;
  
  String getRequestID(RequestType paramRequestType) throws Exception;
  
  String getRequestID(Registration paramRegistration) throws Exception;
  
  Registration loadRegistrationRecord(String paramString) throws Exception;
  
  Registration loadRegistrationRecordByDealershipID(String paramString) throws Exception;
  
  void deleteRegistrationRecord(Registration paramRegistration) throws Exception;
  
  void updateDealershipInformation(Dealership paramDealership) throws Exception;
  
  String registerRegistrationRequest(String paramString1, Subscription paramSubscription, Dealership paramDealership, String paramString2, Integer paramInteger) throws Exception;
  
  void registerRegistrationRequest(String paramString, Registration paramRegistration) throws Exception;
  
  void registerRegistrationRequest(String paramString, Registration paramRegistration, List paramList) throws Exception;
  
  List loadRegistrationRecords(SalesOrganization paramSalesOrganization, RequestStatus paramRequestStatus, RegistrationFilter paramRegistrationFilter, RegistrationAttribute paramRegistrationAttribute, SortDirection paramSortDirection) throws Exception;
  
  Registration createRegistrationRequest() throws Exception;
  
  void computeLicenceKey(Registration paramRegistration, String paramString, Integer paramInteger) throws Exception;
  
  void authorizeRegistrationRequest(Registration paramRegistration) throws Exception;
  
  void revokeAuthorization(Registration paramRegistration) throws Exception;
  
  byte[] generateFaxNotification(Registration paramRegistration, Integer paramInteger) throws Exception;
  
  String exportRegistrationDatabase(SalesOrganization paramSalesOrganization) throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\service\cai\Registry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */