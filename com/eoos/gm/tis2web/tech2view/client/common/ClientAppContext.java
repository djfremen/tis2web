package com.eoos.gm.tis2web.tech2view.client.common;

import java.util.Locale;

public interface ClientAppContext {
  public static final String DRIVER_PROPERTY_TECH2WIN_COM_PORT = "tech2win_com_port";
  
  void init() throws Exception;
  
  String getHomeDir();
  
  String getClientVersion();
  
  Locale getLocale();
  
  String getSessionID();
  
  String getNativeLan();
  
  String getTech2WinComPort();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\tech2view\client\common\ClientAppContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */