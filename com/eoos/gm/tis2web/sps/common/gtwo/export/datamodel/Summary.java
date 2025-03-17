package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import java.io.Serializable;
import java.util.List;

public interface Summary extends Serializable {
  String getControllerName();
  
  History getCurrentSoftware();
  
  History getSelectedSoftware();
  
  List getHistory();
  
  List getModules();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\Summary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */