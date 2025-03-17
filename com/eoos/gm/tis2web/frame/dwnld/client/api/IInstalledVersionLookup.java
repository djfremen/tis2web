package com.eoos.gm.tis2web.frame.dwnld.client.api;

import com.eoos.datatype.IVersionNumber;

public interface IInstalledVersionLookup {
  IVersionNumber getInstalledVersion(IRegistryLookup paramIRegistryLookup);
  
  public static interface IRegistryLookup {
    RegistryValue getRegistryValue(String param1String1, String param1String2);
    
    public static interface RegistryValue {
      Object getData();
      
      String getType();
    }
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\dwnld\client\api\IInstalledVersionLookup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */