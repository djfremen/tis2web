package com.eoos.gm.tis2web.frame.export.common.datatype;

public interface ApplicationConnector {
  Object getDefaultTarget();
  
  void onStartup(Object paramObject);
  
  void onShutdown();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\datatype\ApplicationConnector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */