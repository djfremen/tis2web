package com.eoos.gm.tis2web.sps.common.gtwo.export.datamodel;

import com.eoos.gm.tis2web.sps.service.cai.Value;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public interface Part extends Value, Serializable {
  int getID();
  
  List getCOP();
  
  List getBulletins();
  
  String getCVN();
  
  String getPartNumber();
  
  String getDescription(Locale paramLocale);
  
  String getShortDescription(Locale paramLocale);
  
  List getCalibrationParts();
  
  String getCalibrationVerificationNumber(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\common\gtwo\export\datamodel\Part.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */