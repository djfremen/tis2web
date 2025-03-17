package com.eoos.gm.tis2web.registration.standalone.authorization.service;

import com.eoos.gm.tis2web.registration.service.cai.Dealership;
import com.eoos.gm.tis2web.registration.standalone.authorization.service.cai.Authorization;
import com.eoos.propcfg.Configuration;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public interface SoftwareKeyService {
  public static final String REGISTRATION_SESSION = "TIS2WEB";
  
  void acquireSoftwareKey(Configuration paramConfiguration);
  
  boolean hasValidAuthorization();
  
  boolean hasMigratedHardwareKeyAuthorization();
  
  String getHardwareID();
  
  String getGroupACL();
  
  int getMaxSessionCount();
  
  int getLicensedSessionCount();
  
  Dealership getDealershipInformation();
  
  void setDealershipInformation(Dealership paramDealership) throws IOException;
  
  String getSubscriberID();
  
  String getSoftwareKey();
  
  Authorization getSubscription();
  
  Authorization getDefaultSubscription();
  
  Collection getAuthorizations();
  
  void updateLicense(String paramString1, String paramString2);
  
  boolean isRepeatRegistration(Set paramSet);
  
  void extendAuthorization(Set paramSet);
  
  byte[] createManualRegistrationForm(Collection paramCollection, Integer paramInteger, Output paramOutput);
  
  public static interface Output {
    void setRequestID(String param1String);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\service\SoftwareKeyService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */