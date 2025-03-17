package com.eoos.gm.tis2web.frame.dls.client.api;

import java.net.URL;

public interface DLSManagement {
  SoftwareKey computeSoftwareKey();
  
  void registerSoftwareKey(SoftwareKey paramSoftwareKey);
  
  void registerLease(Lease paramLease, URL paramURL);
  
  void deleteSoftwareKey();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\DLSManagement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */