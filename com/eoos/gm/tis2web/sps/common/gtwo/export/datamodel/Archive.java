package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import java.io.Serializable;
import java.util.List;

public interface Archive extends Serializable {
  String getPartNo();
  
  String getChangeReason();
  
  String getComment();
  
  List getCalibrationUnits();
  
  List getDescriptions();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\Archive.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */