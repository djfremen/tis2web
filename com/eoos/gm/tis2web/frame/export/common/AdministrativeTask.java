package com.eoos.gm.tis2web.frame.export.common;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public interface AdministrativeTask extends Serializable {
  boolean isAvailable(ClientContext paramClientContext);
  
  String getDenotation(Locale paramLocale);
  
  void execute(ClientContext paramClientContext) throws Exception;
  
  String getErrorMessage(Exception paramException, Locale paramLocale);
  
  String getSuccessMessage(Locale paramLocale);
  
  public static interface Container {
    List getTasks(ClientContext param1ClientContext);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\common\AdministrativeTask.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */