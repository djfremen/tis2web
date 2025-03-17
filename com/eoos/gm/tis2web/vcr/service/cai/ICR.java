package com.eoos.gm.tis2web.vcr.service.cai;

import com.eoos.gm.tis2web.vc.service.cai.VCValue;

public interface ICR {
  boolean match(VCValue paramVCValue);
  
  boolean equals(Object paramObject);
  
  int hashCode();
  
  String toString();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vcr\service\cai\ICR.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */