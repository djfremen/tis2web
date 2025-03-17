package com.eoos.gm.tis2web.vcr.service.cai;

import java.util.List;

public interface VCRTerm {
  List getAttributes();
  
  void add(VCRAttribute paramVCRAttribute);
  
  boolean match(VCRAttribute paramVCRAttribute);
  
  int getDomainID();
  
  String toString();
  
  VCRTerm intersect(VCRTerm paramVCRTerm);
  
  boolean identical(VCRTerm paramVCRTerm);
  
  VCRTerm copy();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\cai\VCRTerm.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */