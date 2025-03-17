package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import java.util.List;

public interface SIOWD extends SIO {
  String getSubject(LocaleInfo paramLocaleInfo);
  
  SIOBlob getWiringDiagram(LocaleInfo paramLocaleInfo);
  
  List getElectronicSystemLinks();
  
  List getRelatedCheckingProcedures();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOWD.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */