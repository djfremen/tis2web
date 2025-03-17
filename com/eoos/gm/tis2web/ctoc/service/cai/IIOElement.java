package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.si.service.cai.SIO;
import com.eoos.gm.tis2web.si.service.cai.SIOBlob;
import com.eoos.gm.tis2web.si.service.cai.SIOProperty;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;

public interface IIOElement extends SIO {
  public static final String DELIMITER = ",";
  
  SIOBlob getDocument(LocaleInfo paramLocaleInfo);
  
  void setVCR(VCR paramVCR);
  
  void setOrder(int paramInt);
  
  void add(SIOProperty paramSIOProperty, Object paramObject);
  
  SIOBlob getGraphic(String paramString);
  
  SIO getReferencedSIO(int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\IIOElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */