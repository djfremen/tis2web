package com.eoos.gm.tis2web.sas.client.hwk;

import com.eoos.gm.tis2web.frame.export.common.hwk.exception.UnavailableHardwareKeyException;
import com.eoos.gm.tis2web.sas.common.model.HardwareKey;

public interface IHardwareKeyProvider {
  HardwareKey getHardwareKey() throws UnavailableHardwareKeyException;
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\hwk\IHardwareKeyProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */