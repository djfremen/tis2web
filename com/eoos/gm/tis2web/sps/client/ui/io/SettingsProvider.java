package com.eoos.gm.tis2web.sps.client.ui.io;

import java.util.ArrayList;
import java.util.Vector;

public interface SettingsProvider {
  void setCacheSize(String paramString);
  
  String getCacheSize();
  
  void setVINs(ArrayList paramArrayList);
  
  Vector getVINs();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\io\SettingsProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */