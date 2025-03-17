package com.eoos.gm.tis2web.frame.dls.server;

import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;
import java.util.Collection;

public interface IDatabase extends LeaseValidation {
  boolean registerSoftwareKey(SoftwareKey paramSoftwareKey, String paramString1, String paramString2);
  
  boolean validateSoftwareKey(SoftwareKey paramSoftwareKey);
  
  boolean registerLease(SoftwareKey paramSoftwareKey, Lease paramLease, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  boolean validateLease(SoftwareKey paramSoftwareKey, Lease paramLease);
  
  String getUserGroup(SoftwareKey paramSoftwareKey, Lease paramLease);
  
  Collection getSessionIDs(SoftwareKey paramSoftwareKey);
  
  String getBAC(SoftwareKey paramSoftwareKey, Lease paramLease);
  
  String getCountryCode(SoftwareKey paramSoftwareKey, Lease paramLease);
  
  Collection getUsernames(String paramString) throws Exception;
  
  void deleteSoftwareKeys(Collection paramCollection) throws Exception;
  
  void deleteLeases(Collection paramCollection) throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\IDatabase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */