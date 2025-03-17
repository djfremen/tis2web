package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.common.gtwo.export.DisplayableValue;
import java.io.Serializable;

public interface Module extends DisplayableValue, Serializable {
  String getID();
  
  boolean isPROM();
  
  String getCurrentCalibration();
  
  Part getOriginPart();
  
  Part getSelectedPart();
  
  void setSelectedPart(Part paramPart);
  
  boolean isSelectablePart(Part paramPart);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\Module.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */