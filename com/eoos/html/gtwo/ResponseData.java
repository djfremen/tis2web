package com.eoos.html.gtwo;

import com.eoos.datatype.gtwo.MIMEData;

public interface ResponseData extends MIMEData {
  public static final int CACHE_TYPE_NONE = 0;
  
  public static final int CACHE_TYPE_PUBLIC = 1;
  
  public static final int CACHE_TYPE_PRIVATE = 2;
  
  public static interface MetaData extends MIMEData.MetaData {
    int getCacheType();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\html\gtwo\ResponseData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */