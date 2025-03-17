package com.eoos.gm.tis2web.vcr.service;

import com.eoos.gm.tis2web.frame.export.common.service.Service;
import com.eoos.gm.tis2web.vc.service.cai.VCConfiguration;
import com.eoos.gm.tis2web.vc.service.cai.VCValue;
import com.eoos.gm.tis2web.vcr.service.cai.IVehicleOptionExpression;
import com.eoos.gm.tis2web.vcr.service.cai.VCR;
import com.eoos.gm.tis2web.vcr.service.cai.VCRAttribute;
import com.eoos.gm.tis2web.vcr.service.cai.VCRExpression;
import com.eoos.gm.tis2web.vcr.service.cai.VCRTerm;
import com.eoos.gm.tis2web.vcr.v2.ILVCAdapter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface VCRService extends Service {
  VCRAttribute makeAttribute(VCValue paramVCValue);
  
  VCRAttribute makeAttribute(int paramInt1, int paramInt2);
  
  VCRTerm makeTerm();
  
  VCRTerm makeTerm(VCValue paramVCValue);
  
  VCRTerm makeTerm(VCRAttribute paramVCRAttribute);
  
  VCRExpression makeExpression();
  
  VCR makeVCR();
  
  VCR makeVCR(VCRExpression paramVCRExpression);
  
  VCR makeVCR(VCRTerm paramVCRTerm);
  
  VCR makeVCR(VCRAttribute paramVCRAttribute);
  
  VCR makeVCR(VCValue paramVCValue);
  
  VCR makeVCR(int paramInt);
  
  VCR makeVCR(String paramString);
  
  VCR makeVCR(VCConfiguration paramVCConfiguration);
  
  VCR makeVCR(VCConfiguration paramVCConfiguration, VCValue paramVCValue1, VCValue paramVCValue2);
  
  Map<Integer, VCR> getVCRs(Collection paramCollection);
  
  boolean isNullVCR(VCR paramVCR);
  
  IVehicleOptionExpression createVehicleOptionExpression();
  
  VCR createConstraintVCR();
  
  List checkOptionRestriction(List paramList, VCR paramVCR1, VCR paramVCR2, VCR paramVCR3);
  
  VCR createVCR(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Locale paramLocale, ILVCAdapter paramILVCAdapter);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\VCRService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */