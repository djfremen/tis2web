package com.eoos.gm.tis2web.sas.common.model.reqres;

import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
import java.util.List;

public interface SSARequest2 extends SSARequest {
  HardwareKey getHardwareKey();
  
  SSAResponse createResponse(Integer paramInteger1, Integer paramInteger2, List paramList);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\common\model\reqres\SSARequest2.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */