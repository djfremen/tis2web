package com.eoos.gm.tis2web.sps.client.settings;

import java.util.Properties;

public interface PersistentClientSettings {
  Properties getSettings();
  
  void loadSettings();
  
  boolean createSettingsDir();
  
  boolean saveSettings(Properties paramProperties);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\PersistentClientSettings.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */