package com.eoos.gm.tis2web.rpo.api;

import com.eoos.gm.tis2web.vc.v2.vin.VIN;

public interface RPORetrieval {
  RPOContainer getRPOs(String paramString) throws VIN.InvalidVINException, VIN.UnsupportedVINException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\api\RPORetrieval.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */