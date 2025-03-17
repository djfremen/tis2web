package com.eoos.gm.tis2web.frame.dls.client.api;

import java.net.URL;
import java.util.Collection;

public interface DLSService {
  boolean validateSoftwarekey();
  
  boolean validateAuthorization();
  
  Lease getLease(LeaseSelection paramLeaseSelection) throws MissingLeaseException, ExpiredLeaseException;
  
  Collection getValidLeases();
  
  URL getURL(Lease paramLease);
  
  long getExpirationDate(Lease paramLease);
  
  SoftwareKey getSoftwareKey();
  
  Lease getNewestValidLease();
  
  public static interface LeaseSelection {
    Lease selectLease(Collection param1Collection);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\api\DLSService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */