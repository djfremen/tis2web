package com.eoos.gm.tis2web.vc.service.cai;

import java.util.ArrayList;
import java.util.List;

public interface VINStructure {
  Integer getStructureID();
  
  VCValue getMake();
  
  VCValue getModelYear();
  
  VCValue getWMI();
  
  String getFilterVIN();
  
  String getKey();
  
  String getFilterKey();
  
  List getElements();
  
  VINStructureElement getElement(VCDomain paramVCDomain);
  
  void setStructureID(Integer paramInteger);
  
  boolean match(VCValue paramVCValue1, VCValue paramVCValue2, VCValue paramVCValue3);
  
  boolean match(VCValue paramVCValue1, VCValue paramVCValue2, String paramString);
  
  void add(VCDomain paramVCDomain, int paramInt1, int paramInt2);
  
  void update(VCDomain paramVCDomain, int paramInt1, int paramInt2);
  
  boolean add(VCRvin paramVCRvin);
  
  ArrayList getPatterns();
  
  VCValue match(String paramString, VCDomain paramVCDomain);
  
  public static interface VINStructureElement {
    VCDomain getDomain();
    
    int getFromPosition();
    
    int getToPosition();
    
    List getPatterns();
    
    void add(VCRvin param1VCRvin);
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\vc\service\cai\VINStructure.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */