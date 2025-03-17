package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface SIOElement extends SIO {
  public static final String DELIMITER = ",";
  
  boolean isQualified(LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
  
  List getSITIDs();
  
  List<String> getSits();
  
  String getNonMarketsConstraints();
  
  String getLiteratureNumber();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */