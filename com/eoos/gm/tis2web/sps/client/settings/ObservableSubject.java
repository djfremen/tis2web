package com.eoos.gm.tis2web.sps.client.settings;

public interface ObservableSubject {
  void registerObserver(SettingsObserver paramSettingsObserver);
  
  boolean removeObserver(SettingsObserver paramSettingsObserver);
  
  void notifyObservers(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\settings\ObservableSubject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */