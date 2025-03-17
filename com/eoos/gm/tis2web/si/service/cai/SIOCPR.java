package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOCNode;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;

public interface SIOCPR extends SIO {
  String getSubject(LocaleInfo paramLocaleInfo);
  
  SIOBlob getDocument(LocaleInfo paramLocaleInfo);
  
  Integer getElectronicSystemCode();
  
  String getElectronicSystemLabel(LocaleInfo paramLocaleInfo);
  
  CTOCNode getWiringDiagrams();
  
  ILVCAdapter getILVCAdapter();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIOCPR.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */