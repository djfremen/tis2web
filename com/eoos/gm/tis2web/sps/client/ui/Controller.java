package com.eoos.gm.tis2web.sps.client.ui;

import com.eoos.gm.tis2web.sps.service.cai.Attribute;
import com.eoos.gm.tis2web.sps.service.cai.Value;

public interface Controller {
  void handleSelection(Attribute paramAttribute, Value paramValue);
  
  void handleProgrammingSelection();
  
  void handleFinishDownload();
  
  boolean getInitialNextButtonState();
  
  void setNextButtonState(boolean paramBoolean);
  
  void setVINOnBarStatus(String paramString);
  
  void setInfoOnBarStatus(String paramString);
  
  void requestBulletinDisplay(String paramString);
  
  String requestInstructionHTML(String paramString) throws Exception;
  
  byte[] requestInstructionImage(String paramString) throws Exception;
  
  boolean isNAO();
  
  boolean isGlobalAdapter();
  
  boolean supportSPSFunctions();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\clien\\ui\Controller.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */