package com.eoos.gm.tis2web.sps.client.common;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface ClientSettings {
  public static final String DIR = "sps";
  
  public static final String FILE = "configuration.properties";
  
  public static final String DEFAULT_CONFIGURATION = "com/eoos/gm/tis2web/sps/client/settings/defaultConfiguration.properties";
  
  public static final String CHANGED_CONFIGURATION = "com/eoos/gm/tis2web/sps/client/settings/changedConfiguration.properties";
  
  public static final String LOG4J_TEMPLATE = "com/eoos/gm/tis2web/sps/client/settings/log4jTemplate.properties";
  
  public static final String SETTINGS_VERSION = "settings.version";
  
  public static final String LOG_PREFIX = "log.";
  
  public static final String CACHE_SIZE = "cacheSize";
  
  public static final String CACHE_PATH = "cachePath";
  
  public static final String LOG_DETAILED = "log.detailed";
  
  public static final String LOG_LOGGING = "log.logging";
  
  public static final String LOG_DEBUG_EXTENDED = "debug.extended";
  
  public static final String LOG_FILENAME = "log.fileName";
  
  public static final String LOG_MAXFILESIZE = "log.maxFileSize";
  
  public static final String ALLOW_J2534_OLD_VERSION = "allow.j2534.oldversion";
  
  public static final String TESTMODE = "debug.testmode";
  
  public static final String DEBUG_DTC = "debug.dtc";
  
  public static final String TESTMODE_DTC = "debug.testmode.dtc";
  
  public static final String TOOL_AUTORETRY_DELAY = "tool.autoretry.delay";
  
  public static final String WARRANTY_CODE_LIST = "warranty.code.list";
  
  public static final String VCI1001_ENABLED = "vci.1001.enabled";
  
  public static final String APP_PROPERTY_DTCUPLOAD = "dtcupload";
  
  public static final String APP_PROPERTY_ENABLED = "enabled";
  
  public static final String APP_PROPERTY_DISABLED = "disabled";
  
  public static final String FAILURE_MAIL_RECIPIENTS = "failure.mail.recipients";
  
  public static final String FAILURE_MAIL_FILE_FILTERS = "failure.mail.file.filters";
  
  String getProperty(String paramString);
  
  Properties getProperties(String paramString);
  
  void setProperty(String paramString1, String paramString2);
  
  void setProperties(Properties paramProperties);
  
  Set getKeys();
  
  void setVIN(String paramString);
  
  List getVINs();
  
  boolean setLogPath(String paramString);
  
  String getLogPath();
  
  boolean setSPSCache(String paramString);
  
  String getSPSCache();
  
  boolean clearSPSCache();
  
  boolean saveConfiguration();
  
  boolean toolsAreLocal();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\common\ClientSettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */