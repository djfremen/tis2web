package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;

import java.util.Collection;

public interface SchemaAdapterAPI extends SchemaAdapter {
  Collection getCalibrationVerificationNumber(String paramString1, String paramString2);
  
  public static interface CVNResult {
    String getCalibrationVerificationNumber();
    
    String getSchemaAdapterDisplayableID();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\SchemaAdapterAPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */