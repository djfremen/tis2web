package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;

public interface SILabel {
  String get(LocaleInfo paramLocaleInfo);
  
  void add(LocaleInfo paramLocaleInfo, String paramString);
  
  boolean isSupportedLocale(LocaleInfo paramLocaleInfo);
  
  boolean match(SILabel paramSILabel);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SILabel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */