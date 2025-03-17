package com.eoos.gm.tis2web.sps.client.common;

import com.eoos.gm.tis2web.frame.export.common.hwk.exception.SystemDriverNotInstalledException;
import com.eoos.gm.tis2web.sps.client.settings.SettingsObserver;
import com.eoos.gm.tis2web.sps.client.tool.common.export.Tool;
import com.eoos.gm.tis2web.sps.common.gtwo.export.NavigationTableFilter;
import java.util.List;
import java.util.Locale;

public interface ClientAppContext {
  void init() throws Exception;
  
  ClientSettings getClientSettings();
  
  String getHomeDir();
  
  String getHardwareKey() throws SystemDriverNotInstalledException;
  
  String getHardwareKey32() throws SystemDriverNotInstalledException;
  
  String getRepairShopCode();
  
  String getClientVersion();
  
  String getDefaultMake();
  
  List getToolNames();
  
  List getTools();
  
  Tool getTool(String paramString);
  
  void filterTools(List paramList);
  
  Locale getLocale();
  
  String getSessionID();
  
  String getBACCode();
  
  String getJ2534DriverUpdateInfo();
  
  void updateJ2534Tools();
  
  List getSupportedBrands();
  
  List getSupportedBrandKeys();
  
  void registerSettingsObserver(SettingsObserver paramSettingsObserver);
  
  boolean removeSettingsObserver(SettingsObserver paramSettingsObserver);
  
  boolean testMode();
  
  boolean DTCDebugMode();
  
  boolean DTCTestMode();
  
  Integer getToolAutoRetryDelay();
  
  String getNavTableValidationMap();
  
  boolean isLocalServerInstallation();
  
  String getCountryCode();
  
  String getLCID();
  
  NavigationTableFilter getNavigationTableFilter();
  
  boolean DTCUploadEnabled();
  
  Integer getWarrantyCodeList();
  
  boolean isVCI1001Enabled();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\ClientAppContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */