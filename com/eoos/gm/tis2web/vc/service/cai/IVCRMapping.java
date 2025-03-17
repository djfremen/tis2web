package com.eoos.gm.tis2web.vc.service.cai;

public interface IVCRMapping {
  public static final int PROJECTION = 0;
  
  public static final int MAPPING = 1;
  
  int getType();
  
  VCRValue getValue();
  
  VCRValue getMappedValue();
  
  String getID();
  
  String getKey();
  
  boolean match(IVCRMapping paramIVCRMapping);
  
  boolean equals(Object paramObject);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\IVCRMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */