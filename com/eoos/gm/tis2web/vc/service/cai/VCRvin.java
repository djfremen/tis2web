package com.eoos.gm.tis2web.vc.service.cai;

public interface VCRvin {
  Integer getStructureID();
  
  String getVINElement();
  
  VCValue getValue();
  
  String getKey();
  
  boolean equals(VCRvin paramVCRvin);
  
  boolean match(String paramString, int paramInt);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VCRvin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */