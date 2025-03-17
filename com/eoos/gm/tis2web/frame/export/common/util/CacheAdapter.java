package com.eoos.gm.tis2web.frame.export.common.util;

import com.eoos.gm.tis2web.frame.export.common.ClientContext;
import java.util.Locale;

public interface CacheAdapter {
  String getCacheDescription(Locale paramLocale);
  
  void clear(ClientContext paramClientContext) throws Exception;
  
  public static interface Size {
    int getSize();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\export\commo\\util\CacheAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */