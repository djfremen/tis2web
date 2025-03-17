package com.eoos.gm.tis2web.frame.dls.client;

import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
import java.net.URL;
import java.util.Map;

public interface LeaseRegistry {
  void registerLease(Lease paramLease, URL paramURL);
  
  Map getLeaseMap();
  
  void clear();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\LeaseRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */