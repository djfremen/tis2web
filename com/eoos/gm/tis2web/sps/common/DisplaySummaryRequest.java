package com.eoos.gm.tis2web.sps.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Summary;
import com.eoos.gm.tis2web.sps.common.gtwo.export.request.DisplayRequest;
import java.util.List;
import javax.swing.table.TableModel;

public interface DisplaySummaryRequest extends DisplayRequest {
  TableModel getDescriptionData();
  
  TableModel getModuleData();
  
  List getModuleDataList();
  
  List getModuleDataListGME();
  
  List getDescriptionDataListGME();
  
  List getControllers();
  
  TableModel getVehicleData();
  
  Summary getSummary();
  
  List getSequenceSummary();
  
  boolean isGMEMoreSequence();
  
  void setControllerLabel(String paramString);
  
  String getControllerLabel();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\DisplaySummaryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */