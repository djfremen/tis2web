package com.eoos.softwarekey.common;

public interface SoftwareKey {
  void initialize(SoftwareKeyCallback paramSoftwareKeyCallback);
  
  String getHWID();
  
  String getHWInfo();
  
  boolean isValidHWID(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\softwarekey\common\SoftwareKey.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */