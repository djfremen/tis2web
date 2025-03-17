package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
import java.io.Serializable;
import java.util.List;

public interface ControllerReference extends DisplayableValue, Serializable {
  List getProgrammingMethods();
  
  List getHistory();
  
  boolean isType4Application();
  
  List getProgrammingSequences();
  
  List getProgrammingFunctions();
  
  List getProgrammingMethods(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\ControllerReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */