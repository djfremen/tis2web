package com.eoos.gm.tis2web.sps.server.implementation.adapter.common;

import com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel.Controller;
import java.util.List;

public interface SPSController extends Controller {
  void setHardware(SPSPart paramSPSPart);
  
  List getHardware();
  
  void setControllerData(Object paramObject);
  
  int getID();
  
  String getDescription();
  
  String getLabel();
  
  int getDeviceID();
  
  int getRequestMethodID();
  
  List getProgrammingTypes();
  
  List getPreProgrammingInstructions();
  
  List getPostProgrammingInstructions();
  
  List getPreSelectionOptions() throws Exception;
  
  List getPostSelectionOptions() throws Exception;
  
  SPSProgrammingData getProgrammingData(SPSSchemaAdapter paramSPSSchemaAdapter) throws Exception;
  
  void update(SPSSchemaAdapter paramSPSSchemaAdapter) throws Exception;
  
  Object getControllerData();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\server\implementation\adapter\common\SPSController.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */