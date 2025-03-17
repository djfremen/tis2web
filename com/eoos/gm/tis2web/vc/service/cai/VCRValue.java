package com.eoos.gm.tis2web.vc.service.cai;

public interface VCRValue extends VCValue {
  Integer getDomainID();
  
  Integer getValueID();
  
  VCRLabel getLabel();
  
  void setLabel(VCRLabel paramVCRLabel);
  
  String toString();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCRValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */