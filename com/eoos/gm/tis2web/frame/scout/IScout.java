package com.eoos.gm.tis2web.frame.scout;

import com.eoos.gm.tis2web.frame.LoginInfo;
import java.util.Map;

public interface IScout {
  LoginInfo getLoginInfo(Map paramMap);
  
  String getScoutClassName();
  
  String getScoutId();
  
  boolean isPortalMapped();
  
  void setPortalMapped();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\frame\scout\IScout.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */