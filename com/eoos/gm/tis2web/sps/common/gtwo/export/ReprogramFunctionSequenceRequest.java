package com.eoos.gm.tis2web.sps.common.gtwo.export;

import com.eoos.gm.tis2web.sps.common.gtwo.export.request.ReprogramRequest;
import java.util.List;

public interface ReprogramFunctionSequenceRequest extends ReprogramRequest {
  List getFunctionLabels();
  
  List getProgrammingDataList();
  
  List getPreProgrammingInstructions();
  
  List getPostProgrammingInstructions();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\ReprogramFunctionSequenceRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */