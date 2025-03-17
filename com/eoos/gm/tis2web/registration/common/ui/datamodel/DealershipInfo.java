package com.eoos.gm.tis2web.registration.common.ui.datamodel;

import java.util.List;
import java.util.Locale;

public interface DealershipInfo {
  String getDealershipID();
  
  String getDealership();
  
  String getStreet();
  
  String getZIP();
  
  String getState();
  
  String getCity();
  
  Locale getCountry();
  
  Locale getLanguage();
  
  String getPhone();
  
  String getFax();
  
  String getEmail();
  
  List getContacts();
  
  public static interface Contact {
    String getName();
    
    Locale getLanguage();
  }
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\registration\commo\\ui\datamodel\DealershipInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */