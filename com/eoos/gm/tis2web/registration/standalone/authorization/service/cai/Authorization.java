package com.eoos.gm.tis2web.registration.standalone.authorization.service.cai;

import com.eoos.gm.tis2web.registration.service.cai.Subscription;

public interface Authorization extends Subscription {
  String getAuthorizationID();
  
  String getEmail();
  
  String getForm();
  
  String getGroup();
  
  String getOrganization();
  
  String getUrl();
  
  void addDescription(String paramString1, String paramString2);
  
  void setEmail(String paramString);
  
  void setForm(String paramString);
  
  void setGroup(String paramString);
  
  void setOrganization(String paramString);
  
  void setUrl(String paramString);
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\standalone\authorization\service\cai\Authorization.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */