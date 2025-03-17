package com.eoos.gm.tis2web.lt.service.cai;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;

public interface SIOLT extends CTOCNode {
  String getDisplay(LocaleInfo paramLocaleInfo);
  
  String getMajorOperationNumber();
  
  String getSubject(LocaleInfo paramLocaleInfo);
  
  boolean getChangeFlag();
  
  String getPaintingStage();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\lt\service\cai\SIOLT.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */