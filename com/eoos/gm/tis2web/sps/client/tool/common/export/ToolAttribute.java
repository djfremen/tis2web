package com.eoos.gm.tis2web.sps.client.tool.common.export;

import com.eoos.gm.tis2web.sps.common.gtwo.export.LocalAttribute;
import com.eoos.gm.tis2web.sps.service.cai.DisplayableAttribute;
import java.util.Locale;

public interface ToolAttribute extends DisplayableAttribute, LocalAttribute {
  String getKey();
  
  String getDenotation(Locale paramLocale);
  
  boolean equals(Object paramObject);
  
  int hashCode();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\common\export\ToolAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */