package com.eoos.gm.tis2web.sps.client.tool.navigation;

import com.eoos.gm.tis2web.sps.client.tool.navigation.impl.RIMParams;

public interface VCSNavigator {
  boolean createNavigator();
  
  void discardNavigator();
  
  Integer getMethodGroupID(Object paramObject) throws Exception;
  
  RIMParams getRIMParams();
  
  String getMethodGroupProviderID();
}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\gm\tis2web\sps\client\tool\navigation\VCSNavigator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */