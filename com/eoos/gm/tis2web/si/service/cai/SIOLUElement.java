package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface SIOLUElement {
  public static final String NEW_TECHNICAL_FEATURE = "doctype=NTF";
  
  public static final String ALL_MODELS_INDICATOR = "models=all";
  
  SIOBlob getDocument(LocaleInfo paramLocaleInfo);
  
  String getSubject(LocaleInfo paramLocaleInfo);
  
  List getRelatedLUs();
  
  boolean isQualified(LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOLUElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */