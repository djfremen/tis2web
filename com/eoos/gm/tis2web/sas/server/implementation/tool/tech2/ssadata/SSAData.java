package com.eoos.gm.tis2web.sas.server.implementation.tool.tech2.ssadata;

import com.eoos.gm.tis2web.sas.common.model.HardwareKey;
import com.eoos.gm.tis2web.sas.common.model.VIN;

public interface SSAData {
  void setIDByte(Byte paramByte);
  
  Byte getIDByte();
  
  void setStatus(Byte paramByte);
  
  Byte getStatus();
  
  void setHardwareKey(HardwareKey paramHardwareKey);
  
  HardwareKey getHardwareKey();
  
  void setVersion(Integer paramInteger);
  
  Integer getVersion();
  
  void setFreeShots(Integer paramInteger);
  
  Integer getFreeShots();
  
  void setHardwareGroupID(Integer paramInteger);
  
  Integer getHardwareGroupID();
  
  void setVINTuples(VINTuple[] paramArrayOfVINTuple);
  
  VINTuple[] getVINTuples();
  
  void setSeedTuples(SeedTuple[] paramArrayOfSeedTuple);
  
  SeedTuple[] getSeedTuples();
  
  public static interface SeedTuple {
    void setSeedStatus(Integer param1Integer);
    
    Integer getSeedStatus();
    
    void setAlgorithm(Integer param1Integer);
    
    Integer getAlgorithm();
    
    void setSeed(Integer param1Integer);
    
    Integer getSeed();
    
    void setKey(Integer param1Integer);
    
    Integer getKey();
  }
  
  public static interface VINTuple {
    void setVIN(VIN param1VIN);
    
    VIN getVIN();
    
    void setImmobilizerSecurityCode(String param1String);
    
    String getImmobilizerSecurityCode();
    
    void setInfotainmentSecurityCode(String param1String);
    
    String getInfotainmentSecurityCode();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\server\implementation\tool\tech2\ssadata\SSAData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */