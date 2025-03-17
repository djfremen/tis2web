package com.eoos.gm.tis2web.si.service.cai;

import com.eoos.gm.tis2web.ctoc.service.cai.SITOCElement;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;

public interface SIO extends SITOCElement {
  boolean isQualified(LocaleInfo paramLocaleInfo, String paramString, VCR paramVCR);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\si\service\cai\SIO.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */