package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;

public interface DeviceSelectionRequest extends SelectionRequest {
  Object getController();
  
  String getType();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\DeviceSelectionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */