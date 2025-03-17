package com.eoos.gm.tis2web.ctoc.service.cai;

import com.eoos.gm.tis2web.vcr.service.cai.VCR;

public interface CTOCFactory {
  SITOCElement make(CTOCType paramCTOCType, int paramInt1, int paramInt2, long paramLong, VCR paramVCR);
  
  void register(CTOC paramCTOC);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\cai\CTOCFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */