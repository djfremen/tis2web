package com.eoos.gm.tis2web.sps.client.hardwarekey;

import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;

public interface IHardwareKey {
  String getHWK() throws SystemDriverNotInstalledException;
  
  String getEncodedHWK_16() throws SystemDriverNotInstalledException;
  
  String getEncodedHWK_32() throws SystemDriverNotInstalledException;
  
  boolean releaseLibraries();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\hardwarekey\IHardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */