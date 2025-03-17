package com.eoos.gm.tis2web.rpo.api;

import java.util.Locale;

public interface RPO extends Comparable {
  String getCode();
  
  String getDescription(Locale paramLocale);
  
  RPOFamily getFamily();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\rpo\api\RPO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */