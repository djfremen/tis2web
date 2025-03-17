package com.eoos.gm.tis2web.si.implementation.io.db;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface FTSSIElement {
  List getSITIDs();
  
  String getLabel(LocaleInfo paramLocaleInfo);
  
  String getLiteratureNumber();
  
  String getNonMarketsConstraints();
  
  VCR getVCR();
  
  int getOrder();
  
  Integer getID();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\implementation\io\db\FTSSIElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */