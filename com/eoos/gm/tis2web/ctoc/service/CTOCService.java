package com.eoos.gm.tis2web.ctoc.service;

import com.eoos.gm.tis2web.ctoc.service.cai.CTOC;
import com.eoos.gm.tis2web.ctoc.service.cai.CTOCElement;
import com.eoos.gm.tis2web.ctoc.service.cai.CTOCSurrogate;
import com.eoos.gm.tis2web.ctoc.service.cai.IOFactory;
import com.eoos.gm.tis2web.frame.export.common.locale.LocaleInfo;
import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.frame.export.common.util.IDatabaseLink;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import java.util.Set;

public interface CTOCService extends Service {
  public static final String TECHNICAL_SERVICE_BULLETINS = "SIT-12";
  
  public static final String SCDS = "SCDS";
  
  public static final String TSB = "TSB";
  
  public static final String CPR = "CPR";
  
  public static final String WD = "WD";
  
  public static final String LT = "LT";
  
  public static final String SDL = "Software Download";
  
  public static final String FB = "Feedback";
  
  public static final String NEWS = "News";
  
  public static final String HELP = "Help";
  
  public static final int APPLICATION = 1;
  
  public static final int SIT = 2;
  
  public static final int MANUFACTURER = 3;
  
  public static final int GROUP = 4;
  
  public static final int COUNTRY = 5;
  
  public static final int LOCALE = 9;
  
  public static final String NULL_SIT = "SIT-0";
  
  CTOC getCTOC();
  
  CTOCSurrogate createCTOCSurrogate();
  
  CTOCSurrogate createCTOCSurrogate(CTOCElement paramCTOCElement);
  
  VCR makeConstraintVCR(LocaleInfo paramLocaleInfo, String paramString1, Set paramSet1, Set paramSet2, Set paramSet3, String paramString2);
  
  VCR extendConstraintVCR(VCR paramVCR, int paramInt, String paramString);
  
  IOFactory createIOFactory(IDatabaseLink paramIDatabaseLink);
  
  void reset();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\ctoc\service\CTOCService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */