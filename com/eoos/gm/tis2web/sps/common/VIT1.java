package com.eoos.gm.tis2web.sps.common;

import java.util.List;

public interface VIT1 extends VIT {
  List getControlModuleBlocks();
  
  void addControlModuleBlock(VIT paramVIT);
  
  void removeControlModuleBlock(Integer paramInteger);
  
  List collectAllAttributes();
  
  VIT getHeader();
  
  VIT getControlModuleBlock(Integer paramInteger);
  
  VIT getFreeOptions();
  
  List collectAttributes(Integer paramInteger);
  
  Object getVIN();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\VIT1.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */