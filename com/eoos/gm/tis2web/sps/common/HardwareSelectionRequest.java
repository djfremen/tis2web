package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;

public interface HardwareSelectionRequest extends SelectionRequest {
  String getHardwareDescription();
  
  String getControllerID();
  
  String getLabel();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\HardwareSelectionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */