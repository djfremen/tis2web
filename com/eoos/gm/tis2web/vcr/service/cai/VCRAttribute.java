package com.eoos.gm.tis2web.vcr.service.cai;

public interface VCRAttribute {
  int getDomainID();
  
  int getValueID();
  
  boolean match(VCRAttribute paramVCRAttribute);
  
  String toString();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\cai\VCRAttribute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */