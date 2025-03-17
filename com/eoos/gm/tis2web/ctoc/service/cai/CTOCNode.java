package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.List;

public interface CTOCNode extends SITOCElement {
  CTOCNode getParent();
  
  List filterSITs(CTOCNode paramCTOCNode, LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
  
  boolean isQualified(LocaleInfo paramLocaleInfo, VCR paramVCR);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */