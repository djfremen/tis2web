package com.eoos.propcfg;

import java.util.Set;

public interface Configuration {
  String getProperty(String paramString);
  
  Set getKeys();
  
  String getFullKey(String paramString);
  
  public static interface Modifyable {
    void setProperty(String param1String1, String param1String2);
    
    void remove(String param1String);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\propcfg\Configuration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */