package com.eoos.gm.tis2web.vc.v2.service;

import com.eoos.gm.tis2web.vc.v2.base.configuration.IConfiguration;
import com.eoos.gm.tis2web.vc.v2.vin.VIN;

public interface VehicleCfgStorage {
  IConfiguration getCfg();
  
  VIN getVIN();
  
  void storeCfg(IConfiguration paramIConfiguration, VIN paramVIN);
  
  public static interface Observer {
    void onVehicleConfigurationChange();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\v2\service\VehicleCfgStorage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */