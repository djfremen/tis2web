package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface CTOCSurrogate extends CTOCNode {
  List getChildren(List paramList, LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR, int paramInt);
  
  List getChildren(CTOCNode paramCTOCNode, LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
  
  void setParent(CTOCElement paramCTOCElement);
  
  void add(SITOCElement paramSITOCElement);
  
  void add(List<SITOCElement> paramList);
  
  void append(SITOCElement paramSITOCElement);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCSurrogate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */