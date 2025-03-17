package com.eoos.gm.tis2web.sps.common.gtwo.export.request;

import java.util.List;

public interface ReprogramSequenceRequest extends ReprogramRequest {
  List getProgrammingDataList();
  
  List getType4Data();
  
  List getControllers();
  
  List getInstructions();
  
  List getFailureHandling();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\request\ReprogramSequenceRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */