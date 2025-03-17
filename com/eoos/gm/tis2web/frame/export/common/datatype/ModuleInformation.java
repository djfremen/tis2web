package com.eoos.gm.tis2web.frame.export.common.datatype;

import com.eoos.datatype.VersionNumber;
import java.util.Locale;

public interface ModuleInformation {
  String getDescription(Locale paramLocale);
  
  VersionNumber getVersion();
  
  Object getDatabaseVersionInformation();
  
  public static interface ClientInfoRetrieval {
    ModuleInformation getClientModuleInformation();
  }
  
  public static interface Retrieval {
    ModuleInformation getModuleInformation();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\ModuleInformation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */