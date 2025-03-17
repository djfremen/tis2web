package com.eoos.gm.tis2web.registration.standalone.xml;

import java.util.List;

public interface AUTHORIZATIONType {
  ORGANIZATIONType getORGANIZATION();
  
  void setORGANIZATION(ORGANIZATIONType paramORGANIZATIONType);
  
  List getDESCRIPTION();
  
  GROUPType getGROUP();
  
  void setGROUP(GROUPType paramGROUPType);
  
  String getAuthorizationID();
  
  void setAuthorizationID(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\xml\AUTHORIZATIONType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */