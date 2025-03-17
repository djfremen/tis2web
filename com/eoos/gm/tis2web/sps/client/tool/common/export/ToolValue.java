package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;

public interface ToolValue extends DisplayableValue {
  String getKey();
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */