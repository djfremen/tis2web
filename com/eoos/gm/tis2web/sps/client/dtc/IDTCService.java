package com.eoos.gm.tis2web.sps.client.dtc;

import com.eoos.gm.tis2web.sps.common.VIT1;
import com.eoos.gm.tis2web.sps.service.cai.AttributeValueMap;

public interface IDTCService {
  boolean isAutomaticallyClearDTCsMode();
  
  boolean isAlreadyClearDTCsExecuted();
  
  Integer getVehicleLink();
  
  void evaluateVIT1ClearDTC(VIT1 paramVIT1, AttributeValueMap paramAttributeValueMap) throws Exception;
  
  void executeClearDTCs();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\dtc\IDTCService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */