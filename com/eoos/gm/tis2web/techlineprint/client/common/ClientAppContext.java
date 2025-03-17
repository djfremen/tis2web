package com.eoos.gm.tis2web.techlineprint.client.common;

import java.util.List;
import java.util.Locale;

public interface ClientAppContext {
  public static final String DRIVER_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
  
  void init() throws Exception;
  
  String getHomeDir();
  
  String getClientVersion();
  
  Locale getLocale();
  
  String getSessionID();
  
  String getNativeLan();
  
  List<String> getSupportedDevices();
  
  String getTech2WinComPort();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\techlineprint\client\common\ClientAppContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */