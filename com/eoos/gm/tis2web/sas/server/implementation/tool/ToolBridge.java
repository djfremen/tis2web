package com.eoos.gm.tis2web.sas.server.implementation.tool;

import com.eoos.datatype.gtwo.Pair;
import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SCASKAResult;
import com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata.SSAData;

public interface ToolBridge {
  String initialize(String paramString);
  
  boolean setDriverProperties(Pair[] paramArrayOfPair);
  
  String getSalesOrganization();
  
  boolean setSSAData(SSAData paramSSAData, ToolBridgeImpl.ExceptionCallback paramExceptionCallback);
  
  SSAData getSSAData();
  
  SCASKAResult scaEnableECU(HardwareKey paramHardwareKey);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\ToolBridge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */