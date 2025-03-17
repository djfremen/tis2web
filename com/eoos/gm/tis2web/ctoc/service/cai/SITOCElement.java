package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface SITOCElement {
  Integer getID();
  
  SITOCType getType();
  
  boolean isSIO();
  
  String getLabel(LocaleInfo paramLocaleInfo);
  
  int getOrder();
  
  List getChildren();
  
  List getChildren(List paramList, LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
  
  VCR getVCR();
  
  boolean hasProperty(SITOCProperty paramSITOCProperty);
  
  Object getProperty(SITOCProperty paramSITOCProperty);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\SITOCElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */