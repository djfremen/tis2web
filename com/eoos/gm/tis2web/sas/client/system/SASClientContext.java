package com.eoos.gm.tis2web.sas.client.system;

import com.eoos.context.IContext;
import com.eoos.gm.tis2web.client.util.DeviceSettings;
import java.util.Locale;

public interface SASClientContext extends IContext {
  Locale getLocale();
  
  String getSessionID();
  
  String getLabel(String paramString);
  
  String getMessage(String paramString);
  
  boolean isStandaloneInstallation();
  
  void setDeviceSettings(DeviceSettings paramDeviceSettings);
  
  DeviceSettings getDeviceSettings();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sas\client\system\SASClientContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */