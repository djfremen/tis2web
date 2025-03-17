package com.eoos.gm.tis2web.ctoc.service.cai;

import java.util.List;
import java.util.Map;

public interface CTOCElement extends CTOCNode {
  Integer getLabelID();
  
  Map getProperties();
  
  void setParent(CTOCElement paramCTOCElement);
  
  void add(CTOCProperty paramCTOCProperty, Object paramObject);
  
  void setProperties(Map paramMap);
  
  void add(SITOCElement paramSITOCElement);
  
  void shareChildren(List paramList);
  
  void swap(CTOCElement paramCTOCElement1, CTOCElement paramCTOCElement2);
  
  void release();
  
  void ensureExistingChildren();
  
  void setVCRSIT(int paramInt);
  
  int getVCRSIT();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */