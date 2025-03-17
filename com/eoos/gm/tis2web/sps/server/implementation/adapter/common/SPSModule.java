package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Module;

public interface SPSModule extends Module {
  String getDescription();
  
  byte[] getCalibrationFile(SPSPart paramSPSPart, SPSSchemaAdapter paramSPSSchemaAdapter, boolean paramBoolean) throws Exception;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSModule.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */