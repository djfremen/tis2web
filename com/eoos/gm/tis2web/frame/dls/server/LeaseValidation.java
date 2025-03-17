package com.eoos.gm.tis2web.frame.dls.server;

import com.eoos.gm.tis2web.frame.dls.client.api.Lease;
import com.eoos.gm.tis2web.frame.dls.client.api.SoftwareKey;

public interface LeaseValidation {
  boolean validateLease(SoftwareKey paramSoftwareKey, Lease paramLease);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dls\server\LeaseValidation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */