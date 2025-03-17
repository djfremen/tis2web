package com.eoos.gm.tis2web.snapshot.client.common;

import java.util.Locale;

public interface ClientAppContext {
  public static final String INST_STANDALONE = "standalone";
  
  public static final String INST_LOCAL_SERVER = "local";
  
  public static final String INST_CENTRAL_SERVER = "central";
  
  void init() throws Exception;
  
  String getHomeDir();
  
  String getClientVersion();
  
  Locale getLocale();
  
  String getSessionID();
  
  String getBACCode();
  
  String getNativeLanguage();
  
  boolean mailEnabled();
  
  String getInstallationType();
  
  String getCountryCode();
  
  boolean dtcUploadEnabled();
  
  String getTech2WinComPort();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\snapshot\client\common\ClientAppContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */