package com.eoos.gm.tis2web.vc.service.cai;

public interface VCRConfiguration extends VCConfiguration {
  String getKey();
  
  void addElement(VCRValue paramVCRValue);
  
  void addAssociation(VCRValue paramVCRValue);
  
  void addConstraint(VCRConstraint paramVCRConstraint);
  
  boolean match(VCRConfiguration paramVCRConfiguration);
  
  void setConfigID(int paramInt);
  
  String toString();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCRConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */