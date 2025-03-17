package com.eoos.gm.tis2web.vc.service.cai;

import java.util.List;

public interface VCRConstraint {
  void setConstraintID(int paramInt);
  
  Integer getConstraintID();
  
  void addElement(VCValue paramVCValue);
  
  List getElements();
  
  boolean match(VCRConstraint paramVCRConstraint);
  
  VCValue getElement(VCDomain paramVCDomain);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCRConstraint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */