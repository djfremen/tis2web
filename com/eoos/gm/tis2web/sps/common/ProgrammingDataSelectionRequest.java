package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.ProgrammingSequence;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.SelectionRequest;
import java.util.List;

public interface ProgrammingDataSelectionRequest extends SelectionRequest {
  List getModules();
  
  ProgrammingSequence getProgrammingSequence();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\ProgrammingDataSelectionRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */