package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Option;

public interface SPSOption extends Option {
  String getDescription();
  
  SPSOption getType();
  
  String getHelp(SPSVehicle paramSPSVehicle);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSOption.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */