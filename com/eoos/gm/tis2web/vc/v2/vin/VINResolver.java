package com.eoos.gm.tis2web.vc.v2.vin;

import java.util.Set;

public interface VINResolver {
  Set resolveVIN(VIN paramVIN) throws VIN.InvalidVINException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\vin\VINResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */