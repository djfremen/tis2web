package com.eoos.gm.tis2web.vc.service.cai;

import java.util.List;

public interface VCConfiguration {
  Integer getConfigID();
  
  List getElements();
  
  VCValue getElement(VCDomain paramVCDomain);
  
  List getAssociations();
  
  List getConstraints();
  
  boolean match(VCValue paramVCValue);
  
  boolean matchAssociation(VCValue paramVCValue);
  
  boolean matchConstraint(VCRConstraint paramVCRConstraint);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCConfiguration.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */