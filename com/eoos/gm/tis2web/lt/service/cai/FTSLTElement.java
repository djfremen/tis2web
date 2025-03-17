package com.eoos.gm.tis2web.lt.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;

public interface FTSLTElement {
  Integer getID();
  
  String getMajorOperationNumber();
  
  VCR getVCR();
  
  String getPaintingStage();
  
  String getSubject(LocaleInfo paramLocaleInfo);
  
  String getDisplay(LocaleInfo paramLocaleInfo);
  
  Integer getLabelID();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\service\cai\FTSLTElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */