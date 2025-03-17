package com.eoos.gm.tis2web.sas.common.model.reqres;

import com.eoos.gm.tis2web.sas.common.model.HardwareKey;

public interface SCASKARequestWithHWK extends SCASKARequest {
  SCASKAResponse createResponse(HardwareKey paramHardwareKey);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\SCASKARequestWithHWK.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */