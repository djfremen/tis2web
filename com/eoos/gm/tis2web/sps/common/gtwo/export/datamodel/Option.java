package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
import java.io.Serializable;

public interface Option extends DisplayableAttribute, DisplayableValue, Serializable {
  public static final String HIDDEN_OPTION_PREFIX = "$";
  
  String getID();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\Option.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */