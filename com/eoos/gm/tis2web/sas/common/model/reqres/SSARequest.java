package com.eoos.gm.tis2web.sas.common.model.reqres;

import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
import java.util.List;

public interface SSARequest extends SecurityAccessRequest {
  List getVINs();
  
  SSARequest2 setHardwareKey(HardwareKey paramHardwareKey);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\SSARequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */