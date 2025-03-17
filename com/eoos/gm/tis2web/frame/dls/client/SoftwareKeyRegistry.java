package com.eoos.gm.tis2web.frame.dls.client;

import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;

public interface SoftwareKeyRegistry {
  void registerSoftwareKey(SoftwareKey paramSoftwareKey);
  
  SoftwareKey getSoftwareKey();
  
  void deleteSoftwareKey();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\client\SoftwareKeyRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */