package com.eoos.hardwarekey.common;

import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;

public interface HardwareKey {
  String getHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
  
  boolean migrateHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
  
  boolean isMigratedHardwareKey() throws SystemDriverNotInstalledException, UnavailableHardwareKeyException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\hardwarekey\common\HardwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */