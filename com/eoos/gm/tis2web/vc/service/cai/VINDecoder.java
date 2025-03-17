package com.eoos.gm.tis2web.vc.service.cai;

import com.eoos.propcfg.Configuration;
import java.util.Collection;
import java.util.List;

public interface VINDecoder {
  public static final char VIN_WILDCARD = '*';
  
  public static final int VIN_WMI_POSITION = 1;
  
  public static final int VIN_MODELYEAR_POSITION = 10;
  
  public static final int MAX_FRAGMENT = 10;
  
  List decode(VCMake paramVCMake, String paramString) throws Exception;
  
  List decode(String paramString) throws Exception;
  
  List getValidSalesmakesForVIN(String paramString);
  
  VINStructure lookupStructure(Integer paramInteger);
  
  VINStructure lookupStructure(VCValue paramVCValue1, VCValue paramVCValue2, VCValue paramVCValue3, String paramString);
  
  VINStructure getStructure(Integer paramInteger, VCValue paramVCValue1, VCValue paramVCValue2, VCValue paramVCValue3, String paramString);
  
  Collection getStructures();
  
  void add(VINStructure paramVINStructure);
  
  void setConfiguration(Configuration paramConfiguration);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VINDecoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */