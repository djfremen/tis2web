package com.eoos.gm.tis2web.sps.client.ui.io;

import java.util.ArrayList;
import java.util.Properties;

public interface IOConfiguration {
  ArrayList changeProperty(String paramString1, String paramString2);
  
  void setProperty(String paramString1, String paramString2);
  
  void save(ArrayList paramArrayList);
  
  Properties load();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\io\IOConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */