package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.io.Serializable;

public interface COP extends Value, Serializable {
  public static final int UNKNOWN = 0;
  
  public static final int SELECTABLE = 1;
  
  public static final int NOT_SELECTABLE = 2;
  
  public static final int SELECTED = 3;
  
  Part getPart();
  
  int getMode();
  
  void setMode(int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\COP.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */