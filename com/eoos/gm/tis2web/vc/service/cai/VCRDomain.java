package com.eoos.gm.tis2web.vc.service.cai;

import java.util.Collection;
import java.util.List;

public interface VCRDomain extends VCDomain {
  VCRLabel getDomainLabel();
  
  boolean isLanguageDependent();
  
  int getMemberCount();
  
  Collection getValues();
  
  VCRValue getValue(int paramInt);
  
  VCRValue getValue(Integer paramInteger);
  
  VCRValue lookup(String paramString);
  
  VCRValue lookup(Integer paramInteger, String paramString);
  
  void add(VCRValue paramVCRValue);
  
  void remove(VCRValue paramVCRValue);
  
  void add(IVCRMapping paramIVCRMapping);
  
  void remove(IVCRMapping paramIVCRMapping);
  
  boolean associated(VCValue paramVCValue1, VCValue paramVCValue2);
  
  List getAssociations(VCValue paramVCValue);
  
  List getAssociations(VCValue paramVCValue, VCDomain paramVCDomain);
  
  VCValue map(VCValue paramVCValue, VCDomain paramVCDomain);
  
  List getMappings(VCDomain paramVCDomain);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCRDomain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */