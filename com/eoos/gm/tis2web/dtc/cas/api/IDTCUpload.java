package com.eoos.gm.tis2web.dtc.cas.api;

import com.eoos.gm.tis2web.common.Authentication;
import com.eoos.gm.tis2web.common.HostInfo;
import java.io.IOException;
import java.util.Set;

public interface IDTCUpload {
  boolean uploadEnabled() throws IOException;
  
  Identifier upload(Set paramSet) throws SecurityException, IOException, UnavailableServiceException;
  
  boolean finished(Identifier paramIdentifier);
  
  void reset();
  
  public static interface Callback {
    Authentication getAuthentication(HostInfo param1HostInfo);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\dtc\cas\api\IDTCUpload.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */