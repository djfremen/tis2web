package com.eoos.gm.tis2web.sps.server.implementation.system.serverside.adapter;

public interface SchemaAdapterSPI extends SchemaAdapter {
  String getCalibrationVerificationNumber(String paramString1, String paramString2);
  
  void close();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\system\serverside\adapter\SchemaAdapterSPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */